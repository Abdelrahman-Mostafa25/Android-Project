package com.example.project_sci.ui.gpa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.project_sci.R;

import java.util.regex.Pattern;


public class GPAFragment extends Fragment {


    int totalHours=0,tmpHrs=0;
    float totalPoints=0,curpoints=0;


    Float resGpa=new Float(0);
    String gr,points="";
    Pattern grades = Pattern.compile("(A-|A|B|B\\+|B-|C|C\\+|C-|D\\+|D|a|b|c|d|a-|b-|c-|b\\+|c\\+|d\\+|f|F)");
    Pattern fraction = Pattern.compile("\\d+(\\.\\d+)?");

    EditText grade,hours;
    TextView result;
    Button ad,calc,back;
    public void GPAFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_g_p_a, container, false);

        ad= root.findViewById(R.id.add);
        calc= root.findViewById(R.id.calc);
        back= root.findViewById(R.id.back);

        grade=root.findViewById(R.id.grade);
        hours=root.findViewById(R.id.hours);


        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(view);
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTotal(view);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(view);
            }
        });

        return root;
    }

    public boolean checkValidInput(@NonNull String g , @NonNull String h)
    {
        if( (grades.matcher(g).matches() || fraction.matcher(g).matches()) && !h.isEmpty())
        {
            tmpHrs=Integer.parseInt(hours.getText().toString());
            gr=g;
            return true;
        }
        return false;
    }

    public void calcTotalPoints()
    {
        if(totalHours==0)
            result.setText("");

        totalHours+=tmpHrs;
        grade.setText("");
        hours.setText("");

        if(grades.matcher(gr).matches())
        {
            switch (gr.toUpperCase()) {
                case "A":
                    curpoints=tmpHrs * 4;
                    break;
                case "A-":
                    curpoints= (float) (tmpHrs * 3.7);
                    break;
                case "B+":
                    curpoints= (float) (tmpHrs * 3.3);
                    break;
                case "B":
                    curpoints= (float) (tmpHrs * 3);
                    break;
                case "B-":
                    curpoints= (float)(tmpHrs * 2.7);
                    break;
                case "C+":
                    curpoints= (float)(tmpHrs * 2.3);
                    break;
                case "C":
                    curpoints= (float)(tmpHrs * 2);
                    break;
                case "C-":
                    curpoints= (float)(tmpHrs * 1.7);
                    break;
                case "D+":
                    curpoints= (float)(tmpHrs * 1.3);
                    break;
                case "D":
                    curpoints= tmpHrs;
                    break;
                default:
                    break;
            }
            points+="_"+curpoints;
            totalPoints+=curpoints;
        }

        else
        {
            totalPoints += (Float.parseFloat(gr)*tmpHrs);
        }
        result.setGravity(Gravity.CENTER);
        result.append("Grade : "+gr.toUpperCase()+"⠀⠀Credits : "+tmpHrs+"\n\n");
    }

    public void add (View v)
    {
        result=getActivity().findViewById(R.id.result);
        if(checkValidInput(grade.getText().toString(),hours.getText().toString())) {
            calcTotalPoints();
            return;
        }

        Toast.makeText(getActivity(), "Error Input", Toast.LENGTH_LONG).show();
        grade.setText("");
        hours.setText("");
    }


     public void delete(View v){
         result=getActivity().findViewById(R.id.result);

         if (totalHours > 0) {
             result.setText(result.getText().toString().substring(0, result.getText().toString().lastIndexOf("Grade")));
             totalHours-=tmpHrs;
             totalPoints-=Float.parseFloat(points.substring(points.lastIndexOf("_")+1));
             points=points.substring(0,points.lastIndexOf("_"));
         }

    }


    @SuppressLint("SetTextI18n")
    public void calculateTotal(View view)
    {
        result=getActivity().findViewById(R.id.result);
        result.setGravity(Gravity.CENTER);

        if(checkValidInput(grade.getText().toString(),hours.getText().toString())) {
            calcTotalPoints();
        }
        grade.setText("");
        hours.setText("");

        resGpa=totalPoints/totalHours;

        if(totalHours>0 ) {
            String cgpa=(resGpa.toString().length()>6)?(resGpa.toString().substring(0,4)):(resGpa.toString());

            if(resGpa>=3.83 && resGpa<=4.0)
                cgpa+=" (A)";
           else if(resGpa>=3.67)
                cgpa+=" (A-)";
            else if(resGpa>=3.33)
                cgpa+=" (B+)";
            else if(resGpa>=3)
                cgpa+=" (B)";
            else if(resGpa>=2.67)
                cgpa+=" (C+)";
            else if(resGpa>=2.33)
                cgpa+=" (C)";
            else if(resGpa>=2)
                cgpa+=" (D)";
            else if(resGpa<2 && resGpa>=0)
                 cgpa+=" (F)";
            else
                cgpa+=" (Error)";

            result.append("Your Gpa is : "+cgpa );
            grade.onEditorAction(EditorInfo.IME_ACTION_DONE);
        }

        else
            result.setText("Enter your Grades");


        totalHours=0;
        totalPoints=0;
    }




}
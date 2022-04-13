package com.example.project_sci;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;
import android.support.v7.app.AppCompatActivity;


public class calcGpa extends AppCompatActivity {

    int totalHours=0,tmpHrs=0;
    float totalPoints=0;

    Float resGpa=new Float(0);
    String gr;

    Pattern grades = Pattern.compile("(A-|A|B|B\\+|B-|C|C\\+|C-|D\\+|D|a|b|c|d|a-|b-|c-|b\\+|c\\+|d\\+)");
    Pattern fraction = Pattern.compile("\\d+(\\.\\d+)?");

    EditText grade,hours;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_gpa);
        grade=findViewById(R.id.grade);
        hours=findViewById(R.id.hours);
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
                    totalPoints += (tmpHrs * 4);
                    break;
                case "A-":
                    totalPoints += (tmpHrs * 3.7);
                    break;
                case "B+":
                    totalPoints += (tmpHrs * 3.3);
                    break;
                case "B":
                    totalPoints += (tmpHrs * 3);
                    break;
                case "B-":
                    totalPoints += (tmpHrs * 2.7);
                    break;
                case "C+":
                    totalPoints += (tmpHrs * 2.3);
                    break;
                case "C":
                    totalPoints += (tmpHrs * 2);
                    break;
                case "C-":
                    totalPoints += (tmpHrs * 1.7);
                    break;
                case "D+":
                    totalPoints += (tmpHrs * 1.3);
                    break;
                case "D":
                    totalPoints += (tmpHrs);
                    break;
                default:
                    break;
            }
        }

        else
        {
            totalPoints += (Float.parseFloat(gr)*tmpHrs);
        }
        result.append(gr.toUpperCase()+"⠀⠀⠀⠀"+tmpHrs+"\n");

    }

    public void add (View v)
    {
        result=findViewById(R.id.result);
        if(checkValidInput(grade.getText().toString(),hours.getText().toString())) {
            calcTotalPoints();
            return;
        }

        Toast.makeText(this, "Error Input", Toast.LENGTH_LONG).show();
        grade.setText("");
        hours.setText("");
    }

    @SuppressLint("SetTextI18n")
    public void calculateTotal(View view)
    {
        result=findViewById(R.id.result);

        if(checkValidInput(grade.getText().toString(),hours.getText().toString())) {
            calcTotalPoints();
        }
        grade.setText("");
        hours.setText("");

        resGpa=totalPoints/totalHours;
        if(totalHours>0 ) {
            String cgpa=(resGpa.toString().length()>6)?(resGpa.toString().substring(0,5)):(resGpa.toString());
            result.append("Your Gpa is : "+cgpa );
            grade.onEditorAction(EditorInfo.IME_ACTION_DONE);
        }
        else
            result.setText("Enter your Grades");

        totalHours=0;
        totalPoints=0;
    }

}
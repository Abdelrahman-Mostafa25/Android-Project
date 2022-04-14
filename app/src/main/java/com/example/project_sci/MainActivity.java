package com.example.project_sci;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class    MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public String leatestNews;
    private String channelId="abc";
    private int notificationID =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent intent = new Intent (MainActivity.this, Complaints.class);
//                startActivity(intent);
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_anon,R.id.nav_face,R.id.nav_gpa)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    //Notification Methods +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    void createChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel NC = new NotificationChannel(channelId,"My Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NC.setDescription("Get Notification");
            NotificationManager NMG = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NMG.createNotificationChannel(NC);
        }
    }

    //Notification Display
    void createNotification(String st){
        createChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
        builder.setContentTitle("My Notification Title")
                .setContentText(st)
                .setSmallIcon(R.drawable.ic_launcher_background);
        NotificationManagerCompat NMC = NotificationManagerCompat.from(this);
        ++notificationID;
        NMC.notify(notificationID,builder.build());
    }
    ////Notification Methods +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {             // resp about 3point of setting
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {          // resp about active of nav bar
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goToCalculator(MenuItem item) {
        Intent i=new Intent(this,calcGpa.class);
        startActivity(i);
    }


}
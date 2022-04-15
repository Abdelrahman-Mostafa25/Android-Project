package com.example.project_sci;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class    MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static String leatestNewsTitle;
    String leatestNews;
    static String leatestAnnouncementTitle;
    String leatesAnnouncement;
    static String leatestEventTitle;
    String leatestEvent;
    String channelId="abc";
    int notificationID =0;
    static boolean isConnected = false;
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
                R.id.nav_annon, R.id.nav_news, R.id.nav_events,R.id.nav_alert,R.id.nav_face,R.id.nav_gpa,R.id.nav_home_page)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        checkNewFeeds checkNotification = new checkNewFeeds();
        checkNotification.execute();
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
    void createNotification(String st,String Title){
        createChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId);
        builder.setContentTitle(Title)
                .setContentText(st)
                .setSmallIcon(R.drawable.ic_launcher_background);
        NotificationManagerCompat NMC = NotificationManagerCompat.from(this);
        ++notificationID;
        NMC.notify(notificationID,builder.build());
    }
    ////Notification Methods +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
    boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            isConnected= true;

            return true;
        }
        isConnected = false;
        return false;
    }



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

//    public void goToCalculator(MenuItem item) {
//        Intent i=new Intent(this,calcGpa.class);
//        startActivity(i);
//    }

    private class checkNewFeeds extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids){
            if (isConnectedToInternet()) {
            leatestNews = getNews();
            leatesAnnouncement = getAnnouncement();
            leatestEvent = getEvent();

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {


                        if (!leatestNews.equals(getNews()) && isConnectedToInternet()) {

                            leatestNews = getNews();
                            System.out.println(leatestNews);
                            createNotification(leatestNews, leatestNewsTitle);

                        }
                        if (!leatesAnnouncement.equals(getAnnouncement())&& isConnectedToInternet()) { //For Test make  getAnnouncement() be getNews()

                            leatesAnnouncement = getAnnouncement();
                            System.out.println(leatesAnnouncement);
                            createNotification(leatesAnnouncement, leatestAnnouncementTitle);

                        }
                        if (!leatestEvent.equals(getEvent()) && isConnectedToInternet() ) {

                            leatestEvent = getEvent();
                            System.out.println(leatestEvent);
                            createNotification(leatestEvent, leatestEventTitle);

                        }
                        System.out.println("Request Sent");

                    }
                }, 0, 900000); //Call Every 15 Mins
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public static String getNews () {
        try {
            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/news").get();
            leatestNewsTitle = doc.title();
            Elements elems = doc.getElementsByClass("line-clamp-3");

            Element elem = elems.first();

            return elem.text();

        } catch (IOException e){
            return e.getMessage();
        }
    }
    public static String getAnnouncement () {
        try {

            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/announcements").get();
            leatestAnnouncementTitle = doc.title();
            Elements elems = doc.getElementsByClass("line-clamp-3 dir-rtl");

            Element elem = elems.first();

            return elem.text();

        } catch (IOException e){
            return e.getMessage();
        }
    }
    public static String getEvent () {
        try {

            Document doc = Jsoup.connect("https://science.asu.edu.eg/ar/events").get();
            leatestEventTitle = doc.title();
            Elements elems = doc.getElementsByClass("max-h-12 overflow-ellipsis overflow-hidden");

            Element elem = elems.first();

            return elem.text();

        } catch (IOException e){
            return e.getMessage();
        }

    }


}
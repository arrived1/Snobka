package pl.snobka.arrived1.snobka;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;


public class SplashScreen extends Activity {
    private Animation leftRight;
    private Animation rightLeft;
    private Animation downTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

        leftRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_right);
        rightLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_left);
        downTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down_top);

        TextView textView1 = (TextView)findViewById(R.id.nameTxt1);
        textView1.setText("SNOBKA.PL");
        textView1.setAnimation(rightLeft);


        TextView textView2 = (TextView)findViewById(R.id.nameTxt2);
        textView2.setText("We love fashion!");
        textView2.startAnimation(leftRight);

        TextView textView3 = (TextView)findViewById(R.id.nameTxt3);
        textView3.setText("Nieoficjalny czytnik");
        textView3.startAnimation(downTop);


        InternetAcces internetAcces = new InternetAcces(getApplicationContext());
        if(internetAcces.isOnline()) {
            // Showing splashscreen while making network calls to download necessary
            // data before launching the app Will use AsyncTask to make http call
            new PrefetchData().execute();

        } else {
            textView3.setText("Brak połączenia z internetem!!!");
            textView3.startAnimation(downTop);
        }
    }


    // Async Task to make http call
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String URL = "http://www.snobka.pl";

            RssParser parser = new RssParser();
            List<Entry> entryList = parser.parse(URL);

            DBHandler dbHandler = new DBHandler(getApplicationContext());
            for(Entry entry : entryList)
                dbHandler.addRecord(entry);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreen.this, MyActivity.class);
//            i.putExtra("now_playing", now_playing);
//            i.putExtra("earned", earned);
            startActivity(i);

            // close this activity
            finish();
        }
    }
}
package pl.snobka.arrived1.snobka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class SplashScreen extends Activity {
    private Animation leftRight;
    private Animation rightLeft;
    private Animation downTop;
    private String picUrl = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

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


            Bitmap img = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher);

            DBHandler dbHandler = new DBHandler(getApplicationContext());
            for(Entry entry : entryList) {

                if(!dbHandler.isEntryExists(entry.getNewsId()))
                {
                    Bitmap bitmap = null;
                    Document doc = null;
                    Elements picLinks = null;
                    try {
                        doc = Jsoup.connect(entry.getLink()).get();
                        picLinks = doc.select("img");

                        for(Element element: picLinks) {
                            picUrl = element.toString();
                            //Log.d("DUPA", picUrl);
                            if(picUrl.contains("snobka.article.sds.o2.pl")) {
                                Log.d("DUPA", picUrl);
                                picUrl = element.absUrl("src");
                                //Log.d("DUPA", picUrl);
                                //Log.d("DUPA", "-------------------------------------------------------------------");
                                break;
                            }
                            Log.d("DUPA", "-------------------------------------------------------");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Log.d("DUPA", "picture: " + picUrl);
//                    Log.d("DUPA", "zaczynam sciagniete");
                    bitmap = downloadBitmap(picUrl);
//                    Log.d("DUPA", "skonczylem sciagniete");



                    if(bitmap != null) {
//                        Log.d("DUPA", "wkladam sciagniete");
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        entry.setImage(bitmap);
                        bitmap = null;
                    }
                    else {
//                        Log.d("DUPA", "wkladam ikonke");
                        dbHandler.updateImage(entry.getNewsId(), bos.toByteArray());
                        entry.setImage(img);
                    }
                    dbHandler.addRecord(entry);
                }
            }




//            Document doc = null;
//
////            for(Entry entry : dbHandler.getAllRecords()) {
//
//                try {
//                    doc = Jsoup.connect(entryList.get(0).getLink()).get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                Element e = doc.body();
//                Element res = e.select("div#intertext1").first();
//                Elements elems = res.getAllElements();
//


//            for(int i = 0; i < elems.size(); i++) {
//                Log.d("DUPA", "element link: " + elems.get(i).select("a[href]"));
//                Log.d("DUPA", "element: " + elems.get(i).text());
//            }
//
//                Log.d("DUPA", "\t\t\t\t\t\t\t\t\t\t\t\t\t!");
////                Log.d("DUPA", entry.getLink());
//                Log.d("DUPA", elems.get(0).text());
////            }



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

        private Bitmap downloadBitmap(String picUrl){
            HttpURLConnection conn = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(picUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
//            conn.getContentLength(); //size of downloaded data
                bitmap = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
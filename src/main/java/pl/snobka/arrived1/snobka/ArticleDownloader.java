package pl.snobka.arrived1.snobka;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ArticleDownloader extends AsyncTask<Void, Void, Void> {
    private String url = null;
    private View view = null;
    private String picUrl = null;
    private Integer id = null;
    Bitmap bitmap = null;

    public ArticleDownloader(String url, View view, Integer id) {
        this.url = url;
        this.view = view;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // before making http calls
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Document doc = null;
        Elements picLinks = null;
        try {
            doc = Jsoup.connect(url).get();
            picLinks = doc.select("img");

            for(Element element: picLinks) {
                picUrl = element.toString();
                //Log.d("DUPA", picUrl);
                if(picUrl.contains("snobka.article.sds.o2.pl")) {
                    Log.d("DUPA", picUrl);
                    picUrl = element.absUrl("src");
                    Log.d("DUPA", picUrl);
                    Log.d("DUPA", "-------------------------------------------------------------------");
                    break;
                }
                //Log.d("DUPA", "-------------------------------------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap = downloadBitmap(picUrl);

//        Elements links = null;
//        Elements elements = null;
//        Element body = null;
//        if (doc != null) {
//            body = doc.body();
//        }
//        if(body != null) {
//            Element div = body.select("div#intertext1").first();
//
//            if(div != null) {
//                elements = div.getAllElements();
//                article = elements.get(0).text();
//
//                links = elements.get(0).select("a[href]");
//                //Log.d("DUPA", "element link: " + elements.get(0).select("a[href]"));
//
//                if(links.size() > 4) {
//
//
//                    Log.d("DUPA", "element link: " + links.get(3).text());
//                    Log.d("DUPA", "element link: " + links.get(3).toString());
//                    String absUrl = links.get(3).absUrl("href");
//                    Log.d("DUPA", "element link: " + absUrl);
//
//                    bitmap = downloadBitmap(absUrl);
//                }
//            }
//        }

        //Log.d("DUPA", elems.get(0).text());


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // After completing http call

        if(bitmap != null) {
            ImageView image = (ImageView)view.findViewById(id);
            image.setImageBitmap(bitmap);
        }
    }


    public Bitmap downloadBitmap(String picUrl){
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

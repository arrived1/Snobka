package pl.snobka.arrived1.snobka;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    private DefaultHttpClient httpClient = new DefaultHttpClient();
    private HttpGet httpGet = null;
    private HttpResponse httpResponse = null;
    private HttpEntity httpEntity = null;
    private HttpURLConnection conn = null;

    public Bitmap downloadBitmap(String picUrl){
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

    public String getWebsiteFromUrl(String url) {
        String xml = null;

        try {
            // request method is GET
            httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return xml;
    }
}

package pl.snobka.arrived1.snobka;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;



public class ArticleActivity extends Activity {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        addAdView();

        Bundle data = getIntent().getExtras();
        int item = (int) data.getLong("ID");

        DBHandler dbHandler = new DBHandler(this);
        WebView webView = (WebView)findViewById(R.id.webView);

        webView.loadUrl(dbHandler.getRecord(item).getLink()); //recordy w bazie sa numerowane od 1
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void addAdView() {
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("TEST_DEVICE_ID")
                .build();
        adView.loadAd(adRequest);
    }

//    private void loadAdd() {
//        // Create an ad.
//        adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(AD_UNIT_ID);
//
//        // Add the AdView to the view hierarchy. The view will have no size
//        // until the ad is loaded.
//        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
//        layout.addView(adView);
//
//        // Create an ad request. Check logcat output for the hashed device ID to
//        // get test ads on a physical device.
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
//                .build();
//
//        // Start loading the ad in the background.
//        adView.loadAd(adRequest);
//    }

//    private void setTxtAndAnim(int id, Animation animation, String txt) {
//        TextView textView = (TextView)findViewById(id);
//        textView.setText(txt);
//        textView.startAnimation(animation);
//    }

//    private void setComments(ArticleParser articleParser) {
//        List<String> comments = articleParser.getComments();
//        if(comments.size() > 0) {
//            TextView comment = (TextView)findViewById(R.id.commentsTxt);
//            comment.setText("Komentarze:");
//
//
//            LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//            View commentLayout = findViewById(R.id.commentLayout);
//            for(int i = 0; i < comments.size(); i++) {
//                ViewGroup commentPair = (ViewGroup)inflater.inflate(R.layout.comments_pair,
//                        (ViewGroup)findViewById(R.layout.comments_pair));
//
//                TextView comment1 = (TextView)commentPair.getChildAt(0);
//                comment1.setText(comments.get(i));
//
//                if(comments.size() > i+1) {
//                    TextView comment2 = (TextView)commentPair.getChildAt(1);
//                    comment2.setText(comments.get(i + 1));
//                    i++;
//                }
//
//                ((LinearLayout) commentLayout).addView(commentPair);
//            }
//        }
//    }

//    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//
//        Matrix matrix = new Matrix(); // CREATE A MATRIX FOR THE MANIPULATION
//        matrix.postScale(scaleWidth, scaleHeight); // RESIZE THE BIT MAP
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
//        return resizedBitmap;
//    }
}

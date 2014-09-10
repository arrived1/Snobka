package pl.snobka.arrived1.snobka;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class NewArticleActivity extends Activity {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_article);

        addAdView();

        Bundle data = getIntent().getExtras();
        int item = (int) data.getLong("ID");

        DBHandler dbHandler = new DBHandler(this);
        Entry entry = dbHandler.getRecord(item);

        TextView text1 =  (TextView)findViewById(R.id.articleTxt1);
        text1.setText(entry.getTitle());

//        ArticleDownloader articleDownloader = new ArticleDownloader(this, entry.getLink(), R.id.articleTxt2);
//        articleDownloader.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null)
            adView.resume();
    }

    @Override
    public void onPause() {
        if (adView != null)
            adView.pause();

        super.onPause();
    }

    /**
     * Called before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null)
            adView.destroy();

        super.onDestroy();
    }

    private void addAdView() {
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
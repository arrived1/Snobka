package pl.snobka.arrived1.snobka;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RateUs {
    private final static String APP_TITLE = "Snobka.pl";
    private final static String APP_PNAME = "com.lolgamequiz.my";

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }
        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        Resources resources = mContext.getResources();

        final Dialog dialog = new Dialog(mContext, R.style.customDialogStyle);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_dialog);
        dialog.setTitle(resources.getString(R.string.rateNow) + " " + resources.getString(R.string.app_name));

        TextView tv = (TextView)dialog.findViewById(R.id.rateMainTxt);
        tv.setText(resources.getString(R.string.rateTxt1) + " " +
                resources.getString(R.string.app_name) + " " +
                resources.getString(R.string.rateTxt2));

        Button b1 = (Button)dialog.findViewById(R.id.buttonRate1);
        b1.setText(resources.getString(R.string.rateNow) + " " + APP_TITLE);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
            }
        });

        Button b2 = (Button)dialog.findViewById(R.id.buttonRate2);
        b2.setText(resources.getString(R.string.rateLater));
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button b3 = (Button)dialog.findViewById(R.id.buttonRate3);
        b3.setText(resources.getString(R.string.rateNever));
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
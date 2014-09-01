package pl.snobka.arrived1.snobka;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetAcces {
    private Context context;

    public InternetAcces(Context context_) {
        this.context = context_;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo netInfoMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isWifi = netInfoWifi != null && netInfoWifi.isConnectedOrConnecting();
        boolean isMobile = netInfoMobile != null && netInfoMobile.isConnectedOrConnecting();

        if (isWifi || isMobile)
            return true;
        return false;
    }
}
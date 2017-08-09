package dongbaek.hs.kr.dbapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by h2is1 on 2016-08-14.
 */
public class Util {

    static public boolean isInternetConn(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return !(!mobile.isConnected() && !wifi.isConnected());
    }


}

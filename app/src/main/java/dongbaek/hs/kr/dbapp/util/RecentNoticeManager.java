package dongbaek.hs.kr.dbapp.util;

import android.content.Context;
import android.util.Log;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by h2is1 on 2016-08-20.
 */
public class RecentNoticeManager {

    final public static String RECENT_NOTICE_TITLE = "notice-title";
    final public static String RECENT_NOTICE_CONTENT = "notice-content";

    private AppPreferences pref;
    private Context context;

    public RecentNoticeManager(Context context) {
        this.context = context;
        this.pref = new AppPreferences(context);
    }

    public void saveNotice(String title, String content) {
        pref.put(RECENT_NOTICE_TITLE, title);
        pref.put(RECENT_NOTICE_CONTENT, content);
        Log.d("RECENTPREF_input", title + content);
    }

    public String loadNoticeTitle() {
        return pref.getString(RECENT_NOTICE_TITLE, "");
    }

    public String loadNoticeContent() {
        Log.d("RECENTPREF_output", pref.getString(RECENT_NOTICE_CONTENT, ""));
        return pref.getString(RECENT_NOTICE_CONTENT, "");
    }
}

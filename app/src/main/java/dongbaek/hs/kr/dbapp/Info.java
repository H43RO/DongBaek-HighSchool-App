package dongbaek.hs.kr.dbapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by �ط� on 2015-05-30.
 */
public class Info extends PreferenceActivity {

    private Toolbar toolbar;
    private PackageInfo PI1;
    private PackageInfo PI2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        try {
            PI1 = PI2 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var2_5) {
            var2_5.printStackTrace();
            PI1 = null;
        }

        String string = PI1.versionName;
        this.findPreference((CharSequence) "setting_1_1").setSummary(
                (CharSequence) string);

        toolbar.setTitle(getTitle());
        toolbar.setTitleTextColor(Color.WHITE);

    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.activity_info, new LinearLayout(this), false);

        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewGroup contentWrapper = (ViewGroup) contentView
                .findViewById(R.id.content_wrapper);
        LayoutInflater.from(this).inflate(layoutResID, contentWrapper, true);

        getWindow().setContentView(contentView);
    }
}
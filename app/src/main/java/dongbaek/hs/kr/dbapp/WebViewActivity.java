package dongbaek.hs.kr.dbapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final WebView webView = (WebView) findViewById(R.id.webView);
        if (!(getIntent() == null)) {
            String url = getIntent().getStringExtra("url");
            if (url != null && url.length() != 0) {
                webView.loadUrl(url);
            } else {
                finish();
                Toast.makeText(this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
            }

            String name = getIntent().getStringExtra("name");
            if (name != null && url.length() != 0){
                getSupportActionBar().setTitle(name);
            }else{
                finish();
                Toast.makeText(this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
            Toast.makeText(this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
        }

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType,
                                        long size) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.setDataAndType(Uri.parse(url), mimeType);

                try {
                    startActivity(viewIntent);
                } catch (ActivityNotFoundException ex) {
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
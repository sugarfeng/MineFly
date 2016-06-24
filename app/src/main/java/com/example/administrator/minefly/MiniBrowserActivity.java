package com.example.administrator.minefly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class MiniBrowserActivity extends AppCompatActivity{
    private WebView mWebView;
    private EditText mURL;
    private RelativeLayout main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_browser);
        mWebView = (WebView) findViewById(R.id.browser);
        mURL = (EditText) findViewById(R.id.url_text);
        main_content = (RelativeLayout) findViewById(R.id.main_content);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new MyObject(),"myObj");
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }
    public void doSearch(View view){
        String url = mURL.getText().toString();
        mWebView.loadUrl("file:///android_asset/html.html");
    }
    class MyObject{
        public MyObject(){

        }
        public void showToast(String msg){
            Toast.makeText(MiniBrowserActivity.this,msg,Toast.LENGTH_SHORT).show();
        }
        public void newImageView(final int width, final int height, final String url){
            Toast.makeText(MiniBrowserActivity.this,url,Toast.LENGTH_SHORT).show();
            MiniBrowserActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams pm = new RelativeLayout.LayoutParams(width,height);
                    ImageView img = new ImageView(MiniBrowserActivity.this);
                    Picasso.with(MiniBrowserActivity.this).load(url).into(img);
                    pm.leftMargin = 200;
                    pm.topMargin = 200;
                    img.setLayoutParams(pm);
                    main_content.addView(img);
                }
            });
        }
    }
}

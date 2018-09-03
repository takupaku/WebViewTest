package co.example.takwa.webviewtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    ImageView imageView;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;

    String pageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbarId);
        imageView = findViewById(R.id.imageviewId);
        progressBar.setMax(100);


        webView = findViewById(R.id.webviewId);
        linearLayout = findViewById(R.id.layout);
        swipeRefreshLayout = findViewById(R.id.srLayoutId);
        /*
        websetting websetting=webview.websetting();
        websetting.setjavatscript
         */

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                linearLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                pageUrl = url;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl("https://www.google.com");
        swipeRefreshLayout.setRefreshing(true);
        webView.setWebChromeClient(new WebChromeClient() {
            //cntr+o
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                imageView.setImageBitmap(icon);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.goBackaId:
                //go back
                onBackPressed();
                break;
            case R.id.goForwordId:

                if(webView.canGoForward()){
                    webView.goForward();

                }
                //forword
                break;
            case R.id.aboutUsId:
                Toast.makeText(this,"about us",Toast.LENGTH_SHORT).show();
                break;
            case R.id.shareId:
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,pageUrl);
                intent.putExtra(Intent.EXTRA_SUBJECT,"page url");
                startActivity(Intent.createChooser(intent,"pls select any of one"));


                Toast.makeText(this,"Share ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exitId:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

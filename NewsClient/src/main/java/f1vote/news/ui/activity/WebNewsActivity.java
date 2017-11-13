package f1vote.news.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import f1vote.news.R;

/**
 * Created by markopolo on 03/07/2017.
 */

public class WebNewsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView imageView;
    private Menu menu;
    private TextView toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        progressBar = (ProgressBar) this.findViewById(R.id.progressBars);

        imageView = (ImageView) findViewById(R.id.toolbar_logo);

        imageView.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        // Set Toolbar.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setLogo(null);

        String url=getIntent().getStringExtra("url");

        WebView webview = (WebView) findViewById(R.id.webview_news);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }


        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String nsTitle = view.getTitle();

                toolbarTitle.setText(nsTitle);
            }
        });


        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comment, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        if (item.getItemId() == R.id.share) {
            String url = getIntent().getStringExtra("url");
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, url);

            startActivity(Intent.createChooser(share, "Share link!"));
        }

        return super.onOptionsItemSelected(item);
    }

}

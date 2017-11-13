package f1vote.news.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.graphics.Bitmap;
import android.content.Context;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import f1vote.news.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsContentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ProgressBar progressBar;

    private NestedScrollView nest;

    private ImageView imageView;

    private TextView newsTitle;

    private TextView commentNum;

    private WebView webView;

    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_newscontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progressBar = (ProgressBar) this.findViewById(R.id.progressBars);

        final AppBarLayout appbar = (AppBarLayout) this.findViewById(R.id.app_bar);

        String img = getIntent().getStringExtra("img");

        String title = getIntent().getStringExtra("title");

        imageView = (ImageView) findViewById(R.id.newsImage);

        newsTitle = (TextView) findViewById(R.id.newsTitle);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


        newsTitle.setText(title);



            Glide.with(getApplicationContext())
                    .load(img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        MyTask mt = new MyTask();
        mt.execute();


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comment, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }



    class MyTask extends AsyncTask<Void, Void, Void> {

        Elements paragraph;

        String linkInnerH;

        String title;

        String imgLink;

        String url = getIntent().getStringExtra("url");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        public Void downloadCode() {
            Document doc = null;
            Elements pars = null;
            Elements readMore = null;
            Elements related = null;

            try {
                doc = Jsoup.connect(url).get();
                pars = doc.select(".post-wrap");
                related = doc.select(".related-posts");

                assert pars != null;
                assert readMore != null;
                assert related != null;

                Element h1_ele = doc.getElementsByClass("entry-title").get(0);
                title = h1_ele.text();


                Element img = doc.getElementsByClass("featured-image").get(0);
                Element img_src = img.child(0);
                imgLink = img_src.attr("src");

                String article = pars.html();
                String relPosts = related.html();
                linkInnerH = "<meta name=\"viewport\" content=\"width=device-width, Result-scalable=no\" /><link rel=\"stylesheet\" id=\"extra-style-css\" href=\"https://www.f1vote.com/ru/wp-content/themes/Extra/style.css\" type=\"text/css\" media=\"all\"><script type=\"text/javascript\" src=\"https://www.f1vote.com/ru/wp-includes/js/jquery/jquery.js\"></script><link rel=\"stylesheet\" type=\"text/css\" href=\"https://www.f1vote.com/ru/wp-content/plugins/rating-system/assets/css/style.css?ver=4.7.5\" />" + "<style> img {height:auto;max-width:100%}.fluid-width-video-wrapper{width: 100%;position: relative;padding: 0;}.fluid-width-video-wrapper iframe, .fluid-width-video-wrapper object, .fluid-width-video-wrapper embed {position: absolute;top: 0;left: 0;width: 100%;height: 100%;}embed, iframe, object, video {max-width: 100%;}</style>"
                        + "<style type=\"text/css\">img {border: 0;font-size: 0;line-height: 0;max-width: 100%;}" +
                        "body{color: #000000;padding:15px;font-size: 18px;font-family:'Helvetica Neue',Arial,sans-serif;}body,p,li{line-height:1.2em}.comment-reply-link{text-transform:none!important;background:0 0!important;font-size:14px!important;padding:0!important;color:#bdbdbd!important}.et_pb_button_module_wrapper{padding-top:30px}.et_overlay:before,.et_pb_bg_layout_light .et_pb_more_button,.et_pb_bg_layout_light .et_pb_newsletter_button,.et_pb_bg_layout_light .et_pb_promo_button,.et_pb_bg_layout_light.et_pb_module.et_pb_button,.et_pb_contact_submit,.et_pb_filterable_portfolio .et_pb_portfolio_filters li a.active,.et_pb_filterable_portfolio .et_pb_portofolio_pagination ul li a.active,.et_pb_gallery .et_pb_gallery_pagination ul li a.active,.et_pb_member_social_links a:hover,.et_pb_pricing li a,.et_pb_pricing_table_button,.et_pb_sum,.woocommerce-page #content input.button:hover .et_pb_widget li a:hover,.woocommerce-page #content input.button:hover .et_pb_widget.woocommerce .product_list_widget li a:hover,.woocommerce-page #content input.post-nav .nav-links .button:hover .et_pb_widget li a:hover,.woocommerce-page #content input.read-more-button:hover .et_pb_widget li a:hover{color:#9E9E9E}.related-posts-header{margin-top:40px}.related-posts-content{padding:0}.related-post{padding:10px 0}.related-posts-header h3{padding:10px 0}p {margin-bottom: 0.8em;}" +
                        "font-family: 'PT Sans', Helvetica, Arial, Lucida, sans-serif !important;}</style></head>"
                        + "<body>"
                        + article + relPosts + "</body><script type=\"text/javascript\" src=\"https://www.f1vote.com/ru/wp-content/themes/Extra/includes/builder/scripts/frontend-builder-scripts.js\"></script></html>";
                System.out.print(linkInnerH);
            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected Void doInBackground(Void... params) {
            downloadCode();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        if (linkInnerH != null && title != null && imgLink != null) {
            addCode();
        } else {
                new MyTask().execute();
            }
        }
        private void addCode() {


                newsTitle.setText(title);

                if (imageView.getDrawable() == null) {
                    Glide.with(getApplicationContext())
                            .load(imgLink)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }


            WebView webView = (WebView) findViewById(R.id.webView);
            webView.setWebChromeClient(new WebChromeClient());



            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAppCacheMaxSize(1024*1024*8);
            
            webView.getSettings().setAppCachePath("/data/data/"+ getPackageName() +"/cache");
            webView.getSettings().setAppCacheEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setDefaultTextEncodingName("UTF-8");

            String mimeType = "text/html; charset=UTF-8";
            String encoding = "utf-8";



            webView.loadDataWithBaseURL(url,linkInnerH, mimeType, encoding, null);
            progressBar.setVisibility(View.GONE);

            /*updateMenuTitles();*/

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.contains("f1vote.com/ru")) {
                        Intent intent = new Intent(NewsContentActivity.this, NewsContentActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(NewsContentActivity.this, WebNewsActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                    return true;
                }
            });
        }


        /*private void updateMenuTitles() {
            String date = getIntent().getStringExtra("date");
            if (date == null) {

            } else {
                String[] words = date.split(" | ");
                MenuItem item = menu.findItem(R.id.comment_num);
                item.setTitle(words[0]);
            }


        }*/


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

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_TEXT, url);

            startActivity(Intent.createChooser(share, "Share link!"));
        }

        return super.onOptionsItemSelected(item);
    }

}

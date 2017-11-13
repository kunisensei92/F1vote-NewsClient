package f1vote.news;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;


import f1vote.news.adapter.MyFragmentPagerAdapter;
import f1vote.news.ui.activity.AboutActivity;
import f1vote.news.ui.activity.NewsContentActivity;
import f1vote.news.ui.fragment.AboutFragment;
import f1vote.news.ui.fragment.F1voteFragment;
import f1vote.news.ui.fragment.MemesFragment;
import f1vote.news.ui.fragment.MobileFragment;
import f1vote.news.ui.fragment.NewsFragment;
import f1vote.news.ui.fragment.RssFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MenuItem menuItemWaiting;


    private NewsFragment newsFragment;
    private MobileFragment mobileFragment;
    private AboutFragment aboutFragment;
    private F1voteFragment f1voteFragment;
    private RssFragment rssFragment;
    private MemesFragment memesFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(menuItemWaiting != null) {
                    onNavigationItemSelected(menuItemWaiting);
                }
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        initView();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        menuItemWaiting = null;
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            menuItemWaiting = item;
            mDrawerLayout.closeDrawers();
            return false;
        };

        if (id == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.contacts) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.f1vote.com/ru/contacts/"));
            startActivity(browserIntent);
        } else if (id == R.id.soc_twitter) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/f1vote_ru/"));
            startActivity(browserIntent);
        } else if (id == R.id.soc_vk) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/f1vote/"));
            startActivity(browserIntent);
        } else if (id == R.id.stickers) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=markopolo.formula1driversstickers")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=markopolo.formula1driversstickers")));
            }
        }

        return true;
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(8);


        aboutFragment = new AboutFragment();
        f1voteFragment = new F1voteFragment();
        mobileFragment = new MobileFragment();
        newsFragment = new NewsFragment();
        rssFragment = new RssFragment();
        memesFragment = new MemesFragment();


        listFragment = new ArrayList<>();
        listFragment.add(newsFragment);
        listFragment.add(f1voteFragment);
        listFragment.add(mobileFragment);
        listFragment.add(aboutFragment);
        listFragment.add(rssFragment);
        listFragment.add(memesFragment);


        listTitle = new ArrayList<>();
        listTitle.add("Новое");
        listTitle.add("Эксклюзив");
        listTitle.add("Опросы");
        listTitle.add("Главное");
        listTitle.add("Новости");
        listTitle.add("Мемы");


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(5)));

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragment, listTitle);

        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }



}

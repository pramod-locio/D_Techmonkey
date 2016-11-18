
package com.dtechmonkey.d_techmonkey;

import com.dtechmonkey.d_techmonkey.adapters.*;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager=null;
    private Toolbar toolbar;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        toolbar=(Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Drawer Layout
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        //Navigation view
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);


        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        //Search Start here
        Intent searchIntent=getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query=searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, query,Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onResume() {
        super.onResume();
    }

    // Setting up vewPager in mainActivity
    private void setupViewPager(ViewPager viewPager) {
        String[] categories = new String[] {"HOME", "news", "art-and-culture", "fun-and-entertainment", "military", "women", "nature", "food", "history", "videos", "opinion"};
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), categories);
        /*adapter.addFragment(new FragmentHome(), "HOME");
        adapter.addFragment(new FragmentNews(), "NEWS");
        adapter.addFragment(new FragmentCulture(), "CULTURE");
        adapter.addFragment(new FragmentEntertainment(), "ENTERTAINMENT");
        adapter.addFragment(new FragmentMilitary(), "MILITARY");
        adapter.addFragment(new FragmentWomen(), "WOMEN");
        adapter.addFragment(new FragmentNature(), "NATURE");
        adapter.addFragment(new FragmentFood(), "FOOD");
        adapter.addFragment(new FragmentHistory(), "HISTORY");
        adapter.addFragment(new FragmentVideos(), "VIDEOS");
        adapter.addFragment(new FragmentOpinion(), "OPINION");*/
        viewPager.setAdapter(adapter);
    }

    //Search Implementation here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchView searchView= (SearchView)menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id)
        {
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                //Setting the current page
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_news:
                //Setting the current page
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_culture:
                //Setting the current page
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_entertainment:
                //Setting the current page
                viewPager.setCurrentItem(3);
                break;
            case R.id.nav_military:
                //Setting the current page
                viewPager.setCurrentItem(4);
                break;
            case R.id.nav_women:
                //Setting the current page
                viewPager.setCurrentItem(5);
                break;
            case R.id.nav_nature:
                //Setting the current page
                viewPager.setCurrentItem(6);
                break;
            case R.id.nav_food:
                //Setting the current page
                viewPager.setCurrentItem(7);
                break;
            case R.id.nav_history:
                //Setting the current page
                viewPager.setCurrentItem(8);
                break;
            case R.id.nav_videos:
                //Setting the current page
                viewPager.setCurrentItem(9);
                break;
            case R.id.nav_opinion:
                //Setting the current page
                viewPager.setCurrentItem(10);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
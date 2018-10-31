package com.antonblue16.mytranslateproject;

import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.antonblue16.mytranslateproject.Helper.TranslateHelper;
import com.antonblue16.mytranslateproject.Search.SearchAdapter;
import com.antonblue16.mytranslateproject.Translate.Translate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener
{
    private TranslateHelper translateHelper;
    private SearchAdapter searchAdapter;

    private ArrayList<Translate> listData = new ArrayList<>();

    RecyclerView recyclerView;
    SearchView searchView;
    String languagePick;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        recyclerView = findViewById(R.id.recyclerView);

        searchView = findViewById(R.id.searchBar);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        translateHelper = new TranslateHelper(this);
        searchAdapter = new SearchAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);

        languagePick = "en";
        getData(languagePick, "");
    }

    private void getData(String pick, String search)
    {
        try
        {
            translateHelper.open();

            if(search.isEmpty())
            {
                listData = translateHelper.getAllData(pick);
            }
            else
            {
                listData = translateHelper.getDataByName(search, pick);
            }

            String title = null;
            String hint = null;

            if(pick == "en")
            {
                title = getResources().getString(R.string.english_indonesia);
                hint = getResources().getString(R.string.search);
            }
            else
            {
                title = getResources().getString(R.string.indonesia_english);
                hint = getResources().getString(R.string.cari);
            }
            getSupportActionBar().setSubtitle(title);
            searchView.setQueryHint(hint);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            translateHelper.close();
        }
        searchAdapter.replaceAll(listData);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.navEnglishIndonesia)
        {
            languagePick = "en";
            getData(languagePick, "");
        }
        else if (id == R.id.navIndonesiaEnglish)
        {
            languagePick = "id";
            getData(languagePick, "");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        getData(languagePick, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        getData(languagePick, newText);
        return false;
    }
}

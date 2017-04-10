package com.weizhi.redmartcatalog.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.ui.catalog.CatalogFragment;
import com.weizhi.redmartcatalog.ui.productdetail.ProductDetailFragment;

public class MainActivity extends AppCompatActivity implements
        CatalogFragment.OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener,
        ProductDetailFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(R.string.catalog_screen_title);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CatalogFragment fragment = CatalogFragment.newInstance();
        fragmentTransaction.add(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void goToDetail(@NonNull Product product) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProductDetailFragment fragment = ProductDetailFragment.newInstance(product);
        fragmentTransaction.add(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showTitle(@NonNull String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.getBackStackEntryCount() == 0){
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);
            if(fragment instanceof CatalogFragment){
                ((CatalogFragment) fragment).showCatalogScreen(true);
                ActionBar actionBar = getSupportActionBar();
                if(actionBar != null){
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
            }
        }
        else{
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }
}

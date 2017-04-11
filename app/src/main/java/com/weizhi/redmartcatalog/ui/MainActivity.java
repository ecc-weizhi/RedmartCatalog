package com.weizhi.redmartcatalog.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.ui.catalog.CatalogFragment;
import com.weizhi.redmartcatalog.ui.productdetail.ProductDetailFragment;

public class MainActivity extends AppCompatActivity implements
        CatalogFragment.OnFragmentInteractionListener,
        ProductDetailFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.fragment) == null){
            CatalogFragment fragment = CatalogFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, fragment).commit();
        }
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
    public void goToDetail(@NonNull Product product, @NonNull View sharedView) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProductDetailFragment fragment = ProductDetailFragment.newInstance(product);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new SharedElementTransition());
            fragment.setSharedElementReturnTransition(new SharedElementTransition());
        }

        fragmentTransaction.replace(R.id.fragment, fragment)
                .addSharedElement(sharedView, getString(R.string.product_detail_transition_name)+"0")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void fragmentOnStart(@NonNull String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(fragmentManager.getBackStackEntryCount() > 0);
        }
    }

//    @Override
//    public void showTitle(@NonNull String title) {
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setTitle(title);
//        }
//    }

//    @Override
//    public void onBackStackChanged() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        if(fragmentManager.getBackStackEntryCount() == 0){
//            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);
//            if(fragment instanceof CatalogFragment){
//                ((CatalogFragment) fragment).showCatalogScreen(true);
//                ActionBar actionBar = getSupportActionBar();
//                if(actionBar != null){
//                    actionBar.setDisplayHomeAsUpEnabled(false);
//                }
//            }
//        }
//        else{
//            ActionBar actionBar = getSupportActionBar();
//            if(actionBar != null){
//                actionBar.setDisplayHomeAsUpEnabled(true);
//            }
//        }
//    }
}

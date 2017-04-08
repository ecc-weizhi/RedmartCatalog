package com.weizhi.redmartcatalog.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CatalogActivityFragment extends Fragment implements
        CatalogAdapter.AdapterInterface{

    private RecyclerView mRecyclerView;

    private CatalogAdapter mAdapter;


    public CatalogActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.catalog_recycler);

        mAdapter = new CatalogAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        LoadMoreScrollListener scrollListener =
                new LoadMoreScrollListener(gridLayoutManager, mAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void fetchCatalog(int page, int pageSize) {
        List<Product> productList = new ArrayList<>();
        for(int i=0; i<pageSize; i++){
            ProductImage[] images = new ProductImage[1];
            productList.add(new Product(1, "title", "desc", 1, 1, false,
                    new ProductImage(0, 0, "", 1), images));
        }

        mAdapter.updateDataSet(page, productList);
    }
}

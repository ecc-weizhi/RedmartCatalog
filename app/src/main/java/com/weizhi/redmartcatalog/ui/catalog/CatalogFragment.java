package com.weizhi.redmartcatalog.ui.catalog;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weizhi.redmartcatalog.MyApplication;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.common.Helper;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */
public class CatalogFragment extends Fragment implements
        CatalogContract.View,
        CatalogAdapter.AdapterInterface {

    private CatalogContract.ActionListener mPresenter;

    private RecyclerView mRecyclerView;
    private CatalogAdapter mAdapter;


    public CatalogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPresenter = new CatalogPresenter(this,
                MyApplication.getInstance().getJobManager(),
                MyApplication.getBus());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.catalog_recycler);
        mRecyclerView.addItemDecoration(
                new CatalogItemDecoration((int)Helper.dpToPx(getActivity(), 4),
                        CatalogAdapter.PAGE_SIZE));
        mAdapter = new CatalogAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        LoadMoreScrollListener scrollListener =
                new LoadMoreScrollListener(gridLayoutManager, mAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void onStart(){
        super.onStart();
        mPresenter.onStart();
        if(mAdapter.getItemCount() == 0){
            fetchCatalog(0, mAdapter.PAGE_SIZE);
        }
    }

    @Override
    public void onStop(){
        mPresenter.onStop();
        mAdapter.setIsLoadingMore(false);
        super.onStop();
    }

    @Override
    public void fetchCatalog(int page, int pageSize) {
        mPresenter.fetchCatalog(page, pageSize);
    }

    @Override
    public void addProductList(int page, int pageSize, List<Product> productList) {
        mAdapter.updateDataSet(page, productList);
    }
}

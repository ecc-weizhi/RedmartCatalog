package com.weizhi.redmartcatalog.ui.catalog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weizhi.redmartcatalog.MyApplication;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.common.Helper;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */
public class CatalogFragment extends Fragment implements
        CatalogContract.View,
        CatalogAdapter.AdapterInterface {

    private CatalogContract.ActionListener mPresenter;
    private OnFragmentInteractionListener mParent;

    private View mRootView;
    private RecyclerView mRecyclerView;
    private CatalogAdapter mAdapter;


    public static CatalogFragment newInstance(){
        CatalogFragment fragment = new CatalogFragment();
        return fragment;
    }

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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mParent = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        mRootView = v;
        mRecyclerView = (RecyclerView)v.findViewById(R.id.catalog_recycler);
        int tinyMarginPx = (int) getResources().getDimension(R.dimen.margin_tiny);
        mRecyclerView.addItemDecoration(new CatalogItemDecoration(tinyMarginPx));
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
    public void onProductClick(@NonNull Product product) {
        mRootView.setVisibility(View.GONE);
        mParent.goToDetail(product);
    }

    @Override
    public void onAddToCartClick(@NonNull Product product) {
        Toast.makeText(getActivity(), product.getTitle() + " add to cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addProductList(int page, int pageSize, List<Product> productList) {
        mAdapter.updateDataSet(page, productList);
    }

    public void showCatalogScreen(boolean shouldShow){
        mRootView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    public interface OnFragmentInteractionListener{
        void goToDetail(@NonNull Product product);
    }
}

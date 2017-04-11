package com.weizhi.redmartcatalog.ui.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weizhi.redmartcatalog.MyApplication;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */
public class CatalogFragment extends Fragment implements
        CatalogContract.View,
        CatalogAdapter.AdapterInterface {
    private static final String SAVED_PRODUCT_LIST = "saved_product_list";

    private CatalogContract.ActionListener mPresenter;
    private OnFragmentInteractionListener mParent;

    private View mRootView;
    private RecyclerView mRecyclerView;
    private CatalogAdapter mAdapter;


    public static CatalogFragment newInstance(){
        return new CatalogFragment();
    }

    public CatalogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPresenter = new CatalogPresenter(this,
                MyApplication.getInstance().getJobManager(),
                MyApplication.getBus(),
                MyApplication.getInstance().getCart());

        mAdapter = new CatalogAdapter(this, this, MyApplication.getInstance().getCart());
        if(savedInstanceState != null){
            ArrayList<Product> productList = (ArrayList)savedInstanceState.getSerializable(SAVED_PRODUCT_LIST);
            mAdapter.setDataSet(productList == null ? new ArrayList<Product>() : productList);
        }
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        LoadMoreScrollListener scrollListener =
                new LoadMoreScrollListener(gridLayoutManager, mAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        ArrayList<Product> productList = new ArrayList<>(mAdapter.getDataSet());
        outState.putSerializable(SAVED_PRODUCT_LIST, productList);
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.onStart();
        mParent.fragmentOnStart(getString(R.string.catalog_screen_title));
        if(mAdapter.getItemCount() == 0){
            fetchCatalog(0, CatalogAdapter.PAGE_SIZE);
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
        mPresenter.onProductClick(product);
    }

    @Override
    public void onAddToCartClick(@NonNull Product product) {
        mPresenter.onAddToCartClick(product);
    }

    @Override
    public void onMinusClick(@NonNull Product product) {
        mPresenter.onMinusClick(product);
    }

    @Override
    public void onPlusClick(@NonNull Product product) {
        mPresenter.onPlusClick(product);
    }

    @Override
    public void addProductList(int page, int pageSize, List<Product> productList) {
        mAdapter.updateDataSet(page, productList);
    }

    @Override
    public void showCartQuantityChange(@NonNull Product product) {
        mAdapter.updateItem(product);
    }

    @Override
    public void showGoToProductDetail(@NonNull Product product) {
        CatalogAdapter.ViewHolder vh = (CatalogAdapter.ViewHolder)mRecyclerView
                .findViewHolderForAdapterPosition(mAdapter.getAdapterPosition(product));
        mParent.goToDetail(product, vh.productImage);
    }

    public interface OnFragmentInteractionListener{
        void goToDetail(@NonNull Product product, View sharedView);
        void fragmentOnStart(@NonNull String title);
    }
}

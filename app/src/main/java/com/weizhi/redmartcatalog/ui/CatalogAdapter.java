package com.weizhi.redmartcatalog.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> implements
        AdapterClickListener,
        LoadMoreScrollListener.LoadMoreCallback{
    private static final int PAGE_SIZE = 20;

    private AdapterInterface mParent;

    private List<Product> mProductList = new ArrayList<>();
//    private boolean mIsLoadingMore = false;
    private boolean mHasNoMoreData = false;
    private int mLastLoadedPage = -1;

    public CatalogAdapter(@NonNull AdapterInterface parent){
        mParent = parent;
        List<Product> productList = new ArrayList<>();
        for(int i=0; i<PAGE_SIZE; i++){
            ProductImage[] images = new ProductImage[1];
            productList.add(new Product(1, "title", "desc", 1, 1, false,
                    new ProductImage(0, 0, "", 1), images));
        }
        mProductList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_catalog_item, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @Override
    public void onItemClick(int adapterPosition) {

    }

    @Override
    public void tryLoadMore() {
        if(mHasNoMoreData){
            return;
        }

        mParent.fetchCatalog(mLastLoadedPage + 1, PAGE_SIZE);
    }

    public void updateDataSet(int page, List<Product> productList){
        if(mLastLoadedPage + 1 <= page){
            mProductList.addAll(page * PAGE_SIZE, productList);

            if(productList.size() != PAGE_SIZE){
                mHasNoMoreData = true;
            }

            if(mLastLoadedPage <= page){
                notifyItemRangeChanged(page * PAGE_SIZE, productList.size());
            }
            else{
                notifyItemRangeInserted(page * PAGE_SIZE, productList.size());
            }

            mLastLoadedPage = Math.max(mLastLoadedPage, page);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        public ImageView mProductImage;
        public TextView mTitleText;
        private AdapterClickListener mListener;

        public ViewHolder(View v, AdapterClickListener listener){
            super(v);
            mListener = listener;
            mProductImage = (ImageView) v.findViewById(R.id.product_image);
            mTitleText = (TextView) v.findViewById(R.id.product_title_text);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }

    public interface AdapterInterface{
        void fetchCatalog(int page, int pageSize);
    }
}

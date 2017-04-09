package com.weizhi.redmartcatalog.ui.catalog;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.network.WsConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> implements
        CatalogClickListener,
        LoadMoreScrollListener.LoadMoreCallback{
    public static final int PAGE_SIZE = 20;

    private AdapterInterface mParent;
    private Fragment mFragment;

    private List<Product> mProductList = new ArrayList<>();
    private boolean mIsLoadingMore = false;
    private boolean mHasNoMoreData = false;
    private int mLastLoadedPage = -1;

    public CatalogAdapter(@NonNull AdapterInterface parent, Fragment fragment){
        mParent = parent;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_catalog_item, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Product product = mProductList.get(position);

        // image
        Glide.with(mFragment)
                .load(WsConstants.IMAGE_BASE_URL+product.getMainImage().path)
                .fitCenter()
                .into(holder.productImage);

        // title
        holder.titleText.setText(product.getTitle());

        // weight or volume
        String wtVol = product.getWtVol();
        if(wtVol == null){
            holder.wtVolText.setVisibility(View.GONE);
        }
        else{
            holder.wtVolText.setVisibility(View.VISIBLE);
            holder.wtVolText.setText(wtVol);
        }

        // expiry
        String expiry = product.getExpiry();
        if(expiry == null){
            holder.expiryText.setVisibility(View.GONE);
        }
        else{
            holder.expiryText.setVisibility(View.VISIBLE);
            holder.expiryText.setText(expiry);
        }

        // frozen
        holder.frozenText.setVisibility(product.isFrozen() ? View.VISIBLE : View.GONE);

        // price
        if(product.isOnSale()){
            Spannable promoPrice = new SpannableString(String.format("$%.2f ", product.getPromoPrice()));
            int promoColor = ContextCompat.getColor(holder.priceText.getContext(),
                    R.color.price_promo);
            promoPrice.setSpan(new ForegroundColorSpan(promoColor),
                    0,
                    promoPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            Spannable oldPrice = new SpannableString(String.format("$%.2f", product.getPrice()));
            int oldColor = ContextCompat.getColor(holder.priceText.getContext(),
                    R.color.price_old);
            oldPrice.setSpan(new ForegroundColorSpan(oldColor),
                    0,
                    oldPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            oldPrice.setSpan(new StrikethroughSpan(),
                    0,
                    oldPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.priceText.setText(TextUtils.concat(promoPrice, oldPrice));
        }
        else{
            Spannable price = new SpannableString(String.format("$%.2f", product.getPrice()));

            int priceColor = ContextCompat.getColor(holder.priceText.getContext(),
                    R.color.price_normal);
            price.setSpan(new ForegroundColorSpan(priceColor),
                    0,
                    price.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.priceText.setText(price);
        }
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        Product product = mProductList.get(adapterPosition);
        mParent.onProductClick(product);
    }

    @Override
    public void onAddToCartClick(int adapterPosition) {
        Product product = mProductList.get(adapterPosition);
        mParent.onAddToCartClick(product);
    }

    @Override
    public void tryLoadMore() {
        if(mHasNoMoreData){
            return;
        }

        if(!mIsLoadingMore){
            mIsLoadingMore = true;
            mParent.fetchCatalog(mLastLoadedPage + 1, PAGE_SIZE);
        }
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
            mIsLoadingMore = false;
        }
    }

    public void setIsLoadingMore(boolean isLoadingMore){
        mIsLoadingMore = isLoadingMore;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        public View mainView;
        public ImageView productImage;
        public TextView titleText;
        public TextView wtVolText;
        public TextView expiryText;
        public TextView frozenText;
        public TextView priceText;
        public View dividerView;
        public Button addButton;
        private CatalogClickListener mListener;

        public ViewHolder(View v, CatalogClickListener listener){
            super(v);
            mainView = v;
            mListener = listener;
            productImage = (ImageView) v.findViewById(R.id.product_image);
            titleText = (TextView) v.findViewById(R.id.product_title_text);
            wtVolText = (TextView) v.findViewById(R.id.product_wt_vol_text);
            expiryText = (TextView) v.findViewById(R.id.product_expiry_text);
            frozenText = (TextView) v.findViewById(R.id.product_frozen_text);
            priceText = (TextView) v.findViewById(R.id.product_price_text);
            dividerView = v.findViewById(R.id.divider);
            addButton = (Button) v.findViewById(R.id.product_add_button);

            // expiry label
            int expiryLabelColor = ContextCompat.getColor(expiryText.getContext(),
                    R.color.label_green);
            ((GradientDrawable)expiryText.getBackground()).setColor(expiryLabelColor);

            // frozen label
            int frozenLabelColor = ContextCompat.getColor(expiryText.getContext(),
                    R.color.label_blue);
            ((GradientDrawable)frozenText.getBackground()).setColor(frozenLabelColor);

            addButton.setOnClickListener(this);
            mainView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.product_card:
                    mListener.onItemClick(getAdapterPosition());
                    break;

                case R.id.product_add_button:
                    mListener.onAddToCartClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface AdapterInterface{
        void fetchCatalog(int page, int pageSize);
        void onProductClick(@NonNull Product product);
        void onAddToCartClick(@NonNull Product product);
    }
}

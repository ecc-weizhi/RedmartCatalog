package com.weizhi.redmartcatalog.ui.catalog;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LongSparseArray;
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

import com.bumptech.glide.Glide;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Cart;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.network.WsConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> implements
        CatalogClickListener,
        LoadMoreScrollListener.LoadMoreCallback{
    static final int PAGE_SIZE = 20;

    private AdapterInterface mParent;
    private Fragment mFragment;

    private Cart mCart;
    private LongSparseArray<Integer> mIdToPosition = new LongSparseArray<>();
    private List<Product> mProductList = new ArrayList<>();
    private boolean mIsLoadingMore = false;
    private boolean mHasNoMoreData = false;
    private int mLastLoadedPage = -1;

    CatalogAdapter(@NonNull AdapterInterface parent, Fragment fragment, @NonNull Cart cart){
        mParent = parent;
        mFragment = fragment;
        mCart = cart;
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
                .load(WsConstants.IMAGE_BASE_URL+product.getMainImage().getPath())
                .fitCenter()
                .into(holder.productImage);

        // image label
        switch(product.getPromotionType()){
            case 1:
                if(!TextUtils.isEmpty(product.getSavingText())){
                    int redColor = ContextCompat.getColor(holder.productImageLabel.getContext(),
                            R.color.colorPrimary);
                    ((GradientDrawable)holder.productImageLabel.getBackground()).setColor(redColor);
                    holder.productImageLabel.setText(product.getSavingText());
                    holder.productImageLabel.setVisibility(View.VISIBLE);
                }
                else{
                    holder.productImageLabel.setVisibility(View.GONE);
                }
                break;

            case 3:
                if(!TextUtils.isEmpty(product.getSavingText())){
                    int blueColor = ContextCompat.getColor(holder.productImageLabel.getContext(),
                            R.color.label_blue);
                    ((GradientDrawable)holder.productImageLabel.getBackground()).setColor(blueColor);
                    holder.productImageLabel.setText(product.getSavingText());
                    holder.productImageLabel.setVisibility(View.VISIBLE);
                }
                else{
                    holder.productImageLabel.setVisibility(View.GONE);
                }
                break;

            default:
                holder.productImageLabel.setVisibility(View.GONE);
        }

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
        if(product.getExpiryTime() != 0){
            StringBuilder sb = new StringBuilder();
            sb.append(product.getExpiryTime()).append(product.getExpiryMetric());
            if(product.isExpiryMinimum()){
                sb.append("+");
            }

            holder.expiryText.setVisibility(View.VISIBLE);
            holder.expiryText.setText(sb.toString());
        }
        else{
            holder.expiryText.setVisibility(View.GONE);
        }

        // frozen
        holder.frozenText.setVisibility(product.isFrozen() ? View.VISIBLE : View.GONE);

        // price
        if(product.isOnSale()){
            Spannable promoPrice = new SpannableString(String.format(Locale.US,
                    "$%.2f ", product.getPromoPrice()));
            int promoColor = ContextCompat.getColor(holder.priceText.getContext(),
                    R.color.price_promo);
            promoPrice.setSpan(new ForegroundColorSpan(promoColor),
                    0,
                    promoPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            Spannable oldPrice = new SpannableString(String.format(Locale.US,
                    "$%.2f", product.getPrice()));
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
            Spannable price = new SpannableString(String.format(Locale.US,
                    "$%.2f", product.getPrice()));

            int priceColor = ContextCompat.getColor(holder.priceText.getContext(),
                    R.color.price_normal);
            price.setSpan(new ForegroundColorSpan(priceColor),
                    0,
                    price.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.priceText.setText(price);
        }

        // cart
        showCartQuantity(holder, mCart.getQuantity(product.getId()));
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
    public void onMinusClick(int adapterPosition) {
        Product product = mProductList.get(adapterPosition);
        mParent.onMinusClick(product);
    }

    @Override
    public void onPlusClick(int adapterPosition) {
        Product product = mProductList.get(adapterPosition);
        mParent.onPlusClick(product);
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

    void setDataSet(@NonNull List<Product> productList){
        mProductList = productList;
        mLastLoadedPage = productList.size() / PAGE_SIZE;
        for(int i=0; i<productList.size(); i++){
            Product product = productList.get(i);
            mIdToPosition.put(product.getId(), i);
        }
        notifyDataSetChanged();
    }

    List<Product> getDataSet(){
        return mProductList;
    }

    void updateDataSet(int page, List<Product> productList){
        if(mLastLoadedPage + 1 <= page){
            int startingIndex = mProductList.size();
            mProductList.addAll(page * PAGE_SIZE, productList);

            for(int i=0; i<productList.size(); i++){
                Product product = productList.get(i);
                mIdToPosition.put(product.getId(), startingIndex+i);
            }

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

    void updateItem(@NonNull Product product){
        int position = mIdToPosition.get(product.getId());
        mProductList.set(position, product);
        notifyItemChanged(position);
    }

    void setIsLoadingMore(boolean isLoadingMore){
        mIsLoadingMore = isLoadingMore;
    }

    private void showCartQuantity(ViewHolder vh, int quantity){
        if(quantity == 0){
            vh.border.setVisibility(View.GONE);
            vh.addBackground.setVisibility(View.GONE);
            vh.minusText.setVisibility(View.GONE);
            vh.plusText.setVisibility(View.GONE);
            vh.cartQuantityText.setVisibility(View.GONE);
            vh.addButton.setVisibility(View.VISIBLE);
        }
        else{
            vh.border.setVisibility(View.VISIBLE);
            vh.addBackground.setVisibility(View.VISIBLE);
            vh.minusText.setVisibility(View.VISIBLE);
            vh.plusText.setVisibility(View.VISIBLE);
            vh.cartQuantityText.setVisibility(View.VISIBLE);
            vh.cartQuantityText.setText(String.valueOf(quantity));
            vh.addButton.setVisibility(View.INVISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        View mainView;
        ImageView productImage;
        TextView titleText;
        TextView wtVolText;
        TextView expiryText;
        TextView frozenText;
        TextView priceText;
        View dividerView;
        Button addButton;
        TextView productImageLabel;

        View border;
        View addBackground;
        TextView minusText;
        TextView plusText;
        TextView cartQuantityText;

        private CatalogClickListener mListener;

        ViewHolder(View v, CatalogClickListener listener){
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
            productImageLabel = (TextView) v.findViewById(R.id.product_image_label);

            border = v.findViewById(R.id.border);
            addBackground = v.findViewById(R.id.product_add_background);
            minusText = (TextView)v.findViewById(R.id.minus_button);
            plusText = (TextView)v.findViewById(R.id.plus_button);
            cartQuantityText = (TextView)v.findViewById(R.id.cart_quantity_text);

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
            minusText.setOnClickListener(this);
            plusText.setOnClickListener(this);
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

                case R.id.minus_button:
                    mListener.onMinusClick(getAdapterPosition());
                    break;

                case R.id.plus_button:
                    mListener.onPlusClick(getAdapterPosition());
                    break;
            }
        }
    }

    interface AdapterInterface{
        void fetchCatalog(int page, int pageSize);
        void onProductClick(@NonNull Product product);
        void onAddToCartClick(@NonNull Product product);
        void onMinusClick(@NonNull Product product);
        void onPlusClick(@NonNull Product product);
    }
}

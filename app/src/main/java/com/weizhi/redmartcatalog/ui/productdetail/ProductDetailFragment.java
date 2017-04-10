package com.weizhi.redmartcatalog.ui.productdetail;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.Product;
import com.weizhi.redmartcatalog.model.ProductDescriptionField;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailFragment extends Fragment {
    private static final String ARGS_PRODUCT = "args_product";
    private static final float OLD_PRICE_PROPORTION = 0.6f;

    private LinearLayout mProductDetailLayout;
    private ConstraintLayout mTopLayout;
    private ConstraintLayout mFloatingButtonLayout;
    private TextView mAddToCartText;
    private ViewPager mImagePager;
    private CirclePageIndicator mPagerIndicator;
    private TextView mTitleText;
    private TextView mExpiryText;
    private TextView mFrozenText;
    private TextView mWtVolText;
    private TextView mPriceText;
    private Button mSaveToListButton;

    private Product mProduct;
    private ImagePagerAdapter mAdapter;

    public static ProductDetailFragment newInstance(@NonNull Product product){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mProduct = (Product)getArguments().getSerializable(ARGS_PRODUCT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mProductDetailLayout = (LinearLayout)v.findViewById(R.id.product_detail_content_layout);
        mTopLayout = (ConstraintLayout)v.findViewById(R.id.top_layout);
        mFloatingButtonLayout = (ConstraintLayout)v.findViewById(R.id.floating_button_layout);
        mAddToCartText = (TextView)v.findViewById(R.id.add_to_cart_text);
        mImagePager = (ViewPager)v.findViewById(R.id.product_image_pager);
        mPagerIndicator = (CirclePageIndicator)v.findViewById(R.id.image_pager_indicator);
        mTitleText = (TextView)v.findViewById(R.id.product_title_text);
        mExpiryText = (TextView)v.findViewById(R.id.product_expiry_text);
        mFrozenText = (TextView)v.findViewById(R.id.product_frozen_text);
        mWtVolText = (TextView)v.findViewById(R.id.product_wt_vol_text);
        mPriceText = (TextView)v.findViewById(R.id.product_price_text);
        mSaveToListButton = (Button)v.findViewById(R.id.product_save_to_list_button);

        issue221387Workaround(mFloatingButtonLayout);

        // images
        mAdapter = new ImagePagerAdapter(this);
        mAdapter.updateDataSet(mProduct.getImages());
        mImagePager.setAdapter(mAdapter);
        mPagerIndicator.setViewPager(mImagePager);

        // title
        mTitleText.setText(mProduct.getTitle());

        // expiry
        if(mProduct.getExpiryTime() != 0){
            String metric = mProduct.getExpiryMetric();
            int expiryLabelColor = ContextCompat.getColor(getActivity(), R.color.label_green);
            ((GradientDrawable)mExpiryText.getBackground()).setColor(expiryLabelColor);

            switch(metric){
                case "D":
                    mExpiryText.setText(getString(R.string.expiry_minimum_days, mProduct.getExpiryTime()));
                    mExpiryText.setVisibility(View.VISIBLE);
                    break;

                case "M":
                    mExpiryText.setText(getString(R.string.expiry_minimum_months, mProduct.getExpiryTime()));
                    mExpiryText.setVisibility(View.VISIBLE);
                    break;

                default:
                    mExpiryText.setVisibility(View.GONE);
                    break;
            }
        }
        else{
            mExpiryText.setVisibility(View.GONE);
        }

        // frozen label
        if(mProduct.isFrozen()){
            int frozenLabelColor = ContextCompat.getColor(getActivity(), R.color.label_blue);
            ((GradientDrawable)mFrozenText.getBackground()).setColor(frozenLabelColor);
            mFrozenText.setVisibility(View.VISIBLE);
        }
        else{
            mFrozenText.setVisibility(View.GONE);
        }

        // wt or vol
        if(mProduct.getWtVol() != null){
            mWtVolText.setVisibility(View.VISIBLE);
            mWtVolText.setText(mProduct.getWtVol());
        }
        else{
            mWtVolText.setVisibility(View.GONE);
        }

        // price
        if(mProduct.isOnSale()){
            Spannable promoPrice = new SpannableString(String.format("$%.2f ", mProduct.getPromoPrice()));
            int promoColor = ContextCompat.getColor(mPriceText.getContext(), R.color.price_promo);
            promoPrice.setSpan(new ForegroundColorSpan(promoColor),
                    0,
                    promoPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            Spannable oldPrice = new SpannableString(String.format("$%.2f", mProduct.getPrice()));
            int oldColor = ContextCompat.getColor(mPriceText.getContext(),
                    R.color.price_old);
            oldPrice.setSpan(new ForegroundColorSpan(oldColor),
                    0,
                    oldPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            oldPrice.setSpan(new StrikethroughSpan(),
                    0,
                    oldPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            oldPrice.setSpan(new RelativeSizeSpan(OLD_PRICE_PROPORTION),
                    0,
                    oldPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            mPriceText.setText(TextUtils.concat(promoPrice, oldPrice));
        }
        else{
            Spannable price = new SpannableString(String.format("$%.2f", mProduct.getPrice()));

            int priceColor = ContextCompat.getColor(mPriceText.getContext(),
                    R.color.price_normal);
            price.setSpan(new ForegroundColorSpan(priceColor),
                    0,
                    price.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            mPriceText.setText(price);
        }

        // floating button
        int floatingButtonColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        ((GradientDrawable)mFloatingButtonLayout.getBackground()).setColor(floatingButtonColor);

        // about the product
        CardView aboutCard = (CardView)inflater.inflate(R.layout.view_product_description,
                mProductDetailLayout, false);
        TextView aboutHeader = (TextView)aboutCard.findViewById(R.id.description_name_text);
        TextView aboutContent = (TextView)aboutCard.findViewById(R.id.description_content_text);
        aboutHeader.setText(R.string.description_name);
        aboutContent.setText(mProduct.getDescription());
        mProductDetailLayout.addView(aboutCard);

        // Secondary description
        for(int i=0; i<mProduct.getSecondaryDescriptionField().length; i++){
            ProductDescriptionField description = mProduct.getSecondaryDescriptionField()[i];
            if(!TextUtils.isEmpty(description.getName()) &&
                    !TextUtils.isEmpty(description.getContent())){
                CardView descriptionCard = (CardView)inflater.inflate(
                        R.layout.view_product_description,
                        mProductDetailLayout,
                        false);
                TextView header = (TextView)descriptionCard.findViewById(R.id.description_name_text);
                TextView content = (TextView)descriptionCard.findViewById(R.id.description_content_text);
                header.setText(description.getName());
                content.setText(description.getContent());
                mProductDetailLayout.addView(descriptionCard);
            }
        }

        return v;
    }


    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    /**
     * This is a workaround for issue 221387 for the android support library. The floating action
     * button's visibility must be GONE before calling this method.
     *
     * @see <a href="https://code.google.com/p/android/issues/detail?id=221387">issue 221387</a>
     */
    private void issue221387Workaround(final View viewToBeAnchor){
        mTopLayout.post(new Runnable() {
            @Override
            public void run() {
                viewToBeAnchor.setVisibility(View.VISIBLE);
            }
        });
    }
}

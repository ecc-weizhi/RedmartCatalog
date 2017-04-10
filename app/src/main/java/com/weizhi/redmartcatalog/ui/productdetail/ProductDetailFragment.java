package com.weizhi.redmartcatalog.ui.productdetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
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

import java.util.Locale;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailFragment extends Fragment implements
        View.OnClickListener,
        ProductDetailContract.View{
    private static final String ARGS_PRODUCT = "args_product";
    private static final float OLD_PRICE_PROPORTION = 0.6f;

    private View mRootView;
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
    private TextView mImageLabel;

    private OnFragmentInteractionListener mParent;
    private ProductDetailContract.ActionListener mPresenter;
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
        mPresenter = new ProductDetailPresenter(this);
        mProduct = (Product)getArguments().getSerializable(ARGS_PRODUCT);
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
        mRootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mProductDetailLayout = (LinearLayout)mRootView.findViewById(R.id.product_detail_content_layout);
        mTopLayout = (ConstraintLayout)mRootView.findViewById(R.id.top_layout);
        mFloatingButtonLayout = (ConstraintLayout)mRootView.findViewById(R.id.floating_button_layout);
        mAddToCartText = (TextView)mRootView.findViewById(R.id.add_to_cart_text);
        mImagePager = (ViewPager)mRootView.findViewById(R.id.product_image_pager);
        mPagerIndicator = (CirclePageIndicator)mRootView.findViewById(R.id.image_pager_indicator);
        mTitleText = (TextView)mRootView.findViewById(R.id.product_title_text);
        mExpiryText = (TextView)mRootView.findViewById(R.id.product_expiry_text);
        mFrozenText = (TextView)mRootView.findViewById(R.id.product_frozen_text);
        mWtVolText = (TextView)mRootView.findViewById(R.id.product_wt_vol_text);
        mPriceText = (TextView)mRootView.findViewById(R.id.product_price_text);
        mSaveToListButton = (Button)mRootView.findViewById(R.id.product_save_to_list_button);
        mImageLabel = (TextView)mRootView.findViewById(R.id.product_image_label);

        issue221387Workaround(mFloatingButtonLayout);

        mFloatingButtonLayout.setOnClickListener(this);
        mSaveToListButton.setOnClickListener(this);

        // images
        mAdapter = new ImagePagerAdapter(this);
        mAdapter.updateDataSet(mProduct.getImages());
        mImagePager.setAdapter(mAdapter);
        mPagerIndicator.setViewPager(mImagePager);

        // image label
        switch(mProduct.getPromotionType()){
            case 1:
                if(!TextUtils.isEmpty(mProduct.getSavingText())){
                    int redColor = ContextCompat.getColor(mImageLabel.getContext(),
                            R.color.colorPrimary);
                    ((GradientDrawable)mImageLabel.getBackground()).setColor(redColor);
                    mImageLabel.setText(mProduct.getSavingText());
                    mImageLabel.setVisibility(View.VISIBLE);
                }
                else{
                    mImageLabel.setVisibility(View.GONE);
                }
                break;

            case 3:
                if(!TextUtils.isEmpty(mProduct.getSavingText())){
                    int blueColor = ContextCompat.getColor(mImageLabel.getContext(),
                            R.color.label_blue);
                    ((GradientDrawable)mImageLabel.getBackground()).setColor(blueColor);
                    mImageLabel.setText(mProduct.getSavingText());
                    mImageLabel.setVisibility(View.VISIBLE);
                }
                else{
                    mImageLabel.setVisibility(View.GONE);
                }
                break;

            default:
                mImageLabel.setVisibility(View.GONE);
        }

        // title
        mTitleText.setText(mProduct.getTitle());

        // expiry
        if(mProduct.getExpiryTime() != 0){
            String metric = mProduct.getExpiryMetric();
            int expiryLabelColor = ContextCompat.getColor(getActivity(), R.color.label_green);
            ((GradientDrawable)mExpiryText.getBackground()).setColor(expiryLabelColor);

            if(metric != null){
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
            Spannable promoPrice = new SpannableString(String.format(Locale.US,
                    "$%.2f ", mProduct.getPromoPrice()));
            int promoColor = ContextCompat.getColor(mPriceText.getContext(), R.color.price_promo);
            promoPrice.setSpan(new ForegroundColorSpan(promoColor),
                    0,
                    promoPrice.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            Spannable oldPrice = new SpannableString(String.format(Locale.US,
                    "$%.2f", mProduct.getPrice()));
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
            Spannable price = new SpannableString(String.format(Locale.US,
                    "$%.2f", mProduct.getPrice()));

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

        return mRootView;
    }


    @Override
    public void onStart(){
        super.onStart();
        mParent.showTitle(mProduct.getTitle());
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.product_save_to_list_button:
                mPresenter.onSaveToListClick(mProduct);
                break;

            case R.id.floating_button_layout:
                mPresenter.onAddToCartClick(mProduct);
                break;
        }
    }

    @Override
    public void showAddedToCart(@NonNull Product product) {
        Snackbar sb = Snackbar.make(mRootView, R.string.notification_added_to_cart, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        sb.setActionTextColor(Color.WHITE);
        sb.show();
    }

    @Override
    public void showSavedToList(@NonNull Product product) {
        Snackbar sb = Snackbar.make(mRootView, R.string.notification_saved_to_list, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        sb.setActionTextColor(Color.WHITE);
        sb.show();
    }

    public interface OnFragmentInteractionListener{
        void showTitle(@NonNull String title);
    }
}

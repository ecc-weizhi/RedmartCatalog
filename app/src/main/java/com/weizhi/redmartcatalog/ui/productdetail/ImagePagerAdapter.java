package com.weizhi.redmartcatalog.ui.productdetail;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weizhi.redmartcatalog.R;
import com.weizhi.redmartcatalog.model.ProductImage;
import com.weizhi.redmartcatalog.network.WsConstants;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ImagePagerAdapter extends PagerAdapter {
    private ProductImage[] mImageArray;

    private Fragment mFragment;

    public ImagePagerAdapter(Fragment fragment){
        mFragment = fragment;
    }

    public void updateDataSet(ProductImage[] imageArray){
        mImageArray = imageArray;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ProductImage image = mImageArray[position];

        ImageView imageView = (ImageView)LayoutInflater.from(collection.getContext())
                .inflate(R.layout.view_product_image_item, collection, false);

        Glide.with(mFragment)
                .load(WsConstants.IMAGE_BASE_URL+image.path)
                .fitCenter()
                .into(imageView);

        collection.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mImageArray == null ? 0 : mImageArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

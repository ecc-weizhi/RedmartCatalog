package com.weizhi.redmartcatalog.ui.productdetail;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weizhi.redmartcatalog.R;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class ProductDetailFragment extends Fragment {
    private ConstraintLayout mTopLayout;
    private FloatingActionButton mAddToCartFab;

    public static ProductDetailFragment newInstance(){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mTopLayout = (ConstraintLayout)v.findViewById(R.id.top_layout);
        mAddToCartFab = (FloatingActionButton)v.findViewById(R.id.fab);


        issue221387Workaround();

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
    private void issue221387Workaround(){
        mTopLayout.post(new Runnable() {
            @Override
            public void run() {
                mAddToCartFab.setVisibility(View.VISIBLE);
            }
        });
    }
}

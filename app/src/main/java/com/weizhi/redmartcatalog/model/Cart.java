package com.weizhi.redmartcatalog.model;

import android.support.v4.util.LongSparseArray;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class Cart {
    public static final int MAX_QUANTITY = 24;

    private int mQuantityLimit = MAX_QUANTITY;
    private LongSparseArray<Integer> mQtyMap = new LongSparseArray<>();

    public Cart(int maxQuantity){
        mQuantityLimit = maxQuantity;
    }

    public void clearCart(){
        mQtyMap.clear();
    }

    public void addToCart(long productId, int quantity){
        Integer currentQty = mQtyMap.get(productId);
        if(currentQty == null){
            currentQty = 0;
        }
        int newQuantity = Math.min(mQuantityLimit, currentQty + quantity);

        mQtyMap.put(productId, newQuantity);
    }

    public void removeFromCart(long productId, int quantity){
        Integer currentQty = mQtyMap.get(productId);
        if(currentQty == null){
            currentQty = 0;
        }
        int newQuantity = Math.max(0, currentQty - quantity);

        mQtyMap.put(productId, newQuantity);
    }

    public int getQuantity(long productId){
        Integer currentQty = mQtyMap.get(productId);
        return currentQty == null ? 0 : currentQty;
    }
}

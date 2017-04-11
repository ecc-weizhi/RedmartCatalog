package com.weizhi.redmartcatalog.ui.catalog;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

interface CatalogClickListener {
    void onItemClick(int adapterPosition);
    void onAddToCartClick(int adapterPosition);
    void onMinusClick(int adapterPosition);
    void onPlusClick(int adapterPosition);
}

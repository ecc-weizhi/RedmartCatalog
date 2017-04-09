package com.weizhi.redmartcatalog.ui.catalog;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface CatalogClickListener {
    void onItemClick(int adapterPosition);
    void onAddToCartClick(int adapterPosition);
}

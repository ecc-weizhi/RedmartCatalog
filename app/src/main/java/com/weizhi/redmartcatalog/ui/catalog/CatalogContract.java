package com.weizhi.redmartcatalog.ui.catalog;

import com.weizhi.redmartcatalog.model.Product;

import java.util.List;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public interface CatalogContract {
    interface View{

        void addProductList(int page, int pageSize, List<Product> productList);
    }

    interface ActionListener{
        void onStart();
        void onStop();
        void fetchCatalog(int page, int pageSize);
    }
}

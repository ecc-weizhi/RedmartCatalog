package com.weizhi.redmartcatalog.service.job;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

class FailResponseCode extends Exception {
    int responseCode;

    FailResponseCode(int responseCode){
        this.responseCode = responseCode;
    }
}

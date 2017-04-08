package com.weizhi.redmartcatalog.service.job;

/**
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 */

public class FailResponseCode extends Exception {
    public int responseCode;

    public FailResponseCode(int responseCode){
        this.responseCode = responseCode;
    }
}

package com.cosun.cosunp.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2020/4/9 0009 上午 11:24
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class BaseController {

    private Map<String, Object> errorCodeMap;

    protected Map<String, Object> rtnParam(Integer errorCode, Object data) {
        errorCodeMap = new HashMap<String, Object>();
        errorCodeMap.put("errorCode", errorCode);
        errorCodeMap.put("data", data);
        return errorCodeMap;
    }


}

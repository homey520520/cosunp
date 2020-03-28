package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/3/25 0025 下午 5:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class China implements Serializable {

    private static final long serialVersionUID = 4427922358871590667L;

    private Integer id;
    private String name;
    private Integer pId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}

package com.cosun.cosunp.weixin;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/9/17 0017 下午 1:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Location implements Serializable {

    private static final long serialVersionUID = -8700556683605243413L;

    private Integer id;
    private Double latitude;
    private Double longitude;
    private String openId;
    private long speed;
    private long accuracy;
    private String address;
    private Integer isClock;


    public Integer getIsClock() {
        return isClock;
    }

    public void setIsClock(Integer isClock) {
        this.isClock = isClock;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(long accuracy) {
        this.accuracy = accuracy;
    }
}

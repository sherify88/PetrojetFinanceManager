package com.example.kesho.petrojetfinancemanager;

import java.util.Date;

/**
 * Created by kesho on 3/11/2018.
 */

public class Order implements Comparable<Order> {

    private String checkNumber;
    private String Amount;
    private String vendorId;
    private String responseMonth;
    private String name;
    private String checkStatus;
    private String response;
    private String checkNumberForResposeTree;
    private String responseDate;
    private String delayDate;
    private String delayMSG;
    private String orderDate;




    public Order() {
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDelayMSG() {
        return delayMSG;
    }

    public String getDelayDate() {
        return delayDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public String getResponseMonth() {
        return responseMonth;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCheckNumberForResposeTree() {
        return checkNumberForResposeTree;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getResponse() {
        return response;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public String getName() {
        return name;
    }

    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date datetime) {
        this.dateTime = datetime;
    }

    @Override
    public int compareTo(Order o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
package com.crinoidtechnologies.mishicreationadmin.appSpecificUtils;

/**
 * Created by ${Nitin} on 3/19/2918 for Wit timer
 */


public class ServerUrls {

    // BASE URL
   //  public static final String BASE_URL = "http://mishicreations.com";
    public static final String BASE_URL = "http://mishicreations.com/wp-json/wc/v2";

    // OTHER URL'S
    public static final String ALL_PRODUCT_LIST_URL = BASE_URL + "/products";
    public static final String ALL_CATEGORY_LIST_URL = BASE_URL + "/products/categories";
    public static final String CREATE_A_CATEGORY_URL = BASE_URL + "/products/categories";
    public static final String CREATE_A_PRODUCT_URL = BASE_URL + "/products";
    public static final String UPDATE_A_CATEGORY_URL = BASE_URL + "/products/categories/{id}";
    public static final String UPDATE_A_PRODUCT_URL = BASE_URL + "/products/{id}";
    public static final String DELETE_A_CATEGORY_URL = BASE_URL + "/products/categories/{id}";
    public static final String DELETE_A_PRODUCT_URL = BASE_URL + "/products/{id}";
    public static final String ALL_ORDERS_URL = BASE_URL + "/orders";
    public static final String DELETE_ORDER_URL = BASE_URL + "/orders/{id}";
    public  static final String FETCH_PRODUCT_CATEGORY_WISE=BASE_URL+"/products";


}
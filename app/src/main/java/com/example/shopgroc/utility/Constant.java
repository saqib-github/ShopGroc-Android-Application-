package com.example.shopgroc.utility;

public class Constant {
    public static class DataType{
        public static final String BUNDLE = "bundle";
        public static final String PRODUCT = "product";
        public static final String CART_ITEM = "cartItem";
        public static final String ORDER = "order";
        public static final String USER_ORDER = "userOrder";
        public static final String RIDER_REQUEST_ORDER = "orderRequest";
    }

    public static class SharedPrefKey{
        public static final String PREF_NAME = "shopGroc_api";
        public static final String USER_ID="userId";
        public static final String USER_NAME="name";
        public static final String USER_EMAIL="email";
        public static final String USER_PHONE="phone";
        public static final String USER_ADDRESS="address";
        public static final String USER_IMAGE="userImage";



        public static final String RIDER_ID="riderId";
        public static final String RIDER_NAME="riderName";
        public static final String RIDER_EMAIL="riderEmail";
        public static final String RIDER_PHONE="riderPhone";
        public static final String RIDER_ADDRESS="riderAddress";
        public static final String RIDER_VEHICLE="riderVehicle";
        public static final String RIDER_CNIC="riderCNIC";
        public static final String RIDER_LICENSE="riderIicense";




        public static final String STORE_ID="storeId";
        public static final String STORE_NAME="storeName";
        public static final String STORE_EMAIL="storeEmail";

        public static final String PRODUCT_TITLE="productTitle";
        public static final String PRODUCT_PRICE="productPrice";
        public static final String PRODUCT_CATEGORY="productCategory";
        public static final String PRODUCT_DESCRIPTION="productDescription";
        public static final String PRODUCT_IMAGE="productImage";
    }
    public static class Messege{
        public static final String EMPTY_EMAIL_ERROR = "Email must not be empty!";
        public static final String EMPTY_PASSWORD_ERROR = "Password must not be empty!";
        public static final String PASSWORD_LENGTH_ERROR = "Password should have at least 6 characters";
        public static final String EMPTY_NAME_ERROR = "Name must not be empty!";
        public static final String EMPTY_PHONE_ERROR = "Phone must not be empty!";
        public static final String PHONE_FORMAT_ERROR = "Incorrect Phone!";
        public static final String PHONE_INCOMPLETE_ERROR = "Incomplete Phone!";
        public static final String EMPTY_ADDRESS_ERROR = "Address must not be empty!";
        public static final String LICENSE_ERROR = "License Error";
        public static final String CNIC_ERROR = "CNIC Error";
        public static final String VEHICLE_ERROR = "Vehicle No Error";
    }

    public static class DatabaseTableKey{
        public static final String USER_TABLE= "User";
        public static final String PRODUCT_TABLE= "Product";
        public static final String ORDER_TABLE = "Orders";
        public static final String STORE_ORDER_TABLE = "storeOrders";
        public static final String STORE_TABLE = "Store";
        public static final String RIDER_TABLE = "Rider";
    }

    public static class DatabaseKey{
        public static final String USER_NAME="name";
        public static final String USER_EMAIL="email";
        public static final String USER_PHONE="phone";
        public static final String USER_ADDRESS="address";
        public static final String USER_IMAGE="image";

        public static final String STORE_NAME="storeName";
        public static final String STORE_EMAIL="storeEmail";
        public static final String STORE_PASSWORD="storePassword";

        public static final String ORDER_PRODUCTS="products";
        public static final String ORDER_DELIVERY_CHARGE="deliveryCharges";
        public static final String ORDER_DELIVERY_STATUS="deliveryStatus";
        public static final String ORDER_TIME="orderTime";
        public static final String ORDER_ORDER="orders";
        public static final String ORDER_LOCATION = "location";
        public static final String ORDER_NUMBER = "orderNumber";
        public static final String USER_ID = "userId";


        public static final String ORDERED_PRODUCT_ID="productId";
        public static final String ORDERED_PRODUCT_QUANTITY="productQuantity";
        public static final String ORDERED_PRODUCT_NAME="productName";
        public static final String ORDERED_PRODUCT_PRICE="productPrice";

    }

    public static class DeliveryStatus{
        public static final int ORDER_PENDING=0;
        public static final int ORDER_COMPLETE=1;
        public static final int ORDER_CONFIRMED=2;
        public static final int ORDER_CANCEL=3;
        public static final int ORDER_DELIVERED=4;

        public static final String ORDER_PENDING_STATUS="Pending";
        public static final String ORDER_COMPLETE_STATUS="Complete";
        public static final String ORDER_CANCEL_STATUS="Cancel";
        public static final String ORDER_CONFIRMED_STATUS="Confirmed";
        public static final String ORDER_DELIVERED_STATUS="Delivered";
    }

    public static class DateFormats{
        public static final String DATE_AT_TIME="dd/MM/yyyy 'at' hh:mm aa";
    }
    public static class Date{
        public static final String DATE = "EEE, MMM d, yyyy";
    }
}

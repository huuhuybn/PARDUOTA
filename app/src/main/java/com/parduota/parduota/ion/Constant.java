package com.parduota.parduota.ion;


import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by huy_quynh o`n 6/27/17.
 */

public interface Constant {

    int GET_VERIFY_CODE = 123344;


    int RESULT_FROM_ADD_ITEM = 6677;

    String TERM_ACCEPTED = "1";
    String NOT_TERM_ACCEPTED = "0";

    String ID = "id";

    int UPDATE_SUCCESSFUL = 222;


    String PICK_UP_ONLY = "local_pickup";

    String FREIGHT = "freight";

    String KG_AND_DIMENSION = "kg_and_dimentions";


    String FACEBOOK = "facebook";

    String DRAFT_ITEM = "draft";

    String TITLE = "title";
    String DESCRIPTION = "description";
    String PRICE = "price";
    String LOCATION = "location";
    String COUNTRY = "country";
    String QUANTITY = "quantity";
    String MEDIA = "media";
    String CONDITION = "condition";
    String WEIGHT = "weight";
    String LENGTH = "length";
    String WIDTH = "width";
    String HEIGHT = "height";
    String SHIPPING_TYPE = "shipping_type";
    String SHIPPING_SERVICE = "shipping_service";
    String SHIPPING_SERVICE_COST = "shipping_service_cost";
    String SHIPPING_SERVICE_ADD_COST = "shipping_service_add_cost";
    String SELL_FOR_CHARITY = "sell_for_charity";
    String STATUS = "status";
    String SHIPPING_TYPE_CUSTOM = "shipping_type_custom";


    int VERIFY_EMAIL = 2;
    int VERIFY_ADMIN = 1;
    int VERIFY_NOTHING = 0;


    int USER_NOT_APPROVED = 0;

    int USER_APPROVED = 1;


    String REQUEST_VIP = "1";


    String REQUEST_NO_VIP = "0";
    String LINK = "link";
    String UPDATE_COUNTER = "update_counter";


    String EBAY_ID = "ebay_id";
    String NOTICE = "notice";
    String MESSAGE = "message";
    String COMPANY_ZIP_CODE = "company_zip_code";
    String REGISTERED_NUMBER = "registered_number";
    String COMPANY_ADDRESS = "company_address";
    String COMPANY_COUNTRY = "company_country";
    String P_NAME = "p_name";
    String P_SURNAME = "p_surname";
    String P_PHONE = "p_phone";


    String BUSINESS = "business";
    String ZIP_CODE = "zip_code";
    String MALE = "male";
    String FEMALE = "female";

    String BANK_ACCOUNT = "bank_account";
    String GENDER = "gender";
    String FULL_NAME = "full_name";
    String COMPANY = "company";
    String ADDRESS = "address";
    String PHONE = "phone";
    String OTHER = "other";

    String OLD_PASSWORD = "old_password";
    String NEW_PASSWORD = "password";
    String NEW_PASSWORD_CONFIRM = "password_confirmation";


    // notification click item event
//     <!--type_noti_0' => ':user: created new item - :title',-->
//    <!--'type_noti_1' => ':user: updated  item :title',-->
//    <!--'type_noti_2' => ':user: updated :title is Sold',-->
//    <!--'type_noti_3' => ':user: send a request access :title',-->
//    <!--'type_noti_4' => ':user: You have been unlocked :title',-->
//    <!--'type_noti_5' => ':user: You are Blocked :title',-->
//    <!--'type_noti_6' => ':user: updated :title is Reject',-->
//    <!--'type_noti_7' => ':user: delete :title',-->
//    <!--'type_noti_8' => ':user: Your account is upgraded  :title',-->
//    <!--'type_noti_9' => ':user: Your account is upgraded, You can push item to ebay :title',-->
//    <!--'type_noti_10' => ':user: Your account is downgraded, You can\'t push item to ebay :title',-->
//    <!--'type_noti_11' => ':user: updated :title is cancel',-->
//    <!--'type_noti_12' => ':user: Item with name :title is approved',-->
//    <!--'type_noti_13' => ':user: Item with name :title is active on ebay',-->
//    <!--'type_noti_14' => ':user: make new for you an offer abouts the price:title',-->
//    <!--'type_noti_15' => ':user: make for you an offer abouts the price:title',-->
//    <!--'type_noti_16' => ':user: make for you decline item, click to view:title',-->
//    <!--'type_noti_17' => ':user: accept offer item , click to view:title',-->
//    <!--'type_noti_18' => ':user: created new order ebay :title',-->
//    <!--'type_noti_19' => ':user: have a new message on order :title',-->
//    <!--'type_noti_20' => ':user: close an order :title',-->
//    <!--'type_noti_21' => ':user: You need buy credit for service charge :title',-->
//    <!--'type_noti_22' => ':user: We have just collected your monthly service fee  :title',-->
//    <!--'type_noti_23' => ':user: All item is cancel :title',-->
//    <!--'type_noti_24' => ':user account is initialized',-->
//    <!--'type_noti_25' => ':user: Your account has been activated',-->
//    <!--'type_noti_26' => ':user: We have just collected your monthly service fee for the ebay item',-->
//    <!--'type_noti_27' => ':user: You need buy credit for service charge ebay item',-->
//    <!--'type_noti_28' => ':user: All item is cancel',-->



    int TYPE_UPDATE_ITEM = 1;
    int TYPE_REJECT_ITEM = 6;
    int TYPE_MESSAGE_ORDER = 19;
    int TYPE_CLOSED_ORDER = 20;
    int TYPE_CHARGE = 22;
    int TYPE_ACTIVE_ITEM = 13;


    String TYPE_ORDER_LIST = "order_list";


    String FROM = "From";

    String NOTIFICATION_SCREEN = "notification_screen";
    String ITEM_SCREEN = "item_screen";

    int READED = 1;

    String COMMING_MESSAGE = "coming_message";
    String ERROR = "error";

    boolean isDEBUG = false;
    String TYPE = "type";
    String ANDROID = "android";

    String DATA = "data";
    String UPDATE_TOTAL = "update_total";

    String TOKEN_FCM = "token_fcm";


    String EMAIL = "email";
    String PASSWORD = "password";
    String PROVIDER_ID = "provider_id";
    String PROVIDER = "provider";
    String NAME = "name";

    String OK = "ok";

    String TOKEN_EXPIRED = "token_expired";

    //String BASE_URL = "https://dev.parduota.com/";


    String BASE_URL = "https://www.parduota.com/";


    String URL_GET_MESSAGE = BASE_URL + "api/orders/ping_messages/";


    String URL_UPDATE_PASSWORD = BASE_URL + "api/update/password";

    String URL_LOGIN = BASE_URL + "api/auth/login";

    String URL_GET_NOTIFY = BASE_URL + "api/get_notification";

    String URL_LOGIN_FACEBOOK = BASE_URL + "api/auth/login_social";

    String URL_GET_ITEM_BY_STATUS = BASE_URL + "api/get_item_by_status/";

    String PHOTO_URL = BASE_URL + "storage/";

    String URL_SIGN_UP = BASE_URL + "api/auth/signup";

    String URL_ALL_ITEM = BASE_URL + "api/get_items?";

    String URL_UPDATE_CREDIT = BASE_URL + "api/update/credit?";

    String URL_UPLOAD = BASE_URL + "api/media/upload";


    String URL_SET_FCM_TOKEN = BASE_URL + "api/user/fcm";


    String URL_GET_ORDER = BASE_URL + "api/get_orders?page=";

    String URL_GET_NOTIFICATION = BASE_URL + "api/get_notification?page=";


    String URL_UPDATE_PROFILE = BASE_URL + "api/update_profile";


    String URL_GET_CHARGER_HISTORY = BASE_URL + "api/history_charges?page=";


    String URL_CREATE_ORDER = BASE_URL + "api/orders/add";


    String URL_ADD_MESSAGE = BASE_URL + "api/orders/add_messages/";


    String URL_GET_VERIFY = BASE_URL + "api/request_vip";


    String URL_ADD_ITEM = BASE_URL + "api/item/add";


    String URL_ADD_CREDIT = BASE_URL + "api/update/credit";


    String URL_EDIT_ITEM = BASE_URL + "api/item/edit/";

    String AMOUNT = "amount";

    String CREDIT = "credit__";

    String PAYMENT_ID = "paymentId";


    int ALL = 4;
    int DRAFT = -1;
    int PENDING = 0;
    int SOLD = 1;
    int REJECT = 2;
    int ACTIVE = 3;
    int CANCEL = -2;
    int NOT_APPROVED = -3;
//3: active
//2: reject
//1: sold
//0: pending
//-1: draft
//-2: cancel
//-3: not_approved

    String ACTION_NEXT = "action_next";
    String INDEX = "index";


    int INPUT_TITLE = 1;
    int INPUT_DES = 2;
    int INPUT_OTHER = 3;

    String URL_UPDATE_READ = BASE_URL + "api/readed_notification/";
    String URL_GET_ORDER_DETAIL = BASE_URL + "api/orders/view/";
    String URL_UPDATE_ITEM = BASE_URL + "api/item/edit/";
    String URL_GET_TERMS = BASE_URL + "api/get_setting?type=term_en";

    String URL_ACCECPT_TERM = BASE_URL + "api/term_accept";
    String URL_GET_ITEM_DETAIL = BASE_URL + "api/get_item/";


    String SAVE = "save";
    String URL_DELETE_PHOTO = BASE_URL + "api/media/delete/" ;

}

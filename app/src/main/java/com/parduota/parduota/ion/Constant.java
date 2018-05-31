package com.parduota.parduota.ion;


/**
 * Created by huy_quynh o`n 6/27/17.
 */

public interface Constant {

    int GET_VERIFY_CODE = 123344;


    String TERM_ACCEPTED = "1";
    String NOT_TERM_ACCEPTED = "0";


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

    String TYPE_UPDATE_ITEM = "updated_item";
    String TYPE_MESSAGE_ORDER = "notification.type_desc_19";

    String TYPE_ORDER_LIST = "order_list";


    String FROM = "From";

    String NOTIFICATION_SCREEN = "notification_screen";
    String ITEM_SCREEN = "item_screen";

    int READED = 1;

    String COMMING_MESSAGE = "coming_message";
    String ERROR = "error";

    boolean isDEBUG = true;
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

    //String BASE_URL = "http://dev.ductranxuan.com/";


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
}

package com.parduota.parduota.ion;


/**
 * Created by huy_quynh o`n 6/27/17.
 */

public interface Constant {



    String BANK_ACCOUNT = "bank_account";
    String GENDER = "gender";
    String FULL_NAME = "full_name";
    String COMPANY = "company";
    String ADDRESS = "address";
    String PHONE = "phone";
    String DESCRIPTION = "description";
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

    //String BASE_URL = "http://test.500anhemcode.com/";


    String BASE_URL = "http://parduota.com/";


    String URL_UPDATE_PASSWORD = BASE_URL + "api/update/password";

    String URL_LOGIN = BASE_URL + "api/auth/login";

    String URL_GET_NOTIFY = BASE_URL + "api/get_notification";
    String URL_LOGIN_FACEBOOK = BASE_URL + "login_social";

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

    String AMOUNT = "amount";

    String CREDIT = "credit__";

    String PAYMENT_ID = "paymentId";

    String MEDIA = "media";

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

}

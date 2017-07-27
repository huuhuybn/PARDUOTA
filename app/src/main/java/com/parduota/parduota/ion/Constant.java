package com.parduota.parduota.ion;

import java.net.URL;

/**
 * Created by huy_quynh on 6/27/17.
 */

public interface Constant {

    String DATA = "data";
    String UPDATE_TOTAL = "update_total";


    String EMAIL = "email";
    String PASSWORD = "password";
    String PROVIDER_ID = "provider_id";
    String PROVIDER = "provider";
    String NAME = "name";

    String OK = "ok";

    String TOKEN_EXPIRED = "token_expired";


    String MAIN_URL = "http://test.500anhemcode.com/api/auth/";
    String URL_LOGIN = MAIN_URL + "login";

    String URL_GET_NOTIFY = "http://test.500anhemcode.com/api/get_notification";
    String URL_LOGIN_FACEBOOK = MAIN_URL + "login_social";
    String URL_GET_ITEM_BY_STATUS = "http://test.500anhemcode.com/api/get_item_by_status/0?";

    String PHOTO_URL = "http://test.500anhemcode.com/storage/";

    String URL_SIGN_UP = "http://test.500anhemcode.com/api/auth/signup";
    String URL_ALL_ITEM = "http://test.500anhemcode.com/api/get_items?";

    String URL_UPDATE_CREDIT = "http://test.500anhemcode.com/api/update/credit?";

    String URL_UPLOAD = "http://test.500anhemcode.com/api/media/upload";

    String AMOUNT = "amount";
    String CREDIT = "credit__";

    String PAYMENT_ID = "paymentId";

    String MEDIA = "media";

    int DRAFT = -1;
    int PENDING = 0;
    int SOLD = 1;
    int REJECT = 2;


    String ACTION_NEXT = "action_next";
    String INDEX = "index";


    int INPUT_TITLE = 1;
    int INPUT_DES = 2;
    int INPUT_OTHER = 3;

}

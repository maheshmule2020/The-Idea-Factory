package com.krishna.iparker;

public class IPConfig {
    private static String MAIN_URL = "http://10.10.8.166/iparker/v1/";

    // ADMIN Login Register
    public static String ADMIN_LOGIN_URL = MAIN_URL+"admin_login.php";
    public static String ADMIN_REGISTER_URL = MAIN_URL +"admin_reg.php";
    public static String SEND_LOC= MAIN_URL +"send_loc.php";

    //USER LOGIN REGISTER

    public static String LOGIN_URL = MAIN_URL+"user_login.php";
    public static String REGISTER_URL = MAIN_URL +"user_reg.php";
    public static String GET_LOC= MAIN_URL +"get_loc.php";
    public static String SEND_REQUEST= MAIN_URL +"send_request.php";
    public static String GET_USER_REQUEST= MAIN_URL +"get_request.php";
    public static String GET_OWNER_REQUEST= MAIN_URL +"owner_request.php";
    public static String Accept_USER_REQUEST= MAIN_URL +"user_request_accept.php?id=";
    public static String Accept_Owner_REQUEST= MAIN_URL +"owner_update.php?id=";
    public static final String GET_REQUESTL=MAIN_URL +"get_driver.php";
    public static String GET_ACCEPT = MAIN_URL +"get_accept.php?email=";
    public static String GET_NOTIFY = MAIN_URL +"time_extend_notify.php?email=";

    public static String GET_EXTEND = MAIN_URL +"time_extended.php";
    public static String GET_DEDUCT_AMT = MAIN_URL +"deduct_amount.php";
    public static String REFILL_WALLET_URL = MAIN_URL +"refill_wallet.php";
    public static String DELETE_USER_URL = MAIN_URL +"delete_user_request.php?id=";
    public static String GET_PENAULTY = MAIN_URL +"get_penaulty.php";
    public static String GET_ACC_BALANCE = MAIN_URL +"get_account_balance.php?email=";

    public static String verifyQR_URL = MAIN_URL +"verifyQR.php";
    public static String uid,username,vehicle_no,email,parkingname,date,timeFrom,timeTo;
    public static String skey;

}

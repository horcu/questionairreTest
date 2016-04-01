package com.horcu.apps.peez.common.utilities;

/**
 * Created by hacz on 9/30/2015.
 */
public class consts {
   public static final String PREF_ACCOUNT_NAME = "pref_acct_name";
   public static final String SENDER_ID = "932165043385";

   public static final  double STARTING_CASH = 50.00;
   public static final Long STARTING_RANK = (long)0;
   public static final String REG_ID = "reg_id";
   //public static final String GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE = "server:client_id:932165043385-peo6s0mkqf2ik7q75poo6vm10sv1fs46.apps.googleusercontent.com";
   public static final String GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE = "server:client_id:932165043385-li8t1n391drp1gl3joocrekj7apvbmq8.apps.googleusercontent.com";
   public static final String MESSAGE_COLLAPSED_KEY = "notification_coll_key";
   public static final String NOTIFICATION_BODY = "would like to challenge you. Are you up for it?";
   public static final String NOTIFICATION_CLICK_ACTION = "notification_click_action";
   public static final String DEVICE_REGISTERED = "device_registered";
   public static final String FROM = "from";

   //entity constants
   public static final String ENTITY_QUARTERBACK = "quarterback";
   public static final String ENTITY_RUNNINGBACK = "runningback";
   public static final String ELIGIBLE = "eligible";
   public static final String TIMEPERIOD = "timePeriod";
   public static final String IMG_DEF_URI = "http://static.nfl.com/static/content/public/static/img/fantasy/transparent/200x200/";
   public static final int GET_CONTACTS_RESULTS = 1200;
   public static final String SELECTED_FRIENDS = "selectedFriends";
   public static final String REG_KEY = "reg_key";
   public static final String TEST_GAME_TOPIC = "testGame";
   public static final String TEST_MSG_ID = "1234567890";
   public static final String TEST_TINE_TO_LIVE = "200";
   public static final String USER_IMG = "user_Image";
   public static final String STATUS_NOT_SENT = "not_sent";
   public static final String STATUS_SENT = "sent";
   public static final String FAV_COLOR = "favorite_color";
   public static final int PAGE_GAME = 1;
   public static final int TOTAL_TILES = 36;
   public static final int INTENT_TO_CHALLENGE = 60000;
   public static final String EXTRAS_MOVE_TO = "move_to";
   public static final String PEEZ = "Peez";
   public static final String QUESTIONS_BASE_URL = "http://jservice.io/";
   public static final String QUESTIONS_API_RANDOM = "api/random?count=" + consts.TOTAL_TILES;
   public static final int TOTAL_PLAYERS_TILES = 6;
   public static final String CARD_TYPE_GAMEBOARD = "card_type_gameboard";
   public static final String CARD_TYPE_OPPONENT_HOME = "card_type_opponent_home";
   public static final String CARD_TYPE_PLAYER_HOME = "card_type_player_home";
   public static final int GAMEVIEW_MENU_INDEX = 234254324;
   public static final int GAMEVIEW_ORIGINAL_ICON_INDEX = 919293949;
   public static final int GAMEVIEW_CARD_INDEX = 919293948;
   public static final String REALM_CONFIG_NAME = "realm_v_0.0.1";
   public static final long REALM_SCHEMA_VERSION = 1;
   public static final String MAINVIEW_PAGE = "mainview_page";
   public static final String PLAYER_VM = "player_viewmodel";
   public static final String PLAYER_NAME = "player_name";
   public static final String PLAYER_IMG_URL = "player_img_url";
   public static final String PLAYER_EMAIL = "player_email";
   public static final String PLAYER_TOKEN = "player_token";


   //end entity constants

   //turn dev mode on and off
   public static  boolean DEV_MODE = false;

   //urls for dev/prod
   public static  String DEV_URL = "http://localhost:8099/_ah/api/";
   public static  String PROD_URL = "https://ballrz-7d916.appspot.com/_ah/api/";

   public static final  String WEB_CLIENT_IDS =  "932165043385-peo6s0mkqf2ik7q75poo6vm10sv1fs46.apps.googleusercontent.com";
   public static final  String ANDROID_CLIENT_IDS = "932165043385-li8t1n391drp1gl3joocrekj7apvbmq8.apps.googleusercontent.com";
   public static final String API_KEY = "AIzaSyBiOHmVPiQNeOaE0BBHuYyoFga_1ctXNrs";
   public static final String SERVICE_ACCOUNT = "932165043385-fgcdqdnpmmv2bri11av0ok4lghf7nlfl@developer.gserviceaccount.com";
   public static final String CERT_FINGERPRINT = "1d6edf89342d0d4597d332b0321c9c607911fe2a";


   public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
   public static final String REGISTRATION_COMPLETE = "registrationComplete";


}

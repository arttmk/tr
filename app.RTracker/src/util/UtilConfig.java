package util;

import java.util.HashMap;

/**
 * 設定ファイル関連のライブラリ
 * @author arata.hasebe
 */
public class UtilConfig {
	
	public static final String	SYSTEM_NAME 	= "Tracker";
	public static final String	DB_USER 	= "rt";
	public static final String	DB_PASSWORD 	= "rt";
	public static final String	DB_URL 	= "jdbc:google:rdbms://brainpad.co.jp:tracker:tracker/tracker";
	//public static final String	DB_URL 	= "jdbc:google:rdbms://rtt-tracker:tracker/tracker";
	public static final String	IS_DEBUG 	= "true";
	public static final String	IS_LOCAL 	= "false"; // デプロイする際はfalseにすること
	
	public static final String	MAIL_SMTP_HOST 	= "127.0.0.1";
	//public static final String	MAIL_FROM 	= "g_alert@brainpad.co.jp ";
	public static final String	MAIL_FROM 	= "arata.hasebe@brainpad.co.jp"; // google appsでのメール送信の場合、mail_fromは有効なGアカウントのメアドでないとだめっぽい
	public static final String	MAIL_TO 	= "arata.hasebe@brainpad.co.jp";

	

	
	

}

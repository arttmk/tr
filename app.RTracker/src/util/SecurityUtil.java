package util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mail.MailSender;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 各セキュリティ対策メソッドのライブラリ
 * @author arata.hasebe
 */

public class SecurityUtil {

	/*
	 * XSSで問題になる文字が含まれているか
	 * 
	 */
	 public static boolean hasXssChars(String str) {
		    if (str.matches(".*[<>&\"'].*")){
		      return true;
		    } else {
		      return false;
		    }
	 }

	 // 日付け形式チェック
	 public static boolean checkDate(String strDate) {
		    if (strDate == null || strDate.length() != 10) {
		    	return false;
		    }
		    //strDate = strDate.replace('-', '/');
		    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    // 日付/時刻解析を厳密に行うかどうかを設定する。
		    format.setLenient(false);
		    try {
		        format.parse(strDate);
		        return true;
		    } catch (Exception e) {
		        return false;
		    }
	}
	 
	 
	/*
	 * errorメール送信処理
	 * 
	 */
	
	public static void sendErrorMail(String err){
		// エラーがなければreturn
		if(err.length() == 0) return;
		
		// エラーメール送信
		Properties props = new Properties();
		Session sess = Session.getDefaultInstance(props, null);
		
		String msgBody = err;

		try {
		    Message msg = new MimeMessage(sess);
		    msg.setFrom(new InternetAddress(UtilConfig.MAIL_FROM, "Tracker Admin"));
		    msg.addRecipient(Message.RecipientType.TO,
		                     new InternetAddress(UtilConfig.MAIL_TO, "Dear."));
		    msg.setSubject(UtilConfig.SYSTEM_NAME);
		    msg.setText(msgBody);
		    Transport.send(msg);
		
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	

}

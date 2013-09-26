package mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.UtilConfig;
import util.UtilMisc;



/**
 * メール送信
 * @author arata.hasebe
 *
 */
public class MailSender{
	public static final String SMTP_HOST		= "mail_smtp_host";
	public static final String MAIL_FROM		= "mail_from";
	public static final String MAIL_TO		= "mail_to";
	public static final String MAIL_ENCODE	= "iso-2022-jp";

	private String smtp_host = UtilConfig.MAIL_SMTP_HOST;
	private String from_address = UtilConfig.MAIL_FROM;
	private String to_address = UtilConfig.MAIL_TO;
	private String title = null;
	private String text = null;
	private String from_name = null;

	/**
	 * コンストラクタ
	 * @param confMap
	 */
	public MailSender(HashMap confMap){
		this.setSmtpHost((String)confMap.get(SMTP_HOST));
		this.setFromAddress((String)confMap.get(MAIL_FROM));
		this.setToAddress((String)confMap.get(MAIL_TO));
		this.setFromName((String)confMap.get(UtilConfig.SYSTEM_NAME));
	}
	public MailSender(){
	}

	/**
	 * 件名と内容を指定して送信
	 * @param title
	 * @param text
	 */
	public void send(String title, String text){
		this.setTitle(title);
		this.setText(text);
		this.send();
	}

	/**
	 * 送信処理
	 *
	 */
	public void send(){
		// 値のチェック
		if(UtilMisc.isEmpty(this.smtp_host)) return;
		if(UtilMisc.isEmpty(this.from_address)) return;
		if(UtilMisc.isEmpty(this.to_address)) return;
		if(UtilMisc.isEmpty(this.title)) return;
		if(UtilMisc.isEmpty(this.text)) return;

		try{
	        // SMTPサーバーのアドレスを指定
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", this.smtp_host);
	        Session session=Session.getDefaultInstance(props, null);
	        MimeMessage mimeMessage=new MimeMessage(session);

	        // 送信元メールアドレスと送信者名を指定
	        mimeMessage.setFrom(new InternetAddress(this.from_address, this.from_name, MAIL_ENCODE));

	        // 送信先メールアドレスを指定（宛先指定）
	        Address[] arrAddress = getAddressArray(this.to_address);
	        if(arrAddress.length == 0) return;
	        mimeMessage.setRecipients(Message.RecipientType.TO, arrAddress);

	        // メールのタイトルを指定
	        mimeMessage.setSubject(this.title, MAIL_ENCODE);

	        // メールの内容を指定
	        mimeMessage.setText(this.text + "\n", MAIL_ENCODE);

	        // メールの形式を指定
	        mimeMessage.setHeader("Content-Type","text/plain");
	        mimeMessage.setHeader("Content-Transfer-Encoding", "7bit");

	        // 送信日付を指定
	        mimeMessage.setSentDate(new Date());

	        // 送信します
	        Transport.send(mimeMessage);

		}catch(Exception e){

		}
	}

	/**
	 * カンマ区切りの複数アドレス文字列をアドレス型の配列に変換
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private Address[] getAddressArray(String address) throws Exception {
		String[] strArr = UtilMisc.splitter(address);
		ArrayList list = new ArrayList();
		for (int i = 0; i < strArr.length; i++) {
			if(strArr[i].length() == 0) continue;
			list.add(new InternetAddress(strArr[i]));
		}
		Address[] addr = new Address[list.size()];
		for(int i = 0; i < addr.length; i++) { addr[i] = (Address)list.get(i); }
		return addr;
	}

	public void setFromAddress(String from_address) {
		this.from_address = from_address;
	}

	public void setFromName(String from_name) {
		this.from_name = from_name;
	}

	public void setSmtpHost(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setToAddress(String to_address) {
		this.to_address = to_address;
	}
}
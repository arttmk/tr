package simulator;


import item.LoginUser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import logic.CertificationLogic;

import org.apache.click.control.Button;
import org.apache.click.control.Checkbox;
import org.apache.click.control.FileField;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Label;
import org.apache.click.control.Option;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Radio;
import org.apache.click.control.RadioGroup;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;

import com.google.api.services.bigquery.model.QueryRequest;
import com.google.api.services.bigquery.model.QueryResponse;

import bigQuery.BigQueryApiAccess;
import bigQuery.BigQueryWebServerAuthDemo;

import dao.CampaignDao;
import dao.CompanyDao;
import dao.LoginUserDao;

import servlet.LoadServlet;
import util.DataUtil;
import util.FrameConst;

import util.MessageConst;
import util.SecurityUtil;
import util.UtilConfig;
import util.UtilConst;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class UserAuth extends org.apache.click.Page {

	private static final long serialVersionUID = 1992500877691011798L;

	
	private Form form = new Form("form");
	
	private PasswordField password;
	private Submit ok;
	
	private Label errMsg;

	private HiddenField hid_camp_id = new HiddenField("hid_camp_id", String.class);
	
	
	@Bindable protected String id;
	
	public UserAuth(){

		password = new PasswordField("password","password");
		password.setSize(10);
		password.setMaxLength(10);
		form.add(password);
		
		ok = new Submit("ok","OK",this,"do_auth");
		form.add(ok);
		
		form.add(hid_camp_id);
	    
	    this.addControl(form);
	}

	@Override
	public void onGet() { 
		// timeoutの場合、メッセージを表示
    	if (id != null) {
    		
    		this.hid_camp_id.setValue(id);
    		
    	}
		
		super.onGet();
	}
	
	 /**
	  * ログイン処理
	  *
	  */
	public boolean do_auth(){
		
		CampaignDao dao = new CampaignDao();

		
		//System.out.println("SHA-1   = " + DigestUtils.shaHex(this.password.getValue())); // 暗号化されたパスワードを確認
		// パスワードは暗号化して格納
		//int camp_cnt = dao.get_camapaign_count(this.hid_camp_id.getValue(), DigestUtils.shaHex(this.password.getValue()));
		int camp_cnt = dao.get_camapaign_count(this.hid_camp_id.getValue(), this.password.getValue());
		
		if(camp_cnt == 0){
			errMsg = new Label("errMsg", MessageConst.campaign_password_err_msg);
			form.add(errMsg);
			return true;
		}else{
			// セッション情報の格納
			HttpServletRequest req = this.getContext().getRequest();
			HttpSession session = req.getSession();
			
			session.setAttribute(UtilConst.Session_user_auth, this.password.getValue());

			
			// キャンペーン選択画面へ遷移(getにしたいので、setRedirectで)
			//setForward("campaign_select.htm");
			String path = getContext().getPagePath(ShowGraph.class);
			path += "?id=" + this.hid_camp_id.getValue(); // campaignIDを渡す
			path += "&mode=" + FrameConst.disp_mode;
			
			setRedirect(path);
		}

		
		return true;
		
	}
	

}
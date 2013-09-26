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


public class Login extends org.apache.click.Page {

	private static final long serialVersionUID = 1992500877691011798L;

	
	private Form form = new Form("form");
	
	private TextField login_id;
	private PasswordField password;
	private Submit login;
	
	private Label errMsg;
    
	private CertificationLogic certify = new CertificationLogic();

	
	@Bindable protected String out;
	
	public Login(){

		login_id = new TextField("login_id","login_id");
		login_id.setSize(50);
		login_id.setMaxLength(50);
		form.add(login_id);
		
		password = new PasswordField("password","password");
		password.setSize(10);
		password.setMaxLength(10);
		form.add(password);
		
		login = new Submit("login","Login",this,"do_login");
		form.add(login);


	    
	    this.addControl(form);
	}

	@Override
	public void onGet() { 
		// timeoutの場合、メッセージを表示
		if(out != null && out.equals(UtilConst.Timeout)){
			errMsg = new Label("errMsg", MessageConst.timeoutMsg);
			form.add(errMsg);
		}
		// token不正の場合、メッセージを表示
		if(out != null && out.equals(UtilConst.TokenError)){
			errMsg = new Label("errMsg", MessageConst.tokenErrMsg);
			form.add(errMsg);
		}
		
		super.onGet();
	}
	
	
//	@Override
//	public void onRender() { 
//		// apiアクセスチェック
//		if(BigQueryApiAccessToInstallApp.token == null){
//			errMsg = new Label("errMsg", MessageConst.BQTokenNotExistMsg);
//			form.add(errMsg);
//		}
//		
//		
//		super.onRender();
//		
//	}
	 /**
	  * ログイン処理
	  *
	  */
	public boolean do_login(){
		
		LoginUserDao dao = new LoginUserDao();
		CompanyDao company_dao = new CompanyDao();
		
		
//		String query  = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
//		System.out.println(query);
//		QueryRequest request = new QueryRequest();
//		request.setQuery(query);
//		// テストしたら結構タイムアウトしたので、クエリのタイムアウト時間を伸ばしました。
//		request.setTimeoutMs(1000L * 240);
//
//		// BigQueryのAPIを実行してクエリ結果を取得する
//		try {
//			QueryResponse queryResponse = BigQueryWebServerAuthDemo.bigquery.jobs().query("427471091127", request).execute();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		// XSSチェック
		if(!this.login_id.getValue().isEmpty() && SecurityUtil.hasXssChars(this.login_id.getValue())){
			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
			form.add(errMsg);
			return true;
		}
		
		
		//System.out.println("SHA-1   = " + DigestUtils.shaHex(this.password.getValue())); // 暗号化されたパスワードを確認
		// パスワードは暗号化して格納
		LoginUser loginUser = dao.login_confirm(this.login_id.getValue(), DigestUtils.shaHex(this.password.getValue()));
		
		if(loginUser.getId() == 0){
			errMsg = new Label("errMsg", MessageConst.login_err_msg);
			form.add(errMsg);
			return true;
		}else{
			// セッション情報の格納
			HttpServletRequest req = this.getContext().getRequest();
			HttpSession session = req.getSession();
			
			session.setAttribute(UtilConst.Session_user_id, loginUser.getId());
			session.setAttribute(UtilConst.Session_user_name, loginUser.getName());
			
			// company情報取得
			String company_name = company_dao.get_companyName(loginUser.getCompany_id());
			session.setAttribute(UtilConst.Session_company_name, company_name);
			
			// token作成
			try {
				certify.createFixedToken();
			} catch (Exception e) {
				SecurityUtil.sendErrorMail(e.toString());
				LoadServlet.logger.severe(e.toString()); 
			}
			
			
			// キャンペーン選択画面へ遷移(getにしたいので、setRedirectで)
			//setForward("campaign_select.htm");
			String path = getContext().getPagePath(CampaignSelect.class);
			setRedirect(path);
		}

		
		return true;
		
	}
	

}
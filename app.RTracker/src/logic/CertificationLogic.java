package logic;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.click.control.Form;
import org.apache.click.control.Label;
import org.apache.click.control.Submit;

import servlet.LoadServlet;
import simulator.Login;
import util.UtilConst;

/*
 * ユーザー認証処理もろもろ
 *
 */

public class CertificationLogic extends org.apache.click.Page {
	
	private static final long serialVersionUID = -9022059467100583214L;

	private Label login_user_name;
	private Label login_user_company;
	private Submit logout;
	
	private static int TOKEN_LENGTH = 16;//16*2=32バイト
	
	/**
	 * ログインチェック処理
	 * 
	 * @param form
	 * 
	 * @return path
	 */
	public String checkLoginInfo(Form form){
		String path = "";
		
	    if(this.getContext().getSessionAttribute(UtilConst.Session_user_id) != null){
			
			// ログイン情報設定
			login_user_name = new Label("login_user_name", this.getContext().getSessionAttribute(UtilConst.Session_user_name).toString());
			form.add(login_user_name);
			login_user_company = new Label("login_user_company", this.getContext().getSessionAttribute(UtilConst.Session_company_name).toString());
			form.add(login_user_company);
			
		}else{
			if(!LoadServlet.isDebug){ // debugモードではない場合
		        path = getContext().getPagePath(Login.class);
		        path += "?out="+ UtilConst.Timeout;

			}

		}
		
	    return path;
	}
	
	
	/*
	 * ログアウト処理
	 * 
	 */
	public void clearLoginInfo(){

		this.getContext().setSessionAttribute(UtilConst.Session_user_id, null);
		this.getContext().setSessionAttribute(UtilConst.Session_user_name, null);
		this.getContext().setSessionAttribute(UtilConst.Session_company_name, null);

		
	}
	
	/*
	 * ref.tokenによるセキュリティ（固定token）
	 * 1.画面表示token発行
	 * 2.sessinoへtoken格納
	 * 3.画面onGetでhiddenへtoken格納
	 * 4.画面onRenderでsessionとhiddenのtokenを比較
	 */
	
	/*
	 * 固定token作成処理
	 * 
	 */
	public void createFixedToken() throws Exception {
		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer buf = new StringBuffer();
		SecureRandom random = null;
		 
		random = SecureRandom.getInstance("SHA1PRNG");
		random.nextBytes(token);
		 
		for (int i = 0; i < token.length; i++) {
		buf.append(String.format("%02x", token[i]));
		}

		// sessionへtoken格納
		//System.out.println("token:"+ buf.toString());
		this.getContext().setSessionAttribute(UtilConst.Session_token,  buf.toString());
		
		//return buf.toString();
	}
	
	/*
	 * tokenチェック処理
	 * 
	 */
	public String checkToken(String hid_token){
		String path = "";
		boolean checkNG = false;
		
		if(!LoadServlet.isDebug){ // debugモードではない場合
			// sessionチェック
			if(this.getContext().getSessionAttribute(UtilConst.Session_token) == null || this.getContext().getSessionAttribute(UtilConst.Session_token).toString().isEmpty()) checkNG = true;
			// hiddenチェック
			if(!checkNG){
				if(hid_token == null || hid_token.isEmpty()) checkNG = true;
			}
			// sessionのtokenとhiddenのtokenが合致するか
			if(!checkNG){
				if(!this.getContext().getSessionAttribute(UtilConst.Session_token).toString().equals(hid_token)) checkNG = true;
			}
			
			
			if(checkNG){
		        path = getContext().getPagePath(Login.class);
		        path += "?out=" + UtilConst.TokenError;
			}
		
		}
		return path;
	}
	


}

package simulator;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import logic.CertificationLogic;

import org.apache.click.control.Form;
import org.apache.click.control.Label;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.apache.commons.io.IOUtils;

import util.MessageConst;
import util.SecurityUtil;

import bigQuery.BigQueryApiAccess;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.BigqueryScopes;


public class Oath extends org.apache.click.Page {


	private static final long serialVersionUID = -2019971370171611036L;

	private Form form = new Form("form");
	
	private TextField oath_url;
	private TextField oath_token;
	private Submit oath;
	
	private Label errMsg;
    
	private CertificationLogic certify = new CertificationLogic();

	private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";
	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	
	
	public Oath(){

		oath_url = new TextField("oath_url","oath_url");
		form.add(oath_url);
		oath_token = new TextField("oath_token","oath_token");
		form.add(oath_token);
		
		oath = new Submit("oath","Oath",this,"do_oath");
		form.add(oath);


	    
	    this.addControl(form);
	}

	 /**
	  * BQ用oath処理
	  *
	  */
	public boolean do_oath(){

		
		if(!this.oath_token.getValue().isEmpty() && SecurityUtil.hasXssChars(this.oath_token.getValue())){
			errMsg = new Label("errMsg", MessageConst.XSSErrMsg);
			form.add(errMsg);
			return true;
		}
		
		
		final String CLIENTSECRETS_LOCATION = "client_secrets.json";
		final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
		
		if(!this.oath_token.getValue().isEmpty()){
			
			// oath_tokeの値があるなら、それを設定
			
			try {
				BigQueryApiAccess.token = this.oath_token.getValue();
				BigQueryApiAccess.setCredential();
			} catch (IOException e) {

			}
			
			
		}
		
		

		try {
			InputStream resourceInputStream = BigQueryApiAccess.class.getResourceAsStream(CLIENTSECRETS_LOCATION);

			String jsonString = IOUtils.toString(resourceInputStream);
			InputStream stream = new ByteArrayInputStream(jsonString.getBytes("UTF-8"));

		// ClientSecretを取得する
		BigQueryApiAccess.clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), stream);
		
		//Credential credential = BigQueryRestApiSample.getCredential(clientSecrets);
		
		String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(BigQueryApiAccess.clientSecrets , REDIRECT_URI,
				Collections.singleton(BigqueryScopes.BIGQUERY)).build();
		
		this.oath_url.setValue(authorizeUrl);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	
	

}
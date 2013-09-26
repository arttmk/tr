package bigQuery;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import servlet.LoadServlet;
import util.UtilConfig;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.QueryRequest;
import com.google.api.services.bigquery.model.QueryResponse;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

/**
 * BigQueryのREST-APIのサンプルコード

 * 
 */
public class BigQueryApiAccess {
	// Your Google Developer Project number
	// ApisConsoleのoverviewに書かれているProject Numberを指定する
	private static final String PROJECT_NUMBER = "427471091127";

	// Load Client ID/secret from client_secrets.json file
	private static final String CLIENTSECRETS_LOCATION = "client_secrets.json";

	// Load Client ID/secret from client_secrets.json file
	private static final String SAMPLE_QUERY_LOCATION = "sample.sql";

	// Load Client ID/secret from client_secrets.json file
	private static final String REFLESH_TOKEN_FILE_NAME = "token.dat";

	// For installed applications, use the redirect URI
	// "urn:ietf:wg:oauth:2.0:oob"
	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	// Objects for handling HTTP transport and JSON formatting of API calls
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private static final String BIGQUERY_SCOPE = "https://www.googleapis.com/auth/bigquery";

	public static String token;
	public static Credential credential;
	public static GoogleClientSecrets clientSecrets;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String query  = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
		
		
		if ("**********".equals(PROJECT_NUMBER)) {
			System.err.println("PROJECT_NUMBERを設定する必要があります。");
			return;
		}
		try {
			doQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static List<TableRow> doQuery(String query) throws Exception {
		List<TableRow> rows = null;
		Bigquery bigquery;
		
		if(Boolean.valueOf(UtilConfig.IS_LOCAL)){
			/**************ローカル用↓**************/
			// Sample通りに「getResourceAsStream」の返り値のInputStreamをそのまま使ったところ、
			// 「GoogleClientSecrets.load」でエラーになる。
			// そのためちょっと変なコードになってますが、とりあえずこれで動く
			InputStream resourceInputStream = BigQueryApiAccess.class.getResourceAsStream(CLIENTSECRETS_LOCATION);
			if (resourceInputStream == null) {
				System.err.println("「src/main/resources/jp/vier/sample/bigquery」以下に「" + CLIENTSECRETS_LOCATION
						+ "」を配置する必要があります。");
				
				return rows;
			}
			
			String jsonString = IOUtils.toString(resourceInputStream);
			InputStream stream = new ByteArrayInputStream(jsonString.getBytes("UTF-8"));
	
			// ClientSecretを取得する
			if(clientSecrets == null){
				clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), stream);
				
			}
	
			// Credentialを取得する
			// public変数から取得する版（ローカル用）
			if(credential == null){
				credential = getCredential(clientSecrets);
			}
			
			// Use the credential to create an authorized BigQuery client
			bigquery = new Bigquery(HTTP_TRANSPORT, JSON_FACTORY, credential);
			/**************ローカル用↑**************/
		
		}else{
			/**************サーバ用****************/
			// Service Account認証でCredentialを取得する（デプロイ版用）
			AppIdentityCredential credential = new AppIdentityCredential(BIGQUERY_SCOPE);
			
			// Use the credential to create an authorized BigQuery client(credentialがローカルと異なる)
			bigquery = new Bigquery(HTTP_TRANSPORT, JSON_FACTORY, credential);

		}
		
		// Use the credential to create an authorized BigQuery client
		//Bigquery bigquery = new Bigquery(HTTP_TRANSPORT, JSON_FACTORY, credential);
		
		// Create a query statement and query request object
		// クエリを取得する
		// URL queryUrl = BigQueryRestApiSample.class.getResource(SAMPLE_QUERY_LOCATION);
		//String query = queryUrl.toExternalForm();
		System.out.println(query);
		QueryRequest request = new QueryRequest();
		request.setQuery(query);
		// テストしたら結構タイムアウトしたので、クエリのタイムアウト時間を伸ばす
		request.setTimeoutMs(1000L * 240);

		// BigQueryのAPIを実行してクエリ結果を取得する
		QueryResponse queryResponse = bigquery.jobs().query(PROJECT_NUMBER, request).execute();

		// クエリ結果を出力する
		rows = queryResponse.getRows();

		// info test
		//LoadServlet.logger.warning("execQuery:" + query);

		return rows;
		
//		System.out.println("Query Results:\n----------------");
//		if (rows != null) {
//			LoadServlet.logger.warning("rows:" + rows.size());
//			for (TableRow row : rows) {
//				for (TableCell field : row.getF()) {
//					System.out.printf("%-50s", field.getV());
//					//LoadServlet.logger.warning((String) field.getV()); 
//				}
//				System.out.println();
//			}
//		} else {
//			System.out.println("no results.");
//		}
		
		
	}

	/**
	 * 
	 * @param clientSecrets
	 * @return
	 * @throws IOException
	 */
	public static Credential getCredential(GoogleClientSecrets clientSecrets) throws IOException {

		// 既存のリフレッシュトークンの取得を試みる
		String storedRefreshToken = loadRefleshToken(REFLESH_TOKEN_FILE_NAME);

		// リフレッシュトークンの有無をチェックして、取得できなければOAuthの認可フローを開始する
		if (storedRefreshToken == null) {
			// Create a URL to request that the user provide access to the BigQuery API
			String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(clientSecrets, REDIRECT_URI,
					Collections.singleton(BigqueryScopes.BIGQUERY)).build();

			// Prompt the user to visit the authorization URL, and retrieve the provided authorization code
			System.out.println("Paste this URL into a web browser to authorize BigQuery Access:\n" + authorizeUrl);
			System.out.println("... and paste the code you received here: ");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//ユーザーが入力した認可コード
			String authorizationCode = in.readLine();

			// Create a Authorization flow object
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					clientSecrets, Arrays.asList(BigqueryScopes.BIGQUERY)).setAccessType("offline")
					.setApprovalPrompt("force").build();
			// Exchange the auth code for an access token and refesh token
			GoogleTokenResponse response = flow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI)
					.execute();
			Credential credential = flow.createAndStoreCredential(response, null);

			// リフレッシュトークンをファイルに保存
			saveRefleshToken(REFLESH_TOKEN_FILE_NAME, credential.getRefreshToken());

			return credential;

			// リフレッシュトークンを取得できた場合
		} else {
			// リフレッシュトークンを使って新しいアクセストークンを取得する
			GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
					.setJsonFactory(JSON_FACTORY).setClientSecrets(clientSecrets).build()
					.setFromTokenResponse(new TokenResponse().setRefreshToken(storedRefreshToken));
			credential.refreshToken();

			return credential;
		}
	}

	/**
	 * RefleshTokenを取得する
	 * 
	 * @param path
	 * @return
	 */
	public static String loadRefleshToken(String path) {
//		try {
//			String refleshToken = FileUtils.readFileToString(new File(path), "UTF-8");
//			return refleshToken;
//		} catch (IOException e) {
//			return null;
//		}
		// public変数に渡すようにした
		String refleshToken = token;
		return refleshToken;
	}

	/**
	 * RefleshTokenを保存する
	 * 
	 * @param path
	 * @param refleshToken
	 * @return
	 */
	public static void saveRefleshToken(String path, String refleshToken) throws IOException {
		//FileUtils.write(new File(path), refleshToken, "UTF-8");
		// public変数に渡すようにした
		token = refleshToken;
	}
	
	
	/**
	 * 
	 * @param clientSecrets
	 * @return
	 * @throws IOException
	 */
	public static void setCredential() throws IOException {

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, Arrays.asList(BigqueryScopes.BIGQUERY)).setAccessType("offline")
				.setApprovalPrompt("force").build();
		// Exchange the auth code for an access token and refesh token
		GoogleTokenResponse response = flow.newTokenRequest(token).setRedirectUri(REDIRECT_URI)
				.execute();
		credential = flow.createAndStoreCredential(response, null);

	}
	
	
}

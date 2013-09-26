package bigQuery;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.http.json.JsonHttpRequest;
//import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryRequest;



@SuppressWarnings("serial")
public class Bigquery_service_accounts_demoServlet extends HttpServlet {

  // ENTER YOUR PROJECT ID HERE
  private static final String PROJECT_NUMBER = "427471091127";

  private static final HttpTransport TRANSPORT = new NetHttpTransport();
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final String BIGQUERY_SCOPE = "https://www.googleapis.com/auth/bigquery";

  AppIdentityCredential credential = new AppIdentityCredential(BIGQUERY_SCOPE);
//  Bigquery bigquery = Bigquery.builder(TRANSPORT,JSON_FACTORY)
//      .setHttpRequestInitializer(credential)
//      .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
//        public void initialize(JsonHttpRequest request) {
//          BigqueryRequest bigqueryRequest = (BigqueryRequest) request;
//          bigqueryRequest.setPrettyPrint(true);
//        }
//      }).build();
  
//  Bigquery bigquery = Bigquery.Builder(new NetHttpTransport(), new JacksonFactory(),  HttpRequestInitializer httpRequestInitializer)
//	      .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
//	        public void initialize(JsonHttpRequest request) {
//	          BigqueryRequest bigqueryRequest = (BigqueryRequest) request;
//	          bigqueryRequest.setPrettyPrint(true);
//	        }
//	      }).build();
  
  
  
  Bigquery bigquery = new Bigquery(TRANSPORT, JSON_FACTORY, credential);


  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    resp.getWriter().println(bigquery.datasets()
      .list(PROJECT_NUMBER)
      .execute().toString());
	  
	  
//		// Create a query statement and query request object
//		// クエリを取得する
//		// URL queryUrl = BigQueryRestApiSample.class.getResource(SAMPLE_QUERY_LOCATION);
//		//String query = queryUrl.toExternalForm();
//		String query  = "SELECT TOP(title, 10) as title, COUNT(*) as revision_count FROM [publicdata:samples.wikipedia] WHERE wp_namespace = 0;";
//		System.out.println(query);
//		QueryRequest request = new QueryRequest();
//		request.setQuery(query);
//		// テストしたら結構タイムアウトしたので、クエリのタイムアウト時間を伸ばす
//		request.setTimeoutMs(1000L * 240);
//
//		// BigQueryのAPIを実行してクエリ結果を取得する
//		QueryResponse queryResponse = bigquery.jobs().query(PROJECT_NUMBER, request).execute();
//
//		// クエリ結果を出力する
//		List<TableRow> rows = queryResponse.getRows();
//
//		System.out.println("Query Results:\n----------------");
//		if (rows != null) {
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
}
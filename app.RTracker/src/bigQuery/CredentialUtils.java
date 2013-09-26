package bigQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AppEngineCredentialStore;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.appengine.api.users.UserServiceFactory;

public class CredentialUtils {

	 static final HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();
	  static final JsonFactory JSON_FACTORY = new JacksonFactory();
	  static final String RESOURCE_LOCATION = "client_secrets_web.json";
	  private static GoogleClientSecrets clientSecrets = null;

	  static String getRedirectUri(HttpServletRequest req) {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/oauth2callback");
	    return url.build();
	  }

	  static GoogleClientSecrets getClientCredential() throws IOException {
	    if (clientSecrets == null) {
	      InputStream inputStream = new FileInputStream(new File(RESOURCE_LOCATION));
	      Preconditions.checkNotNull(inputStream, "Cannot open: %s" + RESOURCE_LOCATION);
	      clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, inputStream);
	    }
	    return clientSecrets;
	  }

	  static GoogleAuthorizationCodeFlow newFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	        getClientCredential(), Collections.singleton(BigqueryScopes.BIGQUERY)).setCredentialStore(
	        new AppEngineCredentialStore()).setAccessType("offline").build();
	  }

	  static Bigquery loadbigquery() throws IOException {
	    String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
	    Credential credential = newFlow().loadCredential(userId);
	    return new Bigquery.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
	  }

	  
}
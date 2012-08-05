package info.ogorzalek.birds.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.os.Handler;

public class Backend {

	public static final int TIMEOUT_CONNECTION = 1000;
	public static final int TIMEOUT_SOCKET = 1000;
	
	public static final String BASE_URL = "http://192.168.1.103/birds/index.php/api/01/";
	
	private static Backend instance;
	
	private Handler handler = new Handler();
	private Executor executor = Executors.newCachedThreadPool();
	private HttpClient httpClient = getHttpClient();
	
	private Context context;
	
	private Backend(Context context)
	{
		this.context = context;
	}
	
	public static Backend getInstance(Context context)
	{
		if(instance == null)
			instance = new Backend(context);
		return instance;
	}
	
	// HTTP METHODS
	
	public void get(final OnHttpGetResponseListener listener, final String url)
	{
		Runnable action = new Runnable() {
			public void run() {
				try {
					HttpUriRequest request = new HttpGet(url);
					//request.setHeader("Accept", "application/json");
				
					HttpResponse response = httpClient.execute(request);
					InputStream is = response.getEntity().getContent();
					
					String dataString = getStringResponse(is);
					final JSONObject dataObject = new JSONObject(dataString);
					
					if(dataObject.has("error_object"))
					{
						throw new BackendException(dataString, url);
					}
					
					handler.post(new Runnable() {
						public void run() {
							listener.onResponse(dataObject);
						}
					});
					
					
				} catch (final ClientProtocolException e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				} catch (final IOException e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				} catch (final JSONException e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				} catch (final BackendException e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				}
				
			}
		};
		executor.execute(action);
	}
	

	
	private HttpClient getHttpClient() {
		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET);

		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpClient mHttpClient = new DefaultHttpClient(params);
		return mHttpClient;
	}
	
	private static String getStringResponse(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append((line + "\n"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	public static class MetaResponse
	{
		public String previous;
		public String next;
		public int total;
		public int offset;
		public int limit;
		
		public static MetaResponse fromJSON(JSONObject json)
		{
			Gson gson = new Gson();
			MetaResponse metaResponse = gson.fromJson(json.toString(), MetaResponse.class);
			return metaResponse;
		}
		
	}
	
	public static class BackendException extends Exception
	{
		public String dataString;
		public String action;
		
		public BackendException(String d, String a)
		{
			this.dataString = d;
			this.action = a;
		}
	}
	
	public interface OnHttpGetResponseListener
	{
		public void onResponse(JSONObject data);
		public void onError(Exception e);
	}
	
}

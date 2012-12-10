package info.ogorzalek.birds.general;

import info.ogorzalek.birds.models.ApiKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class Backend {

	public static final String BASE_URL = "http://192.168.1.100/birds/index.php/api/01/";
	// public static final String BASE_URL =
	// "http://ogorzalek.info/birds/index.php/api/01/";

	public Handler handler = new Handler();
	Executor executor = Executors.newCachedThreadPool();

	HttpClient httpClient;

	public static final String PREFS_NAME = "authprefs";
	public static final String PREFS_API_KEY = "api_key";
	public static final String PREFS_DEVICE_ID = "device_id";

	//
	public static final String BASE_MEDIA_URL = "http://10.8.200.241/qr/pictures/";

	private static String apiKey = null;

	@SuppressWarnings("unused")
	private static String deviceId = null; // na razie nie uzywane, ale moze sie
											// przydac

	public static final int TIMEOUT_CONNECTION = 5000; // to sie chyba i tak nie
														// liczy
	public static final int TIMEOUT_SOCKET = 5000;
	public static final int MAX_CACHE_AGE = 10 * 60 * 1000; // ms

	private Context context;

	private static Backend instance;

	public static synchronized Backend instance(Context context) {
		if (instance == null)
			instance = new Backend(context);
		return instance;
	}

	public static synchronized void init(Context ctx) {
		if (instance == null)
			instance = new Backend(ctx);
	}

	private Backend(Context context) {
		this.context = context.getApplicationContext();

		HttpClientProvider httpClientProvider = new HttpClientProvider();
		httpClient = httpClientProvider.get(context);

	}

	public void getApiKey() {
		ApiKey key = ApiKey.getApiKey(context);
		apiKey = key.key;
	}

	// //////////////////////////////////////// // INNY STUFF //
	// //////////////////////////////////////////////// //

	public void put(final OnHttpResponseListener listener, final String url,
			final HttpEntity entity) {
		makeRequest(listener, url, REQUEST_TYPE.PUT, entity);
	}

	public void post(final OnHttpResponseListener listener, final String url,
			final HttpEntity entity) {
		makeRequest(listener, url, REQUEST_TYPE.POST, entity);
	}

	public void get(final OnHttpResponseListener listener, final String url) {
		makeRequest(listener, url, REQUEST_TYPE.GET, null);
	}

	public enum REQUEST_TYPE {
		GET, POST, PUT
	}

	private void makeRequest(final OnHttpResponseListener listener,
			final String url, final REQUEST_TYPE requestType,
			final HttpEntity entity) {

		executor.execute(new Runnable() {
			public void run() {
				try {
					
					getApiKey();

					// Autentyfikacja :D
					String requestUrl = url;
					if (requestUrl.contains("?")) {
						requestUrl = requestUrl + "&api_key=" + apiKey;
					} else {
						requestUrl = requestUrl + "?api_key=" + apiKey;
					}

					HttpUriRequest request;
					if (requestType == REQUEST_TYPE.GET) {
						request = new HttpGet(requestUrl);
					} else if (requestType == REQUEST_TYPE.POST) {
						request = new HttpPost(requestUrl);
					} else if (requestType == REQUEST_TYPE.PUT) {
						request = new HttpPut(requestUrl);
					} else {
						throw new RuntimeException("Wtf?!");
					}

					if (entity != null) {
						if (request instanceof HttpPost) {
							((HttpPost) request).setEntity(entity);
						} else if (request instanceof HttpPut)
							((HttpPut) request).setEntity(entity);
						else {
							throw new RuntimeException("WTF?");
						}
					}

					request.setHeader("Accept", "application/json");

					// === NETWORKING ===
					final HttpResponse response = httpClient.execute(request);

					final int responseCode = response.getStatusLine()
							.getStatusCode();
					String dataString = EntityUtils.toString(response
							.getEntity());

					// Log.d("ddd", dataString);

					if (responseCode == 200
							|| ((responseCode == 201 || responseCode == 202))) {
						final JSONObject data = new JSONObject(dataString);

						if (data.has("error_message")) {

							handler.post(new Runnable() {
								public void run() {
									listener.onError(new BackendException(data
											.toString()));
								}
							});

						} else {

							handler.post(new Runnable() {
								public void run() {
									listener.onResponse(data);
								}
							});

						}

					} else {

						handler.post(new Runnable() {
							public void run() {
								listener.onError(new ClientProtocolException(
										"Response code: " + responseCode));
							}
						});

					}

				} catch (final ClientProtocolException e) {
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
				} catch (final IOException e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				} catch (final Exception e) {
					handler.post(new Runnable() {
						public void run() {
							listener.onError(e);
						}
					});
				}
			}
		});
	}

	@SuppressWarnings("serial")
	public static class BackendException extends Exception {
		public BackendException(String s) {
			super(s);
		}
	}

	@SuppressWarnings("serial")
	public static class NoDataConnetionException extends Exception {
		public NoDataConnetionException() {
			super();
		}

		public NoDataConnetionException(String desc) {
			super(desc);
		}
	}

	public interface OnHttpResponseListener {
		public void onResponse(JSONObject data);

		public void onError(Exception e);
	}

}

package info.ogorzalek.birds.general;



import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class HttpClientProvider {

	// Wait this many milliseconds max for the TCP connection to be established
	private static final int CONNECTION_TIMEOUT = 5 * 1000;

	// Wait this many milliseconds max for the server to send us data once the
	// connection has been established
	private static final int SO_TIMEOUT = 5 * 2 * 1000;

	private String getUserAgent(Context context,
			String defaultHttpClientUserAgent) {
		String versionName;
		try {
			versionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
		StringBuilder ret = new StringBuilder();
		ret.append(context.getPackageName());
		ret.append("/");
		ret.append(versionName);
		ret.append(" (");
		ret.append("Linux; U; Android ");
		ret.append(Build.VERSION.RELEASE);
		ret.append("; ");
		ret.append(Locale.getDefault());
		ret.append("; ");
		ret.append(Build.PRODUCT);
		ret.append(")");
		if (defaultHttpClientUserAgent != null) {
			ret.append(" ");
			ret.append(defaultHttpClientUserAgent);
		}
		return ret.toString();
	}

	public HttpClient get(final Context context) {
		AbstractHttpClient client = new DefaultHttpClient() {
			@Override
			protected ClientConnectionManager createClientConnectionManager() {
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

				HttpParams params = getParams();
				HttpConnectionParams.setConnectionTimeout(params,
						CONNECTION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
				HttpProtocolParams.setUserAgent(
						params,
						getUserAgent(context,
								HttpProtocolParams.getUserAgent(params)));
				return new ThreadSafeClientConnManager(params, registry);
			}
		};

		return client;
	}

}
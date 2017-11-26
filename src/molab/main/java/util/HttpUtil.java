package molab.main.java.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {
	
	private static final Logger log = Logger.getLogger(HttpUtil.class.getName());
	public static final String URI_UPDATE_RUNNER_STATE = "updateRunnerState";

	public static boolean download(String remote, String local) throws MalformedURLException {
		URL url = new URL(remote);
		InputStream is = null;
		FileOutputStream fs = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			is = conn.getInputStream();
			fs = new FileOutputStream(local);
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			int code = conn.getResponseCode();
			if(code == HttpStatus.SC_OK) {
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fs != null) {
				try {
					fs.close();
				} catch (IOException e) {}
			}
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return false;
	}
	
	public static String get(String url) {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(get);
			if(statusCode == HttpStatus.SC_OK) {
				return get.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			get.releaseConnection();
		}
		return null;
	}
	
	/**
	 * @deprecated error */
	public static String post(String uri, String... args) {
		String host = String.format(
				"http://%s:%s/%s/ctApi/%s", 
				Molab.getInstance().getProperty(Molab.CFG_WEB_SERVER), 
				Molab.getInstance().getProperty(Molab.CFG_WEB_PORT), 
				Molab.getInstance().getProperty(Molab.CFG_WEB_NAME),
				uri);
		Map<String, String> params = new HashMap<String, String>();
		if(args.length > 0) {
			for(String arg : args) {
				params.put(arg, arg);
			}
		}
		return post(host, params);
	}
	
	public static String post(String host, Map<String, String> params) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(host);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		if (!params.isEmpty()) {
			int i = 0;
			NameValuePair[] pairs = new NameValuePair[params.size()];
			for (String name : params.keySet()) {
				pairs[i++] = new NameValuePair(name, params.get(name));
			}
			post.addParameters(pairs);
		}
		try {
			int statusCode = client.executeMethod(post);
			if(statusCode == HttpStatus.SC_OK) {
				return post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
	public static String post(String host, File file) throws FileNotFoundException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(host) {
			public String getRequestCharSet() {
				return "UTF-8";
			}
		};
		Part[] parts = { new FilePart("file", file, null, "UTF-8") };
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		try {
			int statusCode = client.executeMethod(post);
			if(statusCode == HttpStatus.SC_OK) {
				return post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
	public static String post(String host, List<File> files) throws FileNotFoundException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(host) {
			public String getRequestCharSet() {
				return "UTF-8";
			}
		};
		Part[] parts = new Part[files.size()];
		for (int i = 0; i < files.size(); i++) {
			parts[i] = new FilePart("files", files.get(i), null, "UTF-8");
		}
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		try {
			int statusCode = client.executeMethod(post);
			if(statusCode == HttpStatus.SC_OK) {
				return post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
}

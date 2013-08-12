package com.appspot.diabeteselsewhere.helper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;

import android.util.Log;

public class DEServerHelper {
	private static final String DiabetesElsewhereEventURL = 
			"http://diabeteselsewhere.appspot.com";

	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];
	private static final String dtag = "DEServerHelper";
	private static JSONObject eventObject;

	public DEServerHelper () {

	}

	public DEServerHelper (JSONObject eo) {
		eventObject = eo;
	}

	public static class ApiException extends Exception
	{
		private static final long serialVersionUID = 1L;

		public ApiException (String msg)
		{
			super (msg);
		}

		public ApiException (String msg, Throwable thr)
		{
			super (msg, thr);
		}
	}

	public static String downloadFromServer(String... params)
	{
		String retval =null;
		//		String event = params[0];
		String url = DiabetesElsewhereEventURL + params[0];
		Log.d(dtag, "Fetching at "+url);
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
		HttpGet request = new HttpGet(url);

		try {
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				throw new ApiException("Invalid response from the server" +
						status.toString());
			}

			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			retval = new String(content.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retval;
	}

	public static Boolean uploadToServer(String url) throws MalformedURLException 
	{
		URL uploadUrl = new URL(DiabetesElsewhereEventURL + url);
		Log.d(dtag, "URL: " + uploadUrl);
		String output = eventObject.toString();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) uploadUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setFixedLengthStreamingMode(output.getBytes().length);
			conn.setRequestProperty("Content-Type", "application/json");
			Log.d(dtag, output);
			DataOutputStream os = new DataOutputStream (conn.getOutputStream());
			os.writeBytes(output);
			os.flush();
			os.close();
			String response = "";
			Scanner is = new Scanner(conn.getInputStream());
			while(is.hasNextLine())
				response += (is.nextLine());
			Log.d(dtag, response);
			return true;
		} catch(MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}

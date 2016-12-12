package org.iii.recaptcha;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaService {

	private String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	public boolean verify(String secretKey , String gRecaptchaResponse) {
		if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
			return false;
		}

		try {
			URL verifyUrl = new URL(SITE_VERIFY_URL);

			// Open Connection to URL
			HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

			// Add Request Header
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Data will be sent to the server.
			String postParams = "secret=" + secretKey + "&response=" + gRecaptchaResponse;
			// Send Request
			conn.setDoOutput(true);

			// Get the output stream of Connection
			// Write data in this stream, which means to send data to Server.
			OutputStream outStream = conn.getOutputStream();
			outStream.write(postParams.getBytes());

			outStream.flush();
			outStream.close();

			// Response code return from server.
			int responseCode = conn.getResponseCode();
			// System.out.println("responseCode=" + responseCode);

			// Get the InputStream from Connection to read data sent from the
			// server.
			InputStream is = conn.getInputStream();
			String jsonstr = IOUtils.toString(is, "UTF-8");
			JSONObject json = new JSONObject(jsonstr);

			// ==> {"success": true}
			 System.out.println("Response: " + json.toString());
			
			boolean success = json.getBoolean("success");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
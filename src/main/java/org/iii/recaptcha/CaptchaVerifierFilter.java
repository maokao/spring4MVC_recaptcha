package org.iii.recaptcha;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

public class CaptchaVerifierFilter extends OncePerRequestFilter {

	protected Logger logger = Logger.getLogger("filter");
	private Boolean useProxy = false;
	private String proxyPort;
	private String proxyHost;
	private String failureUrl;
	private CaptchaCaptureFilter captchaCaptureFilter;
	private String secretKey;

	private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	@Override
	public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		logger.debug("Captcha verifier filter");
		logger.debug("challenge: " + captchaCaptureFilter.getRecaptcha_challenge());
		logger.debug("response: " + captchaCaptureFilter.getRecaptcha_response());
		logger.debug("remoteAddr: " + captchaCaptureFilter.getRemoteAddr());

		// Assign values only when user has submitted a Captcha value
		if (captchaCaptureFilter.getRecaptcha_response() != null) {
			String gRecaptchaResponse = captchaCaptureFilter.getRecaptcha_response();
			// System.out.println(gRecaptchaResponse);

			RecaptchaService recaptchaserivce = new RecaptchaService();

			boolean verify = recaptchaserivce.verify(secretKey, gRecaptchaResponse);
			//System.out.println(verify);
			// Check if valid
			if (!verify) {
				logger.debug("Captcha is invalid!");

				// Redirect user to login page
				failureHandler.setDefaultFailureUrl(failureUrl);
				failureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("Captcha invalid!"));

			} else {
				logger.debug("Captcha is valid!");
			}

			// Reset Captcha fields after processing
			// If this method is skipped, everytime we access a page
			// CaptchaVerifierFilter will infinitely send a request to the
			// Google Captcha service!
			resetCaptchaFields();
		}

		// Proceed with the remaining filters
		chain.doFilter(req, res);
	}

	/**
	 * Reset Captcha fields
	 */
	public void resetCaptchaFields() {
		captchaCaptureFilter.setRemoteAddr(null);
		captchaCaptureFilter.setRecaptcha_challenge(null);
		captchaCaptureFilter.setRecaptcha_response(null);
	}

	public Boolean getUseProxy() {
		return useProxy;
	}

	public void setUseProxy(Boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public CaptchaCaptureFilter getCaptchaCaptureFilter() {
		return captchaCaptureFilter;
	}

	public void setCaptchaCaptureFilter(CaptchaCaptureFilter captchaCaptureFilter) {
		this.captchaCaptureFilter = captchaCaptureFilter;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
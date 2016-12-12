package org.iii.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service
public class  LoginFailureHandler implements AuthenticationFailureHandler {
  

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		try {
		// UsernamePasswordAuthenticationToken token =(UsernamePasswordAuthenticationToken) arg2.getAuthentication();
			//	 System.out.println("username="+token.getName());
			//	 System.out.println("password="+token.getCredentials());
		//	System.out.println(request.getRemoteAddr());
		}
		catch(Exception e){
		}
		 response.sendRedirect("/login2?error");
	}
}
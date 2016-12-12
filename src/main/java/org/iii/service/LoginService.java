package org.iii.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginService {
	
	@Inject
	private org.iii.repository.LoginRepository loginRepository;
	
	public String getUserName(String username) {
		
		try{
			String accountName = loginRepository.selectUser(username);
			return accountName;
			
		}
		catch(Exception e){
			System.out.print(e);
			return "error from LoginService";
		}
		
	}
	
	public String getUserEmail(String username) {
		
		try{
			String email = loginRepository.selectEmail(username);
			return email;
			
		}
		catch(Exception e){
			System.out.print(e);
			return "error from LoginService";
		}
		
	}

	public List getallUserinfo() {

			List alluserinfo = loginRepository.selectallUsers();
			return alluserinfo;
		
	}
	
	public int insertUser(String username, String password, String email, String enabled) {

		int updateCount = loginRepository.insertUser(username, password, email, enabled);
		return updateCount;
	
	}
	
	public void deleteUser(String username) {

		loginRepository.deleteUser(username);
	
	}

	public void customLogin(String username, String password, String security_num, HttpServletRequest request) {
		// TODO Auto-generated method stub
		/*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				new User(username, password, true, true, true, true,
						authorities), "facebook_auth", authorities);
		token.setDetails(new User(account_id, "password", true, true, true,
				true, authorities));
		SecurityContextHolder.getContext().setAuthentication(token);	
*/		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		/*private Set<AccountRole> Roles = new HashSet<AccountRole>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authorities);
		token.setDetails(new WebAuthenticationDetails(request));
		*/
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				new User(username, password, true, true, true, true,
						authorities), "facebook_auth", authorities);
		token.setDetails(new User(username, password, true, true, true,
				true, authorities));
	    // Authenticate the user
	   // Authentication authentication = authenticationManager.authenticate(authRequest);
	    //SecurityContext securityContext = SecurityContextHolder.getContext();
	    //securityContext.setAuthentication(authentication);
		SecurityContextHolder.getContext().setAuthentication(token);
		
	}
	
}

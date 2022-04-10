package com.example.JwtAuthorization.JwtAuthorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager ;
	
	@Autowired
	private MyUserDetailService userDetailService ;
	
	@Autowired
	private JwtUtil jwtUtilToken ;
	
	@RequestMapping({"/hello"})
	public String requestController()
	{
		return "Welcome to Excellence Classes";
	}
	
	
	
	
	
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception 
	{
	
		try 
		{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword() ));
		}
		catch(BadCredentialsException e) 
		{
			throw new Exception("Incorrect username & password",e);
		}
		
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt=jwtUtilToken.generateToken(userDetails);
		
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	

}

package com.example.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/*
	 ajax의 경우 http request header에  XMLHttpRequest라는 값이 세팅되어 요청이 오는데, 인증되지 않는 사용자가
	 ajax로 리소스를 요청할 경우  "Unauthorized" 에러를 발생시키고 나머지 경우는 로그인 페이지로 리다이렉트 시켜줍니다.
	*/
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
		}else{
			response.sendRedirect("/members/login");
		}
	}

}
package com.example.shop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.shop.dto.MemberFormDto;
import com.example.shop.entity.Member;
import com.example.shop.service.MemberService;

import jakarta.transaction.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Member createMember(String email,String password){
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail(email);
		memberFormDto.setName("홍길동");
		memberFormDto.setAddress("서울시 마포구 합정동");
		memberFormDto.setPassword(password);
		Member member = Member.createMember(memberFormDto,passwordEncoder);
		return memberService.saveMember(member);
	}

	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception{
		String email = "test@email.com";
		String password = "1234";
		this.createMember(email,password);
		mockMvc.perform(formLogin().userParameter("email")
			.loginProcessingUrl("/members/login") //로그인 처리 URL 설정
			.user(email).password("12345")) //잘못된 비밀번호 입력
			.andExpect(SecurityMockMvcResultMatchers.unauthenticated());//인증 실패를 기대
	}


}
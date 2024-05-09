package runtimezero.tricount.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import runtimezero.tricount.dto.LoginRequest;
import runtimezero.tricount.dto.SignupRequest;
import runtimezero.tricount.dto.ApiResponse;
import runtimezero.tricount.entity.Member;
import runtimezero.tricount.service.MemberService;
import runtimezero.tricount.util.TricountApiConst;


@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<Member> signup(@Valid @RequestBody SignupRequest request) {
        Member member = Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .build();
        return new ApiResponse<Member>().ok(memberService.signup(member));
    }

    // 로그인

    @PostMapping("/login")
    public ApiResponse login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Member loginMember = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());

        // 로그인 성공 처리 = 쿠키 생성
        Cookie idCookie = new Cookie(TricountApiConst.LOGIN_MEMBER_COOKIE, String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return new ApiResponse().ok();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ApiResponse logout(HttpServletResponse response) {
        memberService.logout(response);
        return new ApiResponse().ok();
    }
}

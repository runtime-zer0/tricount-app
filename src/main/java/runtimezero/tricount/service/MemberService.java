package runtimezero.tricount.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runtimezero.tricount.enums.TricountErrorCode;
import runtimezero.tricount.entity.Member;
import runtimezero.tricount.repository.MemberRepository;
import runtimezero.tricount.util.TricountApiConst;
import runtimezero.tricount.util.TricountApiException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    public Member signup(Member member) {
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(String loginId, String password) {
        // id, password 가 맞으면 통과, 맞지 않으면 로그인 처리를 안해줘야 함
        Member loginMember = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElseThrow(() -> new TricountApiException("Member info is not found!", TricountErrorCode.NOT_FOUND));
        return loginMember;
    }

    // 로그아웃
    public void logout(HttpServletResponse response) {
        expireCookie(response, TricountApiConst.LOGIN_MEMBER_COOKIE);
    }
    public void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


    // 조회 - MemberContext 에서 사용하기 위해
    public Member findMemberById(Long memberId) {
        Optional<Member> loginMember = memberRepository.findById(memberId);
        if (!loginMember.isPresent()) {
            throw new TricountApiException("Member info is not found!", TricountErrorCode.NOT_FOUND);
        }

        return loginMember.get();
    }
}

package runtimezero.tricount.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import runtimezero.tricount.enums.TricountErrorCode;
import runtimezero.tricount.service.MemberService;
import runtimezero.tricount.util.MemberContext;
import runtimezero.tricount.util.TricountApiConst;


@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("로그인 인터셉터 시작");

        Cookie[] cookies = request.getCookies();
        if (!this.containsUserCookie(cookies)) {
            log.info("미인증 사용자 요청");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, TricountErrorCode.LOGIN_NEEDED.getMessage());
            return false;
        }

        log.info("인증된 사용자 요청");
        // MemberContext 에 값을 set 해준다.
        for (Cookie cookie : cookies) {
            if (TricountApiConst.LOGIN_MEMBER_COOKIE.equals(cookie.getName())) {
                try {

                    // TODO cookie 에서 아이디를 꺼내고, DB 에서 이 아이디에 해당하는 Member 를 조회해서 set 해준다.
                    MemberContext.setCurrentMember(memberService.findMemberById(Long.parseLong(cookie.getValue())));
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "MEMBER INFO SET ERROR" + e.getMessage());
                }
            }
        }

        return true;
    }

    private boolean containsUserCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TricountApiConst.LOGIN_MEMBER_COOKIE.equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}

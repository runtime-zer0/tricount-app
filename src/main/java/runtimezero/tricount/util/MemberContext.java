package runtimezero.tricount.util;


import runtimezero.tricount.entity.Member;

public class MemberContext {
    private static final ThreadLocal<Member> memberThreadLocal = new ThreadLocal<>();

    public static void setCurrentMember(Member member) {
        memberThreadLocal.set(member);
    }

    public static Member getCurrentMember() {
        return memberThreadLocal.get();
    }

    // clear 같은것도 넣어줘도 됨.
}

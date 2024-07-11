package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() { // 호출 시 MemeberServiceImpl 생성자 생성시 MemoryMemeberRepository와 의존관계 주입 및 연결
//        return new MemberServiceImpl(new MemoryMemberRepository());/**/
        return new MemberServiceImpl(memberRepository()); // refactoring - method 분리(ctrl alt M)
    }

    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() { // 호출 시 OrderServiceImpl의 생성자 생성, 구체클래스인 MemoryMemeber 및 FoxPolicy 의존성 주입
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy(); // 구성영역인 AppConfig만 수정하면 사용영역(client)의 코드는 수정 필요 없음
    }
}

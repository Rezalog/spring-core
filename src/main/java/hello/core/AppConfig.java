package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 적용 상태에서 soutm 을 이용하여 call 되는 메서드를 확인해보면
 * 본래 자바코드라면 call AppConfig.memberService, memberRepository, memberRepository, orderService, memberRepository
 * 이렇게 출력되어야하지만(순서미보장), 실제로 memberService, memberRepository, orderService 만 출력된다.
 *
 * 이는 @Configuration 이 적용되지않은 순수 자바의 DI Container와
 * @Configuration 이 적용된 Spring DI Container 의 차이로,
 * Spring DI Container 에서는 Bean 등록시 스프링 컨테이너에 존재하면
 * 스프링 컨테이너에서 찾아 반환하거나, 없으면 새로 바이트 코드를 이용한 cglib을 적용하여
 * 대상 클래스를 상속받는 @cglib$xxx class로 바꾸어 Spring Container에 동적으로 등록한 것을 반환한다.
 *
 * 이 덕분에 싱글톤을 보장함을 알 수 있다.
 *
 * 하지만 @Configuration 없이 @Bean 으로만 등록하면 cglib 이 적용되지않고,
 * Spring DI Container에 등록은 되지만 싱글톤을 보장할 수 없다.
 *
 * */

@Configuration  // 설정정보임을 나타냄
public class AppConfig {

    @Bean // spring container에 key(method name), value(return type class) 등록(applicationContext)
    public MemberService memberService() { // 호출 시 MemeberServiceImpl 생성자 생성시 MemoryMemeberRepository와 의존관계 주입 및 연결
//        return new MemberServiceImpl(new MemoryMemberRepository());/**/
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); // refactoring - method 분리(ctrl alt M)
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() { // 호출 시 OrderServiceImpl의 생성자 생성, 구체클래스인 MemoryMemeber 및 FoxPolicy 의존성 주입
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        System.out.println("call AppConfig.discountPolicy");
        return new RateDiscountPolicy(); // 구성영역인 AppConfig만 수정하면 사용영역(client)의 코드는 수정 필요 없음
    }
}

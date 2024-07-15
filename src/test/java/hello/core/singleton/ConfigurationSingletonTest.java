package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        System.out.println("memberService = " + memberService);
        System.out.println("orderService = " + orderService);
        System.out.println("memberRepository = " + memberRepository);
    }
    /*
    *   call AppConfig.memberService
        call AppConfig.memberRepository
        call AppConfig.orderService
        call AppConfig.discountPolicy
        memberService = hello.core.member.MemberServiceImpl@1b822fcc
        orderService = hello.core.order.OrderServiceImpl@24a1c17f
        memberRepository = hello.core.member.MemoryMemberRepository@56102e1c
    * */

    @Test
    void configurationDeepTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean.getClass() = " + bean.getClass());
        // bean.getClass() = class hello.core.AppConfig$$SpringCGLIB$$0
    }
}

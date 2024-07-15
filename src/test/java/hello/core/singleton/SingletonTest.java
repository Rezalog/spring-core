package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isNotSameAs(memberService2);
        /*
        * memberService1 = hello.core.member.MemberServiceImpl@6f8e8894
          memberService2 = hello.core.member.MemberServiceImpl@6531a794
        * 같은 MemberService 타입이지만 다른 인스턴스가 생성됨.
        * DI Container 에 등록된 Bean 이 Singleton 을 보장하지 않음.
        * */
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용할 객체 사용")
    void singletonServiceTest() {
//        SingletonService singletonService1 = new SingletonService(); // error: SingletonService() has private access in SingletonService
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        /*
        * singletonService1 = hello.core.singleton.SingletonService@6531a794
          singletonService2 = hello.core.singleton.SingletonService@6531a794
        * */
        assertThat(singletonService1).isSameAs(singletonService2);

        singletonService1.logic();
    }

    @Test
    @DisplayName("스프링 컨테이너 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        /*
        * memberService1 = hello.core.member.MemberServiceImpl@532721fd
          memberService2 = hello.core.member.MemberServiceImpl@532721fd
        * */
        assertThat(memberService1).isSameAs(memberService2);
    }
}

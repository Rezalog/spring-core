package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

public class MemberApp {

    public static void main(String[] args) {
        /**
         * appconfig의 memberService 메서드 호출로
         * memberServiceImpl 생성자 생성 및 구현클래스와 의존관계 주입
         * */
        // MemberService memberService = new MemberServiceImpl();
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        /**
         * 설정정보(@Configuration이 적용된 class)인 AppConfig를 ApplicationContext의 getBean method로 
         * @Bean 등록대상인 정보를 받아옴
         * => 실행 시 spring 실행 및 bean 등록 대상 정보 확인 가능
         */
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("findMember.getName() = " + findMember.getName());
    }
}

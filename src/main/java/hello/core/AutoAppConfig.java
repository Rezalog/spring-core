package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * AppConfig 는 @Bean 을 이용하여 등록할 대상을 수동으로 지정한다.
 * @ComponentScan 은 @Component Annotation이 붙은 클래스를 찾아 자동으로 Spring Bean에 등록해준다.(Bean 기본이름 : 맨 앞 소문자 클래스명)
 * 이때, @Configuration 내부 로직에 @Component가 붙어있어 AppConfig, TestConfig 등과 충돌이 날 수 있으므로
 * exculdeFilters 속성을 이용하여 제외시킨다. (보통 이렇게까지 하지는 않음)
 *
 * @Component 적용 대상 : MemberServiceImpl, MemoryMemberRepository, RateDiscountPolicy, OrderServiceImpl
 * @Autowired 적용 대상 : MemberServiceImpl, OrderServiceImpl
 * 적용 후 의존관계 주입 없이 구현체들만 Spring Bean 에 등록되어있으므로,
 * 각 생성자 생성하는 코드에 의존관계를 자동으로 주입해주는 @Autowired 를 적용시키면
 * Spring이 Spring DI Container에서 Bean에서 @Autowired 가 적용된 클래스의 생성자와 동일한 type의
 * Bean을 조회하여 의존 관계를 주입한다.
 *
 * ex) MemberServiceImpl.class -------------------------
 *
 * private final MemberRepository memberRepository;
 *
 * @Autowired //--> ac.getBean(MemberRepository.class) 역할(MemberRepository, MemoryMemberRepository)
 * public MemberServiceImpl(MemberRepository memberRepository) {
 *     this.memberRepository = memberRepository
 * }
 * -----------------------------------------------------
 * ==> Spring Bean 등록 :
 *      MemberServiceImpl(@Component),
 *      MemberRepository(getBean, @Autowired),
 *      MemoryMemberRepository(getBean, @Autowired)
 *
 * 그래서 @ComponentScan을 쓰면 보통 @Component 로 자동 Bean 등록 및 @Autowired로 자동 의존관계 주입을 세트로 한다.
 *
 * ============[ @ComponentScan 대상 및 관련 프로퍼티 ]============
 * basePackages = "hello.core.member", "hello.core.service, // hello.core의 member,service 패키지만 @ComponentScan
 * (default : @ComponentScan 이 붙은 설정정보 클래스의 패키지가 시작위치)
 * basePackageClasses = AppConfig.class, // AppConfig.class 하위 패키지를 scan
 * => 보통 default 로 project root 패키지에 설정정보를 구성함(ex : @SpringBootApplication(내부에 @ComponentScan 있음)
 *
 * ============[ @ComponentScan 기본 대상 ]============
 *                                                          [부가기능]
 * 1. @Component        컴포넌트 스캔에서 사용
 * 2. @Controller       스프링 MVC 컨트롤러에 사용          스프링 MVC Controller로 인식
 * 3. @Service          스프링 비즈니스 로직에서 사용        개발자의 비즈니스 로직구분(특별 기능 없음)
 * 4. @Respository      스프링 데이터 접근 계층에서 사용     스프링 데이터 접근 계층으로 인식, 데이터 계층의 예외를 스프링의 추상화된 예외로 변환
 * 5. @Configuration    스프링 설정 정보에서 사용           스프링 설정 정보로 인식, Spring Bean이 Singleton을 유지하도록 추가 처리
 * => 2~5는 @Component가 적용되어있음
 * */
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}

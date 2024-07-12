package hello.core.xml;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class XmlContextApp {

    /**
     * ApplicationContext 인터페이스의 구체클래스인 AnnotationConfigApplicationContext 말고
     * GenericXmlApplicationContext 로 appConfig.xml 파일을 resources에 구성하고
     * 해당 빈 정보를 받오는지 검증, 파일이 없다면 BeanDefinitionStoreException 발생
     * (Interface 는 consturctor-args 태그에 ref로 하위 interface 타입을 맞추고,
     *  해당 하위 interface ref 의 bean은 하위 interface type의 return 구체 클래스를 명시 )
     * */
    @Test
    void findAllXmlBean() {
        ApplicationContext genericXmlApplicationContext = new GenericXmlApplicationContext("appConfig.xml");
        Map<String, MemberService> beansOfType = genericXmlApplicationContext.getBeansOfType(MemberService.class);
        System.out.println("beansOfType = " + beansOfType);
    }
    @Test
    void xmlAppContext() {
        ApplicationContext genericXmlApplicationContext = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = genericXmlApplicationContext.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}

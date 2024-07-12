package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    /**
     * Bean 이 Spring Container 에게 제공되는 방식
     * <p>
     * 1. 팩토리 bean을 통해 팩토리메서드로 제공(BeanDefinition에서 name 등 숨김)
     * (ex. memberService())) - AnnotationConfigAC
     * 2. 직접 등록(BeanDefinition에서 팩토리메서드 없음, name 등 모두 드러남) - GenericXmlAC
     * <p>
     * 기본적으로 ApplcationContext 하위의 AnnotationConfigAC, GenericXmlAC, XxxAC 는
     * 각각 AnnotatedBeanDefinitionReader, XmlBeanDefinitionReader, XxxBeanDefinitionReader가
     * 설정정보인 AppConfig.class, appConfig,xml, appConfig.xxx를 읽고
     * 그 결과 정보인 "Bean 메타정보"를 BeanDefinition interface에 생성하여 담아 추상화된 형태로 제공된다.
     */

    AnnotationConfigApplicationContext ac1 = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext ac2 = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인 - AnnocationConfigAC")
    void findApplicationBean1() {
        String[] beanDefinitionNames = ac1.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac1.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.print("beanDefinitionName = " + beanDefinitionName + ", ");
                System.out.println("beanDefinition = " + beanDefinition);
            }
        }
    }
    /*
    beanDefinitionName = appConfig, beanDefinition = Generic bean: class [hello.core.AppConfig$$SpringCGLIB$$0]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null
    beanDefinitionName = memberService, beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberService; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
    beanDefinitionName = memberRepository, beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberRepository; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
    beanDefinitionName = orderService, beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=orderService; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
    beanDefinitionName = discountPolicy, beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=discountPolicy; initMethodNames=null; destroyMethodNames=[(inferred)]; defined in hello.core.AppConfig
    > Task :test
    * */

    @Test
    @DisplayName("빈 설정 메타정보 확인 - GenericXmlAC")
    void findApplicationBean2() {
        String[] beanDefinitionNames = ac2.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac2.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.print("beanDefinition = " + beanDefinition + ", ");
                System.out.println("beanDefinitionName = " + beanDefinitionName);
            }
        }
    }

    /*
    beanDefinition = Generic bean: class [hello.core.member.MemberServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml], beanDefinitionName = memberService
    beanDefinition = Generic bean: class [hello.core.member.MemoryMemberRepository]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml], beanDefinitionName = memberRepository
    beanDefinition = Generic bean: class [hello.core.order.OrderServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml], beanDefinitionName = orderService
    beanDefinition = Generic bean: class [hello.core.discount.RateDiscountPolicy]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null; defined in class path resource [appConfig.xml], beanDefinitionName = discountPolicy
    > Task :test
    * */
}

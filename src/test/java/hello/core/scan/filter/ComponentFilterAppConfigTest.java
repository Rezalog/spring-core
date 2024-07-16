package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));

    }


    /**
     * @ComponentScan.Filter 의 type 프로퍼티 FilterType enum 옵션
     *
     * ANNOTATION : default, Annotation을 인식하여 동작 - ex) 'org.example.SomAnnotation'
     * ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작 - ex) 'org.example.SomeClass'
     * => @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
     * ASPECTJ : AspectJ 패턴 사용 - ex) 'org.example..*Service+'
     * REGEX : 정규 표현식 - ex) 'org\.example\.DEFAULT.*'
     * CUSTOM : 'TypeFilter'라는 인터페이스를 구현하여 처리  - ex) 'org.example.MyTypeFilter'
     *
     * */
    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(classes = MyExcludeComponent.class) // type=FilterType.ANNOTATION - default, Abb
    )
    static class ComponentFilterAppConfig {

    }
}

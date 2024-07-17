package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Singleton Bean 은 Spring Container 생성 시점에
 * 대상 bean의 초기화 메서드를 실행하고,
 * 같은 인스턴스의 빈을 조회하고,
 * 종료 메서드까지 정상 호출 된 것을 확인할 수 있다.
 *
 * */

public class SingletonTest {

    @Test
    public void singletonBeanFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        assertThat(bean1).isSameAs(bean2);

        ac.close();
    }
    /*
    *   SingletonBean.init
        bean1 = hello.core.scope.SingletonTest$SingletonBean@4ba534b0
        bean2 = hello.core.scope.SingletonTest$SingletonBean@4ba534b0
        SingletonBean.close
    * */

    @Scope("singleton") // singleton 이 default, same as @Scope
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("SingletonBean.close");
        }
    }
}

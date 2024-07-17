package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean Lifecycle
 *
 *              객체 생성            ->          의존관계 주입
 * (생성자, 필요한 데이터 세팅만 담당)       (자동 or 외부에서 setter 수정자 주입)
 *
 * 스프링 빈은 객체를 생성하고, 의존 관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.
 * 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야한다.
 *
 * 스프링은 의존관계 주입이 완료되면 스프링 빈에게 "콜백 메서드"를 통해서 초기화 시점을 알려주는 기능을 제공한다.
 * 또 스프링은 스프링 컨테이너가 종료되기 직전에 "소멸 콜백"을 준다. => 안전하게 종료 작업 진행 가능
 *
 * */

public class BeanLifeCycleTest {
    @Test
    void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // ConfigurableAC
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-core.dev");
            return networkClient;
        }
    }
}
/*
*   생성자 호출, url = null
    connect: null
    call: null message = 초기화 연결 메세지
    close : null

* === NetworkClient 에 InitializingBean, DisposableBean interface implements 후 ===
*
    생성자 호출, url = null
    NetworkClient.afterPropertiesSet
    connect: http://hello-core.dev
    call: http://hello-core.dev message = 초기화 연결 메세지
    NetworkClient.destroy
    close : http://hello-core.dev
    *
*
*  === Bean의 init, close property 로 수정 후 ===
*
*   생성자 호출, url = null
    NetworkClient.init
    connect: http://hello-core.dev
    call: http://hello-core.dev message = 초기화 연결 메세지
    NetworkClient.close
    close : http://hello-core.dev

*  === @PostConstruct, @PreDestory 로 수정 후 ===
*
    생성자 호출, url = null
    NetworkClient.init
    connect: http://hello-core.dev
    call: http://hello-core.dev message = 초기화 연결 메세지
    NetworkClient.close
    close : http://hello-core.dev
* */
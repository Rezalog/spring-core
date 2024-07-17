package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * ==============Bean lifecycle 관련 Spring 제공 interface==============
 *
 * 1. 초기화 : [InitializingBean]
 *
 * public void afterPropertiesSet() {}
 * => 스프링이 객체 생성 및 의존관계 주입이 끝나고 {} 호출
 *
 * 2. 종료 : [DisposableBean]
 *
 * public void destory() {}
 * => Spring Container 인  ApplicationContext 가 closing 이 호출되어 내려간 뒤
 * 차례로 빈들이 종료될 때 {} 호출
 *
 * [ Spring 의 초기화, 콜백 소멸 인터페이스 메서드 사용의 단점 ]
 * 코드가 Spring 전용 interface에 의존적이다.
 * 즉, 초기화 및 소멸 메서드의 이름 변경이 불가능하고, 외부 라이브러리에 적용할 수 없다. 잘 사용하지않음
 *
 * ==============Bean 의 initMethod, destoryMethod property 적용==============
 * 등록하고자 하는 bean 의 method 의 이름을 지정, destoryMethod 의 default 는 (inferred) 로,
 * 따로 등록하지 않으면 close나 shutdown 이름으로 등록된 메서드를 자동으로 호출한다.
 * 대신 빈 문자열로 등록하면 안된다.
 *
 * ==============@PostConstruct, @PreDestory 적용(권장) ==============
 * 종속적인 기술이 아닌 JSR-250 자바표준(인터페이스들의 모음 같은거) 라이브러리 javax에서 지원한다.
 * 스프링이 아닌 다른 컨테이너에서도 동작하고, 수동 빈 등록이 아니니 @ComponentScan 과 잘 어울린다.
 * 대상 init, close 메서드에 각각 @PostConstruct, @PreDestroy를 붙인다.
 * 외부라이브러리에서 사용할 수 없음이 유일한 단점, 이때는 위의 Bean 방법을 이용하자.
 *
 * */

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("close : " + url);
    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}

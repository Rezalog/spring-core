package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Bean lifecycle 관련 Spring 제공 interface
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
 * */

public class NetworkClient implements DisposableBean, InitializingBean{

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
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}

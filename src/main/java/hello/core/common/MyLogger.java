package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 빈 스코프(ObjectProvider 와 @Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS))
 *
 * 1.
 * @Scope(value = "request")
 * => 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 * Application run 시 내장 톰캣에 의해 http://localhost:8080 을 request했을 때 해당 빈이 생성된다.(컴포넌트스캔)
 *
 * 따라서 run 시점에 스프링 컨테이너가 생성되었을때는 해당 빈이 없다.(logDemoService 참조)
 *
 * 2. 순수 자바 코드인 MyLogger 를 ObjectProvider<MyLogger> myLoggerProvider 변수로 변경,
 * myloggerProvider.get()으로 Spring container 가 올라갈때가 아닌
 * request 시 호출되는 Controller 의 logDemo 메서드 실행시 request scope bean의 생성되도록 함
 * ObjectProvider.get() 호출 시점에 request scope bean 등록
 *
 * 3. @Scope(value = "request", proxyMode=ScopedProxyMode.TARGET_CLASS) 추가,
 * @Scope 의 proxyMode = ScopedProxyMode.TARGET_CLASS) 를 설정하면 스프링 컨테이너는 CGLIB
 * 라는 바이트코드를 조작하는 라이브러리를 사용해서, MyLogger를 상속받은 가짜 프록시 객체를 생성
 *
 * 결과를 확인해보면 우리가 등록한 순수한 MyLogger 클래스가 아니라 MyLogger$
 * $EnhancerBySpringCGLIB 이라는 클래스로 만들어진 객체가 대신 등록된 것을 확인할 수 있다.
 * 그리고 스프링 컨테이너에 "myLogger"라는 이름으로 진짜 대신에 이 가짜 프록시 객체를 등록한다.
 * ac.getBean("myLogger", MyLogger.class) 로 조회해도 프록시 객체가 조회되는 것을 확인할 수 있음
 * 그래서 의존관계 주입도 이 가짜 프록시 객체가 주입됨.
 *
 * 마치 싱글톤을 사용하는 것 같지만 다르게 동작하기 때문에 결국 주의해서 사용해야 한다.
 * 이런 특별한 scope는 꼭 필요한 곳에만 최소화해서 사용하자, 무분별하게 사용하면 유지보수하기 어려워진다
 * */

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] [" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() { // request bean spring container에서 등록될때 호출, uuid set
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void destroy() { // spring container 가 내려가고 등록된 bean 이 destroy 되기 직전에 호출
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}

package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 빈 스코프
 *
 * @Scope(value = "request")
 * => 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 * Application run 시 내장 톰캣에 의해 http://localhost:8080 을 request했을 때 해당 빈이 생성된다.(컴포넌트스캔)
 *
 * 따라서 run 시점에 스프링 컨테이너가 생성되었을때는 해당 빈이 없다.(logDemoService 참조)
 * */

@Component
@Scope(value = "request")
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

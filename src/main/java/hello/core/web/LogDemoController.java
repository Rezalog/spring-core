package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 *
 * 로거가 잘 작동하는지 확인하는 테스트용 컨트롤러다.
 * 여기서 HttpServletRequest를 통해서 요청 URL을 받았다.
 * requestURL 값 http://localhost:8080/log-demo
 * 이렇게 받은 requestURL 값을 myLogger에 저장해둔다. myLogger는 HTTP 요청 당 각각 구분되므로 다른
 * HTTP 요청 때문에 값이 섞이는 걱정은 하지 않아도 된다.
 * 컨트롤러에서 controller test라는 로그를 남긴다
 *
 * ※ requestURL을 MyLogger에 저장하는 부분은 컨트롤러 보다는 공통 처리가 가능한 스프링 인터셉터나
 * 서블릿 필터 같은 곳을 활용하는 것이 좋다. 여기서는 예제를 단순화하고, 아직 스프링 인터셉터를 학습하지 않은
 * 분들을 위해서 컨트롤러를 사용했다. 스프링 웹에 익숙하다면 인터셉터를 사용해서 구현해보자
 * */

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest httpServletRequest) { // requestURL set
        String requestURL = httpServletRequest.getRequestURL().toString();
//        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        /* expected
        *   [d06b992f...] request scope bean create
            [d06b992f...][http://localhost:8080/log-demo] controller test
            [d06b992f...][http://localhost:8080/log-demo] service id = testId
            [d06b992f...] request scope bean clos
        * */

        /*
        * 실제는 기대와 다르게 애플리케이션 실행 시점에 오류 발생 ```
        Error creating bean with name 'myLogger': Scope 'request' is not active for the
        current thread; consider defining a scoped proxy for this bean if you intend to
        refer to it from a singleton;
        ```
        스프링 애플리케이션을 실행 시키면 오류가 발생한다. 메시지 마지막에 싱글톤이라는 단어가 나오고…
        스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만,
        request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청이 와야 생성할 수 있다
        *
        ==> ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지
        request scope 빈의 생성을 지연할 수 있다.
        (ObjectProvider.getObject() 를 호출하시는 시점에는 HTTP 요청이 진행중이므로
        request scope 빈의 생성이 정상 처리된다.)
        *
        *  완료(2번 요청)
        * [dbaf1f06-36db-4634-bb2d-502b2ccf03ab] request scope bean create:hello.core.common.MyLogger@3a68cc1b
        [dbaf1f06-36db-4634-bb2d-502b2ccf03ab] [http://localhost:8080/log-demo]controller test
        [dbaf1f06-36db-4634-bb2d-502b2ccf03ab] [http://localhost:8080/log-demo]service id = testId
        [dbaf1f06-36db-4634-bb2d-502b2ccf03ab] request scope bean close:hello.core.common.MyLogger@3a68cc1b
        [5c7aa779-a3d0-4e77-828b-e2a287e0c99d] request scope bean create:hello.core.common.MyLogger@75f49747
        [5c7aa779-a3d0-4e77-828b-e2a287e0c99d] [http://localhost:8080/log-demo]controller test
        [5c7aa779-a3d0-4e77-828b-e2a287e0c99d] [http://localhost:8080/log-demo]service id = testId
        [5c7aa779-a3d0-4e77-828b-e2a287e0c99d] request scope bean close:hello.core.common.MyLogger@75f49747
        *
        * 
        * ==> 프록시 설정으로 변경(MyLogger 참조)
        * */

        return "OK";
    }
}

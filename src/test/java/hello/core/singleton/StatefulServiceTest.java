package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;

public class StatefulServiceTest {

    @Test
    void statefulServiceTest() { // 싱글톤 주의점, 무상태(stateless)로 설계해야하는 이유
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);
        statefulService1.order("orderA", 10000); // 1. 1의 order
        statefulService2.order("orderB", 20000); // 2. 1의 금액 조회 이전 2의 order 발생
        int price = statefulService1.getPrice(); // 3. 1의 금액 조회
        // service 1이든 2든 싱글톤의 동일한 인스턴스라서 1의 order가 2의 order에 price 필드값이 영향을 받음
        assertThat(price).isEqualTo(20000); 
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}

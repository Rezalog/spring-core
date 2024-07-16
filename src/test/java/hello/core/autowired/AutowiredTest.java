package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 *  주입할 Bean 이 Spring Container에 Bean으로 등록되지 않았어도
 *  동작은 가능해야할 때 @Autowired 자동주입을 옵션으로 구현하는 방안
 *
 *  1. @Autowired(required = false) : 자동 주입할 대상이 없으면 해당 메서드 호출 자체가 X
 *  2. org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null 입력, 호출 O
 *  3. Optional<> : 자동 주입할 대상이 없으면 Optional.empty 입력, 호출 O
 *
 *  (아래 3가지 TestBean.class 의 @Autowired 예제에서 Member 는 Bean 으로 등록되지 않은 상태)
 *
 * */

public class AutowiredTest {

    @Test
    void autowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("member = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}

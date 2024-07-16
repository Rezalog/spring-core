package hello.core.order;

import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void testOrderServiceLombokApplied() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);
        System.out.println("orderService = " + orderService);
        BeanDefinition beanDefinition = ac.getBeanDefinition("orderServiceImpl");
        System.out.println("beanDefinition = " + beanDefinition);
        Assertions.assertThat(orderService).isNotNull();
    }
}
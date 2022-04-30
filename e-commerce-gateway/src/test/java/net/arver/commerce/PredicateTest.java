package net.arver.commerce;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * PredicateTest.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@SpringBootTest
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList("nacos", "authority", "gateway", "ribbon", "feign", "hystrix", "e-commerce");

    @Test
    public void testPredicateTest() {
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }
}

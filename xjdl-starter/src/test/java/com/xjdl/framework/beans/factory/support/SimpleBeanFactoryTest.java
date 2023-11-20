package com.xjdl.framework.beans.factory.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleBeanFactoryTest {
    private BeanFactory beanFactory;

    @BeforeAll
    void beforeAll() {
        beanFactory = new BeanFactory();
    }

    @BeforeEach
    public void setUp() {
        log.info("{}", beanFactory.getBeanMap());
    }

    @AfterEach
    public void tearDown() {
        log.info("{}", beanFactory.getBeanMap());
    }

    @Test
    @Order(1)
    void registerBean() {
        beanFactory.registerBean("person", new Person());
    }

    @Test
    @Order(2)
    void getBean() {
        Person person = (Person) beanFactory.getBean("person");
        Assertions.assertNotNull(person);
    }

    class Person {
        @Override
        public String toString() {
            return "person_value";
        }
    }
}

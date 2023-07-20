package com.xjdl.study.designPatterns.behavioralPatterns.composite;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseComposite {
    @Test
    void test() {
        Employee ceo = new Employee("CEO", "A");

        Employee headSales = new Employee("HEAD_SALES", "B");
        Employee headMarketing = new Employee("HEAD_MARKETING", "C");

        Employee saleExecutive1 = new Employee("SALE_EXECUTIVE1", "D");
        Employee saleExecutive2 = new Employee("SALE_EXECUTIVE2", "D");
        Employee saleExecutive3 = new Employee("SALE_EXECUTIVE3", "D");

        Employee clerk1 = new Employee("CLERK1", "F");
        Employee clerk2 = new Employee("CLERK2", "F");
        Employee clerk3 = new Employee("CLERK3", "F");
        Employee clerk4 = new Employee("CLERK4", "F");

        headMarketing.add(clerk1, clerk2, clerk3, clerk4);
        headSales.add(saleExecutive1, saleExecutive2, saleExecutive3);
        ceo.add(headMarketing, headSales);

        log.info("{}", ceo);

        ceo.getSubEmployee().forEach(tier2 -> {
            log.info("{}", tier2);
            tier2.getSubEmployee().forEach(tier3 ->
                    log.info("{}", tier3));
        });
    }
}

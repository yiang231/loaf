package com.xjdl.study.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
class Employee {
	String name;
	int salary;
}

/**
 * parallelStream() 和 stream().parallel()
 */
@Slf4j
public class ParallelStream {
	public static void main(String[] args) {
		long t1, t2;
		List<Employee> eList = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			eList.add(new Employee("A", 20000));
			eList.add(new Employee("B", 3000));
			eList.add(new Employee("C", 15002));
			eList.add(new Employee("D", 7856));
			eList.add(new Employee("E", 200));
			eList.add(new Employee("F", 50000));
		}

		t1 = System.currentTimeMillis();
		log.info("Sequential Stream Count?= {} ", eList.stream().filter(e -> e.getSalary() > 15000).count());

		t2 = System.currentTimeMillis();
		log.info("Sequential Stream Time Taken?= {}", (t2 - t1));

		t1 = System.currentTimeMillis();
		log.info("Parallel Stream Count?= {}", eList.parallelStream().filter(e -> e.getSalary() > 15000).count());

		t2 = System.currentTimeMillis();
		log.info("Parallel Stream Time Taken?= {}", (t2 - t1));

		t1 = System.currentTimeMillis();
		log.info("stream().parallel() Count?= {}", eList.stream().parallel().filter(e -> e.getSalary() > 15000).count());

		t2 = System.currentTimeMillis();
		log.info("stream().parallel() Time Taken?= {}", (t2 - t1));
	}
}

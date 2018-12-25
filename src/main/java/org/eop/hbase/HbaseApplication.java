package org.eop.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author lixinjie
 * @since 2018-09-01
 */
@SpringBootApplication
public class HbaseApplication {

	public static void main(String[] args) {
		System.setProperty("hadoop.home.dir", "G:\\hadoop\\hadoop-2.8.5");
		SpringApplication.run(HbaseApplication.class, args);
	}
}

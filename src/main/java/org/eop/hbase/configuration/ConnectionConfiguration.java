package org.eop.hbase.configuration;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
@Configuration
@EnableConfigurationProperties(ConnectionProperties.class)
public class ConnectionConfiguration {

	private ConnectionProperties connectionProperties;
	
	public ConnectionConfiguration(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	
	@Bean
	public Connection connection() throws IOException {
		org.apache.hadoop.conf.Configuration conf =
				new org.apache.hadoop.conf.Configuration();
		return ConnectionFactory.createConnection(conf);
	}
}

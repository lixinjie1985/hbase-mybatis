package org.eop.hbase.configuration;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
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
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseConfiguration {

	private HbaseProperties hbaseProperties;
	
	public HbaseConfiguration(HbaseProperties hbaseProperties) {
		this.hbaseProperties = hbaseProperties;
	}
	
	@Bean
	public Connection connection() throws IOException {
		org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", hbaseProperties.getZookeeper().getQuorum());
		conf.set("hbase.rpc.timeout", hbaseProperties.getRpc().getTimeout().toString());
		conf.set("hbase.rpc.read.timeout", hbaseProperties.getRpc().getReadTimeout().toString());
		conf.set("hbase.rpc.write.timeout", hbaseProperties.getRpc().getWriteTimeout().toString());
		conf.set("hbase.client.operation.timeout", hbaseProperties.getClient().getOperationTimeout().toString());
		conf.set("hbase.client.scanner.timeout.period", hbaseProperties.getClient().getScannerTimeoutPeriod().toString());
		return ConnectionFactory.createConnection(conf);
	}
}

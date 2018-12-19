package org.eop.hbase.table;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
@Component
public class HbaseTable {

	@Autowired
	private Connection connection;
	
	public Table getTable(String name) throws IOException {
		return connection.getTable(TableName.valueOf(name));
	}
	
	public Table buildTable(String name, ExecutorService pool) {
		TableBuilder builder = connection.getTableBuilder(TableName.valueOf(name), pool);
		builder.setOperationTimeout(0).setReadRpcTimeout(0)
			.setReadRpcTimeout(0).setWriteRpcTimeout(0);
		return builder.build();
	}
}

package org.eop.hbase.table.batch;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.hbase.client.Append;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RowMutations;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-24
 */
@Component
public class TableBatch {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void batch(String tableName, String rowKey) throws IOException, InterruptedException {
		Get get = new Get(Bytes.toBytes(rowKey));
		Put put = new Put(Bytes.toBytes(rowKey));
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		Increment increment = new Increment(Bytes.toBytes(rowKey));
		Append append = new Append(Bytes.toBytes(rowKey));
		RowMutations rowMutations = new RowMutations(Bytes.toBytes(rowKey));
		Object[] results = new Object[6];
		hbaseTable.getTable(tableName).batch(Arrays.asList(get, put, delete, increment, append, rowMutations), results);
	}
}

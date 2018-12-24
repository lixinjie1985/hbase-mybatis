package org.eop.hbase.table.increment;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-24
 */
@Component
public class TableIncrement {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void increment(String tableName, String rowKey, byte[][][] fqs, long[] amounts) throws IOException {
		Increment increment = new Increment(Bytes.toBytes(rowKey));
		for (int i = 0, length = fqs.length; i < length; i++) {
			increment.addColumn(fqs[i][0], fqs[i][0], amounts[i]);
		}
		hbaseTable.getTable(tableName).increment(increment);
		
		hbaseTable.getTable(tableName).incrementColumnValue(Bytes.toBytes(rowKey), fqs[0][0], fqs[0][1], amounts[0]);
		
		hbaseTable.getTable(tableName).incrementColumnValue(Bytes.toBytes(rowKey), fqs[0][0], fqs[0][1], amounts[0],  Durability.USE_DEFAULT);
	}
}

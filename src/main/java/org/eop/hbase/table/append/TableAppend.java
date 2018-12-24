package org.eop.hbase.table.append;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Append;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-24
 */
@Component
public class TableAppend {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void addpend(String tableName, String rowKey, byte[][][] fqvs) throws IOException {
		Append append = new Append(Bytes.toBytes(rowKey));
		for (int i = 0, length = fqvs.length; i < length; i++) {
			append.addColumn(fqvs[i][0], fqvs[i][0], fqvs[i][0]);
		}
		hbaseTable.getTable(tableName).append(append);
	}
}

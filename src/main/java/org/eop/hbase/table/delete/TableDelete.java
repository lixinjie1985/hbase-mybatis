package org.eop.hbase.table.delete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
@Component
public class TableDelete {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void deleteOne(String tableName, String rowKey, String[]... cfqs) throws IOException {
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		for (String[] cfq : cfqs) {
			//删除最新版本的列
			delete.addColumn(Bytes.toBytes(cfq[0]), Bytes.toBytes(cfq[1]));
			//删除所有版本的列
			delete.addColumns(Bytes.toBytes(cfq[0]), Bytes.toBytes(cfq[1]));
			//删除列族中所有版本的所有列
			delete.addFamily(Bytes.toBytes(cfq[0]));
		}
		hbaseTable.getTable(tableName).delete(delete);
	}
	
	public void deleteMany(String tableName, String rowKeys[], String[][]... cfqss) throws IOException {
		List<Delete> deletes = new ArrayList<>();
		for (int i = 0, length = rowKeys.length; i < length; i++) {
			Delete delete = new Delete(Bytes.toBytes(rowKeys[i]));
			for (String[] cfq : cfqss[i]) {
				//删除最新版本的列
				delete.addColumn(Bytes.toBytes(cfq[0]), Bytes.toBytes(cfq[1]));
				//删除所有版本的列
				delete.addColumns(Bytes.toBytes(cfq[0]), Bytes.toBytes(cfq[1]));
				//删除列族中所有版本的所有列
				delete.addFamily(Bytes.toBytes(cfq[0]));
			}
			deletes.add(delete);
		}
		hbaseTable.getTable(tableName).delete(deletes);
	}
}

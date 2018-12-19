package org.eop.hbase.table.put;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.CellBuilder;
import org.apache.hadoop.hbase.CellBuilderFactory;
import org.apache.hadoop.hbase.CellBuilderType;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
@Component
public class TablePut {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void putOneRow(String tableName, String rowKey,
			String[]... columnFamilyQualifierValues) throws IOException {
		Put put = new Put(Bytes.toBytes(rowKey));
		CellBuilder cb = CellBuilderFactory.create(CellBuilderType.SHALLOW_COPY);
		for (String[] cfqv : columnFamilyQualifierValues) {
			//
			put.addColumn(Bytes.toBytes(cfqv[0]),
				Bytes.toBytes(cfqv[1]), Bytes.toBytes(cfqv[2]));
			//
			cb.setFamily(Bytes.toBytes(cfqv[0])).setQualifier(Bytes.toBytes(cfqv[1]))
				.setValue(Bytes.toBytes(cfqv[2]));
			put.add(cb.build());
			cb.clear();
		}
		hbaseTable.getTable(tableName).put(put);
	}
	
	public void putManyRows(String tableName) throws IOException {
		List<Put> puts = new ArrayList<>();
		hbaseTable.getTable(tableName).put(puts);
	}
}

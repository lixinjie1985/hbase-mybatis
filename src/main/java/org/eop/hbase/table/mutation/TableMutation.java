package org.eop.hbase.table.mutation;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Mutation;
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
public class TableMutation {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void mutation(String tableName, String rowKey, byte[][][] fqvs) throws IOException {
		RowMutations rowMutations = new RowMutations(Bytes.toBytes(rowKey));
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(fqvs[0][0], fqvs[0][1], fqvs[0][2]);
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		delete.addColumns(fqvs[0][0], fqvs[0][1]);
		//目前只支持put和delete
		rowMutations.add((Mutation)put);
		rowMutations.add((Mutation)delete);
		hbaseTable.getTable(tableName).mutateRow(rowMutations);
	}
}

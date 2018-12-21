package org.eop.hbase.table.get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
@Component
public class TableGet {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void getOne(String tableName, String rowKey, String family, String qualifier) throws IOException {
		Get get = new Get(Bytes.toBytes(rowKey));
		//获取列族中指定的列
		get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		//获取列族中所有的列
		get.addFamily(Bytes.toBytes(family));
		Result result = hbaseTable.getTable(tableName).get(get);
		//返回第一列的值
		result.value();
		//返回结果背后的单元格数组
		result.rawCells();
		//返回一个已排序的单元格列表
		result.listCells();
		//检测底层是不是没有返回来单元格
		result.isEmpty();
		//是否是一个游标
		result.isCursor();
		//获取最新版本的数据
		result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		//获取行键
		result.getRow();
		//返回一个游标如果结果是游标的话
		result.getCursor();
		//返回指定列的所有版本单元格列表
		result.getColumnCells(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		//返回当前单元格
		result.current();
		//前进一个单元格
		result.advance();
		//返回单元格扫描器
		result.cellScanner();
		//结果中是否包含指定列
		result.containsColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
	}
	
	public void getMany(String tableName, String rowKey, String family, String qualifier) throws IOException {
		List<Get> gets = new ArrayList<>();
		Result[] results = hbaseTable.getTable(tableName).get(gets);
	}
}

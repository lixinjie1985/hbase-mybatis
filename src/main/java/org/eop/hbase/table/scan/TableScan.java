package org.eop.hbase.table.scan;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.Consistency;
import org.apache.hadoop.hbase.client.IsolationLevel;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.table.HbaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-21
 */
@Component
public class TableScan {

	@Autowired
	private HbaseTable hbaseTable;
	
	public void getScanner(String tableName, String family, String qualifier) throws IOException {
		ResultScanner scanner = hbaseTable.getTable(tableName).getScanner(Bytes.toBytes(family));
		scanner = hbaseTable.getTable(tableName).getScanner(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		Iterator<Result> iterator = scanner.iterator();
		Result result = scanner.next();
		int nbRows = 10;
		Result[] results = scanner.next(nbRows);
	}
	
	public void getScannerByScan(String tableName, String family, String qualifier) throws IOException {
		Scan scan = new Scan();
		//返回指定列
		scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
		//返回列族中所有列
		scan.addFamily(Bytes.toBytes(family));
		//指定每列读取的版本个数
		int versions = 3;
		scan.readVersions(versions);
		//读取所有的版本
		scan.readAllVersions();
		//设置一次最多返回的单元格数目
		int batch = 100;
		scan.setBatch(batch);
		//设置列族的版本范围
		long minStamp = 0;
		long maxStamp = 10;
		scan.setColumnFamilyTimeRange(Bytes.toBytes(family), minStamp, maxStamp);
		//设置一致性级别（默认强一致性）
		scan.setConsistency(Consistency.STRONG);
		//设置隔离级别（默认读已提交的）
		scan.setIsolationLevel(IsolationLevel.READ_COMMITTED);
		//设置行数
		int limit = 10;
		scan.setLimit(limit);
		//只想获取一行时设置
		scan.setOneRowLimit();
		//设置是否逆序
		scan.setReversed(false);
		//设置行键前缀过滤器
		//最后是转化为startRow和stopRow来实现的
		String rowPrefix = null;
		scan.setRowPrefixFilter(Bytes.toBytes(rowPrefix));
		//设置时间戳范围
		scan.setTimeRange(minStamp, maxStamp);
		//设置具体的时间戳
		long timestamp = 0;
		scan.setTimestamp(timestamp);
		//设置开始和结束行
		String startRow = null;
		String stopRow = null;
		boolean inclusive = false;
		scan.withStartRow(Bytes.toBytes(startRow));
		scan.withStartRow(Bytes.toBytes(startRow), inclusive);
		scan.withStopRow(Bytes.toBytes(stopRow));
		scan.withStopRow(Bytes.toBytes(stopRow), inclusive);
		//在执行查询时设置服务器端过滤器
		Filter filter = null;
		scan.setFilter(filter);
		ResultScanner scanner = hbaseTable.getTable(tableName).getScanner(scan);
	}
}

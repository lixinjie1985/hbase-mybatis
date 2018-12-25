package org.eop.hbase.admin.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.CompactType;
import org.apache.hadoop.hbase.client.CompactionState;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.admin.HbaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-25
 */
@Component
public class TableAdmin {

	@Autowired
	private HbaseAdmin hbaseAdmin;
	
	public void createTable(String name, String[] cfs) throws IOException {
		TableDescriptorBuilder tbuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(name));
		ColumnFamilyDescriptor[] cfds = new ColumnFamilyDescriptor[cfs.length];
		for (int i = 0; i < cfs.length; i++) {
			ColumnFamilyDescriptorBuilder cfb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cfs[i]));
			cfds[i] = cfb.build();
			
		}
		tbuilder.setColumnFamilies(Arrays.asList(cfds));
		TableDescriptor td = tbuilder.build();
		hbaseAdmin.getAdmin().createTable(td);
		//按照分割key创建好region，region的数量是分割key的数目加1
		byte[][] splitKeys = new byte[][] {Bytes.toBytes("100"), Bytes.toBytes("200")};
		hbaseAdmin.getAdmin().createTable(td, splitKeys);
		//一共创建numRegions个region，startKey作为第一个region的结束key
		//endKey作为最后一个region的开始key，第一个region的开始key和最后
		//一个region的结束key都是null。
		//把从startKey到endKey这段区间划分为足够的片段，来满足region的创建
		//可以理解为每个片段对应一个region
		byte[] startKey = Bytes.toBytes("");
        byte[] endKey = Bytes.toBytes("");
        int numRegions = 10;
        hbaseAdmin.getAdmin().createTable(td, startKey, endKey, numRegions);
	}
	
	public void deleteTable(String name) throws IOException {
		hbaseAdmin.getAdmin().deleteTable(TableName.valueOf(name));
	}
	
	public void disableTable(String name) throws IOException {
		hbaseAdmin.getAdmin().disableTable(TableName.valueOf(name));
	}
	
	public void enableTable(String name) throws IOException {
		hbaseAdmin.getAdmin().enableTable(TableName.valueOf(name));
	}
	
	public TableDescriptor getDescriptor(String name) throws TableNotFoundException, IOException {
		return hbaseAdmin.getAdmin().getDescriptor(TableName.valueOf(name));
	}
	
	public boolean isTableAvailable(String name) throws IOException {
		return hbaseAdmin.getAdmin().isTableAvailable(TableName.valueOf(name));
	}
	
	public boolean isTableDisabled(String name) throws IOException {
		return hbaseAdmin.getAdmin().isTableDisabled(TableName.valueOf(name));
	}
	
	public boolean isTableEnabled(String name) throws IOException {
		return hbaseAdmin.getAdmin().isTableEnabled(TableName.valueOf(name));
	}
	
	public List<TableDescriptor> listTableDescriptors() throws IOException {
		//列出所有用户空间的表
		return hbaseAdmin.getAdmin().listTableDescriptors();
	}
	
	public List<TableDescriptor> listTableDescriptors(String... names) throws IOException {
		List<TableName> tableNames = new ArrayList<>();
		for (String name : names) {
			tableNames.add(TableName.valueOf(name));
		}
		return hbaseAdmin.getAdmin().listTableDescriptors(tableNames);
	}
	
	public List<TableDescriptor> listTableDescriptors(String nameRegex) throws IOException {
		Pattern pattern = Pattern.compile(nameRegex);
		boolean includeSysTables = false;
		hbaseAdmin.getAdmin().listTableDescriptors(pattern);
		return hbaseAdmin.getAdmin().listTableDescriptors(pattern, includeSysTables);
	}
	
	public List<TableDescriptor> listTableDescriptorsByNamespace(String namespace) throws IOException {
		return hbaseAdmin.getAdmin().listTableDescriptorsByNamespace(Bytes.toBytes(namespace));
	}
	
	public TableName[] listTableNames() throws IOException {
		//列出所有用户空间下的表名
		return hbaseAdmin.getAdmin().listTableNames();
	}
	
	public TableName[] listTableNames(String nameRegex) throws IOException {
		Pattern pattern = Pattern.compile(nameRegex);
		boolean includeSysTables = false;
		hbaseAdmin.getAdmin().listTableNames(pattern);
		return hbaseAdmin.getAdmin().listTableNames(pattern, includeSysTables);
	}
	
	public TableName[] listTableNamesByNamespace(String namespace) throws IOException {
		return hbaseAdmin.getAdmin().listTableNamesByNamespace(namespace);
	}
	
	public void modifyTable(String name) throws IOException {
		TableDescriptor td = TableDescriptorBuilder.newBuilder(TableName.valueOf(name)).build();
		hbaseAdmin.getAdmin().modifyTable(td);
	}
	
	public boolean tableExists(String name) throws IOException {
		return hbaseAdmin.getAdmin().tableExists(TableName.valueOf(name));
	}
	
	public void addColumnFamily(String name, String cf) throws IOException {
		 ColumnFamilyDescriptor cfd =  ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf)).build();
		 hbaseAdmin.getAdmin().addColumnFamily(TableName.valueOf(name), cfd);
	}
	
	public void deleteColumnFamily(String name, String cf) throws IOException {
		hbaseAdmin.getAdmin().deleteColumnFamily(TableName.valueOf(name), Bytes.toBytes(cf));
	}
	
	public void modifyColumnFamily(String name, String cf) throws IOException {
		ColumnFamilyDescriptor cfd =  ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf)).build();
		hbaseAdmin.getAdmin().modifyColumnFamily(TableName.valueOf(name), cfd);
	}
	
	public void compact(String name, String cf) throws IOException, InterruptedException {
		hbaseAdmin.getAdmin().compact(TableName.valueOf(name));
		hbaseAdmin.getAdmin().compact(TableName.valueOf(name),  CompactType.NORMAL);
		//压缩指定列
		hbaseAdmin.getAdmin().compact(TableName.valueOf(name), Bytes.toBytes(cf));
		hbaseAdmin.getAdmin().compact(TableName.valueOf(name), Bytes.toBytes(cf), CompactType.MOB);
	}
	
	public void flush(String name) throws IOException {
		hbaseAdmin.getAdmin().flush(TableName.valueOf(name));
	}
	
	public CompactionState getCompactionState(String name) throws IOException {
		//查看表当前是否正在被压缩以及压缩类型
		hbaseAdmin.getAdmin().getCompactionState(TableName.valueOf(name));
		return hbaseAdmin.getAdmin().getCompactionState(TableName.valueOf(name), CompactType.NORMAL);
	}
	
	public long getLastMajorCompactionTimestamp(String name) throws IOException {
		return hbaseAdmin.getAdmin().getLastMajorCompactionTimestamp(TableName.valueOf(name));
	}
	
	public void majorCompact(String name, String cf) throws IOException, InterruptedException {
		hbaseAdmin.getAdmin().majorCompact(TableName.valueOf(name));
		hbaseAdmin.getAdmin().majorCompact(TableName.valueOf(name), CompactType.NORMAL);
		//压缩指定列族
		hbaseAdmin.getAdmin().majorCompact(TableName.valueOf(name), Bytes.toBytes(cf));
		hbaseAdmin.getAdmin().majorCompact(TableName.valueOf(name), Bytes.toBytes(cf), CompactType.NORMAL);
	}
	
	public void split(TableName tableName, byte[] splitPoint) throws IOException {
		//分割一个表的所有region
		hbaseAdmin.getAdmin().split(tableName);
		hbaseAdmin.getAdmin().split(tableName, splitPoint);
	}
	
}

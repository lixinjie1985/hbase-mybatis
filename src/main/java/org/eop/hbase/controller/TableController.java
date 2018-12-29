package org.eop.hbase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.admin.HbaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinjie
 * @since 2018-12-26
 */
@RequestMapping("/hbase/table")
@RestController
public class TableController {

	@Autowired
	private HbaseAdmin hbaseAdmin;
	
	@RequestMapping("/listTableNames")
	public List<TableName> listTableNames() throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		//列出所有用户空间下的表名
		TableName[] tableNames = admin.listTableNames();
		hbaseAdmin.closeAdmin(admin);
		return Arrays.asList(tableNames);
	}
	
	@RequestMapping("/listTableNamesByNameRegex")
	public List<TableName> listTableNames(@RequestParam("name")String nameRegex, Boolean includeSysTables) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		Pattern pattern = Pattern.compile(nameRegex);
		TableName[] tableNames = null;
		if (includeSysTables == null) {
			tableNames = admin.listTableNames(pattern);
		} else {
			tableNames = admin.listTableNames(pattern, includeSysTables);
		}
		hbaseAdmin.closeAdmin(admin);
		return Arrays.asList(tableNames);
	}
	
	@RequestMapping("/listTableNamesByNamespace")
	public List<TableName> listTableNamesByNamespace(String namespace) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		TableName[] tableNames = admin.listTableNamesByNamespace(namespace);
		hbaseAdmin.closeAdmin(admin);
		return Arrays.asList(tableNames);
	}
	
	@RequestMapping("/listTableDescriptors")
	public List<TableDescriptor> listTableDescriptors() throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		//列出所有用户空间的表
		List<TableDescriptor> list = admin.listTableDescriptors();
		hbaseAdmin.closeAdmin(admin);
		return list;
	}
	
	//如果指定了namespace，names里就不要再带了
	//如果没有指定namespace，names可以带，
	//也可以不带，不带时使用默认的命名空间
	@RequestMapping("/listTableDescriptorsByNames")
	public List<TableDescriptor> listTableDescriptors(String namespace, @RequestParam("name")String... names) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		List<TableName> tableNames = new ArrayList<>();
		for (String name : names) {
			//带namespace的表名叫做name/fullName
			//不带namespace的表名叫做qualifier
			tableNames.add(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		}
		List<TableDescriptor> list = admin.listTableDescriptors(tableNames);
		hbaseAdmin.closeAdmin(admin);
		return list;
	}
	
	@RequestMapping("/listTableDescriptorsByNameRegex")
	public List<TableDescriptor> listTableDescriptors(@RequestParam("name")String nameRegex, Boolean includeSysTables) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		Pattern pattern = Pattern.compile(nameRegex);
		List<TableDescriptor> list = null;
		if (includeSysTables == null) {
			list = admin.listTableDescriptors(pattern);
		} else {
			list = admin.listTableDescriptors(pattern, includeSysTables);
		}
		hbaseAdmin.closeAdmin(admin);
		return list;
	}
	
	@RequestMapping("/listTableDescriptorsByNamespace")
	public List<TableDescriptor> listTableDescriptorsByNamespace(String namespace) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		List<TableDescriptor> list = admin.listTableDescriptorsByNamespace(Bytes.toBytes(namespace));
		hbaseAdmin.closeAdmin(admin);
		return list;
	}
	
	//namespace规则同上
	@RequestMapping("/getDescriptor")
	public TableDescriptor getDescriptor(String namespace, String name) throws TableNotFoundException, IOException {
		Admin admin = hbaseAdmin.getAdmin();
		TableDescriptor td = admin.getDescriptor(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return td;
	}
	
	@RequestMapping("/tableExists")
	public boolean tableExists(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		boolean result = admin.tableExists(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return result;
	}
	
	@RequestMapping("/isTableDisabled")
	public boolean isTableDisabled(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		boolean result = admin.isTableDisabled(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return result;
	}
	
	@RequestMapping("/isTableEnabled")
	public boolean isTableEnabled(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		boolean result = admin.isTableEnabled(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return result;
	}
	
	@RequestMapping("/disableTable")
	public String disableTable(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		admin.disableTable(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return "success";
	}
	
	@RequestMapping("/enableTable")
	public String enableTable(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		admin.enableTable(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return "success";
	}
	
	@RequestMapping("/isTableAvailable")
	public boolean isTableAvailable(String namespace, String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		boolean result = admin.isTableAvailable(namespace == null ? TableName.valueOf(name) : TableName.valueOf(namespace, name));
		hbaseAdmin.closeAdmin(admin);
		return result;
	}
	
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
	
	public void modifyTable(String name) throws IOException {
		TableDescriptor td = TableDescriptorBuilder.newBuilder(TableName.valueOf(name)).build();
		hbaseAdmin.getAdmin().modifyTable(td);
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
}

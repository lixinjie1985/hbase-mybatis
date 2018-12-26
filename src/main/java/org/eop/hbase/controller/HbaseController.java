package org.eop.hbase.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.eop.hbase.admin.HbaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixinjie
 * @since 2018-12-26
 */
@RequestMapping("/hbase")
@RestController
public class HbaseController {

	@Autowired
	private HbaseAdmin hbaseAdmin;
	
	@RequestMapping("/namespace/list")
	public List<NamespaceDescriptor> listNamespaces() throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		NamespaceDescriptor[] nds = admin.listNamespaceDescriptors();
		hbaseAdmin.closeAdmin(admin);
		return Arrays.asList(nds);
	}
	
	@RequestMapping("/namespace/get")
	public NamespaceDescriptor getNamespace(String name) throws NamespaceNotFoundException, IOException {
		Admin admin = hbaseAdmin.getAdmin();
		NamespaceDescriptor nd = admin.getNamespaceDescriptor(name);
		hbaseAdmin.closeAdmin(admin);
		return nd;
	}
	
	@RequestMapping("/namespace/create")
	public String createNamespace(String name) throws IOException {
		//系统命名空间，由Hbase自己使用
		NamespaceDescriptor sysND = NamespaceDescriptor.SYSTEM_NAMESPACE;
		byte[] sysN = NamespaceDescriptor.SYSTEM_NAMESPACE_NAME;
		String sysStr = NamespaceDescriptor.SYSTEM_NAMESPACE_NAME_STR;
		//默认命名空间，当没有指定命名空间时使用
		NamespaceDescriptor defND = NamespaceDescriptor.DEFAULT_NAMESPACE;
		byte[] defN = NamespaceDescriptor.DEFAULT_NAMESPACE_NAME;
		String defStr = NamespaceDescriptor.DEFAULT_NAMESPACE_NAME_STR;
		Admin admin = hbaseAdmin.getAdmin();
		//自己定义命名空间
	 	NamespaceDescriptor.Builder builder = NamespaceDescriptor.create(name);
	 	NamespaceDescriptor nd = builder.build();
	 	//创建
	 	admin.createNamespace(nd);
	 	hbaseAdmin.closeAdmin(admin);
	 	return "success";
	}
	
	public void modifyNamespace(String name) throws IOException {
		NamespaceDescriptor nd = NamespaceDescriptor.create(name).build();
		hbaseAdmin.getAdmin().modifyNamespace(nd);
	}
	
	@RequestMapping("/namespace/delete")
	public String deleteNamespace(String name) throws IOException {
		Admin admin = hbaseAdmin.getAdmin();
		admin.deleteNamespace(name);
		hbaseAdmin.closeAdmin(admin);
		return "success";
	}
}

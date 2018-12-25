package org.eop.hbase.admin.namespace;

import java.io.IOException;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.eop.hbase.admin.HbaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-25
 */
@Component
public class NamespaceAdmin {

	@Autowired
	private HbaseAdmin hbaseAdmin;
	
	public void createNamespace(String name) throws IOException {
		//系统命名空间，由Hbase自己使用
		NamespaceDescriptor sysND = NamespaceDescriptor.SYSTEM_NAMESPACE;
		byte[] sysN = NamespaceDescriptor.SYSTEM_NAMESPACE_NAME;
		String sysStr = NamespaceDescriptor.SYSTEM_NAMESPACE_NAME_STR;
		//默认命名空间，当没有指定命名空间时使用
		NamespaceDescriptor defND = NamespaceDescriptor.DEFAULT_NAMESPACE;
		byte[] defN = NamespaceDescriptor.DEFAULT_NAMESPACE_NAME;
		String defStr = NamespaceDescriptor.DEFAULT_NAMESPACE_NAME_STR;
		//自己定义命名空间
	 	NamespaceDescriptor.Builder builder = NamespaceDescriptor.create(name);
	 	NamespaceDescriptor nd = builder.build();
	 	//创建
	 	hbaseAdmin.getAdmin().createNamespace(nd);
	}
	
	public void deleteNamespace(String name) throws IOException {
		hbaseAdmin.getAdmin().deleteNamespace(name);
	}
	
	public NamespaceDescriptor getNamespace(String name) throws NamespaceNotFoundException, IOException {
		return hbaseAdmin.getAdmin().getNamespaceDescriptor(name);
	}
	
	public NamespaceDescriptor[] listNamespaces() throws IOException {
		return hbaseAdmin.getAdmin().listNamespaceDescriptors();
	}
	
	public void modifyNamespace(String name) throws IOException {
		NamespaceDescriptor nd = NamespaceDescriptor.create(name).build();
		hbaseAdmin.getAdmin().modifyNamespace(nd);
	}
}

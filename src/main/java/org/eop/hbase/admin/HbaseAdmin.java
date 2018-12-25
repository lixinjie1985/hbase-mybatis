package org.eop.hbase.admin;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-25
 */
@Component
public class HbaseAdmin {

	@Autowired
	private Connection connection;
	
	public Admin getAdmin() throws IOException {
		return connection.getAdmin();
	}
	
	public void closeAdmin(Admin admin) throws IOException {
		admin.close();
	}
}

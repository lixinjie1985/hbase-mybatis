package org.eop.hbase.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author lixinjie
 * @since 2018-12-21
 */
@ConfigurationProperties(prefix = "hbase")
public class HbaseProperties {
	
	@NestedConfigurationProperty
	private ZookeeperProperties zookeeper;

	@NestedConfigurationProperty
	private RpcProperties rpc;

	@NestedConfigurationProperty
	private ClientProperties client;

	public ZookeeperProperties getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZookeeperProperties zookeeper) {
		this.zookeeper = zookeeper;
	}

	public RpcProperties getRpc() {
		return rpc;
	}

	public void setRpc(RpcProperties rpc) {
		this.rpc = rpc;
	}

	public ClientProperties getClient() {
		return client;
	}

	public void setClient(ClientProperties client) {
		this.client = client;
	}
}

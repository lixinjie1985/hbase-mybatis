package org.eop.hbase.configuration;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
public class RpcProperties {

	private Integer timeout;
	private Integer readTimeout;
	private Integer writeTimeout;
	
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Integer getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
	public Integer getWriteTimeout() {
		return writeTimeout;
	}
	public void setWriteTimeout(Integer writeTimeout) {
		this.writeTimeout = writeTimeout;
	}
}

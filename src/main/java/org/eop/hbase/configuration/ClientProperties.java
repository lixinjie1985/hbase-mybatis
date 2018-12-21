package org.eop.hbase.configuration;

/**
 * @author lixinjie
 * @since 2018-12-19
 */
public class ClientProperties {

	private Integer operationTimeout;
	private Integer scannerTimeoutPeriod;
	
	public Integer getOperationTimeout() {
		return operationTimeout;
	}
	public void setOperationTimeout(Integer operationTimeout) {
		this.operationTimeout = operationTimeout;
	}
	public Integer getScannerTimeoutPeriod() {
		return scannerTimeoutPeriod;
	}
	public void setScannerTimeoutPeriod(Integer scannerTimeoutPeriod) {
		this.scannerTimeoutPeriod = scannerTimeoutPeriod;
	}
}

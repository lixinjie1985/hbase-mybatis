package org.eop.hbmy.autoconfigure.httpinvoker;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lixinjie
 * @since 2018-09-07
 */
@ConfigurationProperties(prefix = "httpinvoker")
public class HttpInvokerProperties {

	private Map<String, HttpComponentsEntry> httpcomponents;
	private Map<String, OkHttp3Entry> okhttp3;
	
	public Map<String, HttpComponentsEntry> getHttpcomponents() {
		return httpcomponents;
	}
	public void setHttpcomponents(Map<String, HttpComponentsEntry> httpcomponents) {
		this.httpcomponents = httpcomponents;
	}
	public Map<String, OkHttp3Entry> getOkhttp3() {
		return okhttp3;
	}
	public void setOkhttp3(Map<String, OkHttp3Entry> okhttp3) {
		this.okhttp3 = okhttp3;
	}

	public static class HttpComponentsEntry {
		private int connectionRequestTimeout;
		private int connectTimeout;
		private int readTimeout;
		private int maxConnPerRoute;
		private int maxConnTotal;
		private long maxIdleTime;
		
		public int getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}
		public void setConnectionRequestTimeout(int connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
		}
		public int getConnectTimeout() {
			return connectTimeout;
		}
		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		public int getReadTimeout() {
			return readTimeout;
		}
		public void setReadTimeout(int readTimeout) {
			this.readTimeout = readTimeout;
		}
		public int getMaxConnPerRoute() {
			return maxConnPerRoute;
		}
		public void setMaxConnPerRoute(int maxConnPerRoute) {
			this.maxConnPerRoute = maxConnPerRoute;
		}
		public int getMaxConnTotal() {
			return maxConnTotal;
		}
		public void setMaxConnTotal(int maxConnTotal) {
			this.maxConnTotal = maxConnTotal;
		}
		public long getMaxIdleTime() {
			return maxIdleTime;
		}
		public void setMaxIdleTime(long maxIdleTime) {
			this.maxIdleTime = maxIdleTime;
		}
	}
	
	public static class OkHttp3Entry {
		private int connectTimeout;
		private int readTimeout;
		private int writeTimeout;
		
		public int getConnectTimeout() {
			return connectTimeout;
		}
		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		public int getReadTimeout() {
			return readTimeout;
		}
		public void setReadTimeout(int readTimeout) {
			this.readTimeout = readTimeout;
		}
		public int getWriteTimeout() {
			return writeTimeout;
		}
		public void setWriteTimeout(int writeTimeout) {
			this.writeTimeout = writeTimeout;
		}
	}
}

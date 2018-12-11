package org.eop.hbmy.autoconfigure.httpinvoker;

import org.eop.common.http.invoker.HttpComponentsRestTemplateHttpInvoker;
import org.eop.common.http.invoker.IHttpInvoker;
import org.eop.common.http.invoker.OkHttp3RestTemplateHttpInvoker;
import org.eop.hbmy.autoconfigure.httpinvoker.HttpInvokerProperties.HttpComponentsEntry;
import org.eop.hbmy.autoconfigure.httpinvoker.HttpInvokerProperties.OkHttp3Entry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-09-07
 */
@Configuration
@EnableConfigurationProperties(HttpInvokerProperties.class)
public class HttpInvokerAutoConfiguration {

	private HttpInvokerProperties httpInvokerProperties;
	
	public HttpInvokerAutoConfiguration(HttpInvokerProperties httpInvokerProperties) {
		this.httpInvokerProperties = httpInvokerProperties;
	}
	
	@Bean
	public IHttpInvoker httpcomponentsHttpInvoker() {
		HttpComponentsEntry entry = httpInvokerProperties.getHttpcomponents().get("httpcomponentsHttpInvoker");
		return new HttpComponentsRestTemplateHttpInvoker(entry.getConnectionRequestTimeout(),
				entry.getConnectTimeout(), entry.getReadTimeout(), entry.getMaxConnPerRoute(),
				entry.getMaxConnTotal(), entry.getMaxIdleTime());
	}
	
	@Bean
	public IHttpInvoker okhttp3HttpInvoker() {
		OkHttp3Entry entry = httpInvokerProperties.getOkhttp3().get("okhttp3HttpInvoker");
		return new OkHttp3RestTemplateHttpInvoker(entry.getConnectTimeout(), entry.getReadTimeout(),
				entry.getWriteTimeout());
	}
}

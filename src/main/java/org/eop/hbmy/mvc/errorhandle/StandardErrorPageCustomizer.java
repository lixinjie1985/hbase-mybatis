package org.eop.hbmy.mvc.errorhandle;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-09-06
 */
@Component
public class StandardErrorPageCustomizer implements ErrorPageRegistrar, Ordered {

	private ServerProperties serverProperties;
	
	public StandardErrorPageCustomizer(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}
	
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		String errorPath = serverProperties.getError().getPath();
		registry.addErrorPages(new ErrorPage(RuntimeException.class, errorPath + "/RuntimeException"),
				new ErrorPage(Exception.class, errorPath + "/Exception"),
				new ErrorPage(Throwable.class, errorPath + "/Throwable"),
				new ErrorPage(errorPath + "/error"));
	}
	
	@Override
	public int getOrder() {
		return 10;
	}

	public ServerProperties getServerProperties() {
		return serverProperties;
	}

}

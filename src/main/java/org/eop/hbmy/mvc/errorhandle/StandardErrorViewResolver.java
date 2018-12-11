package org.eop.hbmy.mvc.errorhandle;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.DefaultErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lixinjie
 * @since 2018-09-06
 */
@Component
public class StandardErrorViewResolver extends DefaultErrorViewResolver {

	public StandardErrorViewResolver(ApplicationContext applicationContext,
		ResourceProperties resourceProperties) {
		super(applicationContext, resourceProperties);
	}

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		ModelAndView modelAndView = super.resolveErrorView(request, status, model);
		try {
			if (modelAndView == null) {
				Method resolve = DefaultErrorViewResolver.class.getDeclaredMethod("resolve", String.class, Map.class);
				resolve.setAccessible(true);
				modelAndView = (ModelAndView)resolve.invoke(this, "error", model);
			}
		} catch (Exception e) {
			//ignore
		}
		return modelAndView;
	}
}

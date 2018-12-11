package org.eop.hbmy.mvc.errorhandle;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eop.common.result.RestResult;
import org.eop.common.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lixinjie
 * @since 2018-09-05
 */
@Controller
public class StandardErrorController extends BasicErrorController {

	private static final Logger log = LoggerFactory.getLogger(StandardErrorController.class);
	
	private ErrorAttributes errorAttributes;
	private ServerProperties serverProperties;
	
	public StandardErrorController(ErrorAttributes errorAttributes,
			ServerProperties serverProperties,
			ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
		super(errorAttributes, serverProperties.getError(),
				errorViewResolversProvider.getIfAvailable());
		this.errorAttributes = errorAttributes;
		this.serverProperties = serverProperties;
	}

	@RequestMapping(path = "/error", produces = "text/html")
	public ModelAndView handleErrorHtml(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = super.errorHtml(request, response);
		System.out.println(getError(request));
		System.out.println(modelAndView.getModel());
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(path = "/error")
	public RestResult handleError(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Map<String, Object>> responseEntity = super.error(request);
		return new RestResult(responseEntity.getStatusCode().value(),
				responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getBody());
	}
	
	
	@RequestMapping(path = "/RuntimeException", produces = "text/html")
	public ModelAndView handleRuntimeExceptionHtml(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		//500, "Internal Server Error"
		return handleErrorHtml(request, response);
	}
	
	@ResponseBody
	@RequestMapping(path = "/RuntimeException")
	public RestResult handleRuntimeException(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		return new RestResult(ResultCode.ServerError.getCode(), ResultCode.ServerError.getDesc(),
				super.error(request).getBody());
	}
	
	@RequestMapping(path = "/Exception", produces = "text/html")
	public ModelAndView handleExceptionHtml(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		//500, "Internal Server Error"
		return handleErrorHtml(request, response);
	}
	
	@ResponseBody
	@RequestMapping(path = "/Exception")
	public RestResult handleException(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		return new RestResult(ResultCode.ServerError.getCode(), ResultCode.ServerError.getDesc(),
				super.error(request).getBody());
	}
	
	@RequestMapping(path = "/Throwable", produces = "text/html")
	public ModelAndView handleThrowableHtml(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		//500, "Internal Server Error"
		return handleErrorHtml(request, response);
	}
	
	@ResponseBody
	@RequestMapping(path = "/Throwable")
	public RestResult handleThrowable(HttpServletRequest request, HttpServletResponse response) {
		log.error("", getError(request));
		return new RestResult(ResultCode.ServerError.getCode(), ResultCode.ServerError.getDesc(),
				super.error(request).getBody());
	}
	
	protected Throwable getError(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getError(requestAttributes);
	}

	protected ErrorAttributes getErrorAttributes() {
		return errorAttributes;
	}

	protected ServerProperties getServerProperties() {
		return serverProperties;
	}
	
}

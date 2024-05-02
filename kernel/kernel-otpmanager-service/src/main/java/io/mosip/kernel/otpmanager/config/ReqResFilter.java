package io.mosip.kernel.otpmanager.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import io.mosip.kernel.core.logger.spi.Logger;

/**
 * Request Response Filter class that implements {@link Filter}.
 * 
 * @author Sagar Mahapatra
 * @since 1.0.0
 *
 */
public class ReqResFilter implements Filter {

	private static final Logger mosipLogger = LoggerConfiguration.logConfig(ReqResFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// over-ridden method
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		ContentCachingRequestWrapper requestWrapper = null;
		ContentCachingResponseWrapper responseWrapper = null;
		try {
			// Default processing for url ends with .stream
			if (httpServletRequest.getRequestURI().endsWith(".stream")) {
				chain.doFilter(request, response);
				return;
			}
			requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
			responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
			chain.doFilter(requestWrapper, responseWrapper);
			responseWrapper.copyBodyToResponse();

		} catch (Exception e) {
			mosipLogger.error("", "", "", e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// over-ridden method
	}
}

package fr.mch.mdo.restaurant.web;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;

public class HeaderFilter implements Filter
{
	private static ILogger logger = MdoBeanFactory.getInstance().getLogger(ForbiddenDirectAccessJspFilter.class.getName());
	private FilterConfig filterConfig;
	private Map<String, String> headersMap;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		String headerParam = filterConfig.getInitParameter("header");
		if (headerParam == null) {
			logger.info("No headers were found in the web.xml (init-param) for the HeaderFilter !");
			return;
		}

		this.headersMap = new LinkedHashMap<String, String>();

		if (headerParam.contains("|")) {
			String[] headers = headerParam.split("\\|");
			for (String header : headers)
				parseHeader(header);
		} else {
			parseHeader(headerParam);
		}

		logger.info("The following headers were registered in the HeaderFilter :");
		Set<Map.Entry<String, String>> headers = this.headersMap.entrySet();
		for (Map.Entry<String, String> item : headers)
			logger.info((String) item.getKey() + ':' + (String) item.getValue());
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (this.headersMap != null) {
			Set<Map.Entry<String, String>> headers = this.headersMap.entrySet();
			for (Map.Entry<String, String> header : headers) {
				((HttpServletResponse) response).setHeader((String) header.getKey(), (String) header.getValue());
			}

		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		this.filterConfig = null;
		this.headersMap = null;
	}

	private void parseHeader(String header) {
		String headerName = header.substring(0, header.indexOf(":"));
		if (!this.headersMap.containsKey(headerName))
			this.headersMap.put(headerName, header.substring(header.indexOf(":") + 1));
	}
}
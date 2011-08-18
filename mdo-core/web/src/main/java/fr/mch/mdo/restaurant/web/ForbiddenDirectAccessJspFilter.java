package fr.mch.mdo.restaurant.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;

public class ForbiddenDirectAccessJspFilter implements Filter 
{
	private static ILogger logger = MdoBeanFactory.getInstance().getLogger(ForbiddenDirectAccessJspFilter.class.getName());

	private String defaultEntryURI = null;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Un-comment the next line if we want to enable JSP access
		// chain.doFilter(request, response); return;

		if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
			logger.warn("message.debug.permitted.jsp");
			((HttpServletResponse) response).sendRedirect(defaultEntryURI);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		defaultEntryURI = filterConfig.getServletContext().getContextPath() + filterConfig.getServletContext().getInitParameter(Constants.DEFAULT_ENTRY_URI_KEY);

		// throw new ServletException("Unable to get the " +
		// Constants.DEFAULT_FORWARD_KEY + " value in the web.xml file");
	}

}

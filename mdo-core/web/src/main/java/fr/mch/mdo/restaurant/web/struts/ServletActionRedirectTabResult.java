package fr.mch.mdo.restaurant.web.struts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ServletActionRedirectResult;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ServletActionRedirectTabResult extends ServletActionRedirectResult
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String tab;

	/**
	 * @param tab
	 *            the tab to set
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}

	/**
	 * @return the tab
	 */
	public String getTab() {
		return tab;
	}

	@Override
	protected List<String> getProhibitedResultParams() {
		List<String> result = new ArrayList<String>(super.getProhibitedResultParams());
		// Do not take into account "tab" parameter as request parameter in the
		// URL
		result.add("tab");
		return result;
	}

	@Override
	protected void sendRedirect(HttpServletResponse response, String finalLocation) throws IOException {
		// tab parameter must be in last position
		super.sendRedirect(response, finalLocation + tab);
	}
}

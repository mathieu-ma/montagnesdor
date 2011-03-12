package fr.mch.mdo.restaurant.web.taglib;


import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlParseMenuTag extends TagSupport
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4729152639479222337L;
	
	private static final String MENU_ENTRY = "menu-entry";
	private static final String SELECTED_ITEM = "selected";
	private static final String SELECTED_ITEM_VALUE = "true";
	private static final String GENERATED_ID = "generated-id";
	private static final String SUB_MENU = "sub-menu";
	private static final String ITEM = "item";
	private static final String LABEL = "label";
	private static final String LABEL_KEY = "key";
	private static final String LINK = "link";
	
	private Object					doc;
	private String					selectedMenuItemId;
	private List<String>				selectedMenuItemIdParentList;
	private String					var;

	private ResourceBundle bundle;
	
	// state in support of XML parsing...
	private DocumentBuilderFactory	dbf;
	private DocumentBuilder			db;

	// *********************************************************************
	// Constructor and initialization
	public XmlParseMenuTag()
	{
		super();
		init();
	}

	private void init()
	{
		doc = null;
		selectedMenuItemId = null;
		selectedMenuItemIdParentList = null;
		var = null;
		dbf = null;
		db = null;
	}

	// parse 'source' or body, storing result in 'var'
	public int doEndTag() throws JspException
	{
		//String selectedMenuItemIdEL = (String)ExpressionEvaluatorManager.evaluate("selectedMenuItemId", this.selectedMenuItemId, String.class, this, super.pageContext);
		try
		{
			//Generate list of parent selected item 
			processSelectedMenuItemIdParentList();
			
			bundle = BundleSupport.getLocalizationContext(pageContext).getResourceBundle();
			
			// set up our DocumentBuilder
			if(dbf == null)
			{
				dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				dbf.setValidating(false);
			}
			db = dbf.newDocumentBuilder();

			// produce a Document by parsing whatever the attributes tell us to
			// use
			Document d;
			Object xmlText = this.doc;
			if(xmlText instanceof Reader)
				d = parseInputSource(new InputSource((Reader) xmlText));
			else
				throw new JspTagException(Resources.getMessage("PARSE_INVALID_SOURCE"));

			// we've got a Document object; store it out as appropriate
			// (let any exclusivity or other constraints be enforced by TEI/TLV)
			if(var != null)
				pageContext.setAttribute(var, d, PageContext.PAGE_SCOPE);

			return EVAL_PAGE;
		}
		catch(SAXException ex)
		{
			throw new JspException(ex);
		}
		catch(IOException ex)
		{
			throw new JspException(ex);
		}
		catch(ParserConfigurationException ex)
		{
			throw new JspException(ex);
		}
	}

	// *********************************************************************
	// Private utility methods
	/** Parses the given InputSource into a Document. */
	private Document parseInputSource(InputSource s) throws SAXException, IOException
	{
		Document result = db.parse(s);
		
		Element root = result.getDocumentElement();
		if(root.hasChildNodes())
		{
			processElement(root.getChildNodes(), "");
		}

		return result;
	}

	
	private void processSelectedMenuItemIdParentList()
	{
		selectedMenuItemIdParentList = new ArrayList<String>();
		String temp = "1";
		if(selectedMenuItemId!=null && !selectedMenuItemId.equals("0") && selectedMenuItemId.matches("\\d+(_\\d+)*"))
		{
			temp = new String(selectedMenuItemId);
		}
		selectedMenuItemIdParentList.add(temp);

		while(temp.lastIndexOf("_")>-1)
		{
			temp = temp.substring(0, temp.lastIndexOf("_"));
			selectedMenuItemIdParentList.add(temp);
		}
	}
	
	public String getSelectedMenuItemId()
	{
		return selectedMenuItemId;
	}

	public void setSelectedMenuItemId(String selectedMenuItemId)
	{
		this.selectedMenuItemId = selectedMenuItemId;
	}

	public void setDoc(Object doc)
	{
		this.doc = doc;
	}

	public void setVar(String var)
	{
		this.var = var;
	}

	public void processElement(NodeList list, String parentGeneratedId)
	{
		int itemSubmenuId = 0;
		for(int i=0; i<list.getLength(); i++)
		{
			Node node = list.item(i);
			//System.out.println("*****START********");
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				
				String generatedId = "";
				if(element.getNodeName().equals(MENU_ENTRY) || element.getNodeName().equals(SUB_MENU) || element.getNodeName().equals(ITEM))
				{
					itemSubmenuId++;
					generatedId = generateMenuId(element, parentGeneratedId, itemSubmenuId);
					if(selectedMenuItemId==null || selectedMenuItemId.equals(""))
					{
						selectedMenuItemId = "1";
					}
					processSelectedItem(element, selectedMenuItemId);
				}
				
				//System.out.println(element.getNodeName()+":"+element.getNodeValue());
				if(element.getNodeName()!=null && element.getNodeName().equals(LABEL))
				{
					processLabelElement(element);
				}
				if(element.getNodeName()!=null && element.getNodeName().equals(LINK))
				{
					processLinkElement(element);
				}
				
				//System.out.println("*****END********");
				if(node.hasChildNodes())
				{
					processElement(node.getChildNodes(), generatedId);
				}
			}
		}
	}
	
	private String generateMenuId(Element element, String parentGeneratedId, int itemSubmenuId)
	{
		String generatedId = "";

		if(parentGeneratedId.equals(""))
			generatedId = ""+itemSubmenuId;
		else if(element.getNodeName().equals(ITEM))
		{
			String prefixParentGeneratedId = parentGeneratedId;
			if(parentGeneratedId.lastIndexOf("_")>0)
			{
				prefixParentGeneratedId = parentGeneratedId.substring(0, parentGeneratedId.lastIndexOf("_")+1);
			}
			generatedId = prefixParentGeneratedId + (itemSubmenuId + 1);
		}
		else
			generatedId = parentGeneratedId+"_"+itemSubmenuId;
		
		element.setAttribute(GENERATED_ID, generatedId);
			
		return generatedId;
	}
	
	private void processLinkElement(Element element)
	{
		if(element.hasChildNodes())
		{
			NodeList nodeList = element.getChildNodes(); 
			for(int i=0; i<nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);
				if(node.getNodeType()==Node.TEXT_NODE)
				{
					String link = pageContext.getServletContext().getContextPath() + node.getNodeValue();
					Document doc = element.getOwnerDocument();
					element.replaceChild(doc.createTextNode(link), node);
					break;
				}
			}
		}
	}
	
	private void processLabelElement(Element element)
	{
		if(element.hasAttributes())
		{
			String key = element.getAttribute(LABEL_KEY);
			//System.out.println("Key "+key);
			if(key!=null)
			{
				element.removeAttribute(LABEL_KEY);
				String label = bundle.getString(key);
				Document doc = element.getOwnerDocument();
				element.appendChild(doc.createTextNode(label));
			}	
		}
	}

	private void processSelectedItem(Element element, String currentSelectedItemId)
	{
		if(element.hasAttributes())
		{
			String generatedId = element.getAttribute(GENERATED_ID);
			if(generatedId!=null)
			{
				if(selectedMenuItemIdParentList.contains(generatedId))
				{
					element.setAttribute(SELECTED_ITEM, SELECTED_ITEM_VALUE);
				}
			}	
		}
	}
	
/*	
	public static String currentSelectedItemId ="1_2_1";
	public static void main(String[] args) throws Exception
	{
			try
			{
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	
				//initialize StreamResult with File object to save to file
				StreamResult result = new StreamResult(new StringWriter());
				DOMSource source = new DOMSource(d);
				transformer.transform(source, result);
	
				String xmlString = result.getWriter().toString();
				System.out.println(xmlString);	
			}
			catch(Exception e)
			{
				
			}

	}
*/	
}

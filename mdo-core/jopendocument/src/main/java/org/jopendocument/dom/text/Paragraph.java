/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.dom.text;

import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.ODSingleXMLDocument;

import java.util.HashSet;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 * A text paragraph, the basic unit of text. See §4.1 of the OpenDocument specification.
 */
public class Paragraph extends TextNode<ParagraphStyle> {

    static Element createEmpty(XMLVersion ns) {
        return new Element("p", ns.getTEXT());
    }

    static private XPath textStylesPath = null;

    static private Set<String> getTextStyles(final Element elem) {
        final Set<String> res = new HashSet<String>();
        synchronized (Paragraph.class) {
            if (textStylesPath == null)
                try {
                    textStylesPath = XPath.newInstance(".//text:span/@text:style-name");
                } catch (JDOMException e) {
                    // shouldn't happen since it's a constant
                    throw new IllegalStateException(e);
                }
            try {
                for (final Object o : textStylesPath.selectNodes(elem))
                    res.add(((Attribute) o).getValue());
            } catch (JDOMException e) {
                throw new IllegalArgumentException("could'nt evaluate with " + elem, e);
            }
        }
        return res;
    }

    public Paragraph(Element elem) {
        super(elem, ParagraphStyle.class);
    }

    public Paragraph(XMLVersion ns) {
        this(createEmpty(ns));
    }

    public Paragraph() {
        this(XMLVersion.getDefault());
    }

    public Paragraph(String text) {
        this();
        addContent(text);
    }

    public final void setStyle(String styleName) {
        getElement().setAttribute("style-name", styleName, getElement().getNamespace());
    }

    public final void addContent(String text) {
        this.getElement().addContent(text);
    }

    public final void addTab() {
        this.getElement().addContent(new Element("tab", getElement().getNamespace()));
    }

    public final void addStyledContent(String text, String styleName) {
        if (styleName.equals(getStyleName())) {
            this.addContent(text);
        } else {
            final Element span = new Element("span", getElement().getNamespace());
            span.setAttribute("style-name", styleName, getElement().getNamespace());
            span.addContent(text);
            getElement().addContent(span);
        }
    }

    private final Set<String> getUsedTextStyles() {
        return getTextStyles(getElement());
    }

    protected void checkDocument(ODSingleXMLDocument doc) {
        if (this.getStyleName() != null && getStyle(doc.getPackage(), doc.getDocument()) == null)
            throw new IllegalArgumentException("unknown style " + getStyleName() + " in " + doc);
        for (final String styleName : this.getUsedTextStyles()) {
            if (doc.getStyle(TextStyle.STYLE_FAMILY, styleName) == null) {
                throw new IllegalArgumentException(this + " is using a text:span with an undefined style : " + styleName);
            }
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getElement().getText();
    }
}

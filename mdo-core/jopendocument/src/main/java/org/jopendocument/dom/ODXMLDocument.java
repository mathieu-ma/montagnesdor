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

/*
 * Créé le 28 oct. 2004
 */
package org.jopendocument.dom;

import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.XPathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Factory;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

/**
 * An OpenDocument XML document, like content.xml ou styles.xml.
 * 
 * @author Sylvain CUAZ
 */
public class ODXMLDocument {

    /**
     * All top-level elements that an office document may contain. Note that only the single xml
     * representation (office:document) contains all of them.
     */
    private static final Map<XMLVersion, List<Element>> ELEMS_ORDER;
    static {
        ELEMS_ORDER = new HashMap<XMLVersion, List<Element>>(2);
        ELEMS_ORDER.put(XMLVersion.getOOo(), createChildren(XMLVersion.getOOo()));
        ELEMS_ORDER.put(XMLVersion.getOD(), createChildren(XMLVersion.getOD()));
    }

    private static final List<Element> createChildren(XMLVersion ins) {
        final Namespace ns = ins.getOFFICE();
        final List<Element> res = new ArrayList<Element>(8);
        res.add(new Element("meta", ns));
        res.add(new Element("settings", ns));
        res.add(new Element("script", ns));
        res.add(new Element("font-decls", ns));
        res.add(new Element("styles", ns));
        res.add(new Element("automatic-styles", ns));
        res.add(new Element("master-styles", ns));
        res.add(new Element("body", ns));
        return res;
    }

    // namespaces for the name attributes
    static private final Map<String, String> namePrefixes;
    static {
        namePrefixes = new HashMap<String, String>();
        namePrefixes.put("table:table", "table");
        namePrefixes.put("text:a", "office");
        namePrefixes.put("draw:text-box", "draw");
        namePrefixes.put("draw:image", "draw");
        namePrefixes.put("draw:frame", "draw");
    }

    /**
     * The XML elements posessing a name.
     * 
     * @return the qualified names of named elements.
     * @see #getDescendantByName(String, String)
     */
    public static Set<String> getNamedElements() {
        return Collections.unmodifiableSet(namePrefixes.keySet());
    }

    private final Document content;
    private final XMLVersion version;
    private final ChildCreator childCreator;

    // before making it public, assure that content is really of version "version"
    // eg by checking some namespace
    protected ODXMLDocument(final Document content, final XMLVersion version) {
        if (content == null)
            throw new NullPointerException("null document");
        this.content = content;
        this.version = version;
        this.childCreator = new ChildCreator(this.content.getRootElement(), ELEMS_ORDER.get(this.getVersion()));
    }

    public ODXMLDocument(Document content) {
        this(content, XMLVersion.getVersion(content.getRootElement()));
    }

    public ODXMLDocument(ODXMLDocument doc) {
        this((Document) doc.content.clone(), doc.version);
    }

    public Document getDocument() {
        return this.content;
    }

    public String isValid() {
        return OOXML.get(this.getVersion()).isValid(this.getDocument());
    }

    public final XMLVersion getVersion() {
        return this.version;
    }

    // *** children
    
    public final Element getChild(String childName) {
        return this.getChild(childName, false);
    }

    /**
     * Return the asked child, optionally creating it.
     * 
     * @param childName the name of the child.
     * @param create whether it should be created in case it doesn't exist.
     * @return the asked child or <code>null</code> if it doesn't exist and create is
     *         <code>false</code>
     */
    public Element getChild(String childName, boolean create) {
        return this.childCreator.getChild(this.getVersion().getOFFICE(), childName, create);
    }

    public void setChild(Element elem) {
        if (!elem.getNamespace().equals(this.getVersion().getOFFICE()))
            throw new IllegalArgumentException("all children of a document belong to the office namespace.");
        this.childCreator.setChild(elem);
    }

    // *** descendants

    protected final Element getDescendant(String path) throws JDOMException {
        return this.getDescendant(path, false);
    }

    protected final Element getDescendant(String path, boolean create) throws JDOMException {
        Element res = (Element) this.getXPath(path).selectSingleNode(this.getDocument().getRootElement());
        if (res == null && create) {
            final Element parent = this.getDescendant(XPathUtils.parentOf(path), create);
            final String namespace = XPathUtils.namespace(path);
            final Namespace ns = namespace == null ? null : this.getVersion().getNS(namespace);
            res = new Element(XPathUtils.localName(path), ns);
            parent.addContent(res);
        }
        return res;
    }

    public final XPath getXPath(String string) throws JDOMException {
        return OOUtils.getXPath(string, this.getVersion());
    }

    /**
     * Search for a descendant with the passed name.
     * 
     * @param qName the XML element qualified name, eg "table:table".
     * @param name the value of the name, eg "MyTable".
     * @return the first element named <code>name</code> or <code>null</code> if none is found, eg
     *         &lt;table:table table:name="MyTable" &gt;
     * @throws IllegalArgumentException if <code>qName</code> is not in {@link #getNamedElements()}
     */
    public final Element getDescendantByName(String qName, String name) {
        return this.getDescendantByName(this.getDocument().getRootElement(), qName, name);
    }

    public final Element getDescendantByName(Element root, String qName, String name) {
        if (root.getDocument() != this.getDocument())
            throw new IllegalArgumentException("root is not part of this.");
        if (!namePrefixes.containsKey(qName))
            throw new IllegalArgumentException(qName + " not in " + getNamedElements());
        final String xp = ".//" + qName + "[@" + namePrefixes.get(qName) + ":name='" + name + "']";
        try {
            return (Element) this.getXPath(xp).selectSingleNode(root);
        } catch (JDOMException e) {
            // static xpath, should not happen
            throw new IllegalStateException("could not find " + xp, e);
        }
    }

    // *** styles

    public final Element getStyle(final String family, final String name) {
        // see section 14.1 § Style Name : "uniquely identifies a style"
        // was using an XPath but we had performance issues, we first rewrote it using variables
        // (and thus parsing it only once) and saw a 40% speedup, but by rewriting it in java we
        // we went from 70ms/instance + 1ms/call to 0.015ms/call :-)
        // final String stylePath = "style:style[@style:family=$family and @style:name=$name]";
        // this.styleXP = this.getXPath("./office:styles/" + stylePath +
        // " | ./office:automatic-styles/" + stylePath +
        // " | ./office:master-styles/style:master-page/" + stylePath);
        final Element root = this.getDocument().getRootElement();
        final Namespace office = getVersion().getOFFICE();
        Element res = this.findStyleStyleChild(root.getChild("styles", office), family, name);
        if (res != null) {
            return res;
        }

        res = this.findStyleStyleChild(root.getChild("automatic-styles", office), family, name);
        if (res != null) {
            return res;
        }

        final Element masterStyles = root.getChild("master-styles", office);
        if (masterStyles != null) {
            res = this.findStyleStyleChild(root.getChild("master-page", getVersion().getSTYLE()), family, name);
            if (res != null) {
                return res;
            }
        }

        return null;
    }

    private final Element findStyleStyleChild(final Element styles, final String family, final String name) {
        if (styles == null)
            return null;

        final Namespace styleNS = getVersion().getSTYLE();
        // from JDOM : traversal through the List is best done with a Iterator
        for (final Object o : styles.getChildren("style", styleNS)) {
            final Element styleElem = (Element) o;
            // name first since it is more specific (and often includes family, eg "co2")
            if (name.equals(styleElem.getAttributeValue("name", styleNS)) && family.equals(styleElem.getAttributeValue("family", styleNS))) {
                return styleElem;
            }
        }
        return null;
    }

    /**
     * Find an unused style name in this document.
     * 
     * @param family the family of the style, eg "table-column".
     * @param baseName the base name, eg "myColStyle".
     * @return an unused name, eg "myColStyle12".
     */
    public final String findUnusedName(final String family, final String baseName) {
        for (int i = 0; i < 1000; i++) {
            final String name = baseName + i;
            final Element elem = this.getStyle(family, name);
            if (elem == null)
                return name;
        }
        return null;
    }

    public final void addAutoStyle(final Element styleElem) {
        this.getChild("automatic-styles", true).addContent(styleElem);
    }

    public String asString() {
        return JDOMUtils.output(this.content);
    }

    protected static interface ElementTransformer {
        Element transform(Element elem) throws JDOMException;
    }

    protected static final ElementTransformer NOP_ElementTransformer = new ElementTransformer() {
        public Element transform(Element elem) {
            return elem;
        }
    };

    protected void mergeAll(ODXMLDocument other, String path) throws JDOMException {
        this.mergeAll(other, path, null);
    }

    /**
     * Fusionne l'élément spécifié par topElem. Applique addTransf avant l'ajout. Attention seuls
     * les élément (et non les commentaires, text, etc.) de <code>other</code> sont ajoutés.
     * 
     * @param other le document à fusionner.
     * @param path le chemon de l'élément à fusionner, eg "./office:body".
     * @param addTransf la transformation à appliquer avant d'ajouter ou <code>null</code>.
     * @throws JDOMException
     */
    protected void mergeAll(ODXMLDocument other, String path, ElementTransformer addTransf) throws JDOMException {
        this.add(path, -1, other, path, addTransf);
    }

    /**
     * Add the part pointed by <code>rpath</code> of other in this document like child number
     * <code>lindex</code> of the part pointed by <code>lpath</code>.
     * 
     * @param lpath local xpath.
     * @param lindex local index beneath lpath, < 0 meaning the end.
     * @param other the document to add.
     * @param rpath the remote xpath, note: the content of that element will be added NOT the
     *        element itself.
     * @param addTransf the children of rpath will be transformed, can be <code>null</code>.
     * @throws JDOMException if an error occur.
     */
    protected void add(final String lpath, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        this.add(new Factory() {
            public Object create() {
                try {
                    return getDescendant(lpath, true);
                } catch (JDOMException e) {
                    throw new IllegalStateException("error", e);
                }
            }
        }, lindex, other, rpath, addTransf);
    }

    /**
     * Add the part pointed by <code>rpath</code> of other in this document like child number
     * <code>lindex</code> of <code>elem</code>.
     * 
     * @param elem local element, if <code>null</code> add to rpath see
     *        {@link #mergeAll(ODXMLDocument, String, org.jopendocument.dom.ODXMLDocument.ElementTransformer)}
     *        .
     * @param lindex local index beneath lpath, < 0 meaning the end, ignored if elem is
     *        <code>null</code>.
     * @param other the document to add.
     * @param rpath the remote xpath, note: the content of that element will be added NOT the
     *        element itself.
     * @param addTransf the children of rpath will be transformed, can be <code>null</code>.
     * @throws JDOMException if an error occur.
     */
    protected void add(final Element elem, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        if (elem == null) {
            this.mergeAll(other, rpath, addTransf);
        } else {
            if (!this.getDocument().getRootElement().isAncestor(elem))
                throw new IllegalArgumentException(elem + " not part of " + this);
            this.add(new Factory() {
                public Object create() {
                    return elem;
                }
            }, lindex, other, rpath, addTransf);
        }
    }

    @SuppressWarnings("unchecked")
    private void add(Factory elemF, int lindex, ODXMLDocument other, String rpath, ElementTransformer addTransf) throws JDOMException {
        final Element toAdd = other.getDescendant(rpath);
        // si on a qqchose à ajouter
        if (toAdd != null) {
            final List<Content> cloned = toAdd.cloneContent();
            final List<Content> listToAdd;
            if (addTransf == null) {
                listToAdd = cloned;
            } else {
                listToAdd = new ArrayList<Content>(cloned.size());
                final Iterator iter = cloned.iterator();
                while (iter.hasNext()) {
                    final Content c = (Content) iter.next();
                    if (c instanceof Element) {
                        final Element transformedElem = addTransf.transform((Element) c);
                        if (transformedElem != null)
                            listToAdd.add(transformedElem);
                    }
                }
            }
            // on crée si besoin le "récepteur"
            final Element thisElem = (Element) elemF.create();
            if (lindex < 0)
                thisElem.addContent(listToAdd);
            else
                thisElem.addContent(lindex, listToAdd);
        }
    }

    protected final void addIfNotPresent(ODXMLDocument doc, String path) throws JDOMException {
        this.addIfNotPresent(doc, path, -1);
    }

    /**
     * Adds an element from doc to this, if it's not already there.
     * 
     * @param doc the other document.
     * @param path an XPath denoting an element, and relative to the root element, eg
     *        ./office:settings.
     * @param index the index where to add the element, -1 means the end.
     * @throws JDOMException if a problem occurs with path.
     */
    protected final void addIfNotPresent(ODXMLDocument doc, String path, int index) throws JDOMException {
        final Element myElem = this.getDescendant(path);
        if (myElem == null) {
            final Element otherElem = doc.getDescendant(path);
            if (otherElem != null) {
                final Element myParent = this.getDescendant(XPathUtils.parentOf(path));
                if (index == -1)
                    myParent.addContent((Element) otherElem.clone());
                else
                    myParent.addContent(index, (Element) otherElem.clone());
            }
        }
    }

}
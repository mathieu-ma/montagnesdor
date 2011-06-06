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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ContentType;
import org.jopendocument.dom.ContentTypeVersioned;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.TableModel;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 * A calc document.
 * 
 * @author Sylvain
 */
public class SpreadSheet implements ODDocument {

    public static SpreadSheet createFromFile(File f) throws IOException {
        return create(new ODPackage(f));
    }

    public static SpreadSheet create(final ODPackage fd) {
        return new SpreadSheet(fd.getDocument("content.xml"), fd.getDocument("styles.xml"), fd);
    }

    public static SpreadSheet createEmpty(TableModel t) throws IOException {
        return createEmpty(t, XMLVersion.getOD());
    }

    public static SpreadSheet createEmpty(TableModel t, XMLVersion ns) throws IOException {
        final Document doc = new Document(new Element("document", ns.getOFFICE()));
        // OpenDocument use relaxNG
        if (ns == XMLVersion.OOo)
            doc.setDocType(new DocType("office:document", "-//OpenOffice.org//DTD OfficeDocument 1.0//EN", "office.dtd"));
        final ContentTypeVersioned ct = ContentTypeVersioned.fromType(ContentType.SPREADSHEET, ns);
        if (ct.getVersion().equals(XMLVersion.OOo)) {
            doc.getRootElement().setAttribute("class", ct.getShortName(), ns.getOFFICE());
        } else if (ct.getVersion().equals(XMLVersion.OD)) {
            doc.getRootElement().setAttribute("mimetype", ct.getMimeType(), ns.getOFFICE());
        }
        // don't forget that, otherwise OO crash
        doc.getRootElement().addContent(new Element("automatic-styles", ns.getOFFICE()));

        final Element topBody = new Element("body", ns.getOFFICE());
        final Element body;
        if (ct.getVersion().equals(XMLVersion.OD)) {
            body = new Element(ct.getShortName(), ns.getOFFICE());
            topBody.addContent(body);
        } else
            body = topBody;
        doc.getRootElement().addContent(topBody);
        final Element sheetElem = Sheet.createEmpty(ns);
        body.addContent(sheetElem);

        final SpreadSheet spreadSheet = new SpreadSheet(doc, null);
        spreadSheet.getSheet(0).merge(t, 0, 0, true);
        return spreadSheet;
    }

    /**
     * Export the passed data to file.
     * 
     * @param t the data to export.
     * @param f where to export, if the extension is missing (or wrong) the correct one will be
     *        added, eg "dir/data".
     * @param ns the version of XML.
     * @return the saved file, eg "dir/data.ods".
     * @throws IOException if the file can't be saved.
     */
    public static File export(TableModel t, File f, XMLVersion ns) throws IOException {
        return SpreadSheet.createEmpty(t, ns).saveAs(f);
    }

    private final ODPackage originalFile;
    private final Map<Element, Sheet> sheets;

    public SpreadSheet(Document doc, Document styles) {
        this(doc, styles, null);
    }

    private SpreadSheet(final Document doc, final Document styles, final ODPackage orig) {
        if (orig != null) {
            // ATTN OK because this is our private instance (see createFromFile())
            this.originalFile = orig;
        } else {
            this.originalFile = new ODPackage();
        }
        this.originalFile.putFile("content.xml", doc);
        if (styles != null)
            this.originalFile.putFile("styles.xml", styles);

        // map Sheet by XML elements so has not to depend on ordering or name
        this.sheets = new HashMap<Element, Sheet>();
    }

    final Document getContent() {
        return this.getPackage().getContent().getDocument();
    }

    @Override
    public final XMLVersion getVersion() {
        return this.getPackage().getVersion();
    }

    private Element getBody() {
        final Element body = this.getContent().getRootElement().getChild("body", this.getVersion().getOFFICE());
        if (this.getVersion().equals(XMLVersion.OOo))
            return body;
        else
            return body.getChild("spreadsheet", this.getVersion().getOFFICE());
    }

    // ** from 8.3.1 Referencing Table Cells (just double the backslash for . and escape the $)
    private static final String minCell = "\\$?([A-Z]+)\\$?([0-9]+)";
    // added parens to capture cell address
    // \1 is sheet name, \4 cell address
    static final Pattern cellPattern = Pattern.compile("(\\$?([^\\. ']+|'([^']|'')+'))?\\.(" + minCell + ")");
    static final Pattern minCellPattern = Pattern.compile(minCell);
    // added parens to capture cell addresses
    // \1 is sheet name, \4 cell address, \6 second sheet name, \9 second cell address
    private static final Pattern cellRangePattern = java.util.regex.Pattern
            .compile("(\\$?([^\\. ']+|'([^']|'')+'))?\\.(\\$?[A-Z]+\\$?[0-9]+)(:(\\$?([^\\. ']+|'([^']|'')+'))?\\.(\\$?[A-Z]+\\$?[0-9]+))?");

    protected final String parseSheetName(final String n) {
        if (n == null)
            return null;

        // ToDo handle '' (but OpenOffice doesn't)
        return n.charAt(0) == '$' ? n.substring(1) : n;
    }

    /**
     * Return a view of the passed range.
     * 
     * @param name a named range.
     * @return the matching TableModel, <code>null</code> if it doesn't exist.
     */
    public final MutableTableModel<SpreadSheet> getTableModel(String name) {
        final Element range;
        try {
            final XPath path = this.getXPath("./table:named-expressions/table:named-range[@table:name='" + name + "']");
            range = (Element) path.selectSingleNode(this.getBody());
        } catch (JDOMException e) {
            throw new IllegalStateException(e);
        }
        if (range == null)
            return null;

        // OpenOffice only supports absolute addresses, so need to use base-cell-address
        final String baseCell = range.getAttributeValue("cell-range-address", getVersion().getTABLE());
        final Matcher m = cellRangePattern.matcher(baseCell);
        if (!m.matches())
            throw new IllegalStateException(baseCell + " is not a valid range address");
        final String sheet1 = parseSheetName(m.group(1));
        final String sheet2 = parseSheetName(m.group(6));
        if (sheet2 != null && !sheet2.equals(sheet1))
            throw new UnsupportedOperationException("different sheet names: " + sheet1 + " != " + sheet2);

        final Sheet sheet = this.getSheet(sheet1, true);
        final Point start = Table.resolve(m.group(4));
        final Point end = Table.resolve(m.group(9));

        return sheet.getMutableTableModel(start, end);
    }

    /**
     * Return the cell at the passed address.
     * 
     * @param ref the full address, eg "$sheet.A12".
     * @return the cell at the passed address.
     */
    public final Cell<SpreadSheet> getCellAt(String ref) {
        final Matcher m = cellPattern.matcher(ref);
        if (!m.matches())
            throw new IllegalArgumentException(ref + " is not a valid cell address: " + m.pattern().pattern());
        final String sheetName = parseSheetName(m.group(1));
        if (sheetName == null)
            throw new IllegalArgumentException("no sheet specified: " + ref);
        return this.getSheet(sheetName, true).getCellAt(Sheet.resolve(m.group(5), m.group(6)));
    }

    public XPath getXPath(String p) throws JDOMException {
        return OOUtils.getXPath(p, this.getVersion());
    }

    // query directly the DOM, that way don't need to listen to it (eg for name, size or order
    // change)
    @SuppressWarnings("unchecked")
    private final List<Element> getTables() {
        return this.getBody().getChildren("table", this.getVersion().getTABLE());
    }

    public int getSheetCount() {
        return this.getTables().size();
    }

    public Sheet getSheet(int i) {
        return this.getSheet(getTables().get(i));
    }

    public Sheet getSheet(String name) {
        return this.getSheet(name, false);
    }

    /**
     * Return the first sheet with the passed name.
     * 
     * @param name the name of a sheet.
     * @param mustExist what to do when no match is found : <code>true</code> to throw an exception,
     *        <code>false</code> to return null.
     * @return the first matching sheet, <code>null</code> if <code>mustExist</code> is
     *         <code>false</code> and no match is found.
     * @throws NoSuchElementException if <code>mustExist</code> is <code>true</code> and no match is
     *         found.
     */
    public Sheet getSheet(String name, final boolean mustExist) throws NoSuchElementException {
        for (final Element table : getTables()) {
            if (name.equals(Table.getName(table)))
                return getSheet(table);
        }
        if (mustExist)
            throw new NoSuchElementException("no such sheet: " + name);
        else
            return null;
    }

    private final Sheet getSheet(Element table) {
        Sheet res = this.sheets.get(table);
        if (res == null) {
            res = new Sheet(this, table);
            this.sheets.put(table, res);
        }
        return res;
    }

    void invalidate(Element element) {
        this.sheets.remove(element);
    }

    /**
     * Adds an empty sheet.
     * 
     * @param index where to add the new sheet.
     * @param name the name of the new sheet.
     * @return the newly created sheet.
     */
    public final Sheet addSheet(final int index, String name) {
        if (name == null)
            throw new NullPointerException("null name");
        final Element newElem = Table.createEmpty(getVersion());
        return this.addSheet(index, newElem, name);
    }

    final Sheet addSheet(final int index, final Element newElem, final String name) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Negative index: " + index);
        if (index > getSheetCount())
            throw new IndexOutOfBoundsException("index (" + index + ") > count (" + getSheetCount() + ")");
        // the following statement fails when adding after the last table:table :
        // this.getTables().add(index, newElem);
        // it add at the end of its parent element (e.g. after table:named-expressions).
        // so use the fact that there's always at least one sheet and all sheets are grouped :
        final Element sheet1 = this.getSheet(0).getElement();
        final int firstIndex = sheet1.getParentElement().indexOf(sheet1);
        sheet1.getParentElement().addContent(firstIndex + index, newElem);

        final Sheet res = this.getSheet(newElem);
        if (name != null)
            res.setName(name);
        assert res.getName() != null;
        return res;
    }

    public final Sheet addSheet(String name) {
        return this.addSheet(getSheetCount(), name);
    }

    void move(Sheet sheet, int toIndex) {
        final Element parentElement = sheet.getElement().getParentElement();
        sheet.getElement().detach();
        parentElement.addContent(toIndex, sheet.getElement());
        // no need to update this.sheets since it doesn't depend on order
    }

    // *** Files

    public File saveAs(File file) throws FileNotFoundException, IOException {
        this.getPackage().setFile(file);
        return this.getPackage().save();
    }

    @Override
    public final ODPackage getPackage() {
        return this.originalFile;
    }

}
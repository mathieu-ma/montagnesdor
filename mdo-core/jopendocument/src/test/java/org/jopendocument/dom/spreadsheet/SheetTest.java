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

import static java.util.Arrays.asList;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.CellStyle.Side;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;
import org.jopendocument.dom.text.Paragraph;
import org.jopendocument.dom.text.ParagraphStyle;
import org.jopendocument.util.Tuple2;

import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import junit.framework.TestCase;

import org.jdom.Element;

public class SheetTest extends TestCase {

    static final DefaultTableModel tm;
    static {
        tm = new DefaultTableModel();
        tm.addColumn("col1");
        tm.addColumn("col2");
        tm.addColumn("col3");

        // trailing 0 to test precision (i.e. not equals to -5.32)
        tm.addRow(new Object[] { "un1", new BigDecimal("-5.320"), new Integer(123) });
    }

    private Sheet sheet;
    private Sheet realSheet;

    protected void setUp() throws Exception {
        this.sheet = SpreadSheet.createEmpty(tm, XMLVersion.getOOo()).getSheet(0);
        this.realSheet = SpreadSheet.create(new ODPackage(this.getClass().getResourceAsStream("test.ods"))).getSheet(0);
    }

    protected void tearDown() throws Exception {
        this.sheet = null;
        this.realSheet = null;
    }

    public void testCreate() throws IOException {
        // save
        final TableModel model = new DefaultTableModel(new Object[][] { { "Data", "DataCol2" } }, new Object[] { "Col1", "Col2" });
        final SpreadSheet created = SpreadSheet.createEmpty(model);
        assertValid(created);

        final File saved = created.saveAs(File.createTempFile("testCreate", null));
        saved.deleteOnExit();
        // then read
        final Sheet sheetRead = SpreadSheet.createFromFile(saved).getSheet(0);
        // and check that the data is correct
        assertEquals(2, sheetRead.getRowCount());
        assertEquals("Col1", sheetRead.getValueAt("A1"));
        // data w/o column names
        final SheetTableModel<SpreadSheet> tmRead = sheetRead.getTableModel(0, 1);
        assertEquals(2, tmRead.getColumnCount());
        assertEquals(1, tmRead.getRowCount());
        assertEquals("DataCol2", tmRead.getValueAt(0, 1));
    }

    public void _testName() throws Exception {
        final String n = "users";
        final SpreadSheet realSpread = this.realSheet.getSpreadSheet();
        assertEquals(n, realSpread.getSheet(n).getName());

        // the sheet can be accessed with a changed name
        final String newName = "newName";
        assertFalse(newName.equals(this.realSheet.getName()));
        this.realSheet.setName(newName);
        assertTrue(newName.equals(this.realSheet.getName()));
        assertSame(this.realSheet, realSpread.getSheet(newName));

        // test add
        assertEquals(2, realSpread.getSheetCount());
        final Sheet sheet0 = realSpread.getSheet(0);
        final Sheet sheet1 = realSpread.getSheet(1);
        // add at the end since we used to have a bug that added table:table at the very end of
        // body, e.g. after table:named-expressions.
        final Sheet addSheet = realSpread.addSheet(2, "newSheet");
        assertEquals(3, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet("newSheet"));
        assertSame(sheet0, realSpread.getSheet(0));
        assertSame(sheet1, realSpread.getSheet(1));
        assertSame(addSheet, realSpread.getSheet(2));

        // assert that the new sheet is functional
        addSheet.ensureRowCount(8);
        // (was throwing a NPE in Table.updateWidth())
        addSheet.ensureColumnCount(10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                addSheet.setValueAt(i + "," + j, i, j);
            }
        }

        assertValid(this.realSheet.getSpreadSheet());

        // test move
        addSheet.move(0);
        assertEquals(3, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet("newSheet"));
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(sheet0, realSpread.getSheet(1));
        assertSame(sheet1, realSpread.getSheet(2));

        assertValid(this.realSheet.getSpreadSheet());

        // test copy
        final Sheet copiedSheet = addSheet.copy(2, "copiedSheet");
        assertEquals(4, realSpread.getSheetCount());
        assertSame(copiedSheet, realSpread.getSheet("copiedSheet"));
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(sheet0, realSpread.getSheet(1));
        assertSame(copiedSheet, realSpread.getSheet(2));
        assertSame(sheet1, realSpread.getSheet(3));

        assertValid(this.realSheet.getSpreadSheet());

        // test rm
        final int count = realSpread.getSheetCount();
        assertSame(sheet0, this.realSheet);
        this.realSheet.detach();
        assertNull(realSpread.getSheet(this.realSheet.getName()));
        assertEquals(count - 1, realSpread.getSheetCount());
        assertSame(addSheet, realSpread.getSheet(0));
        assertSame(copiedSheet, realSpread.getSheet(1));
        assertSame(sheet1, realSpread.getSheet(2));

        assertValid(this.realSheet.getSpreadSheet());
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.setValueAt(Object, int, int)'
     */
    public void testSetValueAt() throws Exception {
        assertEquals("col1", this.sheet.getValueAt(0, 0));
        this.sheet.setValueAt("test", 0, 0);
        assertEquals("test", this.sheet.getValueAt(0, 0));

        this.sheet.setValueAt(60001399.1523, 0, 0);
        assertEquals(new BigDecimal("60001399.1523"), this.sheet.getValueAt(0, 0));

        final Calendar now = Calendar.getInstance();
        this.sheet.setValueAt(now.getTime(), 0, 0);
        final Object valueAt = this.sheet.getValueAt(0, 0);
        // OpenDocument can encode time up to the ms
        assertEquals(now.getTime(), valueAt);
        assertValid(this.sheet.getSpreadSheet());

        try {
            this.realSheet.getCellAt("C3");
            fail("should have failed since C3 is covered");
        } catch (IllegalArgumentException e) {
            // ok
        }
        this.realSheet.getCellAt("B3").unmerge();
        // should now succeed
        this.realSheet.getCellAt("C3").setValue(new Date());

        // preserve style
        {
            // test with odt (it has always worked in ods since text:p cannot have style, it is on
            // the cell)
            final ODSingleXMLDocument doc = new ODPackage(this.getClass().getResourceAsStream("../test.odt")).toSingle();
            final Table<ODSingleXMLDocument> t = new Table<ODSingleXMLDocument>(doc, doc.getDescendantByName("table:table", "JODTestTable"));
            final MutableCell<ODSingleXMLDocument> cell = t.getCellAt(2, 1);
            assertEquals("end", getFirstPStyle(cell).getAlignment());
            cell.setValue("somethingElse");
            assertEquals("end", getFirstPStyle(cell).getAlignment());
        }

        assertValid(this.realSheet.getSpreadSheet());
    }

    private final ParagraphStyle getFirstPStyle(final Cell<ODSingleXMLDocument> cell) {
        final Paragraph p = new Paragraph(cell.getElement().getChild("p", cell.getNS().getTEXT()));
        p.setDocument(cell.getODDocument());
        return p.getStyle();
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getValueAt(int, int)'
     */
    public void testGetValueAt() {
        assertEquals(new BigDecimal(123), this.sheet.getValueAt(2, 1));
        // testGetValueAtString()
        assertEquals(new BigDecimal(123), this.sheet.getValueAt("C2"));

        final Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2008, 11, 25);
        final Cell<SpreadSheet> dateCell = this.realSheet.getImmutableCellAt("F3");
        assertEquals(c.getTime(), dateCell.getValue());
        assertEquals("25 déc. 2008", dateCell.getTextValue());

        final Cell textCell = this.realSheet.getImmutableCellAt("B3");
        assertEquals("Gestion\tdes droits\nnouvelle  arborescence ", textCell.getValue());
        assertEquals("Gestion\tdes droits\nnouvelle  arborescence ", textCell.getTextValue(true));
        // test std mode
        // <cell>
        // <text:p>
        // Paragraph
        // </text:p>
        // </cell>
        textCell.getElement().removeContent();
        final Element p = new Element("p", textCell.getElement().getNamespace("text"));
        p.addContent("\n\t\tGestion \t des droits");
        textCell.getElement().addContent("\n\t").addContent(p).addContent("\n");
        assertEquals("Gestion des droits", textCell.getTextValue(false));

        assertSame(this.realSheet.getCellAt("F3"), this.realSheet.getSpreadSheet().getCellAt(this.realSheet.getName() + ".$F$3"));
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getRowCount()'
     */
    public void testGetCount() {
        // col labels + 1 row
        assertEquals(2, this.sheet.getRowCount());
        assertEquals(3, this.sheet.getColumnCount());
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.ensureColumnCount(int)'
     */
    public void testEnsureColumnCount() {
        final int before = this.sheet.getColumnCount();
        final int newSize = before - 2;
        this.sheet.ensureColumnCount(newSize);
        assertEquals(before, this.sheet.getColumnCount());
    }

    public void testSetColumnCount() throws Exception {
        // test in-memory
        final int newSize = this.sheet.getColumnCount() + 5;
        try {
            this.sheet.setValueAt("test", newSize - 1, 0);
            fail("should have thrown IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException exn) {
            // normal
        }
        this.sheet.setColumnCount(newSize);
        this.sheet.setValueAt("test", newSize - 1, 0);

        // test with real file
        final int origCount = this.realSheet.getColumnCount();
        try {
            this.realSheet.setValueAt("over", origCount + 10 - 1, 1);
            fail("should throw exn since we try to write past the limit");
        } catch (RuntimeException e) {
            // ok
        }
        this.realSheet.setColumnCount(origCount + 10);
        this.realSheet.setValueAt("over", origCount + 10 - 1, 1);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 15);
        this.realSheet.getSpreadSheet().getPackage().save(out);
        final SpreadSheet reRead = SpreadSheet.create(new ODPackage(new ByteArrayInputStream(out.toByteArray())));
        assertEquals("over", reRead.getSheet(0).getValueAt(origCount + 10 - 1, 1));

        assertValid(this.realSheet.getSpreadSheet());
    }

    public void testRemoveCol() throws Exception {
        this.realSheet.setColumnCount(9, -1, true);
        assertEquals(9, this.realSheet.getColumnCount());
        assertFalse(this.realSheet.getImmutableCellAt("C3").isValid());
        this.realSheet.removeColumn(1, true);
        assertEquals(8, this.realSheet.getColumnCount());
        // B3 was covering C3, but since the B column is gone C3 is incovered
        assertTrue(this.realSheet.getImmutableCellAt("C3").isValid());

        final ODSingleXMLDocument doc = new ODPackage(this.getClass().getResourceAsStream("../test.odt")).toSingle();
        final Table<ODSingleXMLDocument> t = new Table<ODSingleXMLDocument>(doc, doc.getDescendantByName("table:table", "JODTestTable"));
        final float colW = t.getColumn(1).getWidth();
        float tableW = t.getWidth();
        t.removeColumn(1, false);
        // normal float error
        assertTrue(Math.abs(tableW - colW - t.getWidth()) <= Math.ulp(t.getWidth()));

        tableW = t.getWidth();
        t.removeColumn(1, true);
        assertEquals(tableW, t.getWidth());
    }

    public void testSetRowCount() throws IOException {
        this.realSheet.setRowCount(3);
        final SpreadSheet reParsed = new SpreadSheet(this.realSheet.getODDocument().getContent(), null);
        assertEquals(3, reParsed.getSheet(0).getRowCount());
        assertValid(this.realSheet.getSpreadSheet());
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getTableModel(int, int)'
     */
    public void testGetTableModel() {
        SheetTableModel<SpreadSheet> t = this.sheet.getTableModel(1, 0);
        assertEquals(tm.getColumnCount() - 1, t.getColumnCount());
        // +1: col labels
        assertEquals(tm.getRowCount() + 1, t.getRowCount());
        assertEquals(new BigDecimal("-5.320"), t.getValueAt(1, 0));

        // empty hence no style
        assertNull(t.getImmutableCellAt(0, 0).getStyle());
        try {
            t.getValueAt(1, t.getColumnCount());
            fail("should have thrown exn");
        } catch (IndexOutOfBoundsException e) {
            // ok
        }
    }

    /*
     * Test method for 'org.jopendocument.dom.spreadsheet.Sheet.getMutableTableModel(int, int)'
     */
    public void testGetMutableTableModel() {
        this.sheet.getMutableTableModel(1, 0).setValueAt("test", 0, 1);
        assertEquals("test", this.sheet.getCellAt("C1").getValue());

        final MutableTableModel<SpreadSheet> tm = this.realSheet.getSpreadSheet().getTableModel("rights");
        assertEquals("K1", tm.getValueAt(13, 6));

        assertEquals(Color.CYAN, tm.getCellAt(0, 0).getStyle().getBackgroundColor());
        assertEquals("#ffcc99", tm.getCellAt(1, 2).getStyle().getTableCellProperties().getRawBackgroundColor());

        try {
            tm.setValueAt("foo", 0, tm.getColumnCount());
            fail("should have thrown exn");
        } catch (IndexOutOfBoundsException e) {
            // ok
        }
    }

    public void testMerge() throws IOException {
        final MutableCell<SpreadSheet> b3 = this.realSheet.getCellAt("B3");
        assertEquals(2, b3.getColumnsSpanned());
        assertEquals(1, b3.getRowsSpanned());

        b3.unmerge();
        assertEquals(1, b3.getColumnsSpanned());
        assertEquals(1, b3.getRowsSpanned());
        assertTrue(this.realSheet.getImmutableCellAt("C3").isValid());
        assertTrue(this.realSheet.getImmutableCellAt("B4").isValid());

        b3.merge(3, 2);
        assertEquals(3, b3.getColumnsSpanned());
        assertEquals(2, b3.getRowsSpanned());
        assertTrue(b3.isValid());
        assertFalse(this.realSheet.getImmutableCellAt("C3").isValid());
        assertFalse(this.realSheet.getImmutableCellAt("B4").isValid());
        // we cannot have overlapping merge since we cannot get to a covered cell
        try {
            this.realSheet.getCellAt("C3");
            fail("should have thrown an exn");
        } catch (Exception e) {
            // ok : we can't modify a covered cell
        }

        // merge more
        b3.merge(3, 3);
        assertEquals(3, b3.getColumnsSpanned());
        assertEquals(3, b3.getRowsSpanned());

        // if we call merge() with less cells it un-merge the rest
        b3.merge(1, 3);
        assertEquals(1, b3.getColumnsSpanned());
        assertEquals(3, b3.getRowsSpanned());
        assertTrue(b3.isValid());
        assertTrue(this.realSheet.getImmutableCellAt("C3").isValid());
        assertFalse(this.realSheet.getImmutableCellAt("B4").isValid());

        try {
            this.realSheet.getCellAt(0, 0).merge(2, 4);
            fail("Allowed overlapping merge");
        } catch (Exception e) {
            // ok
        }
        // the document was unchanged by the failed attempt
        assertFalse(this.realSheet.getImmutableCellAt(0, 0).coversOtherCells());
        assertTrue(this.realSheet.getImmutableCellAt(0, 1).isValid());
        assertValid(this.realSheet.getSpreadSheet());
    }

    public void testResolve() {
        assertEquals(new Point(0, 22), Sheet.resolve("A23"));
        assertEquals(new Point(26, 33), Sheet.resolve("AA34"));
        assertNull(Sheet.resolve("A23A"));
        assertNull(Sheet.resolve("test"));
        assertNull(Sheet.resolve("23"));

        assertEquals(77, Sheet.toInt("BZ"));
    }

    public void testDuplicateRows() throws Exception {
        this.realSheet.duplicateRows(8, 3, 2);
        assertEquals(this.realSheet.getValueAt("B11"), this.realSheet.getValueAt("B14"));
        assertEquals("Comptabilité", this.realSheet.getValueAt("H9"));
        // two copies
        assertEquals(this.realSheet.getValueAt("H9"), this.realSheet.getValueAt("H12"));
        assertEquals(this.realSheet.getValueAt("H9"), this.realSheet.getValueAt("H15"));

        assertValid(this.realSheet.getSpreadSheet());

        assertEquals(2, this.sheet.getRowCount());
        // test cloning of last row
        this.sheet.duplicateRows(1, 1, 3);
        assertEquals(5, this.sheet.getRowCount());
        assertEquals(this.sheet.getValueAt("C2"), this.sheet.getValueAt("C4"));
    }

    public void testXY() throws Exception {
        final int y = 35;
        {
            final MutableCell c = this.realSheet.getCellAt(2, y);
            assertEquals("LECTURE SEULE", c.getValue());
            assertEquals(2, c.getX());
            assertEquals(y, c.getY());
        }
        // offset by 6
        this.realSheet.duplicateRows(8, 3, 2);

        {
            final MutableCell c = this.realSheet.getCellAt(2, y + 6);
            assertEquals("LECTURE SEULE", c.getValue());
            assertEquals(2, c.getX());
            assertEquals(y + 6, c.getY());
        }
    }

    public void testStyle() throws Exception {
        // even though the style-name is null
        assertNull(this.realSheet.getImmutableCellAt(2, 10).getStyleAttr());
        final String style = this.realSheet.getStyleNameAt(2, 10);
        assertNotNull(style);
        // the correct style can still be found
        assertEquals(Color.CYAN, this.realSheet.getCellAt(2, 10).getStyle().getBackgroundColor());
        // also from MutableCell
        assertEquals(style, this.realSheet.getCellAt(2, 10).getStyleName());

        // ATTN getStyle().getReferences() is low-level so if the style of a cell is not directly
        // referenced (i.e. it uses the default for its row/column) it won't get found
        final MutableCell<SpreadSheet> cellAt00 = this.realSheet.getCellAt(0, 0);
        assertNull(cellAt00.getStyleAttr());
        assertFalse(cellAt00.getStyle().getReferences().contains(cellAt00.getElement()));
        // unless getStyleReferences() is used
        assertTrue(this.realSheet.getStyleReferences(this.realSheet.getStyleNameAt(0, 0)).contains(Tuple2.create(0, 0)));

        {
            final CellStyle centeredInBold = this.realSheet.getCellAt("B9").getStyle();
            assertTrue(centeredInBold.getReferences().contains(this.realSheet.getCellAt("B9").getElement()));

            assertEquals("center", centeredInBold.getParagraphProperties().getAlignment());
            assertEquals("bold", centeredInBold.getTextProperties().getWeight());
            assertEquals("0.002cm solid #000000", centeredInBold.getTableCellProperties().getBorder(Side.LEFT));
            assertEquals(null, centeredInBold.getTableCellProperties().getBorderLineWidth(Side.LEFT));
            assertEquals(0, centeredInBold.getTableCellProperties().getRotationAngle());
            assertEquals(true, centeredInBold.getTableCellProperties().isContentPrinted());
            assertEquals(false, centeredInBold.getTableCellProperties().isContentRepeated());
            assertEquals(false, centeredInBold.getTableCellProperties().isShrinkToFit());
        }
        {
            final CellStyle notPrinted = this.realSheet.getCellAt("B6").getStyle();
            assertTrue(notPrinted.getReferences().contains(this.realSheet.getCellAt("B6").getElement()));

            assertEquals("start", notPrinted.getParagraphProperties().getAlignment());
            assertEquals(null, notPrinted.getTextProperties().getWeight());
            assertEquals("0.105cm double #000080", notPrinted.getTableCellProperties().getBorder(Side.RIGHT));
            assertEquals("none", notPrinted.getTableCellProperties().getBorder(Side.TOP));
            assertEquals(asList("0.035cm", "0.035cm", "0.035cm"), asList(notPrinted.getTableCellProperties().getBorderLineWidth(Side.RIGHT)));
            assertEquals(333, notPrinted.getTableCellProperties().getRotationAngle());
            assertEquals(false, notPrinted.getTableCellProperties().isContentPrinted());
            assertEquals(false, notPrinted.getTableCellProperties().isContentRepeated());
            assertEquals(true, notPrinted.getTableCellProperties().isShrinkToFit());
        }
        // assert that we don't crash if we ask for an attribute whose namespace is not in our
        // element (here "fo")
        assertNull(CellStyle.DESC.createAutoStyle(this.sheet.getSpreadSheet().getPackage()).getBackgroundColor());

        // column styles
        assertEquals(54.38f, this.realSheet.getColumn(1).getWidth());
        final SpreadSheet sxc = SpreadSheet.create(new ODPackage(this.getClass().getResourceAsStream("test.sxc")));
        assertEquals(33.85f, sxc.getSheet(0).getColumn(1).getWidth());

        // change style
        {
            final Column<SpreadSheet> col1 = this.realSheet.getColumn(9);
            final Column<SpreadSheet> col2 = this.realSheet.getColumn(10);
            // even with shared style
            assertEquals(col1.getStyle().getName(), col2.getStyle().getName());
            assertNotNull(col1.getStyle().getBreakBefore());
            final float initialWidth = col2.getWidth();
            // if we change the width of col1
            col1.setWidth(initialWidth + 15);
            // it only change that
            assertEquals(initialWidth + 15, col1.getWidth());
            assertEquals(initialWidth, col2.getWidth());
            // and nothing else
            assertEquals(col2.getStyle().getBreakBefore(), col1.getStyle().getBreakBefore());

            // new style is only created if needed
            final String newName = col1.getStyle().getName();
            assertFalse(newName.equals(col2.getStyle().getName()));
            col1.setWidth(initialWidth + 17);
            assertEquals(newName, col1.getStyle().getName());

            // assert that getPrivateStyle() works even w/o a style
            final Table<?> addSheet = this.realSheet.getSpreadSheet().addSheet("testEmptyStyle");
            assertNull(addSheet.getColumn(0).getStyle());
            addSheet.getColumn(0).setWidth(20);
            final ColumnStyle createdStyle = addSheet.getColumn(0).getStyle();
            assertNotNull(createdStyle);
            assertEquals(createdStyle.getName(), addSheet.getColumn(0).getPrivateStyle().getName());
            assertEquals(20.0f, addSheet.getColumn(0).getWidth());
        }
        // change shared style referenced by one XML element
        {
            // both cells have the same style
            assertEquals("Default", this.sheet.getStyleNameAt(0, 0));
            assertEquals("Default", this.sheet.getStyleNameAt(0, 1));

            // create a new cell style and set it as the default for the first column
            final CellStyle cellStyle = CellStyle.DESC.createAutoStyle(this.sheet.getSpreadSheet().getPackage(), "defaultCell");
            final Element col0Elem = this.sheet.getColumn(0).getElement();
            col0Elem.setAttribute("default-cell-style-name", cellStyle.getName(), col0Elem.getNamespace());
            // both cells have thus the new style
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 0));
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 1));
            // although the style is only referenced by the column
            assertEquals(Collections.singletonList(col0Elem), cellStyle.getReferences());

            // changing the background of the first cell from nothing to CYAN
            assertNull(cellStyle.getBackgroundColor());
            this.sheet.getCellAt(0, 0).setBackgroundColor(Color.CYAN);
            // imply that the first cell changes style
            final CellStyle newStyle = this.sheet.getStyleAt(0, 0);
            assertFalse(cellStyle.getName().equals(newStyle.getName()));
            // but not the second one
            assertEquals(cellStyle.getName(), this.sheet.getStyleNameAt(0, 1));
            // the first cell has indeed changed colour
            assertEquals(Color.CYAN, newStyle.getTableCellProperties().getBackgroundColor());
            // but not the second one
            assertNull(this.sheet.getStyleAt(0, 1).getBackgroundColor());

            // changing a second time doesn't create a new style
            this.sheet.getCellAt(0, 0).setBackgroundColor(Color.YELLOW);
            assertEquals(newStyle.getName(), this.sheet.getStyleNameAt(0, 0));
        }

        assertValid(this.sheet.getSpreadSheet());
        assertValid(this.realSheet.getSpreadSheet());
    }

    private void assertValid(SpreadSheet ssheet) {
        final Map<String, String> areSubDocumentsValid = ssheet.getPackage().validateSubDocuments();
        assertTrue(areSubDocumentsValid + "", areSubDocumentsValid.isEmpty());
    }
}

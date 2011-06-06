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

package org.jopendocument.sample;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.OOXML;
import org.xml.sax.SAXException;

import com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory;

public class TextCat {

    public static void main(String[] args) {
        try {
            // Load 2 text documents
            File file1 = new File(TextCat.class.getResource("template/ooo2flyer_p1.odt").toURI());
			ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromFile(file1);
			
            File file2 = new File(TextCat.class.getResource("template/ooo2flyer_p2.odt").toURI());
			ODSingleXMLDocument p2 = ODSingleXMLDocument.createFromFile(file2);

            // Concatenate them
            p1.add(p2);

            // Save to file and Open the document with OpenOffice.org !
            OOUtils.open(p1.saveAs(new File("target/template/cat.odt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            // Specify you want a factory for RELAX NG
//            System.setProperty(SchemaFactory.class.getName() + ":" + XMLConstants.RELAXNG_NS_URI, "com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory");
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI, XMLSyntaxSchemaFactory.class.getName(), null);
            
			final Schema schema = factory.newSchema(OOXML.class.getResource("oofficeDTDs/OpenDocument-strict-schema-v1.1.rng"));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

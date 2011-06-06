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

package org.jopendocument.dom.template;

import java.io.File;
import java.io.IOException;

import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.template.engine.DataModel;
import org.jopendocument.dom.template.engine.RhinoTemplateScriptEngine;

public class RhinoFileTemplate extends Template {
    private DataModel engine;

    public RhinoFileTemplate(String fileName) throws IOException, TemplateException, JDOMException {
        this(new File(fileName));
    }

    public RhinoFileTemplate(File f) throws IOException, TemplateException, JDOMException {
        super(f);
        engine = new RhinoTemplateScriptEngine();
    }

    public ODSingleXMLDocument createDocument() throws TemplateException {
        return super.createDocument(engine);
    }

    public void showParagraph(String name) {
        this.setField("showParagraph_" + name, Boolean.TRUE);
    }

    public void hideParagraph(String name) {
        this.setField("showParagraph_" + name, Boolean.FALSE);
    }

    public void setField(String key, Object value) {
        this.engine.put(key, value);
    }

    public File saveAs(File outFile) throws IOException, TemplateException {
        // Create the document
        ODSingleXMLDocument doc = this.createDocument();
        return doc.saveAs(outFile);
    }
}

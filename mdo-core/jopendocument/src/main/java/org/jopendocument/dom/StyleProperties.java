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

package org.jopendocument.dom;

import java.awt.Color;

import org.jdom.Element;
import org.jdom.Namespace;

public abstract class StyleProperties {

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final String TRANSPARENT_NAME = "transparent";

    private final StyleStyle parentStyle;
    private final String propPrefix;

    public StyleProperties(StyleStyle style, final String propPrefix) {
        this.parentStyle = style;
        this.propPrefix = propPrefix;
    }

    public final StyleStyle getParentStyle() {
        return this.parentStyle;
    }

    public final Element getElement() {
        return this.getParentStyle().getFormattingProperties(this.propPrefix);
    }

    protected final boolean parseBoolean(final String s, boolean def) {
        return s == null ? def : Boolean.parseBoolean(s);
    }

    protected final Namespace getNS(final String prefix) {
        return this.getParentStyle().getNS().getNS(prefix);
    }

    // * 20.173 fo:background-color of OpenDocument-v1.2-part1, usable with all our subclasses :
    // <style:graphic-properties>, <style:header-footer-properties>, <style:page-layout-properties>,
    // <style:paragraph-properties>, <style:section-properties>, <style:table-cell-properties>,
    // <style:table-properties>, <style:table-row-properties> and <style:text-properties>

    public final String getRawBackgroundColor() {
        return this.getElement().getAttributeValue("background-color", this.getNS("fo"));
    }

    public final Color getBackgroundColor() {
        final String res = this.getRawBackgroundColor();
        if (res == null) {
            return null;
        } else if (TRANSPARENT_NAME.equals(res)) {
            return TRANSPARENT;
        } else
            return OOUtils.decodeRGB(res);
    }

    public final void setBackgroundColor(Color color) {
        this.setBackgroundColor(color.getAlpha() == TRANSPARENT.getAlpha() ? TRANSPARENT_NAME : OOUtils.encodeRGB(color));
    }

    public final void setBackgroundColor(String color) {
        this.getElement().setAttribute("background-color", color, this.getNS("fo"));
    }
}
/*
 * Copyright (c) 2002-2019 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host.html;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.util.MimeType;

/**
 * Tests for {@link HTMLStyleElement}.
 *
 * @author Marc Guillemot
 * @author Ronald Brill
 * @author Frank Danek
 */
@RunWith(BrowserRunner.class)
public class HTMLStyleElementTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object HTMLStyleElement]", "[object CSSStyleSheet]", "undefined"})
    public void stylesheet() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function doTest() {\n"
            + "  var f = document.getElementById('myStyle');\n"
            + "  alert(f);\n"
            + "  alert(f.sheet);\n"
            + "  alert(f.styleSheet);\n"
            + "}</script>\n"
            + "<style id='myStyle'>p: vertical-align:top</style>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * As of HtmlUnit-2.5 only the first child node of a STYLE was parsed.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void styleChildren() throws Exception {
        final String html
            = "<html><head><script>\n"
            + "function doTest() {\n"
            + "  var doc = document;\n"
            + "  var style = doc.createElement('style');\n"
            + "  doc.documentElement.firstChild.appendChild(style);\n"
            + "  style.appendChild(doc.createTextNode('* { z-index: 0; }\\\n'));\n"
            + "  style.appendChild(doc.createTextNode('DIV { z-index: 10; position: absolute; }\\\n'));\n"
            + "  if (doc.styleSheets[0].cssRules)\n"
            + "    rules = doc.styleSheets[0].cssRules;\n"
            + "  else\n"
            + "    rules = doc.styleSheets[0].rules;\n"
            + "  alert(rules.length);\n"
            + "}</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({".a > .t { }", ".b > .t { }", ".c > .t { }"})
    public void innerHtml() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='style_none'>.a > .t { }</style>\n"
            + "<style type='text/test' id='style_text'>.b > .t { }</style>\n"
            + "<style type='text/html' id='style_html'>.c > .t { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('style_none');\n"
            + "  alert(style.innerHTML);\n"
            + "  style = document.getElementById('style_text');\n"
            + "  alert(style.innerHTML);\n"
            + "  style = document.getElementById('style_html');\n"
            + "  alert(style.innerHTML);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "text/test", "text/css"})
    public void type() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='style_none'>my { }</style>\n"
            + "<style type='text/test' id='style_text'>my { }</style>\n"
            + "<style type='text/css' id='style_css'>my { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('style_none');\n"
            + "  alert(style.type);\n"
            + "  style = document.getElementById('style_text');\n"
            + "  alert(style.type);\n"
            + "  style = document.getElementById('style_css');\n"
            + "  alert(style.type);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "all", "screen, print,test"})
    public void media() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='style_none'>my { }</style>\n"
            + "<style media='all' id='style_all'>my { }</style>\n"
            + "<style media='screen, print,test' id='style_some'>my { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('style_none');\n"
            + "  alert(style.media);\n"
            + "  style = document.getElementById('style_all');\n"
            + "  alert(style.media);\n"
            + "  style = document.getElementById('style_some');\n"
            + "  alert(style.media);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"all", "", "screen:screen", "priNT", "screen, print"})
    public void media_setter() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='myStyle' media='all'>my { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('myStyle');\n"

            + "  alert(style.media);\n"

            + "  style.media = '';\n"
            + "  alert(style.media);\n"

            + "  style.media = 'screen';\n"
            + "  alert(style.media + ':' + style.attributes['media'].value);\n"

            + "  style.media = 'priNT';\n"
            + "  alert(style.media);\n"

            + "  style.media = 'screen, print';\n"
            + "  alert(style.media);\n"

            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"undefined", "undefined"},
            FF52 = {"false", "true"})
    public void scoped() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='style_none'>my { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('style_none');\n"
            + "  alert(style.scoped);\n"
            + "  style = document.getElementById('style_scoped');\n"
            + "  alert(style.scoped);\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <style id='style_scoped' scoped>my { }</style>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"undefined", "true", "false"},
            FF52 = {"false", "true", "false"})
    public void scoped_setter() throws Exception {
        final String html
            = "<html><head>\n"

            + "<style id='myStyle' media='all'>my { }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('myStyle');\n"

            + "  alert(style.scoped);\n"

            + "  style.scoped = true;\n"
            + "  alert(style.scoped);\n"

            + "  style.media = false;\n"
            + "  alert(style.media);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"", "text/css"})
    public void type_setter() throws Exception {
        final String html
            = "<html><head>\n"
            + "<style id='style_none'></style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  style = document.getElementById('style_none');\n"
            + "  alert(style.type);\n"
            + "  style.type = 'text/css';\n"
            + "  alert(style.type);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"rgb(0, 128, 0)", "false", "rgb(0, 0, 0)"})
    public void disabled() throws Exception {
        final String html
            = "<html><head>\n"
            + "<style id='myStyle'> .abc { color: green; }</style>\n"

            + "<script>\n"
            + "function doTest() {\n"
            + "  var div = document.getElementById('myDiv');\n"
            + "  var style = document.getElementById('myStyle');\n"
            + "  alert(window.getComputedStyle(div, '').color);\n"
            + "  alert(style.disabled);\n"
            + "  style.disabled = true;\n"
            + "  alert(window.getComputedStyle(div, '').color);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='doTest()'>\n"
            + "  <div id='myDiv' class='abc'>abcd</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 255)"})
    public void styleInCdataXHtml() throws Exception {
        final String html =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<!DOCTYPE html PUBLIC \n"
            + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
            + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
            + "<head>\n"
            + "  <style>\n"
            + "    //<![CDATA[\n"
            + "      #one {color: red;}\n"
            + "    //]]>\n"
            + "  </style>\n"
            + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
            + "  <style>\n"
            + "    <![CDATA[\n"
            + "      #three {color: blue;}\n"
            + "    ]]>\n"
            + "  </style>\n"
            + "  <script>\n"
            + "    function doTest() {\n"
            + "      var div = document.getElementById('one');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('two');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('three');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <div id='one'>one</div>\n"
            + "  <div id='two'>two</div>\n"
            + "  <div id='three'>three</div>\n"
            + "</body></html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                .getOptions().setThrowExceptionOnScriptError(false);
        }
        final WebDriver driver = loadPage2(html, URL_FIRST, MimeType.APPLICATION_XHTML, StandardCharsets.UTF_8);
        verifyAlerts(driver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 255)"})
    public void scriptInCdataXml() throws Exception {
        final String html =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<!DOCTYPE html PUBLIC \n"
            + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
            + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
            + "<head>\n"
            + "  <style>\n"
            + "    //<![CDATA[\n"
            + "      #one {color: red;}\n"
            + "    //]]>\n"
            + "  </style>\n"
            + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
            + "  <style>\n"
            + "    <![CDATA[\n"
            + "      #three {color: blue;}\n"
            + "    ]]>\n"
            + "  </style>\n"
            + "  <script>\n"
            + "    function doTest() {\n"
            + "      var div = document.getElementById('one');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('two');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('three');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <div id='one'>one</div>\n"
            + "  <div id='two'>two</div>\n"
            + "  <div id='three'>three</div>\n"
            + "</body></html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                .getOptions().setThrowExceptionOnScriptError(false);
        }
        final WebDriver driver = loadPage2(html, URL_FIRST, MimeType.TEXT_XML, StandardCharsets.UTF_8);
        verifyAlerts(driver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 0)"})
    public void scriptInCdataHtml() throws Exception {
        final String html =
            "<html>\n"
            + "<head>\n"
            + "  <style>\n"
            + "    //<![CDATA[\n"
            + "      #one {color: red;}\n"
            + "    //]]>\n"
            + "  </style>\n"
            + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
            + "  <style>\n"
            + "    <![CDATA[\n"
            + "      #three {color: blue;}\n"
            + "    ]]>\n"
            + "  </style>\n"
            + "  <script>\n"
            + "    function doTest() {\n"
            + "      var div = document.getElementById('one');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('two');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "      div = document.getElementById('three');\n"
            + "      alert(window.getComputedStyle(div, null).color);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <div id='one'>one</div>\n"
            + "  <div id='two'>two</div>\n"
            + "  <div id='three'>three</div>\n"
            + "</body></html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                .getOptions().setThrowExceptionOnScriptError(false);
        }
        loadPageWithAlerts2(html);
    }
}

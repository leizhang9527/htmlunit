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

import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.CHROME;
import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.FF;
import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.IE;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;
import com.gargoylesoftware.htmlunit.util.MimeType;

/**
 * Tests for {@link HTMLElement}.
 *
 * @author Brad Clarke
 * @author Chris Erskine
 * @author David D. Kilzer
 * @author Daniel Gredler
 * @author Marc Guillemot
 * @author Hans Donner
 * @author <a href="mailto:george@murnock.com">George Murnock</a>
 * @author Bruce Faulkner
 * @author Ahmed Ashour
 * @author Sudhan Moghe
 * @author Ethan Glasser-Camp
 * @author Ronald Brill
 * @author Frank Danek
 */
@RunWith(BrowserRunner.class)
public class HTMLElementTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"all is not supported", "all is not supported",
            "all is not supported", "all is not supported", "all is not supported"})
    public void all_IndexByInt() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  dumpAll('body');\n"
            + "  dumpAll('testDiv');\n"
            + "  dumpAll('testA');\n"
            + "  dumpAll('testImg');\n"
            + "  dumpAll('testDiv2');\n"
            + "}\n"
            + "function dumpAll(_id) {\n"
            + "  var oNode = document.getElementById(_id);\n"
            + "  var col = oNode.all;\n"
            + "  if (col) {\n"
            + "    var str = 'all node for ' + _id + ': ';\n"
            + "    for (var i = 0; i < col.length; i++) {\n"
            + "      str += col[i].tagName + ' ';\n"
            + "    }\n"
            + "    alert(str);\n"
            + "  } else {\n"
            + "    alert('all is not supported');\n"
            + "  }\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()' id='body'>\n"
            + "  <div id='testDiv'>foo<a href='foo.html' id='testA'><img src='foo.png' id='testImg'></a></div>\n"
            + "  <div id='testDiv2'>foo</div>\n"
            + "</body></html>";

        getMockWebConnection().setDefaultResponse("Error: not found", 404, "Not Found", MimeType.TEXT_HTML);

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts({"a", "a", "undefined", "null"})
    public void getAttribute() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert(myNode.title);\n"
                + "    alert(myNode.getAttribute('title'));\n"
                + "    alert(myNode.Title);\n"
                + "    alert(myNode.getAttribute('class'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode' title='a'>\n"
                + "</p>\n"
                + "</body>\n"
                + "</html>";

        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "test");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void getAttribute_styleAttribute() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('tester');\n"
            + "    alert(elem.getAttribute('style'));\n"
            + "  }\n"
            + "</script>\n"
            + "<body onload='test()'>\n"
            + "  <div id='tester'>tester</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts("color: green;")
    public void getAttribute_styleAttributeWithFlag() throws Exception {
        final String html =
              "<html><body onload='test()'><div id='div' style='color: green;'>abc</div>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('div');\n"
            + "    alert(div.getAttribute('style', 2));\n"
            + "  }\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Some libraries like MochiKit looks after the number of attributes of a freshly created node.
     * When this is fixed for IE, all {@link com.gargoylesoftware.htmlunit.libraries.MochiKitTest}
     * working for FF will work for IE too.
     * @throws Exception on test failure
     */
    @Test
    @Alerts("0 attribute")
    public void attributes() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.body.firstChild;\n"
                + "    if (myNode.attributes.length == 0)\n"
                + "      alert('0 attribute');\n"
                + "    else\n"
                + "      alert('at least 1 attribute');\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>" // no \n here!
                + "<span>test span</span>\n"
                + "</body>\n"
                + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts(DEFAULT = {"null", "bla", "true"},
            IE = {"", "bla", "true"})
    public void getSetAttributeNS() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "<title>test</title>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var myNode = document.getElementById('myNode');\n"
                + "  alert(myNode.getAttributeNS('myNamespaceURI', 'my:foo'));\n"
                + "  myNode.setAttributeNS('myNamespaceURI', 'my:foo', 'bla');\n"
                + "  alert(myNode.getAttributeNS('myNamespaceURI', 'foo'));\n"
                + "  alert(myNode.getAttributeNodeNS('myNamespaceURI', 'foo').specified);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode' title='a'>\n"
                + "</p>\n"
                + "</body>\n"
                + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"text", "i", "i", "[object CSS2Properties]", "function", "undefined", "undefined"},
            IE = {"text", "i", "i", "[object MSStyleCSSProperties]", "function", "undefined", "undefined"},
            CHROME = {"text", "i", "i", "[object CSSStyleDeclaration]", "function", "undefined", "undefined"})
    @NotYetImplemented({FF, IE})
    public void attributesAccess() throws Exception {
        final String html
            = "<html><head>\n"
            + "</head>\n"
            + "<body>\n"
            + "  <input type='text' id='i' name='i' style='color:red' onclick='alert(1)' custom1='a' />\n"
            + "  <script>\n"
            + "    var i = document.getElementById('i');\n"
            + "    alert(i.type);\n"
            + "    alert(i.id);\n"
            + "    alert(i.name);\n"
            + "    alert(i.style);\n"
            + "    alert(typeof i.onclick);\n"
            + "    alert(i.custom1);\n"
            + "    alert(i.custom2);\n"
            + "  </script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts({"a", "b", "undefined", "foo"})
    public void setAttribute() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    alert(myNode.title);\n"
            + "    myNode.setAttribute('title', 'b');\n"
            + "    alert(myNode.title);\n"
            + "    alert(myNode.Title);\n"
            + "    myNode.Title = 'foo';\n"
            + "    alert(myNode.Title);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<p id='myNode' title='a'>\n"
            + "</p>\n"
            + "</body>\n"
            + "</html>";

        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "test");
    }

    /**
     * Caution: with IE if you get a node with some lowercase letters, the node will be retrieved
     * and will get as name the value passed as attribute to getAttributeNode.
     * The consequence for IE: x.getAttributeNode("Foo").nodeName != x.getAttributeNode("foo").nodeName
     * @throws Exception on test failure
     */
    @Test
    @Alerts(DEFAULT = {
            "null",
            "expando=undefined",
            "firstChild=null",
            "lastChild=null",
            "name=custom_attribute",
            "nextSibling=null",
            "nodeName=custom_attribute",
            "nodeType=2",
            "nodeValue=bleh",
            "(ownerDocument == document) = true",
            "parentNode=null",
            "previousSibling=null",
            "specified=true",
            "value=bleh"
            },
            IE = {
            "null",
            "expando=true",
            "firstChild=[object Text]",
            "lastChild=[object Text]",
            "name=custom_attribute",
            "nextSibling=null",
            "nodeName=custom_attribute",
            "nodeType=2",
            "nodeValue=bleh",
            "(ownerDocument == document) = true",
            "parentNode=null",
            "previousSibling=null",
            "specified=true",
            "value=bleh"
           })
    public void getAttributeNode() throws Exception {
        final String html =
              "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var div = document.getElementById('div2');\n"
            + "      alert(div.getAttributeNode('notExisting'));\n"
            + "      var customAtt = div.getAttributeNode('custom_attribute');\n"
            + "      alertAttributeProperties(customAtt);\n"
            + "    }\n"
            + "    function alertAttributeProperties(att) {\n"
            + "      alert('expando=' + att.expando);\n"
            + "      alert('firstChild=' + att.firstChild);\n"
            + "      alert('lastChild=' + att.lastChild);\n"
            + "      alert('name=' + att.name);\n"
            + "      alert('nextSibling=' + att.nextSibling);\n"
            + "      alert('nodeName=' + att.nodeName);\n"
            + "      alert('nodeType=' + att.nodeType);\n"
            + "      alert('nodeValue=' + att.nodeValue);\n"
            + "      alert('(ownerDocument == document) = ' + (att.ownerDocument == document));\n"
            + "      alert('parentNode=' + att.parentNode);\n"
            + "      alert('previousSibling=' + att.previousSibling);\n"
            + "      alert('specified=' + att.specified);\n"
            + "      alert('value=' + att.value);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='div1'></div>\n"
            + "  <div id='div2' name='blah' custom_attribute='bleh'></div>\n"
            + "  <div id='div3'></div>\n"
            + "</body>\n"
            + "</html>";

        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "test");
    }

    /**
     * Tests setAttribute() with name of event handler.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"onfocus1-onclick1-", "onfocus1-onclick1-onblur1-onfocus2-"})
    public void setAttribute_eventHandler() throws Exception {
        final String html = "<html><head><title></title><script>\n"
            + "  function inform(msg) {\n"
            + "    document.title += msg;\n"
            + "    document.title += '-';\n"
            + "  }\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"
            + "    var password = document.getElementById('password');\n"

            + "    text.setAttribute('onclick', \"inform('onclick1');\");\n"
            + "    text.setAttribute('onFocus', \"inform('onfocus1');\");\n"
            + "    text.setAttribute('ONBLUR', \"inform('onblur1');\");\n"

            + "    password.setAttribute('onfocus', \"inform('onfocus2');\");\n"
            + "    password.setAttribute('onblur', \"inform('onblur2');\");\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login'>\n"
            + "    <input type='password' id='password' name='password'>\n"
            + "  </form>\n"
            + "</body></html>";

        final String[] alerts = getExpectedAlerts();
        int i = 0;

        final WebDriver driver = loadPage2(html);

        driver.findElement(By.id("login")).click();
        assertTitle(driver, alerts[i++]);

        driver.findElement(By.id("password")).click();
        assertTitle(driver, alerts[i++]);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"null", "inform('newHandler')", "null"},
            IE = {"null", "inform('newHandler')", ""})
    @NotYetImplemented(IE)
    public void setAttribute_eventHandlerNull() throws Exception {
        final String html = "<html><head><title></title><script>\n"
            + "  function inform(msg) {\n"
            + "    document.title += msg;\n"
            + "    document.title += '-';\n"
            + "  }\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"
            + "    var password = document.getElementById('password');\n"

            + "    alert(text.getAttribute('onclick'));\n"
            + "    text.setAttribute('onclick', \"inform('newHandler')\");\n"
            + "    alert(text.getAttribute('onclick'));\n"

            + "    text.setAttribute('onclick', null);\n"
            + "    alert(text.getAttribute('onclick'));\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login'>\n"
            + "  </form>\n"
            + "</body></html>";

        final WebDriver driver = loadPageWithAlerts2(html);

        driver.findElement(By.id("login")).click();
        assertTitle(driver, "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "inform('newHandler')", ""})
    public void setAttribute_eventHandlerEmptyString() throws Exception {
        final String html = "<html><head><title></title><script>\n"
            + "  function inform(msg) {\n"
            + "    document.title += msg;\n"
            + "    document.title += '-';\n"
            + "  }\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"
            + "    var password = document.getElementById('password');\n"

            + "    alert(text.getAttribute('onclick'));\n"
            + "    text.setAttribute('onclick', \"inform('newHandler')\");\n"
            + "    alert(text.getAttribute('onclick'));\n"

            + "    text.setAttribute('onclick', '');\n"
            + "    alert(text.getAttribute('onclick'));\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login'>\n"
            + "  </form>\n"
            + "</body></html>";

        final WebDriver driver = loadPageWithAlerts2(html);

        driver.findElement(By.id("login")).click();
        assertTitle(driver, "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "inform('newHandler')", "undefined"})
    public void setAttribute_eventHandlerUndefined() throws Exception {
        final String html = "<html><head><title></title><script>\n"
            + "  function inform(msg) {\n"
            + "    document.title += msg;\n"
            + "    document.title += '-';\n"
            + "  }\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"
            + "    var password = document.getElementById('password');\n"

            + "    alert(text.getAttribute('onclick'));\n"
            + "    text.setAttribute('onclick', \"inform('newHandler')\");\n"
            + "    alert(text.getAttribute('onclick'));\n"

            + "    text.setAttribute('onclick', undefined);\n"
            + "    alert(text.getAttribute('onclick'));\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login'>\n"
            + "  </form>\n"
            + "</body></html>";

        final WebDriver driver = loadPageWithAlerts2(html);

        driver.findElement(By.id("login")).click();
        assertTitle(driver, "");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"focus-click-", "focus-click-blur-"})
    public void setAttribute_eventHandlerEventArgument() throws Exception {
        final String html = "<html><head><title></title><script>\n"
            + "  function inform(msg) {\n"
            + "    document.title += msg;\n"
            + "    document.title += '-';\n"
            + "  }\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"

            + "    text.setAttribute('onclick', 'inform(event.type);');\n"
            + "    text.setAttribute('onFocus', 'inform(event.type);');\n"
            + "    text.setAttribute('ONBLUR', 'inform(event.type);');\n"

            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login'>\n"
            + "    <input type='password' id='password' name='password'>\n"
            + "  </form>\n"
            + "</body></html>";

        final String[] alerts = getExpectedAlerts();
        int i = 0;

        final WebDriver driver = loadPage2(html);

        driver.findElement(By.id("login")).click();
        assertTitle(driver, alerts[i++]);

        driver.findElement(By.id("password")).click();
        assertTitle(driver, alerts[i++]);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"inform(\"onclick\")", "inform('newHandler')", "newHandler"})
    public void getAttribute_eventHandler() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text = document.getElementById('login');\n"

            + "    alert(text.getAttribute('onclick'));\n"
            + "    text.setAttribute('onclick', \"inform('newHandler')\");\n"
            + "    alert(text.getAttribute('onclick'));\n"
            + "  }\n"
            + "  function inform(msg) {\n"
            + "    alert(msg);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <form>\n"
            + "    <input type='text' id='login' name='login' onclick='inform(\"onclick\")'>\n"
            + "  </form>\n"
            + "</body></html>";

        final String[] alerts = getExpectedAlerts();

        final WebDriver webDriver = loadPage2(html);
        verifyAlerts(webDriver, alerts[0], alerts[1]);

        webDriver.findElement(By.id("login")).click();
        verifyAlerts(webDriver, alerts[2]);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts({"left", "left", "right", "right"})
    public void setAttributeNode() throws Exception {
        final String html =
              "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      // Get the old alignment.\n"
            + "      var div1 = document.getElementById('div1');\n"
            + "      var a1 = div1.getAttributeNode('align');\n"
            + "      alert(a1.value);\n"
            + "      // Set the new alignment.\n"
            + "      var a2 = document.createAttribute('align');\n"
            + "      a2.value = 'right';\n"
            + "      a1 = div1.setAttributeNode(a2);\n"
            + "      alert(a1.value);\n"
            + "      alert(div1.getAttributeNode('align').value);\n"
            + "      alert(div1.getAttribute('align'));\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='div1' align='left'></div>\n"
            + "</body>\n"
            + "</html>";
        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "test");
    }

    /**
     * Test for <tt>getElementsByTagName</tt>.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"all = 4", "row = 2", "by wrong name: 0"})
    public void getElementsByTagName() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function doTest() {\n"
            + "  var a1 = document.getElementsByTagName('td');\n"
            + "  alert('all = ' + a1.length);\n"
            + "  var firstRow = document.getElementById('r1');\n"
            + "  var rowOnly = firstRow.getElementsByTagName('td');\n"
            + "  alert('row = ' + rowOnly.length);\n"
            + "  alert('by wrong name: ' + firstRow.getElementsByTagName('>').length);\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<table>\n"
            + "<tr id='r1'><td>1</td><td>2</td></tr>\n"
            + "<tr id='r2'><td>3</td><td>4</td></tr>\n"
            + "</table>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"div1", "div2"})
    public void getElementsByTagName2() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    for (var f = 0; (formnode = document.getElementsByTagName('form').item(f)); f++)\n"
            + "      for (var i = 0; (node = formnode.getElementsByTagName('div').item(i)); i++)\n"
            + "        alert(node.id);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <form>\n"
            + "    <div id='div1'/>\n"
            + "    <div id='div2'/>\n"
            + "  </form>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test that {@link HTMLElement#getElementsByTagName} returns an associative array.
     * Test for Bug #321.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"first", "second", "third"})
    public void getElementsByTagNameCollection() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var form1 = document.getElementById('form1');\n"
            + "  var elements = form1.getElementsByTagName('input');\n"
            + "  alert(elements['one'].name);\n"
            + "  alert(elements['two'].name);\n"
            + "  alert(elements['three'].name);\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "<form id='form1'>\n"
            + "<input id='one' name='first' type='text'>\n"
            + "<input id='two' name='second' type='text'>\n"
            + "<input id='three' name='third' type='text'>\n"
            + "</form>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Tests that getElementsByTagName('*') returns all child elements, both
     * at the document level and at the element level.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"8", "3"})
    public void getElementsByTagNameAsterisk() throws Exception {
        final String html = "<html><body onload='test()'><script>\n"
            + "  function test() {\n"
            + "    alert(document.getElementsByTagName('*').length);\n"
            + "    alert(document.getElementById('div').getElementsByTagName('*').length);\n"
            + "  }\n"
            + "</script>\n"
            + "<div id='div'><p>a</p><p>b</p><p>c</p></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "true", "false", "false"})
    public void getElementsByTagNameEquality() throws Exception {
        final String html =
              "<html><body><div id='d'><script>\n"
            + "var div = document.getElementById('d');\n"
            + "alert(document.getElementsByTagName('*') == document.getElementsByTagName('*'));\n"
            + "alert(document.getElementsByTagName('script') == document.getElementsByTagName('script'));\n"
            + "alert(document.getElementsByTagName('foo') == document.getElementsByTagName('foo'));\n"
            + "alert(document.getElementsByTagName('script') == document.getElementsByTagName('body'));\n"
            + "alert(document.getElementsByTagName('script') == div.getElementsByTagName('script'));\n"
            + "</script></div></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test getting the class for the element.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("the class is x")
    public void getClassName() throws Exception {
        final String html
            = "<html><head><style>.x { font: 8pt Arial bold; }</style>\n"
            + "<script>\n"
            + "function doTest() {\n"
            + "  var ele = document.getElementById('pid');\n"
            + "  var aClass = ele.className;\n"
            + "  alert('the class is ' + aClass);\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<p id='pid' class='x'>text</p>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test getting the class for the element.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"* x # x *", "*\tx\t#\tx\t*", "*  #  *", "*\t\t#\t\t*", "*\t \n \n#\t \n \n*", "*x\ty#x\ty*"})
    public void getClassNameWhitespace() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function doTest() {\n"
            + "  var elem = document.getElementById('pid1');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "  elem = document.getElementById('pid2');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "  elem = document.getElementById('pid3');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "  elem = document.getElementById('pid4');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "  elem = document.getElementById('pid5');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "  elem = document.getElementById('pid6');\n"
            + "  alert('*' + elem.className + '#' + elem.getAttribute('class') + '*');\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='doTest()'>\n"
            + "  <p id='pid1' class=' x '>text</p>\n"
            + "  <p id='pid2' class='\tx\t'>text</p>\n"
            + "  <p id='pid3' class='  '>text</p>\n"
            + "  <p id='pid4' class='\t\t'>text</p>\n"
            + "  <p id='pid5' class='\t \r \n'>text</p>\n"
            + "  <p id='pid6' class='x\ty'>text</p>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test setting the class for the element.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("the class is z")
    public void setClassName() throws Exception {
        final String html
            = "<html><head><style>.x { font: 8pt Arial bold; }</style>\n"
            + "<script>\n"
            + "function doTest() {\n"
            + "  var ele = document.getElementById('pid');\n"
            + "  ele.className = 'z';\n"
            + "  var aClass = ele.className;\n"
            + "  alert('the class is ' + aClass);\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<p id='pid' class='x'>text</p>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("if (1 > 2 & 3 < 2) willNotHappen('yo');")
    public void getInnerHTML() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script id='theScript'>if (1 > 2 & 3 < 2) willNotHappen('yo');</script>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('theScript');\n"
            + "    alert(myNode.innerHTML);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<form id='myNode'></form>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("<div id=\"i\" foo=\"\" name=\"\"></div>")
    public void getInnerHTML_EmptyAttributes() throws Exception {
        final String html = "<body onload='alert(document.body.innerHTML)'><div id='i' foo='' name=''></div></body>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <b>Old innerHTML</b>", "New = New  cell value"})
    public void getSetInnerHTMLSimple_FF() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    alert('Old = ' + myNode.innerHTML);\n"
            + "    myNode.innerHTML = 'New  cell value';\n"
            + "    alert('New = ' + myNode.innerHTML);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<p id='myNode'><b>Old innerHTML</b></p>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test the use of innerHTML to set a new input.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("true")
    public void getSetInnerHTMLNewInput() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    myNode.innerHTML = '<input type=\"checkbox\" name=\"myCb\" checked>';\n"
            + "    alert(myNode.myCb.checked);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<form id='myNode'></form>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <b>Old innerHTML</b>",
            "New = New  cell value &amp; \u0110 \u0110"})
    public void getSetInnerHTMLChar_FF() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    alert('Old = ' + myNode.innerHTML);\n"
            + "    myNode.innerHTML = 'New  cell value &amp; \\u0110 &#272;';\n"
            + "    alert('New = ' + myNode.innerHTML);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<p id='myNode'><b>Old innerHTML</b></p>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setInnerHTMLExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<scr'+'ipt>alerter();</scr'+'ipt>';\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.innerHTML = newnode;\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setInnerHTMLExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<div><scr'+'ipt>alerter();</scr'+'ipt></div>';\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.innerHTML = newnode;\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void setInnerHTMLDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<scr'+'ipt>function tester() { alerter(); }</scr'+'ipt>';\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.innerHTML = newnode;\n"
            + "    try {\n"
            + "      tester();\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Verifies outerHTML, innerHTML and innerText for newly created div.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "true", "true"})
    public void outerHTMLinNewDiv() throws Exception {
        final String html = "<html><body onload='test()'><script>\n"
            + "  function test() {\n"
            + "    var div = document.createElement('div');\n"
            + "    alert('outerHTML' in div);\n"
            + "    alert('innerHTML' in div);\n"
            + "    alert('innerText' in div);\n"
            + "  }\n"
            + "</script>\n"
            + "<div id='div'><span class='a b'></span></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Verifies that empty tags are not abbreviated into their &lt;tag/&gt; form.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"<div id=\"div\"><ul></ul></div>", "<ul></ul>", ""})
    public void getSetInnerHtmlEmptyTag_FF() throws Exception {
        final String html = "<html><body onload='test()'><script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('div');\n"
            + "    alert(div.outerHTML);\n"
            + "    alert(div.innerHTML);\n"
            + "    alert(div.innerText);\n"
            + "  }\n"
            + "</script>\n"
            + "<div id='div'><ul/></div>"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Verifies that attributes containing whitespace are always quoted.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"<div id=\"div\"><span class=\"a b\"></span></div>", "<span class=\"a b\"></span>", ""})
    public void getSetInnerHtmlAttributeWithWhitespace_FF() throws Exception {
        final String html = "<html><body onload='test()'><script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('div');\n"
            + "    alert(div.outerHTML);\n"
            + "    alert(div.innerHTML);\n"
            + "    alert(div.innerText);\n"
            + "  }\n"
            + "</script>\n"
            + "<div id='div'><span class='a b'></span></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting innerHTML to empty string.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("Empty ChildrenLength: 0")
    public void setInnerHTMLEmpty() throws Exception {
        final String html = "<html><head></head><body>\n"
                + "<div id='testDiv'>foo</div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    node.innerHTML = '';\n"
                + "    alert('Empty ChildrenLength: ' + node.childNodes.length);\n"
                + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting innerHTML to null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "Null ChildrenLength: 0",
            IE = "Null ChildrenLength: 1")
    public void setInnerHTMLNull() throws Exception {
        final String html = "<html><head></head><body>\n"
                + "<div id='testDiv'>foo</div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    node.innerHTML = null;\n"
                + "    alert('Null ChildrenLength: ' + node.childNodes.length);\n"
                + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test setting innerHTML should reset style cache.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("true")
    public void setInnerHTMLResetsStyle() throws Exception {
        final String html = "<html><head></head>\n"
                + "<body>\n"
                + "<div id='testDiv'></div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    var height = node.offsetHeight;\n"
                + "    node.innerHTML = 'HtmlUnit';\n"
                + "    alert(height < node.offsetHeight);\n"
                + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test getting <code>outerHTML</code> of a <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("Outer = <div id=\"myNode\">New  cell value</div>")
    public void getOuterHTMLFromBlock() throws Exception {
        final String html = createPageForGetOuterHTML("div", "New  cell value", false);
        loadPageWithAlerts2(html);
    }

    /**
     * Test getting <code>outerHTML</code> of a <code>span</code> (inline).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("Outer = <span id=\"myNode\">New  cell value</span>")
    public void getOuterHTMLFromInline() throws Exception {
        final String html = createPageForGetOuterHTML("span", "New  cell value", false);
        loadPageWithAlerts2(html);
    }

    /**
     * Test getting <code>outerHTML</code> of a <code>br</code> (empty).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("Outer = <br id=\"myNode\">")
    public void getOuterHTMLFromEmpty() throws Exception {
        final String html = createPageForGetOuterHTML("br", "", true);
        loadPageWithAlerts2(html);
    }

    /**
     * Test getting <code>outerHTML</code> of an unclosed <code>p</code>.<br>
     * Closing <code>p</code> is optional.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "Outer = <p id=\"myNode\">New  cell value\n\n</p>",
            IE = "Outer = <p id=\"myNode\">New  cell value\n\n")
    @NotYetImplemented
    public void getOuterHTMLFromUnclosedParagraph() throws Exception {
        final String html = createPageForGetOuterHTML("p", "New  cell value", true);
        loadPageWithAlerts2(html);
    }

    private static String createPageForGetOuterHTML(final String nodeTag, final String value, final boolean unclosed) {
        return "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Outer = ' + myNode.outerHTML);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <" + nodeTag + " id='myNode'>" + value + (unclosed ? "" : "</" + nodeTag + ">") + "\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Test setting outerHTML to null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = null", "Children: 1"})
    public void setOuterHTMLNull() throws Exception {
        final String html = createPageForSetOuterHTML("div", null);
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting outerHTML to null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = undefined", "Children: 1"})
    public void setOuterHTMLUndefined() throws Exception {
        final String html = createPageForSetOuterHTML("div", "undefined");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting outerHTML to ''.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLEmpty() throws Exception {
        final String html = createPageForSetOuterHTML("div", "");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting outerHTML to ''.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New =   ", "Children: 1"})
    public void setOuterHTMLBlank() throws Exception {
        final String html = createPageForSetOuterHTML("div", "  ");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>div</code> (block) to a text.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = New  cell value", "Children: 1"})
    public void setOuterHTMLAddTextToBlock() throws Exception {
        final String html = createPageForSetOuterHTML("div", "New  cell value");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>span</code> (inline) to a text.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = New  cell value", "Children: 1"})
    public void setOuterHTMLAddTextToInline() throws Exception {
        final String html = createPageForSetOuterHTML("span", "New  cell value");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>div</code> (block) to a <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <div>test</div>", "Children: 1"})
    public void setOuterHTMLAddBlockToBlock() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<div>test</div>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>span</code> (inline) to a <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <div>test</div>", "Children: 1"})
    public void setOuterHTMLAddBlockToInline() throws Exception {
        final String html = createPageForSetOuterHTML("span", "<div>test</div>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>span</code> (inline) to a <code>span</code> (inline).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <span>test</span>", "Children: 1"})
    public void setOuterHTMLAddInlineToInline() throws Exception {
        final String html = createPageForSetOuterHTML("span", "<span>test</span>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>div</code> (block) to a <code>span</code> (inline).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <span>test</span>", "Children: 1"})
    public void setOuterHTMLAddInlineToBlock() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<span>test</span>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to a <code>br</code> (empty).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <br>", "Children: 1"})
    public void setOuterHTMLAddEmpty() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<br>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to <code>tr</code> (read-only).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"-0", "1", "2", "3", "-4", "5", "6", "7", "8", "9", "10", "11"})
    public void setOuterHTMLToReadOnly() throws Exception {
        final String html =  "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var nodeTypes = ['body', 'caption', 'col', 'colgroup', 'head', 'html',\n"
            + "                     'tbody', 'td', 'tfoot', 'th', 'thead', 'tr'];\n"
            + "    for (var i = 0; i < nodeTypes.length; i++) {\n"
            + "      var nodeType = nodeTypes[i];\n"
            + "      var myNode = document.getElementsByTagName(nodeType)[0];\n"
            + "      try {\n"
            + "        myNode.outerHTML = 'test';\n"
            + "        alert('-' + i);\n"
            + "      } catch(e) {alert(i); }\n"
            + "    }\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <table>\n"
            + "    <caption></caption>\n"
            + "    <colgroup><col></colgroup>\n"
            + "    <thead><tr><td></td><th></th></tr></thead>\n"
            + "    <tbody></tbody>\n"
            + "    <tfoot></tfoot>\n"
            + "  </table>\n"
            + "  </table>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>p</code> to a <code>div</code> (block).<br>
     * <code>p</code> allows no block elements inside.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <div>test</div>", "Children: 1"})
    public void setOuterHTMLAddBlockToParagraph() throws Exception {
        final String html = createPageForSetOuterHTML("p", "<div>test</div>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of a <code>p</code> to a <code>p</code>.<br>
     * A following <code>p</code> closes the one before.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <p>test</p>", "Children: 1"})
    public void setOuterHTMLAddParagraphToParagraph() throws Exception {
        final String html = createPageForSetOuterHTML("p", "<p>test</p>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to an unclosed <code>p</code>.<br>
     * Closing <code>p</code> is optional.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <p>test</p>", "Children: 1"})
    public void setOuterHTMLAddUnclosedParagraph() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<p>test");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> of an <code>a</code> to an <code>a</code>.<br>
     * <code>a</code> allows no <code>a</code> inside.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <a>test</a>", "Children: 1"})
    public void setOuterHTMLAddAnchorToAnchor() throws Exception {
        final String html = createPageForSetOuterHTML("a", "<a>test</a>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to an XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <div></div>", "Children: 1"})
    public void setOuterHTMLAddSelfClosingBlock() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<div/>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <div><div></div></div>", "Children: 1"})
    @NotYetImplemented
    public void setOuterHTMLAddMultipleSelfClosingBlock() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<div/><div>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to an XHTML self-closing <code>span</code> (inline).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <span></span>", "Children: 1"})
    public void setOuterHTMLAddSelfClosingInline() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<span/>");
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to an XHTML self-closing <code>br</code> (empty).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = <br>", "Children: 1"})
    public void setOuterHTMLAddSelfClosingEmpty() throws Exception {
        final String html = createPageForSetOuterHTML("div", "<br/>");
        loadPageWithAlerts2(html);
    }

    private static String createPageForSetOuterHTML(final String nodeTag, final String newValue) {
        String newVal = "null";
        if ("undefined".equals(newValue)) {
            newVal = "undefined";
        }
        else if (newValue != null) {
            newVal = "'" + newValue + "'";
        }
        return "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    var innerNode = document.getElementById('innerNode');\n"
            + "    alert('Old = ' + myNode.innerHTML);\n"
            + "    innerNode.outerHTML = " + newVal + ";\n"
            + "    alert('New = ' + myNode.innerHTML);\n"
            + "    alert('Children: ' + myNode.childNodes.length);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <" + nodeTag + " id='myNode'><span id='innerNode'>Old outerHTML</span></" + nodeTag + ">\n"
            + "</body>\n"
            + "</html>";
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"},
            CHROME = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "exception"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLDetachedElementNull() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = null;\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"},
            CHROME = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "exception"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLDetachedElementUndefined() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = undefined;\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"},
            CHROME = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "exception"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLDetachedElementEmpty() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"},
            CHROME = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "exception"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLDetachedElementBlank() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test setting <code>outerHTML</code> to two XHTML self-closing <code>div</code> (block).
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = <span id=\"innerNode\">Old outerHTML</span>",
                    "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"},
            CHROME = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "exception"},
            IE = {"Old = <span id=\"innerNode\">Old outerHTML</span>", "New = ", "Children: 0"})
    public void setOuterHTMLDetachedElement() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '<p>test</p>';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setOuterHTMLExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<scr'+'ipt>alerter();</scr'+'ipt>';\n"
            + "    var oldnode = document.getElementById('myNode');\n"
            + "    oldnode.outerHTML = newnode;\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setOuterHTMLExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<div><scr'+'ipt>alerter();</scr'+'ipt></div>';\n"
            + "    var oldnode = document.getElementById('myNode');\n"
            + "    oldnode.outerHTML = newnode;\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void setOuterHTMLDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = '<scr'+'ipt>function tester() { alerter(); }</scr'+'ipt>';\n"
            + "    var oldnode = document.getElementById('myNode');\n"
            + "    oldnode.outerHTML = newnode;\n"
            + "    try {\n"
            + "      tester();\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test the <tt>#default#clientCaps</tt> default IE behavior.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"body.cpuClass = undefined", "exception"})
    public void addBehaviorDefaultClientCaps() throws Exception {
        final String html = "<html><body><script>\n"
            + "try {\n"
            + "  var body = document.body;\n"
            + "  alert('body.cpuClass = ' + body.cpuClass);\n"
            + "  var id = body.addBehavior('#default#clientCaps');\n"
            + "  alert('body.cpuClass = ' + body.cpuClass);\n"
            + "  var id2 = body.addBehavior('#default#clientCaps');\n"
            + "  body.removeBehavior(id);\n"
            + "  alert('body.cpuClass = ' + body.cpuClass);\n"
            + "} catch(e) { alert('exception'); }\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test the removal of behaviors.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"body.isHomePage = undefined", "!addBehavior", "!removeBehavior", "exception"})
    public void removeBehavior() throws Exception {
        final String html = "<html><body><script>\n"
            + "try {\n"
            + "  var body = document.body;\n"
            + "  alert('body.isHomePage = ' + body.isHomePage);\n"

            + "  if(!body.addBehavior) { alert('!addBehavior'); }\n"
            + "  if(!body.removeBehavior) { alert('!removeBehavior'); }\n"

            + "  var id = body.addBehavior('#default#homePage');\n"
            + "  alert('body.isHomePage = ' + body.isHomePage('not the home page'));\n"
            + "  body.removeBehavior(id);\n"
            + "  alert('body.isHomePage = ' + body.isHomePage);\n"
            + "} catch(e) { alert('exception'); }\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"BR", "DIV", "2", "3"})
    public void children() throws Exception {
        final String html = "<html><body>\n"
            + "<div id='myDiv'><br/><div><span>test</span></div></div>\n"
            + "<script>\n"
            + "  var oDiv = document.getElementById('myDiv');\n"
            + "  for (var i = 0; i < oDiv.children.length; i++) {\n"
            + "    alert(oDiv.children[i].tagName);\n"
            + "  }\n"
            + "  var oCol = oDiv.children;\n"
            + "  alert(oCol.length);\n"
            + "  oDiv.insertAdjacentHTML('beforeEnd', '<br>');\n"
            + "  alert(oCol.length);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"1", "0"})
    public void childrenDoesNotCountTextNodes() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  children = document.getElementById('myBody').children;\n"
            + "  alert(children.length);\n"

            + "  children = document.getElementById('myId').children;\n"
            + "  alert(children.length);\n"
            + "}\n"
            + "</script></head><body id='myBody' onload='test()'>\n"
            + "  <div id='myId'>abcd</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"2", "exception"},
            IE = {"2", "BR"})
    public void childrenFunctionAccess() throws Exception {
        final String html = "<html><body>\n"
            + "<div id='myDiv'><br/><div>\n"
            + "<script>\n"
            + "try {\n"
            + "  var oDiv = document.getElementById('myDiv');\n"
            + "  alert(oDiv.children.length);\n"
            + "  alert(oDiv.children(0).tagName);\n"
            + "} catch(e) { alert('exception'); }\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"Old = Old\n\ninnerText", "New = New cell value"},
            IE = {"Old = Old \ninnerText", "New = New cell value"})
    @NotYetImplemented({CHROME, FF})
    public void getSetInnerTextSimple() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myNode = document.getElementById('myNode');\n"
            + "    alert('Old = ' + myNode.innerText);\n"
            + "    myNode.innerText = 'New cell value';\n"
            + "    alert('New = ' + myNode.innerText);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "<div id='myNode'><b>Old <p>innerText</p></b></div>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test the removal of attributes from HTMLElements.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"removeMe", "null"})
    public void removeAttribute() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>Test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myDiv = document.getElementById('aDiv');\n"
            + "    alert(myDiv.getAttribute('name'));\n"
            + "    myDiv.removeAttribute('name');\n"
            + "    alert(myDiv.getAttribute('name'));\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'><div id='aDiv' name='removeMe'>\n"
            + "</div></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * IE doesn't really make a distinction between property and attribute...
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"hello", "null", "hello"})
    public void removeAttribute_property() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>Test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var myDiv = document.getElementById('aDiv');\n"
            + "    myDiv.foo = 'hello';\n"
            + "    alert(myDiv.foo);\n"
            + "    alert(myDiv.getAttribute('foo'));\n"
            + "    myDiv.removeAttribute('foo');\n"
            + "    alert(myDiv.foo);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'><div id='aDiv' name='removeMe'>\n"
            + "</div></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test scrolls (real values don't matter currently).
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"number", "number", "number", "number", "number", "number", "number", "number"})
    public void scrolls() throws Exception {
        final String html = "<html>\n"
              + "<head>\n"
              + "  <title>Test</title>\n"
              + "</head>\n"
              + "<body>\n"
              + "</div></body>\n"
              + "<div id='div1'>foo</div>\n"
              + "<script>\n"
              + "function alertScrolls(_oElt) {\n"
              + "  alert(typeof _oElt.scrollHeight);\n"
              + "  alert(typeof _oElt.scrollWidth);\n"
              + "  alert(typeof _oElt.scrollLeft);\n"
              + "  _oElt.scrollLeft = 123;\n"
              + "  alert(typeof _oElt.scrollTop);\n"
              + "  _oElt.scrollTop = 123;\n"
              + "}\n"
              + "alertScrolls(document.body);\n"
              + "alertScrolls(document.getElementById('div1'));\n"
              + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0", "0", "0", "0", "17", "0", "0"})
    public void scrollLeft_overflowScroll() throws Exception {
        scrollLeft("scroll");
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0", "0", "0", "0", "17", "0", "0"})
    public void scrollLeft_overflowAuto() throws Exception {
        scrollLeft("auto");
    }

    /**
     * NOTE: When running this test with Firefox (3.6, at least), it's important to reload the page with Ctrl+F5
     * in order to completely clear the cache; otherwise, Firefox appears to incorrectly cache some style attributes.
     * @throws Exception if an error occurs
     */
    private void scrollLeft(final String overflow) throws Exception {
        final String html
            = "<html><body onload='test()'>\n"
            + "<div id='d1' style='width:100px;height:100px;background-color:green;'>\n"
            + "  <div id='d2' style='width:50px;height:50px;background-color:blue;'></div>\n"
            + "</div>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var d1 = document.getElementById('d1'), d2 = document.getElementById('d2');\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.scrollLeft = -1;\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.scrollLeft = 5;\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d2.style.width = '200px';\n"
            + "  d2.style.height = '200px';\n"
            + "  d1.scrollLeft = 7;\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.style.overflow = '" + overflow + "';\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.scrollLeft = 17;\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.style.overflow = 'visible';\n"
            + "  alert(d1.scrollLeft);\n"
            + "  d1.scrollLeft = 19;\n"
            + "  alert(d1.scrollLeft);\n"
            + "}\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "10", "0"})
    public void scrollLeft() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>Test</title>\n"
            + "  <script>\n"
            + "  function doTest() {\n"
            + "    var outer = document.getElementById('outer');\n"
            + "    var inner = document.getElementById('inner');\n"
            + "    alert(outer.scrollLeft);\n"

            + "    outer.scrollLeft = 10;\n"
            + "    alert(outer.scrollLeft);\n"

            + "    outer.scrollLeft = -4;\n"
            + "    alert(outer.scrollLeft);\n"
            + "  }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='doTest()'>\n"
            + "  <div id='outer' style='overflow: scroll; width: 100px'>\n"
            + "    <div id='inner' style='width: 250px'>abcdefg hijklmnop qrstuvw xyz</div>\n"
            + "  </div>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0", "0", "0", "0", "17", "0", "0"})
    public void scrollTop_overflowScroll() throws Exception {
        scrollTop("scroll");
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0", "0", "0", "0", "17", "0", "0"})
    public void scrollTop_overflowAuto() throws Exception {
        scrollTop("auto");
    }

    /**
     * NOTE: When running this test with Firefox (3.6, at least), it's important to reload the page with Ctrl+F5
     * in order to completely clear the cache; otherwise, Firefox appears to incorrectly cache some style attributes.
     * @throws Exception if an error occurs
     */
    private void scrollTop(final String overflow) throws Exception {
        final String html
            = "<html><body onload='test()'>\n"
            + "<div id='d1' style='width:100px;height:100px;background-color:green;'>\n"
            + "  <div id='d2' style='width:50px;height:50px;background-color:blue;'></div>\n"
            + "</div>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var d1 = document.getElementById('d1'), d2 = document.getElementById('d2');\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.scrollTop = -1;\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.scrollTop = 5;\n"
            + "  alert(d1.scrollTop);\n"
            + "  d2.style.width = '200px';\n"
            + "  d2.style.height = '200px';\n"
            + "  d1.scrollTop = 7;\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.style.overflow = '" + overflow + "';\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.scrollTop = 17;\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.style.overflow = 'visible';\n"
            + "  alert(d1.scrollTop);\n"
            + "  d1.scrollTop = 19;\n"
            + "  alert(d1.scrollTop);\n"
            + "}\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Tests that JavaScript scrollIntoView() function doesn't fail.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("ok")
    public void scrollIntoView() throws Exception {
        final String html = "<html>\n"
              + "<body>\n"
              + "<script id='me'>document.getElementById('me').scrollIntoView(); alert('ok');</script>\n"
              + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Tests the offsetParent property.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({
            "element: span3 offsetParent: td2", "element: td2 offsetParent: table2",
            "element: tr2 offsetParent: table2", "element: table2 offsetParent: td1",
            "element: td1 offsetParent: table1", "element: tr1 offsetParent: table1",
            "element: table1 offsetParent: body1", "element: span2 offsetParent: body1",
            "element: span1 offsetParent: body1", "element: div1 offsetParent: body1",
            "element: body1 offsetParent: null"})
    public void offsetParent_Basic() throws Exception {
        final String html = "<html><head>\n"
            + "<script type='text/javascript'>\n"
            + "function alertOffsetParent(id) {\n"
            + "  var element = document.getElementById(id);\n"
            + "  var offsetParent = element.offsetParent;\n"
            + "  var alertMessage = 'element: ' + element.id + ' offsetParent: ';\n"
            + "  if (offsetParent) {\n"
            + "    alertMessage += offsetParent.id;\n"
            + "  }\n"
            + "  else {\n"
            + "    alertMessage += offsetParent;\n"
            + "  }\n"
            + "  alert(alertMessage);\n"
            + "}\n"
            + "function test() {\n"
            + "  alertOffsetParent('span3');\n"
            + "  alertOffsetParent('td2');\n"
            + "  alertOffsetParent('tr2');\n"
            + "  alertOffsetParent('table2');\n"
            + "  alertOffsetParent('td1');\n"
            + "  alertOffsetParent('tr1');\n"
            + "  alertOffsetParent('table1');\n"
            + "  alertOffsetParent('span2');\n"
            + "  alertOffsetParent('span1');\n"
            + "  alertOffsetParent('div1');\n"
            + "  alertOffsetParent('body1');\n"
            + "}\n"
            + "</script></head>\n"
            + "<body id='body1' onload='test()'>\n"
            + "<div id='div1'>\n"
            + "  <span id='span1'>\n"
            + "    <span id='span2'>\n"
            + "      <table id='table1'>\n"
            + "        <tr id='tr1'>\n"
            + "          <td id='td1'>\n"
            + "            <table id='table2'>\n"
            + "              <tr id='tr2'>\n"
            + "                <td id='td2'>\n"
            + "                  <span id='span3'>some text</span>\n"
            + "                </td>\n"
            + "              </tr>\n"
            + "            </table>\n"
            + "          </td>\n"
            + "        </tr>\n"
            + "      </table>\n"
            + "    </span>\n"
            + "  </span>\n"
            + "</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Tests the offsetParent property.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "null"})
    public void offsetParent_newElement() throws Exception {
        final String html = "<html><body>\n"
            + "<script>\n"
            + "  var oNew = document.createElement('span');\n"
            + "  alert(oNew.offsetParent);\n"
            + "  var fragment = document.createDocumentFragment();\n"
            + "  fragment.appendChild(oNew);\n"
            + "  alert(oNew.offsetParent);\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Tests the offsetParent property, including the effects of CSS "position" attributes.
     * Based on <a href="http://dump.testsuite.org/2006/dom/style/offset/spec#offsetparent">this work</a>.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"null", "body", "exception", "body", "body", "body",
            "f1", "body", "h1", "i1", "td", "exception", "td", "body", "body"},
            FF = {"null", "body", "body", "body", "body", "body",
            "f1", "body", "h1", "i1", "td", "body", "td", "body", "body"})
    public void offsetParent_WithCSS() throws Exception {
        final String html = "<html>\n"
            + "  <body id='body' onload='test()'>\n"
            + "    <div id='a1'><div id='a2'>x</div></div>\n"
            + "    <div id='b1'><div id='b2' style='position:fixed'>x</div></div>\n"
            + "    <div id='c1'><div id='c2' style='position:static'>x</div></div>\n"
            + "    <div id='d1'><div id='d2' style='position:absolute'>x</div></div>\n"
            + "    <div id='e1'><div id='e2' style='position:relative'>x</div></div>\n"
            + "    <div id='f1' style='position:fixed'><div id='f2'>x</div></div>\n"
            + "    <div id='g1' style='position:static'><div id='g2'>x</div></div>\n"
            + "    <div id='h1' style='position:absolute'><div id='h2'>x</div></div>\n"
            + "    <div id='i1' style='position:relative'><div id='i2'>x</div></div>\n"
            + "    <table id='table'>\n"
            + "      <tr id='tr'>\n"
            + "        <td id='td'>\n"
            + "          <div id='j1'><div id='j2'>x</div></div>\n"
            + "          <div id='k1'><div id='k2' style='position:fixed'>x</div></div>\n"
            + "          <div id='l1'><div id='l2' style='position:static'>x</div></div>\n"
            + "          <div id='m1'><div id='m2' style='position:absolute'>x</div></div>\n"
            + "          <div id='n1'><div id='n2' style='position:relative'>x</div></div>\n"
            + "        </td>\n"
            + "      </tr>\n"
            + "    </table>\n"
            + "    <script>\n"
            + "      function alertOffsetParentId(id) {\n"
            + "        try {\n"
            + "          alert(document.getElementById(id).offsetParent.id);\n"
            + "        } catch (e) { alert('exception'); }\n"
            + "      }\n"
            + "      function test() {\n"
            + "        alert(document.getElementById('body').offsetParent);  // null (FF) null (IE)\n"
            + "        alertOffsetParentId('a2'); // body body \n"
            + "        alertOffsetParentId('b2'); // body exception \n"
            + "        alertOffsetParentId('c2'); // body body \n"
            + "        alertOffsetParentId('d2'); // body body \n"
            + "        alertOffsetParentId('e2'); // body body \n"
            + "        alertOffsetParentId('f2'); // f1   f1 \n"
            + "        alertOffsetParentId('g2'); // body body \n"
            + "        alertOffsetParentId('h2'); // h1   h1   \n"
            + "        alertOffsetParentId('i2'); // i1   i1   \n"
            + "        alertOffsetParentId('j2'); // td   td   \n"
            + "        alertOffsetParentId('k2'); // body exception   \n"
            + "        alertOffsetParentId('l2'); // td   td   \n"
            + "        alertOffsetParentId('m2'); // body body \n"
            + "        alertOffsetParentId('n2'); // body body \n"
            + "      }\n"
            + "    </script>\n"
            + "  </body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test for Bug #616.
     * @throws Exception if the test fails
     */
    @Test
    public void offsetParent_withSelectors() throws Exception {
        final String html = "<html><head><style>\n"
            + "div ul > li {\n"
            + "  font-size: xx-small;\n"
            + "}\n"
            + "</style><script>\n"
            + "function test() {\n"
            + "  var divThing = document.getElementById('outer');\n"
            + "  while (divThing) {\n"
            + "    divThing = divThing.offsetParent;\n"
            + "  }\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "<div id='outer'></div>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined",
                "undefined", "123", "from myFunction", "123", "from myFunction"})
    public void prototype() throws Exception {
        final String html = "<html><head><title>Prototype test</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var d = document.getElementById('foo');\n"
            + "  alert(d.foo);\n"
            + "  alert(d.myFunction);\n"
            + "  var link = document.getElementById('testLink');\n"
            + "  alert(link.foo);\n"
            + "  alert(link.myFunction);\n"
            + "  HTMLElement.prototype.foo = 123;\n"
            + "  alert(HTMLElement.foo);\n"
            + "  HTMLElement.prototype.myFunction = function() { return 'from myFunction'; };\n"
            + "  alert(d.foo);\n"
            + "  alert(d.myFunction());\n"
            + "  alert(link.foo);\n"
            + "  alert(link.myFunction());\n"
            + "}\n"
            + "</script></head><body onload='test()''>\n"
            + "<div id='foo'>bla</div>\n"
            + "<a id='testLink' href='foo'>bla</a>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * 'Element' and 'HTMLElement' prototypes are synonyms.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("in selectNodes")
    public void prototype_Element() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  Element.prototype.selectNodes = function(sExpr){\n"
            + "    alert('in selectNodes');\n"
            + "  }\n"
            + "  document.getElementById('myDiv').selectNodes();\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myDiv'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "true"})
    public void instanceOf() throws Exception {
        final String html = "<html><head><title>instanceof test</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var d = document.getElementById('foo');\n"
            + "  alert(d instanceof HTMLDivElement);\n"
            + "  var link = document.getElementById('testLink');\n"
            + "  alert(link instanceof HTMLAnchorElement);\n"
            + "}\n"
            + "</script></head><body onload='test()''>\n"
            + "<div id='foo'>bla</div>\n"
            + "<a id='testLink' href='foo'>bla</a>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "[object HTMLBodyElement]"})
    public void parentElement() throws Exception {
        final String html
            = "<html id='htmlID'>\n"
            + "<head>\n"
            + "</head>\n"
            + "<body>\n"
            + "<div id='divID'/>\n"
            + "<script language=\"javascript\">\n"
            + "  alert(document.getElementById('htmlID').parentElement);\n"
            + "  alert(document.getElementById('divID' ).parentElement);\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            IE = {"[object MSCurrentStyleCSSProperties]", "#000000"})
    @NotYetImplemented(IE)
    public void currentStyle() throws Exception {
        style("currentStyle");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            IE = {"[object MSStyleCSSProperties]", ""})
    @NotYetImplemented(IE)
    public void runtimeStyle() throws Exception {
        style("runtimeStyle");
    }

    private void style(final String styleProperty) throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elem = document.getElementById('myDiv');\n"
            + "    var style = elem." + styleProperty + ";\n"
            + "    alert(style);\n"
            + "    if (style) { alert(style.color); }\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "<div id='myDiv'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"0", "0"})
    public void clientLeftTop() throws Exception {
        final String html = "<html><body>"
            + "<div id='div1'>hello</div><script>\n"
            + "  var d1 = document.getElementById('div1');\n"
            + "  alert(d1.clientLeft);\n"
            + "  alert(d1.clientTop);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Another nice feature of the IE.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"0", "0"})
    public void clientLeftTop_documentElement() throws Exception {
        final String html =
              "<!DOCTYPE HTML "
            +      "PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
            + "<html>\n"
            + "<body>"
            + "<div id='div1'>hello</div><script>\n"
            + "  var d1 = document.documentElement;\n"
            + "  alert(d1.clientLeft);\n"
            + "  alert(d1.clientTop);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"4", "4"})
    public void clientLeftTopWithBorder() throws Exception {
        final String html = "<html><body>"
            + "<div id='div1' style='border: 4px solid black;'>hello</div><script>\n"
            + "  var d1 = document.getElementById('div1');\n"
            + "  alert(d1.clientLeft);\n"
            + "  alert(d1.clientTop);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "[object DOMRect]",
            IE = "[object ClientRect]")
    public void getBoundingClientRect() throws Exception {
        final String html = "<html><body>\n"
            + "<div id='div1'>hello</div><script>\n"
            + "  var d1 = document.getElementById('div1');\n"
            + "  var pos = d1.getBoundingClientRect();\n"
            + "  alert(pos);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"400", "100"})
    public void getBoundingClientRect2() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.getElementById('div1');\n"
            + "    var pos = d1.getBoundingClientRect();\n"
            + "    alert(pos.left);\n"
            + "    alert(pos.top);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "<div id='outer' style='position: absolute; left: 400px; top: 100px; width: 50px; height: 80px;'>"
            + "<div id='div1'></div></div>"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"0", "100", "100", "50"})
    public void getBoundingClientRect_Scroll() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.getElementById('outer');\n"
            + "    d1.scrollTop = 150;\n"
            + "    var pos = d1.getBoundingClientRect();\n"
            + "    alert(pos.left);\n"
            + "    alert(pos.top);\n"

            + "    d1 = document.getElementById('div1');\n"
            + "    pos = d1.getBoundingClientRect();\n"
            + "    alert(pos.left);\n"
            + "    alert(pos.top);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='outer' "
               + "style='position: absolute; height: 500px; width: 500px; top: 100px; left: 0px; overflow:auto'>\n"
            + "  <div id='div1' "
               + "style='position: absolute; height: 100px; width: 100px; top: 100px; left: 100px; z-index:99;'>"
               + "</div>\n"
            + "  <div id='div2' "
              + "style='position: absolute; height: 100px; width: 100px; top: 100px; left: 300px; z-index:99;'></div>\n"
            + "  <div style='position: absolute; top: 1000px;'>way down</div>\n"
            + "</div>"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object DOMRect]", "0", "0"},
            IE = "exception")
    public void getBoundingClientRectDisconnected() throws Exception {
        final String html = "<html>\n"
            + "<head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.createElement('div');\n"
            + "    try {\n"
            + "      var pos = d1.getBoundingClientRect();\n"
            + "      alert(pos);\n"
            + "      alert(pos.left);\n"
            + "      alert(pos.top);\n"
            + "    } catch (e) { alert('exception');}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object DOMRect]", "1"},
            CHROME = {"[object DOMRectList]", "1"},
            IE = {"[object ClientRectList]", "1"})
    public void getClientRects() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.getElementById('div1');\n"
            + "    var rects = d1.getClientRects();\n"
            + "    alert(rects);\n"
            + "    alert(rects.length);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='div1'/>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object ClientRectList]", "0"},
            CHROME = {"[object DOMRectList]", "0"},
            FF = {"", "0"})
    public void getClientRectsDisconnected() throws Exception {
        final String html =
            "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.createElement('div');\n"
            + "    alert(d1.getClientRects());\n"
            + "    alert(d1.getClientRects().length);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"[object ClientRectList]", "0", "[object ClientRectList]", "0"},
            CHROME = {"[object DOMRectList]", "0", "[object DOMRectList]", "0"},
            FF = {"", "0", "", "0"})
    public void getClientRectsDisplayNone() throws Exception {
        final String html =
            "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.getElementById('div1');\n"
            + "    display(d1);\n"
            + "    var d2 = document.getElementById('div2');\n"
            + "    display(d2);\n"
            + "  }\n"
            + "\n"
            + "  function display(elem) {\n"
            + "    alert(elem.getClientRects());\n"
            + "    alert(elem.getClientRects().length);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='div1' style='display: none'>\n"
            + "    <div id='div2' />\n"
            + "  </div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "null"})
    public void innerHTML_parentNode() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div1 = document.createElement('div');\n"
            + "    alert(div1.parentNode);\n"
            + "    div1.innerHTML = '<p>hello</p>';\n"
            + "    if(div1.parentNode)\n"
            + "      alert(div1.parentNode.nodeName);\n"
            + "    else\n"
            + "      alert(div1.parentNode);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "null"})
    public void innerText_parentNode() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div1 = document.createElement('div');\n"
            + "    alert(div1.parentNode);\n"
            + "    div1.innerText = '<p>hello</p>';\n"
            + "    if(div1.parentNode)\n"
            + "      alert(div1.parentNode.nodeName);\n"
            + "    else\n"
            + "      alert(div1.parentNode);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"true", "true", "true"},
            IE = {"false", "true", "false"})
    public void uniqueID() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div1 = document.getElementById('div1');\n"
            + "    var div2 = document.getElementById('div2');\n"
            + "    alert(div1.uniqueID == undefined);\n"
            + "    alert(div1.uniqueID == div1.uniqueID);\n"
            + "    alert(div1.uniqueID == div2.uniqueID);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='div1'/>\n"
            + "  <div id='div2'/>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Tests if element.uniqueID starts with 'ms__id', and is lazily created.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            IE = {"true", "true"})
    public void uniqueIDFormatIE() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div1 = document.getElementById('div1');\n"
            + "    var div2 = document.getElementById('div2');\n"
            + "    var id2 = div2.uniqueID;\n"
            + "    //as id2 is retrieved before getting id1, id2 should be < id1;\n"
            + "    var id1 = div1.uniqueID;\n"
            + "    if (id1 === undefined) { alert('undefined'); return }\n"
            + "    alert(id1.substring(0, 6) == 'ms__id');\n"
            + "    var id1Int = parseInt(id1.substring(6, id1.length));\n"
            + "    var id2Int = parseInt(id2.substring(6, id2.length));\n"
            + "    alert(id2Int < id1Int);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='div1'/>\n"
            + "  <div id='div2'/>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("exception")
    public void setExpression() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      var div1 = document.getElementById('div1');\n"
            + "      div1.setExpression('title','id');\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='div1'/>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"ex setExpression", "ex removeExpression"})
    public void removeExpression() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div1 = document.getElementById('div1');\n"

            + "    try {\n"
            + "      div1.setExpression('title','id');\n"
            + "    } catch(e) { alert('ex setExpression'); }\n"

            + "    try {\n"
            + "      div1.removeExpression('title');\n"
            + "    } catch(e) { alert('ex removeExpression'); }\n"

            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='div1'/>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("clicked")
    public void dispatchEvent() throws Exception {
        final String html =
            "<html><head>\n"
            + "<script>\n"
            + "function foo() {\n"
            + "  var e = document.createEvent('MouseEvents');\n"
            + "  e.initMouseEvent('click', true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
            + "  var d = document.getElementById('d');\n"
            + "  var canceled = !d.dispatchEvent(e);\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='foo()'><div id='d' onclick='alert(\"clicked\")'>foo</div></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {},
            FF = "page2 loaded")
    public void dispatchEvent_submitOnForm() throws Exception {
        final String html = "<html>\n"
            + "<head><title>page 1</title></head>\n"
            + "<body>\n"
            + "<form action='page2' id='theForm'>\n"
            + "  <span id='foo'/>\n"
            + "</form>\n"
            + "<script>\n"
            + "  var e = document.createEvent('HTMLEvents');\n"
            + "  e.initEvent('submit', true, false);\n"
            + "  document.getElementById('theForm').dispatchEvent(e);\n"
            + "</script>\n"
            + "</body></html>";

        final String page2 = "<html><body><script>alert('page2 loaded');</script></body></html>";

        getMockWebConnection().setResponse(new URL(URL_FIRST, "page2"), page2);
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    public void dispatchEvent_submitOnFormChild() throws Exception {
        final String html = "<html><head><title>page 1</title></head><body>\n"
            + "<form action='page2'><span id='foo'/></form>\n"
            + "<script>\n"
            + "try {\n"
            + "  var e = document.createEvent('HTMLEvents');\n"
            + "  e.initEvent('submit', true, false);\n"
            + "  document.getElementById('foo').dispatchEvent(e);\n"
            + "} catch(e) { alert('exception'); }\n"
            + "</script></body></html>";

        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "page 1");
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "false"})
    public void hasAttribute() throws Exception {
        final String html
            = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elt = document.body;\n"
            + "    alert(elt.hasAttribute('onload'));\n"
            + "    alert(elt.hasAttribute('onLoad'));\n"
            + "    alert(elt.hasAttribute('foo'));\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("function")
    public void hasAttributeTypeOf() throws Exception {
        final String html
            = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elt = document.body;\n"
            + "    alert(typeof elt.hasAttribute);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"function", "true", "true", "false"})
    public void hasAttributeQuirksMode() throws Exception {
        final String html =
              "<html>\n"
            + "<head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var elt = document.body;\n"
            + "    alert(typeof elt.hasAttribute);\n"
            + "    alert(elt.hasAttribute('onload'));\n"
            + "    alert(elt.hasAttribute('onLoad'));\n"
            + "    alert(elt.hasAttribute('foo'));\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"undefined", "undefined", "undefined"})
    public void getComponentVersion() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  alert(document.body.cpuClass);\n"
            + "  document.body.style.behavior = 'url(#default#clientCaps)';\n"
            + "  alert(document.body.cpuClass);\n"
            + "  if (document.body.getComponentVersion) {\n"
            + "    var ver=document.body.getComponentVersion('{E5D12C4E-7B4F-11D3-B5C9-0050045C3C96}','ComponentID');\n"
            + "    alert(ver.length);\n"
            + "  }\n"
            + "  document.body.style.behavior = '';\n"
            + "  alert(document.body.cpuClass);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"36", "46"})
    public void clientWidthAndHeight() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var myDiv = document.getElementById('myDiv');\n"
            + "    alert(myDiv.clientWidth);\n"
            + "    alert(myDiv.clientHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "<style>#myDiv { width:30px; height:40px; padding:3px; border:5px; margin:7px; }</style>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'/>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "true", "true", "true"})
    public void clientWidthAndHeightPositionAbsolute() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('myDiv');\n"
            + "    var absDiv = document.getElementById('myAbsDiv');\n"

            // + "    alert(div.clientWidth +', ' + absDiv.clientWidth);\n"
            + "    alert(div.clientWidth > 100);\n"
            + "    alert(absDiv.clientWidth > 10);\n"
            + "    alert(absDiv.clientWidth < 100);\n"

            // + "    alert(div.clientHeight +', ' + absDiv.clientHeight);\n"
            + "    alert(div.clientHeight > 10);\n"
            + "    alert(div.clientHeight == absDiv.clientHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'>Test</div>\n"
            + "  <div id='myAbsDiv' style='position: absolute'>Test</div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0"})
    public void clientWidthAndHeightPositionAbsoluteEmpty() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var absDiv = document.getElementById('myAbsDiv');\n"
            + "    alert(absDiv.clientWidth);\n"
            + "    alert(absDiv.clientHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myAbsDiv' style='position: absolute'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "false", "true", "true", "true", "true", "true", "true", "true"})
    public void scrollWidthAndHeight() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var myDiv = document.getElementById('myDiv');\n"
            + "    alert(myDiv1.scrollWidth >= myDiv1.clientWidth);\n"
            + "    alert(myDiv1.scrollHeight >= myDiv1.clientHeight);\n"

            + "    alert(myDiv2.scrollWidth >= myDiv1.scrollWidth);\n"
            + "    alert(myDiv2.scrollHeight >= myDiv1.scrollHeight);\n"
            + "    alert(myDiv2.scrollWidth >= myDiv2.clientWidth);\n"
            + "    alert(myDiv2.scrollHeight >= myDiv2.clientHeight);\n"

            + "    alert(myDiv3.scrollWidth >= myDiv2.scrollWidth);\n"
            + "    alert(myDiv3.scrollHeight >= myDiv2.scrollHeight);\n"
            + "    alert(myDiv3.scrollWidth >= myDiv3.clientWidth);\n"
            + "    alert(myDiv3.scrollHeight >= myDiv3.clientHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv1'/>\n"
            + "  <div id='myDiv2' style='height: 42px; width: 42px' />\n"
            + "  <div id='myDiv3' style='height: 7em; width: 7em' />\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0"})
    public void scrollWidthAndHeightDisplayNone() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var myDiv = document.getElementById('myDiv');\n"
            + "    alert(myDiv.scrollWidth);\n"
            + "    alert(myDiv.scrollHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv' style='display: none;' />\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"0", "0"})
    public void scrollWidthAndHeightDetached() throws Exception {
        final String html =
              "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var myDiv = document.createElement('div');\n"
            + "    alert(myDiv.scrollWidth);\n"
            + "    alert(myDiv.scrollHeight);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for Bug #655.
     * @throws Exception if the test fails
     */
    @Test
    public void stackOverflowWithInnerHTML() throws Exception {
        final String html = "<html><head><title>Recursion</title></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "     document.body.innerHTML = unescape(document.body.innerHTML);\n"
            + "</script></body></html>";
        final WebDriver driver = loadPageWithAlerts2(html);
        assertTitle(driver, "Recursion");
    }

    /**
     * Test setting the class for the element.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"x", "null", "[object Attr]", "null", "x", "byClassname"})
    public void class_className_attribute() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function doTest() {\n"
            + "  var e = document.getElementById('pid');\n"
            + "  alert(e.getAttribute('class'));\n"
            + "  alert(e.getAttribute('className'));\n"
            + "  alert(e.getAttributeNode('class'));\n"
            + "  alert(e.getAttributeNode('className'));\n"
            + "  e.setAttribute('className', 'byClassname');\n"
            + "  alert(e.getAttribute('class'));\n"
            + "  alert(e.getAttribute('className'));\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<p id='pid' class='x'>text</p>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"-undefined-x", "null-x-null", "null-[object Attr]-null", "null-[object Attr]-null",
            "x-byClassname", "[object Attr]-[object Attr]", "byClassname-byClassname", "[object Attr]-[object Attr]"})
    public void class_className_attribute2() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function doTest() {\n"
            + "  var e = document.getElementById('pid');\n"
            + "  alert(e['lang'] + '-' + e['class'] + '-' + e['className']);\n"
            + "  alert(e.getAttribute('lang') + '-' + e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
            + "  alert(e.getAttributeNode('lang') + '-' + e.getAttributeNode('class') + '-' + "
            + "e.getAttributeNode('className'));\n"
            + "  alert(e.attributes.getNamedItem('lang') + '-' + e.attributes.getNamedItem('class') + '-' + "
            + "e.attributes.getNamedItem('className'));\n"
            + "  e.setAttribute('className', 'byClassname');\n"
            + "  alert(e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
            + "  alert(e.getAttributeNode('class') + '-' + e.getAttributeNode('className'));\n"
            + "  e.setAttribute('class', 'byClassname');\n"
            + "  alert(e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
            + "  alert(e.getAttributeNode('class') + '-' + e.getAttributeNode('className'));\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<p id='pid' class='x'>text</p>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"true", "true", "true", "false", "false", "false", "false", "true", "true", "false", "false"},
            IE = {"true", "true", "true", "false", "false", "false", "false", "true", "false", "false",
                        "exception"})
    public void contains() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "try {\n"
            + "  var div1 = document.getElementById('div1');\n"
            + "  var div2 = document.getElementById('div2');\n"
            + "  var text = div2.firstChild;\n"
            + "  var div3 = document.getElementById('div3');\n"
            + "  alert(div1.contains(div1));\n"
            + "  alert(div1.contains(div2));\n"
            + "  alert(div1.contains(div3));\n"
            + "  alert(div1.contains(div4));\n"
            + "  alert(div2.contains(div1));\n"
            + "  alert(div3.contains(div1));\n"
            + "  alert(div4.contains(div1));\n"
            + "  alert(div2.contains(div3));\n"
            + "  alert(div2.contains(text));\n"
            + "  alert(div3.contains(text));\n"
            + "  alert(text.contains(div3));\n"
            + "} catch(e) { alert('exception'); }\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "<div id='div1'>\n"
            + "  <div id='div2'>hello\n"
            + "    <div id='div3'>\n"
            + "    </div>\n"
            + "  </div>\n"
            + "</div>\n"
            + "<div id='div4'>\n"
            + "</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "exception",
            IE = "false")
    public void contains_invalid_argument() throws Exception {
        final String html = "<html><body><script>\n"
            + "try {\n"
            + "  alert(document.body.contains([]));\n"
            + "} catch(e) { alert('exception'); }\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("undefined")
    public void filters() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var div1 = document.getElementById('div1');\n"
            + "  var defined = typeof(div1.filters) != 'undefined';\n"
            + "  alert(defined ? 'defined' : 'undefined');\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "<div id='div1'>\n"
            + "</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"> myClass <", "> myId  <"})
    public void attributes_trimmed() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var div1 = document.body.firstChild;\n"
            + "  alert('>' + div1.className + '<');\n"
            + "  alert('>' + div1.id + '<');\n"
            + "}\n"
            + "</script></head><body onload='test()'>"
            + "<div id=' myId  ' class=' myClass '>\n"
            + "hello"
            + "</div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"function", "* => body: 0, div1: 0", "foo => body: 3, div1: 1", "foo red => body: 1, div1: 0",
                "red foo => body: 1, div1: 0", "blue foo => body: 0, div1: 0", "null => body: 0, div1: 0"})
    public void getElementsByClassName() throws Exception {
        final String html
            = "<html><head><title>First</title><script>\n"
            + "function test(x) {\n"
            + "  var b = document.body;\n"
            + "  var div1 = document.getElementById('div1');\n"
            + "  var s = x + ' => body: ' + b.getElementsByClassName(x).length;\n"
            + "  s += ', div1: ' + div1.getElementsByClassName(x).length;\n"
            + "  alert(s);\n"
            + "}\n"
            + "function doTest() {\n"
            + "  var b = document.body;\n"
            + "  var div1 = document.getElementById('div1');\n"
            + "  alert(typeof document.body.getElementsByClassName);\n"
            + "  test('*');\n"
            + "  test('foo');\n"
            + "  test('foo red');\n"
            + "  test('red foo');\n"
            + "  test('blue foo');\n"
            + "  test(null);\n"
            + "}\n"
            + "</script></head><body onload='doTest()'>\n"
            + "<div class='foo' id='div1'>\n"
            + "  <span class='c2'>hello</span>\n"
            + "  <span class='foo' id='span2'>World!</span>\n"
            + "</div>\n"
            + "<span class='foo red' id='span3'>again</span>\n"
            + "<span class='red' id='span4'>bye</span>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "[object HTMLDivElement]"})
    public void parentElement2() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var fragment = document.createDocumentFragment();\n"
            + "  var div = document.createElement('div');\n"
            + "  var bold = document.createElement('b');\n"
            + "  fragment.appendChild(div);\n"
            + "  div.appendChild(bold);\n"
            + "  alert(div.parentElement);\n"
            + "  alert(bold.parentElement);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * The method doScroll() should throw an exception if document is not yet loaded,
     * have a look into <a href="http://javascript.nwbox.com/IEContentLoaded/">this</a>.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"exception", "exception"})
    public void doScroll() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  try {\n"
            + "    document.documentElement.doScroll('left');\n"
            + "    alert('success');\n"
            + "  } catch (e) {\n"
            + "    alert('exception');\n"
            + "  }\n"
            + "}\n"
            + "test();\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "removeNode not available",
            IE = {"div2", "null", "div3", "div4", "div6", "div7", "null"})
    public void removeNode() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var div1 = document.getElementById('div1');\n"
            + "  var div2 = document.getElementById('div2');\n"
            + "  if (!div2.removeNode) { alert('removeNode not available'); return }\n"

            + "  alert(div1.firstChild.id);\n"
            + "  alert(div2.removeNode().firstChild);\n"
            + "  alert(div1.firstChild.id);\n"
            + "  alert(div1.firstChild.nextSibling.id);\n"
            + "\n"
            + "  var div5 = document.getElementById('div5');\n"
            + "  var div6 = document.getElementById('div6');\n"
            + "  alert(div5.firstChild.id);\n"
            + "  alert(div6.removeNode(true).firstChild.id);\n"
            + "  alert(div5.firstChild);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='div1'><div id='div2'><div id='div3'></div><div id='div4'></div></div></div>\n"
            + "  <div id='div5'><div id='div6'><div id='div7'></div><div id='div8'></div></div></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"undefined", "false", "hello", "true"})
    public void firefox__proto__() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var div1 = document.createElement('div');\n"
            + "  alert(div1.myProp);\n"
            + "  var p1 = div1['__proto__'];\n"
            + "  alert(p1 == undefined);\n"
            + "  if (p1)\n"
            + "    p1.myProp = 'hello';\n"
            + "  alert(div1.myProp);\n"
            + "  alert(p1 !== document.createElement('form')['__proto__']);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"false,false,false,false,false,true,false", "clearAttributes not available"},
            IE = {"false,false,false,false,false,true,false", "false,false,false,false,false,true,false"})
    public void clearAttributes() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "  function u(o) { return typeof o == 'undefined'; }\n"
            + "</script></head>\n"
            + "<body>\n"
            + "  <input type='text' id='i' name='i' style='color:red' onclick='alert(1)' custom1='a' />\n"
            + "  <script>\n"
            + "    var i = document.getElementById('i');\n"
            + "    i.custom2 = 'b';\n"
            + "    alert([u(i.type), u(i.id), u(i.name), u(i.style), u(i.onclick),"
            + "           u(i.custom1), u(i.custom2)].join(','));\n"
            + "    if(i.clearAttributes) {\n"
            + "      alert([u(i.type), u(i.id), u(i.name), u(i.style), u(i.onclick),"
            + "             u(i.custom1), u(i.custom2)].join(','));\n"
            + "    } else {\n"
            + "      alert('clearAttributes not available');\n"
            + "    }\n"
            + "  </script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "mergeAttributes not available",
            IE = {"false,false,false,false,false,true,true", "i", "",
                        "false,false,false,false,false,true,true", "i", ""})
    public void mergeAttributes() throws Exception {
        mergeAttributesTest("i2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "mergeAttributes not available",
            IE = {"false,false,false,false,false,true,true", "i", "",
                        "false,false,false,false,false,true,true", "i", ""})
    public void mergeAttributesTrue() throws Exception {
        mergeAttributesTest("i2, true");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "mergeAttributes not available",
            IE = {"false,false,false,false,false,true,true", "i", "",
                        "false,false,false,false,false,true,true", "i2", "i2"})
    public void mergeAttributesfalse() throws Exception {
        mergeAttributesTest("i2, false");
    }

    private void mergeAttributesTest(final String params) throws Exception {
        final String html
            = "<input type='text' id='i' />\n"
            + "<input type='text' id='i2' name='i2' style='color:red' onclick='alert(1)' custom1='a' />\n"
            + "<script>\n"
            + "function u(o) { return typeof o == 'undefined'; }\n"
            + "  var i = document.getElementById('i');\n"
            + "  if (i.mergeAttributes) {\n"
            + "    var i2 = document.getElementById('i2');\n"
            + "    i2.custom2 = 'b';\n"
            + "    alert([u(i.type), u(i.id), u(i.name), u(i.style), u(i.onclick),"
            + "           u(i.custom1), u(i.custom2)].join(','));\n"
            + "    alert(i.id);\n"
            + "    alert(i.name);\n"
            + "    i.mergeAttributes(" + params + ");\n"
            + "    alert([u(i.type), u(i.id), u(i.name), u(i.style), u(i.onclick),"
            + "           u(i.custom1), u(i.custom2)].join(','));\n"
            + "    alert(i.id);\n"
            + "    alert(i.name);\n"
            + "  } else {\n"
            + "    alert('mergeAttributes not available');\n"
            + "  }\n"
            + "</script>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("false")
    public void document() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  alert(document.body.document === document);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"exception call", "exception set"})
    public void prototype_innerHTML() throws Exception {
        final String html = "<html><body>\n"
            + "<script>\n"
            + "try {\n"
            + "  alert(HTMLElement.prototype.innerHTML);\n"
            + "} catch (e) { alert('exception call') }\n"
            + "try {\n"
            + "  var myFunc = function() {};\n"
            + "  HTMLElement.prototype.innerHTML = myFunc;\n"
            + "  alert(HTMLElement.prototype.innerHTML == myFunc);\n"
            + "} catch (e) { alert('exception set') }\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"", "#0000aa", "x", "BlanchedAlmond", "aBlue", "bluex"},
            IE = {"", "#0000aa", "#0", "blanchedalmond", "#ab00e", "#b00e0"})
    public void setColorAttribute() throws Exception {
        final String html =
            "<html>\n"
            + "  <head>\n"
            + "    <script>\n"
            + "      function test() {\n"
            + "        var b = document.getElementById('body');\n"
            + "        alert(b.vLink);\n"
            + "        document.vlinkColor = '#0000aa';\n"
            + "        alert(b.vLink);\n"
            + "        document.vlinkColor = 'x';\n"
            + "        alert(b.vLink);\n"
            + "        document.vlinkColor = 'BlanchedAlmond';\n"
            + "        alert(b.vLink);\n"
            + "        document.vlinkColor = 'aBlue';\n"
            + "        alert(b.vLink);\n"
            + "        document.vlinkColor = 'bluex';\n"
            + "        alert(b.vLink);\n"
            + "      }\n"
            + "    </script>\n"
            + "  </head>\n"
            + "  <body id='body' onload='test()'>blah</body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<span onclick=\"var f = &quot;hello&quot; + 'world'\">test span</span>")
    public void innerHTMLwithQuotes() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      alert(document.getElementById('foo').innerHTML);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head><body onload='test()'>\n"
            + "  <div id='foo'><span onclick=\"var f = &quot;hello&quot; + 'world'\">test span</span></div>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"button", "null", "false", "true"},
            IE = {"button", "", "false", "true"})
    public void attributeNS() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var e = document.getElementById('foo');\n"
            + "    alert(e.getAttribute('type'));\n"
            + "    try {\n"
            + "      alert(e.getAttributeNS('bar', 'type'));\n"
            + "      alert(e.hasAttributeNS('bar', 'type'));\n"
            + "      e.removeAttributeNS('bar', 'type');\n"
            + "      alert(e.hasAttribute('type'));\n"
            + "    } catch (e) {alert('getAttributeNS() not supported')}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <input id='foo' type='button' value='someValue'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("[object DOMStringMap]")
    public void dataset() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert(document.body.dataset);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts("")
    public void setAttribute_className() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.createElement('div');\n"
            + "    div.setAttribute('className', 't');\n"
            + "    alert(div.className);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts("t")
    public void setAttribute_class() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.createElement('div');\n"
            + "    div.setAttribute('class', 't');\n"
            + "    alert(div.className);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts("")
    public void setAttribute_className_standards() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_ + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.createElement('div');\n"
            + "    div.setAttribute('className', 't');\n"
            + "    alert(div.className);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts("t")
    public void setAttribute_class_standards() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_ + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.createElement('div');\n"
            + "    div.setAttribute('class', 't');\n"
            + "    alert(div.className);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts({"null", "", "null", "undefined"})
    public void getAttribute2() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var form = document.getElementById('testForm');\n"
                + "    alert(form.getAttribute('target'));\n"
                + "    alert(form.target);\n"
                + "    alert(form.getAttribute('target222'));\n"
                + "    alert(form.target222);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='testForm' action='#' method='get'>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception on test failure
     */
    @Test
    @Alerts({"null", "", "null", "undefined"})
    public void getAttribute2_standards() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_ + "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var form = document.getElementById('testForm');\n"
                + "    alert(form.getAttribute('target'));\n"
                + "    alert(form.target);\n"
                + "    alert(form.getAttribute('target222'));\n"
                + "    alert(form.target222);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='testForm' action='#' method='get'>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"DIV", "SECTION", "<div></div>", "<section></section>"})
    public void nodeNameVsOuterElement() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      alert(document.createElement('div').tagName);\n"
            + "      alert(document.createElement('section').tagName);\n"
            + "      alert(document.createElement('div').cloneNode( true ).outerHTML);\n"
            + "      alert(document.createElement('section').cloneNode( true ).outerHTML);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"null", "ho"})
    public void getSetAttribute_in_xml() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
            + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
            + "    text += '  <xsl:template match=\"/\">\\n';\n"
            + "    text += \"  <html xmlns='http://www.w3.org/1999/xhtml'>\\n\";\n"
            + "    text += '    <body>\\n';\n"
            + "    text += '    </body>\\n';\n"
            + "    text += '  </html>\\n';\n"
            + "    text += '  </xsl:template>\\n';\n"
            + "    text += '</xsl:stylesheet>';\n"
            + "    if (window.ActiveXObject) {\n"
            + "      var doc=new ActiveXObject('Microsoft.XMLDOM');\n"
            + "      doc.async = false;\n"
            + "      doc.loadXML(text);\n"
            + "    } else {\n"
            + "      var parser=new DOMParser();\n"
            + "      var doc=parser.parseFromString(text,'text/xml');\n"
            + "    }\n"
            + "    var elem = doc.documentElement.getElementsByTagName('html').item(0);\n"
            + "    alert(elem.getAttribute('hi'));\n"
            + "    elem.setAttribute('hi', 'ho');\n"
            + "    alert(elem.getAttribute('hi'));\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object Text]", "[object Text]"})
    public void textContentShouldNotDetachNestedNode() throws Exception {
        final String html = "<html><body><div><div id='it'>foo</div></div><script>\n"
            + "  var elt = document.getElementById('it');\n"
            + "  alert(elt.firstChild);\n"
            + "  elt.parentNode.textContent = '';\n"
            + "  alert(elt.firstChild);\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "<svg id=\"svgElem2\"></svg>",
            IE = "<svg xmlns=\"http://www.w3.org/2000/svg\" id=\"svgElem2\" />")
    @NotYetImplemented(IE)
    public void innerHTML_svg() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var div = document.createElement('div');\n"
                + "      document.body.appendChild(div);\n"
                + "      var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');\n"
                + "      svg.setAttribute('id', 'svgElem2');\n"
                + "      div.appendChild(svg);\n"
                + "      alert(div.innerHTML);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void appendChildExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    try {\n"
            + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.appendChild(newnode);\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void appendChildExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('div');\n"
            + "    var newscript = document.createElement('script');\n"
            + "    newnode.appendChild(newscript);\n"
            + "    try {\n"
            + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.appendChild(newnode);\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("declared")
    public void appendChildDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.appendChild(newnode);\n"
            + "    tester();\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void insertBeforeExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    try {\n"
            + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.insertBefore(newnode, null);\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void insertBeforeExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('div');\n"
            + "    var newscript = document.createElement('script');\n"
            + "    newnode.appendChild(newscript);\n"
            + "    try {\n"
            + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.insertBefore(newnode, null);\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("declared")
    public void insertBeforeDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.insertBefore(newnode, null);\n"
            + "    tester();\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void replaceChildExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    try {\n"
            + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.replaceChild(newnode, document.getElementById('inner'));\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'><div id='inner'></div></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void replaceChildExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('div');\n"
            + "    var newscript = document.createElement('script');\n"
            + "    newnode.appendChild(newscript);\n"
            + "    try {\n"
            + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
            + "      var outernode = document.getElementById('myNode');\n"
            + "      outernode.replaceChild(newnode, document.getElementById('inner'));\n"
            + "    } catch(e) { alert('exception-append'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'><div id='inner'></div></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("declared")
    public void replaceChildDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.replaceChild(newnode, document.getElementById('inner'));\n"
            + "    tester();\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'><div id='inner'></div></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"outside", "1", "middle", "2", "3", "4",
        "before-begin after-begin inside before-end after-end"})
    public void insertAdjacentHTML() throws Exception {
        insertAdjacentHTML("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentHTML("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentHTML("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    /**
     * @param beforeEnd data to insert
     * @param afterEnd data to insert
     * @param beforeBegin data to insert
     * @param afterBegin data to insert
     * @throws Exception if the test fails
     */
    private void insertAdjacentHTML(final String beforeEnd,
            final String afterEnd, final String beforeBegin, final String afterBegin) throws Exception {
        final String html = "<html><head><title>First</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var oNode = document.getElementById('middle');\n"
            + "  oNode.insertAdjacentHTML('" + beforeEnd + "', ' <span id=3>before-end</span> ');\n"
            + "  oNode.insertAdjacentHTML('" + afterEnd + "', ' <span id=4>after-end</span> ');\n"
            + "  oNode.insertAdjacentHTML('" + beforeBegin + "', ' <span id=1>before-begin</span> ');\n"
            + "  oNode.insertAdjacentHTML('" + afterBegin + "', ' <span id=2>after-begin</span> ');\n"
            + "  var coll = document.getElementsByTagName('SPAN');\n"
            + "  for (var i = 0; i < coll.length; i++) {\n"
            + "    alert(coll[i].id);\n"
            + "  }\n"
            + "  var outside = document.getElementById('outside');\n"
            + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
            + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
            + "  text = text.replace(/(\\s{2,})/g, ' ');\n"
            + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
            + "  alert(text);\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <span id='outside' style='color: #00ff00'>\n"
            + "    <span id='middle' style='color: #ff0000'>\n"
            + "      inside\n"
            + "    </span>\n"
            + "  </span>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void insertAdjacentHTMLExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.insertAdjacentHTML('afterend', '<scr'+'ipt>alerter();</scr'+'ipt>');\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void insertAdjacentHTMLExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.insertAdjacentHTML('afterend', '<div><scr'+'ipt>alerter();</scr'+'ipt></div>');\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void insertAdjacentHTMLDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    outernode.insertAdjacentHTML('afterend', "
            + "'<scr'+'ipt>function tester() { alerter(); }</scr'+'ipt>');\n"
            + "    try {\n"
            + "      tester();\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"outside", "1", "middle", "2", "3", "4",
                "before-begin after-begin inside before-end after-end"})
    public void insertAdjacentElement() throws Exception {
        insertAdjacentElement("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentElement("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentElement("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    private void insertAdjacentElement(final String beforeEnd,
            final String afterEnd, final String beforeBegin, final String afterBegin) throws Exception {
        final String html = "<html><head><title>First</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var oNode = document.getElementById('middle');\n"
            + "  if (!oNode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"

            + "  oNode.insertAdjacentElement('" + beforeEnd + "', makeElement(3, 'before-end'));\n"
            + "  oNode.insertAdjacentElement('" + afterEnd + "', makeElement(4, ' after-end'));\n"
            + "  oNode.insertAdjacentElement('" + beforeBegin + "', makeElement(1, 'before-begin '));\n"
            + "  oNode.insertAdjacentElement('" + afterBegin + "', makeElement(2, ' after-begin'));\n"
            + "  var coll = document.getElementsByTagName('SPAN');\n"
            + "  for (var i = 0; i < coll.length; i++) {\n"
            + "    alert(coll[i].id);\n"
            + "  }\n"
            + "  var outside = document.getElementById('outside');\n"
            + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
            + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
            + "  text = text.replace(/(\\s{2,})/g, ' ');\n"
            + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
            + "  alert(text);\n"
            + "}\n"
            + "function makeElement(id, value) {\n"
            + "  var span = document.createElement('span');\n"
            + "  span.appendChild(document.createTextNode(value));\n"
            + "  span.id = id;\n"
            + "  return span;\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <span id='outside' style='color: #00ff00'>\n"
            + "    <span id='middle' style='color: #ff0000'>\n"
            + "      inside\n"
            + "    </span>\n"
            + "  </span>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void insertAdjacentElementExecuteJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    newnode.appendChild(document.createTextNode('alerter();'));\n"

            + "    var outernode = document.getElementById('myNode');\n"
            + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
            + "    outernode.insertAdjacentElement('afterend', newnode);\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("executed")
    public void insertAdjacentElementExecuteNestedJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('div');\n"
            + "    var newscript = document.createElement('script');\n"
            + "    newnode.appendChild(newscript);\n"
            + "    newscript.appendChild(document.createTextNode('alerter();'));\n"

            + "    var outernode = document.getElementById('myNode');\n"
            + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
            + "    outernode.insertAdjacentElement('afterend', newnode);\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('executed');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("declared")
    public void insertAdjacentElementDeclareJavaScript() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var newnode = document.createElement('script');\n"
            + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
            + "    var outernode = document.getElementById('myNode');\n"
            + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
            + "    outernode.insertAdjacentElement('afterend', newnode);\n"
            + "    tester();\n"
            + "  }\n"
            + "  function alerter() {\n"
            + "    alert('declared');\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <div id='myNode'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"outside", "middle",
                "before-begin after-begin inside before-end after-end"})
    public void insertAdjacentText() throws Exception {
        insertAdjacentText("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentText("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentText("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    private void insertAdjacentText(final String beforeEnd,
            final String afterEnd, final String beforeBegin, final String afterBegin) throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title><script>\n"
            + "function test() {\n"
            + "  var oNode = document.getElementById('middle');\n"
            + "  if (!oNode.insertAdjacentText) { alert('insertAdjacentText not available'); return }\n"
            + "  oNode.insertAdjacentText('" + beforeEnd + "', 'before-end');\n"
            + "  oNode.insertAdjacentText('" + afterEnd + "', ' after-end');\n"
            + "  oNode.insertAdjacentText('" + beforeBegin + "', 'before-begin ');\n"
            + "  oNode.insertAdjacentText('" + afterBegin + "', ' after-begin');\n"
            + "  var coll = document.getElementsByTagName('SPAN');\n"
            + "  for (var i = 0; i < coll.length; i++) {\n"
            + "    alert(coll[i].id);\n"
            + "  }\n"
            + "  var outside = document.getElementById('outside');\n"
            + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
            + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
            + "  text = text.replace(/(\\s{2,})/g, ' ');\n"
            + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
            + "  alert(text);\n"
            + "}\n"
            + "</script></head><body onload='test()'>\n"
            + "  <span id='outside' style='color: #00ff00'>\n"
            + "    <span id='middle' style='color: #ff0000'>\n"
            + "      inside\n"
            + "    </span>\n"
            + "  </span>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Simple test that calls setCapture.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "setCapture available",
            CHROME = "exception")
    public void setCapture() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('myDiv');\n"
            + "    try {\n"
            + "      div.setCapture();\n"
            + "      div.setCapture(true);\n"
            + "      div.setCapture(false);\n"
            + "      alert('setCapture available');\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Simple test that calls setCapture.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "releaseCapture available",
            CHROME = "exception")
    public void releaseCapture() throws Exception {
        final String html = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html><head><title>foo</title>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('myDiv');\n"
            + "    try {\n"
            + "      div.releaseCapture();\n"
            + "      alert('releaseCapture available');\n"
            + "    } catch(e) { alert('exception'); }\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"inherit", "false", "string", "boolean"})
    public void contentEditable() throws Exception {
        final String html = ""
            + "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var div = document.getElementById('myDiv');\n"
            + "    alert(div.contentEditable);\n"
            + "    alert(div.isContentEditable);\n"
            + "    alert(typeof div.contentEditable);\n"
            + "    alert(typeof div.isContentEditable);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'></div>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * See issue #1702.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("true")
    public void oninput() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <title>Test</title>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var testNode = document.createElement('div');\n"
            + "      alert('oninput' in testNode);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object HTMLBodyElement]", "[object HTMLButtonElement]",
                    "http://srv/htmlunit.org", "http://srv/htmlunit.org"})
    public void focus() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "  <title>Test</title>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      alert(document.activeElement);\n"

            + "      var testNode = document.getElementById('myButton');\n"
            + "      testNode.focus();\n"
            + "      alert(document.activeElement);\n"

            + "      testNode = document.getElementById('myA');\n"
            + "      testNode.focus();\n"
            + "      alert(document.activeElement);\n"

            + "      testNode = document.getElementById('myDiv');\n"
            + "      testNode.focus();\n"
            + "      alert(document.activeElement);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='myDiv'>blah</div>\n"
            + "  <a id='myA' href='http://srv/htmlunit.org'>anchor</a>\n"
            + "  <button id='myButton'>Press</button>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void blur() throws Exception {
        final String html = "<html><head>\n"
            + "<body>\n"
            + "  <div id='div1' onblur='alert(\"blurred\")'>the first div</div>\n"
            + "  <div id='div2'>the second div</div>\n"
            + "</body></html>";

        final WebDriver webDriver = loadPage2(html);

        webDriver.findElement(By.id("div1")).click();
        webDriver.findElement(By.id("div2")).click();

        verifyAlerts(webDriver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("blurred")
    public void blur2() throws Exception {
        final String html = "<html><head>\n"
            + "<body>\n"
            + "  <div id='div1' onblur='alert(\"blurred\")' tabindex='0'>the first div</div>\n"
            + "  <div id='div2'>the second div</div>\n"
            + "</body></html>";

        final WebDriver webDriver = loadPage2(html);

        webDriver.findElement(By.id("div1")).click();
        webDriver.findElement(By.id("div2")).click();

        verifyAlerts(webDriver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object HTMLDivElement]", "[object HTMLBodyElement]", "[object Window]"})
    public void currentTarget() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<title>Test</title>\n"
            + "<script>\n"
            + "  function handler(ev) {\n"
            + "    alert(ev.currentTarget);\n"
            + "  }\n"

            + "  function test() {\n"
            + "    var byId = document.getElementById.bind(document);\n"

            + "    var under = byId('under');\n"
            + "    var over = byId('over');\n"
            + "    var body = document.body;\n"

            + "    var types = ['click'];\n"
            + "    for (var i = 0, type; (type = types[i]); ++i) {\n"
            + "      under.addEventListener(type, handler);\n"
            + "      over.addEventListener(type, handler);\n"
            + "      body.addEventListener(type, handler);\n"
            + "      window.addEventListener(type, handler);\n"
            + "    }\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='under'><p id='contents'>Hello</p></div>\n"
            + "  <div id='over'>abc</div>\n"
            + "</body>\n"
            + "</html>";

        final WebDriver webDriver = loadPage2(html);
        webDriver.findElement(By.id("over")).click();
        verifyAlerts(webDriver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("mousedown-over-over\nmousedown-over-body\nmousedown-over-undefined\n"
        + "mouseup--body\nmouseup--undefined")
    @NotYetImplemented
    public void clickAnElementThatDisappears() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "<title>Test</title>\n"
            + "<script>\n"
            + "  function handler(e) {\n"
            + "    var log = document.getElementById('log');\n"
            + "    log.innerHTML += '<p></p>';\n"
            + "    log.lastElementChild.textContent = e.type + '-' + e.target.id + '-' + e.currentTarget.id;\n"
            + "  }\n"

            + "  function test() {\n"
            + "    var over = document.getElementById('over');\n"
            + "    var body = document.body;\n"

            + "    var types = ['click', 'mousedown', 'mouseup'];\n"
            + "    for (var i = 0, type; (type = types[i]); ++i) {\n"
            + "      over.addEventListener(type, handler);\n"
            + "      body.addEventListener(type, handler);\n"
            + "      window.addEventListener(type, handler);\n"
            + "    }\n"

            + "    over.addEventListener('mousedown', function () {\n"
            + "      over.style.display = 'none';\n"
            + "    });\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body id='body' onload='test()'>\n"
            + "  <div id='over'>abc</div>\n"
            + "  <div id='log'></div>\n"
            + "  </div>\n"
            + "</body>\n"
            + "</html>";

        final WebDriver webDriver = loadPage2(html);
        webDriver.findElement(By.id("over")).click();
        assertEquals(getExpectedAlerts()[0], webDriver.findElement(By.id("log")).getText());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("<select id=\"myId\"><option>Two</option></select>")
    public void innerHTML() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var select = document.getElementById('myId');\n"
            + "      select.innerHTML = \"<select id='myId2'><option>Two</option></select>\";\n"
            + "      alert(document.body.innerHTML.trim());\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <select id='myId'><option>One</option></select>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    public void innerHTMLGetElementsByTagName() throws Exception {
        final String html =
            HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var div = document.createElement('div');\n"
            + "      div.innerHTML = \"<table></table><a href='/a'>a</a>\";\n"
            + "      alert(div.getElementsByTagName('a').length);\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"false", "false"})
    public void hidden() throws Exception {
        final String html =
            "<html><head><script>\n"
            + "  function test() {\n"
            + "    var d1 = document.getElementById('div1');\n"
            + "    alert(d1.hidden);\n"
            + "    var d2 = document.getElementById('div2');\n"
            + "    alert(d2.hidden);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='div1' style='display: none'>\n"
            + "  </div>\n"
            + "  <div id='div2' />\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void isDisplayed() throws Exception {
        final String html =
            "<html>\n"
            + "<body>\n"
            + "  <div id='div1' hidden>\n"
            + "    <div id='child' />\n"
            + "  </div>\n"
            + "</body></html>";

        final WebDriver driver = loadPage2(html);
        final WebElement element = driver.findElement(By.id("child"));
        assertFalse(element.isDisplayed());
    }
}

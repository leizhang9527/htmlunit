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
package com.gargoylesoftware.htmlunit.javascript.host;

import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.FF;
import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.FF52;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;
import com.gargoylesoftware.htmlunit.util.MimeType;

/**
 * Tests for {@link Window}. The only difference with {@link WindowTest} is that these
 * tests already run with BrowserRunner.
 *
 * @author Marc Guillemot
 * @author Ahmed Ashour
 * @author Ronald Brill
 * @author Frank Danek
 * @author Carsten Steul
 * @author Colin Alworth
 */
@RunWith(BrowserRunner.class)
public class Window2Test extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"[object Window]", "exception", "undefined", "undefined", "hello", "hello", "world", "world"})
    public void thisIsWindow() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  alert(this);\n"
            + "  try {\n"
            + "    alert(abc);\n"
            + "  } catch(e) {alert('exception')}\n"
            + "  alert(this.abc);\n"
            + "  alert(this.def);\n"
            + "  this.abc = 'hello';\n"
            + "  def = 'world';\n"
            + "  alert(abc);\n"
            + "  alert(this.abc);\n"
            + "  alert(def);\n"
            + "  alert(this.def);\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"function", "function"})
    public void thisIsWindow2() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  function hello() {\n"
            + "    var x = 1;\n"
            + "  } \n"
            + "  alert(typeof hello);\n"
            + "  alert(typeof window.hello);\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * "window.controllers" is used by some JavaScript libraries to determine if the browser is Gecko based or not.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"not found", "true"},
            FF = {"found", "true"})
    public void FF_controllers() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "if (window.controllers)\n"
            + "  alert('found');\n"
            + "else\n"
            + "  alert('not found');\n"
            + "window.controllers = 'hello';\n"
            + "alert(window.controllers == 'hello');\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("true")
    public void FF_controllers_set() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  window.controllers = 'hello';\n"
            + "  alert(window.controllers == 'hello');\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Verifies that properties added to <tt>Function.prototype</tt> are visible on <tt>window.onload</tt>.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"a", "1"})
    public void onload_prototype() throws Exception {
        final String html
            = "<html><body onload='alert(1)'>\n"
            + "<script>Function.prototype.x='a'; alert(window.onload.x);</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"SGVsbG8gV29ybGQh", "Hello World!"})
    public void atob() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var data = window.btoa('Hello World!');\n"
            + "  alert(data);\n"
            + "  alert(atob(data));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"exception", "exception"})
    public void atobUnicode() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  try {\n"
            + "    window.btoa('I \\u2661 Unicode!');\n"
            + "  } catch(e) {alert('exception')}\n"
            + "  try {\n"
            + "    window.atob('I \\u2661 Unicode!');\n"
            + "  } catch(e) {alert('exception')}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"CSAe", "\t \u001e"})
    public void atobControlChar() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var data = window.btoa('\\t \\u001e');\n"
            + "  alert(data);\n"
            + "  alert(atob(data));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"bnVsbA==", "null"})
    public void atobNull() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var data = window.btoa(null);\n"
            + "  alert(data);\n"
            + "  alert(atob(data));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"dW5kZWZpbmVk", "undefined"})
    public void atobUndefined() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var data = window.btoa(undefined);\n"
            + "  alert(data);\n"
            + "  alert(atob(data));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Rhino and Nashorn define some properties in the top scope (see ScriptRuntime and lazilyNames in Rhino),
     * and we don't need them.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"getClass: undefined,undefined", "java: undefined,undefined", "javax: undefined,undefined",
            "javafx: undefined,undefined", "org: undefined,undefined", "com: undefined,undefined",
            "edu: undefined,undefined", "net: undefined,undefined", "JavaAdapter: undefined,undefined",
            "JavaImporter: undefined,undefined", "Continuation: undefined,undefined", "Packages: undefined,undefined",
            "XML: undefined,undefined", "XMLList: undefined,undefined", "Namespace: undefined,undefined",
            "QName: undefined,undefined", "arguments: undefined,undefined", "load: undefined,undefined",
            "loadWithNewGlobal: undefined,undefined", "exit: undefined,undefined", "quit: undefined,undefined",
            "__FILE__: undefined,undefined", "__DIR__: undefined,undefined", "__LINE__: undefined,undefined",
            "context: undefined,undefined", "engine: undefined,undefined", "__noSuchProperty__: undefined,undefined",
            "Java: undefined,undefined", "JSAdapter: undefined,undefined",
            "NaN: number,number", "Infinity: number,number", "eval: function,function", "print: function,function",
            "parseInt: function,function", "parseFloat: function,function",
            "isNaN: function,function", "isFinite: function,function", "encodeURI: function,function",
            "encodeURIComponent: function,function", "decodeURI: function,function",
            "decodeURIComponent: function,function", "escape: function,function", "unescape: function,function"})
    public void topLevelProperties() throws Exception {
        final String[] properties = {"getClass", "java", "javax", "javafx", "org", "com", "edu", "net", "JavaAdapter",
            "JavaImporter", "Continuation", "Packages", "XML", "XMLList", "Namespace", "QName", "arguments", "load",
            "loadWithNewGlobal", "exit", "quit", "__FILE__", "__DIR__", "__LINE__", "context", "engine",
            "__noSuchProperty__", "Java", "JSAdapter",
            "NaN", "Infinity", "eval", "print", "parseInt", "parseFloat", "isNaN", "isFinite", "encodeURI",
            "encodeURIComponent", "decodeURI", "decodeURIComponent", "escape", "unescape"};

        final String html = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var props = ['" + String.join("', '", properties) + "'];\n"
            + "  for (var i = 0; i < props.length; i++)\n"
            + "    alert(props[i] + ': ' + typeof(window[props[i]]) + ',' + typeof(eval('this.' + props[i])));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html, 3 * DEFAULT_WAIT_TIME);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("javax.script.filename: undefined")
    public void topLevelPropertiesWithDot() throws Exception {
        final String[] properties = {"javax.script.filename"};

        final String html = "<html><head></head><body>\n"
            + "<script>\n"
            + "  var props = ['" + String.join("', '", properties) + "'];\n"
            + "  for (var i = 0; i < props.length; i++)\n"
            + "    alert(props[i] + ': ' + typeof(window[props[i]]));\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void execScript2() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      window.execScript('alert(1);');\n"
            + "    }\n"
            + "    catch(e) { alert('exception') }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void execScript_returnValue() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "try {\n"
            + "  alert(window.execScript('1') === undefined);\n"
            + "}\n"
            + "catch(e) { alert('exception') }\n"
            + "</script></head><body>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            IE = "function")
    public void collectGarbage() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    alert(typeof CollectGarbage);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"original", "changed"})
    public void eval_localVariable() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var f = document.getElementById('testForm1');\n"
            + "    alert(f.text1.value);\n"
            + "    eval('f.text_' + 1).value = 'changed';\n"
            + "    alert(f.text1.value);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "  <form id='testForm1'>\n"
            + "    <input id='text1' type='text' name='text_1' value='original'>\n"
            + "  </form>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test window properties that match Prototypes.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"function Node() {\n    [native code]\n}", "function Element() {\n    [native code]\n}"},
            CHROME = {"function Node() { [native code] }", "function Element() { [native code] }"},
            IE = {"[object Node]", "[object Element]"})
    public void windowProperties() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    alert(window.Node);\n"
            + "    alert(window.Element);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "<form name='myForm'></form>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test that length of frames collection is retrieved.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"0", "0"})
    public void framesLengthZero() throws Exception {
        final String html
            = "<html><head><title>foo</title><script>\n"
            + "alert(window.length);\n"
            + "alert(window.frames.length);\n"
            + "</script></head><body>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Test that length of frames collection is retrieved when there are frames.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"2", "2", "frame1", "frame2"})
    public void framesLengthAndFrameAccess() throws Exception {
        final String html =
            "<html>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  alert(window.length);\n"
            + "  alert(window.frames.length);\n"
            + "  alert(window.frames[0].name);\n"
            + "  alert(window.frames.frame2.name);\n"
            + "}\n"
            + "</script>\n"
            + "<frameset rows='50,*' onload='test()'>\n"
            + "<frame name='frame1' src='about:blank'/>\n"
            + "<frame name='frame2' src='about:blank'/>\n"
            + "</frameset>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"0", "0", "2", "2", "2", "true"})
    public void windowFramesLive() throws Exception {
        final String html =
            "<html>\n"
            + "<script>\n"
            + "alert(window.length);\n"
            + "var oFrames = window.frames;\n"
            + "alert(oFrames.length);\n"
            + "function test() {\n"
            + "  alert(oFrames.length);\n"
            + "  alert(window.length);\n"
            + "  alert(window.frames.length);\n"
            + "  alert(oFrames == window.frames);\n"
            + "}\n"
            + "</script>\n"
            + "<frameset rows='50,*' onload='test()'>\n"
            + "<frame src='about:blank'/>\n"
            + "<frame src='about:blank'/>\n"
            + "</frameset>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for http://sourceforge.net/p/htmlunit/bugs/234/
     * and https://bugzilla.mozilla.org/show_bug.cgi?id=443491.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("hello")
    public void overwriteFunctions_navigator() throws Exception {
        final String html = "<html><head><script>\n"
            + "  function test() {\n"
            + "    function navigator() {\n"
            + "      alert('hello');\n"
            + "    }\n"
            + "    navigator();\n"
            + "  }\n"
            + "</script></head><body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for bug 2808901.
     * @throws Exception if an error occurs
     */
    @Test
    public void onbeforeunload_setToString() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  window.onbeforeunload = \"alert('x')\";\n"
            + "  window.location = 'about:blank';\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for bug 2808901.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "function"})
    public void onbeforeunload_defined() throws Exception {
        onbeforeunload("onbeforeunload", "var x;");
    }

    /**
     * Regression test for bug 2808901.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "object"})
    public void onbeforeunload_notDefined() throws Exception {
        onbeforeunload("onbeforeunload", null);
    }

    private void onbeforeunload(final String name, final String js) throws Exception {
        final String html
            = "<html><body" + (js != null ? " " + name + "='" + js + "'" : "") + "><script>\n"
            + "  alert('" + name + "' in window);\n"
            + "  var x = false;\n"
            + "  for(var p in window) { if(p == '" + name + "') { x = true; break; } }\n"
            + "  alert(x);\n"
            + "  alert(typeof window." + name + ");\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Verifies that <tt>window.frames</tt> basically returns a reference to the window.
     * Regression test for bug 2824436.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"[object Window]", "[object Window]", "[object Window]", "1", "true", "true",
                    "[object Window]", "true", "true", "no function", "undefined", "true", "true",
                    "[object History]", "true", "true", "[object Window]", "true", "true"})
    public void framesAreWindows() throws Exception {
        final String html = "<html><body><iframe name='f'></iframe><script>\n"
            + "alert(window.frames);\n"
            + "alert(window.f);\n"
            + "alert(window.frames.f);\n"
            + "alert(window.length);\n"
            + "alert(window.length == window.frames.length);\n"
            + "alert(window.length == window.frames.frames.length);\n"
            + "alert(window[0]);\n"
            + "alert(window[0] == window.frames[0]);\n"
            + "alert(window[0] == window.frames.frames[0]);\n"
            + "try {\n"
            + "  alert(window(0));\n"
            + "  alert(window(0) == window.frames(0));\n"
            + "  alert(window(0) == window.frames.frames(0));\n"
            + "} catch(e) {\n"
            + "  alert('no function');\n"
            + "}\n"
            + "alert(window[1]);\n"
            + "alert(window[1] == window.frames[1]);\n"
            + "alert(window[1] == window.frames.frames[1]);\n"
            + "alert(window.history);\n"
            + "alert(window.history == window.frames.history);\n"
            + "alert(window.history == window.frames.frames.history);\n"
            + "alert(window.self);\n"
            + "alert(window.self == window.frames.self);\n"
            + "alert(window.self == window.frames.frames.self);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Tests window.open().
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"Hello window", ""})
    public void open() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function info(msg) {\n"
            + "    alert(msg);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  window.open('" + URL_SECOND + "');\n"
            + "</script>\n"
            + "</body></html>";
        final String windowContent = "<html><head></head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  window.opener.info('Hello window');\n"
                + "  window.opener.info(window.name);\n"
                + "</script>\n"
                + "</body></html>";
        getMockWebConnection().setDefaultResponse(windowContent);
        loadPageWithAlerts2(html);

        // for unknown reason, the selenium driven browser is in an invalid state after this test
        releaseResources();
        shutDownAll();
    }

    /**
     * Tests window.open(...) with some params.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"Hello window", "New window"})
    public void openWindowParams() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function info(msg) {\n"
            + "    alert(msg);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  window.open('" + URL_SECOND + "', 'New window', 'width=200,height=100');\n"
            + "</script>\n"
            + "</body></html>";
        final String windowContent = "<html><head></head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  window.opener.info('Hello window');\n"
                + "  window.opener.info(window.name);\n"
                + "</script>\n"
                + "</body></html>";
        getMockWebConnection().setDefaultResponse(windowContent);
        loadPageWithAlerts2(html);

        // for unknown reason, the selenium driven browser is in an invalid state after this test
        releaseResources();
        shutDownAll();
    }

    /**
     * Tests window.open(...) with replace param.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"window1", "window2"})
    public void openWindowParamReplace() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function info(msg) {\n"
            + "    alert(msg);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  window.open('" + URL_SECOND + "', 'window1', 'width=200,height=100', true);\n"
            + "  window.open('" + URL_SECOND + "', 'window2', 'width=200,height=100', 'true');\n"
            + "</script>\n"
            + "</body></html>";
        final String windowContent = "<html><head></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  window.opener.info(window.name);\n"
            + "</script>\n"
            + "</body></html>";
        getMockWebConnection().setDefaultResponse(windowContent);
        loadPageWithAlerts2(html);

        // for unknown reason, the selenium driven browser is in an invalid state after this test
        releaseResources();
        shutDownAll();
    }

    /**
     * For FF, window's opener can't be set unless to its current value.
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"[object Window]", "[object Window] (true)", "1234 (true)", "null (true)", "undefined (true)",
                    "[object Window] (true)", "[object Window] (true)", "[object Window] (true)"},
            IE = {"[object Window]", "[object Window] (true)", "exception", "null (true)", "undefined (true)",
                "[object Window] (true)", "[object Window] (true)", "[object Window] (true)"})
    public void set_opener() throws Exception {
        final String html = "<html><head><script>\n"
            + "var otherWindow = window.open('about:blank');\n"
            + "function trySetOpener1(_win, value) {\n"
            + "  try {\n"
            + "    _win.opener = value;\n"
            + "    alert(_win.opener + ' (' + (_win.opener === value) + ')');\n"
            + "  }\n"
            + "  catch(e) { alert('exception') }\n"
            + "}\n"
            + "function trySetOpener(_win) {\n"
            + "  var originalValue = _win.opener;\n"
            + "  alert(originalValue);\n"
            + "  trySetOpener1(_win, _win.opener);\n"
            + "  trySetOpener1(_win, 1234);\n"
            + "  trySetOpener1(_win, null);\n"
            + "  trySetOpener1(_win, undefined);\n"
            + "  trySetOpener1(_win, _win);\n"
            + "  trySetOpener1(_win, otherWindow);\n"
            + "  trySetOpener1(_win, originalValue);\n"
            + "}\n"
            + "function doTest() {\n"
            + "  trySetOpener(window.open('about:blank'));\n"
            + "}\n"
            + "</script></head>\n"
            + "<body onload='doTest()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
        // for some reason, the selenium driven browser is in an invalid state after this test
        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"exception", "exception", "exception", "exception"},
            IE = {"JScript", "11", "0", "number"})
    public void IEScriptEngineXxx() throws Exception {
        final String html = "<html><head><script>\n"
            + "try { alert(ScriptEngine()); } catch(e) { alert('exception') }\n"
            + "try { alert(ScriptEngineMajorVersion()); } catch(e) { alert('exception') }\n"
            + "try { alert(ScriptEngineMinorVersion()); } catch(e) { alert('exception') }\n"
            + "try { alert(typeof ScriptEngineBuildVersion()); } catch(e) { alert('exception') }\n"
            + "</script></head>\n"
            + "<body>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for bug 2897473.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(CHROME = {"true", "true", "136", "true", "true", "16"},
            FF = {"true", "true", "94", "true", "true", "14"},
            IE = {"true", "true", "63", "true", "true", "16"})
    public void heightsAndWidths() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.innerHeight > 0);\n"
            + "  alert(window.innerHeight == document.body.clientHeight);\n"
            + "  alert(window.outerHeight - window.innerHeight);\n"
            + "  alert(window.innerWidth > 0);\n"
            + "  alert(window.innerWidth == document.body.clientWidth);\n"
            + "  alert(window.outerWidth - window.innerWidth);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "1234"})
    public void setInnerWidth() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.innerWidth > 0);\n"
            + "  window.innerWidth = 1234;\n"
            + "  alert(window.innerWidth);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "1234"})
    public void setInnerHeight() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.innerHeight > 0);\n"
            + "  window.innerHeight = 1234;\n"
            + "  alert(window.innerHeight);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "1234"})
    public void setOuterWidth() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.outerWidth > 0);\n"
            + "  window.outerWidth = 1234;\n"
            + "  alert(window.outerWidth);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "1234"})
    public void setOuterHeight() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.outerHeight > 0);\n"
            + "  window.outerHeight = 1234;\n"
            + "  alert(window.outerHeight);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for bug 2944261.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(CHROME = {"632", "1256", "615", "1239"},
            FF60 = {"674", "1258", "657", "1241"},
            FF52 = {"674", "1258", "657", "1241"},
            IE = {"705", "1256", "688", "1239"})
    @NotYetImplemented
    // TODO width and height calculation needs to be reworked in HtmlUnit
    // but as the calculation might be effected by e.g. current windows style it is not that simple
    public void changeHeightsAndWidths() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script language='javascript'>\n"
            + "  function test() {\n"
            + "    var oldHeight = document.body.clientHeight;\n"
            + "    var oldWidth = document.body.clientWidth;\n"
            + "    alert(document.body.clientHeight);\n"
            + "    alert(document.body.clientWidth);\n"
            + "    newDiv = document.createElement('div');\n"
            + "    document.body.appendChild(newDiv);\n"
            + "    newDiv.style['height'] = oldHeight + 100 + 'px';\n"
            + "    newDiv.style['width'] = oldWidth + 100 + 'px';\n"
            + "    alert(document.body.clientHeight);\n"
            + "    alert(document.body.clientWidth);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body>\n"
            + "</html>";
        loadPageWithAlerts2(html);
    }

    /**
     * Regression test for bug 2897457.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"0,0", "100,200", "110,230", "0,0", "no scrollByLines()", "0,0", "no scrollByPages()"},
            FF52 = {"0,0", "100,200", "110,230", "0,0", "0,95", "0,0", "0,1238"},
            FF60 = {"0,0", "100,200", "110,230", "0,0", "0,95", "0,0", "0,1254"})
    @NotYetImplemented(FF)
    public void scrolling1() throws Exception {
        scrolling(true);
    }

    /**
     * Regression test for bug 2897457.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"0,0", "0,0", "0,0", "0,0", "no scrollByLines()", "0,0", "no scrollByPages()"},
            FF = {"0,0", "0,0", "0,0", "0,0", "0,0", "0,0", "0,0"})
    public void scrolling2() throws Exception {
        scrolling(false);
    }

    private void scrolling(final boolean addHugeDiv) throws Exception {
        final String html
            = "<html><body onload='test()'>\n"
            + (addHugeDiv ? "<div id='d' style='width:10000px;height:10000px;background-color:blue;'></div>\n" : "")
            + "<script>\n"
            + "function test() {\n"
            + "  var b = document.body;\n"
            + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  window.scrollTo(100, 200);\n"
            + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  window.scrollBy(10, 30);\n"
            + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  window.scrollTo(-5, -20);\n"
            + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  if(window.scrollByLines) {\n"
            + "    window.scrollByLines(5);\n"
            + "    alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  } else {\n"
            + "    alert('no scrollByLines()');\n"
            + "  }\n"
            + "  window.scroll(0, 0);\n"
            + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  if(window.scrollByPages) {\n"
            + "    window.scrollByPages(2);\n"
            + "    alert(b.scrollLeft + ',' + b.scrollTop);\n"
            + "  } else {\n"
            + "    alert('no scrollByPages()');\n"
            + "  }\n"
            + "}\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"0", "0", "0", "0"},
            IE = {"0", "0", "undefined", "undefined"})
    public void pageXOffset() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  window.scrollBy(5, 10);\n"
            + "  alert(window.pageXOffset);\n"
            + "  alert(window.pageYOffset);\n"
            + "  alert(window.scrollX);\n"
            + "  alert(window.scrollY);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("object")
    public void typeof() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert(typeof window);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"undefined", "undefined"},
            FF52 = {"11", "91"},
            FF60 = {"11", "83"})
    @NotYetImplemented(FF52)
    public void mozInnerScreen() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(window.mozInnerScreenX);\n"
            + "  alert(window.mozInnerScreenY);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            FF = "number")
    public void mozPaintCount() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  alert(typeof window.mozPaintCount);\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"exception", "exception", "Success"})
    public void eval() throws Exception {
        final String html
            = "<html><body onload='test()'><script>\n"
            + "function test() {\n"
            + "  var x = new Object();\n"
            + "  x.a = 'Success';\n"
            + "  try {\n"
            + "    alert(window['eval']('x.a'));\n"
            + "  } catch(e) {alert('exception')}\n"
            + "  try {\n"
            + "    alert(window.eval('x.a'));\n"
            + "  } catch(e) {alert('exception')}\n"
            + "  try {\n"
            + "    alert(eval('x.a'));\n"
            + "  } catch(e) {alert('exception')}\n"
            + "}\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     * @see com.gargoylesoftware.htmlunit.javascript.host.event.EventTest#firedEvent_equals_original_event()
     */
    @Test
    @Alerts({"true", "I was here"})
    public void firedEvent_equals_original_event() throws Exception {
        final String html =
            "<html><head><title>First</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  var myEvent;\n"
            + "  var listener = function(x) {\n"
            + "    alert(x == myEvent);\n"
            + "    x.foo = 'I was here';\n"
            + "  }\n"
            + "  window.addEventListener('click', listener, false);\n"
            + "  myEvent = document.createEvent('HTMLEvents');\n"
            + "  myEvent.initEvent('click', true, true);\n"
            + "  window.dispatchEvent(myEvent);\n"
            + "  alert(myEvent.foo);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "true", "true", "true"})
    public void thisEquals() throws Exception {
        final String html =
            "<html><head><title>First</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  alert(this === window);\n"
            + "  alert(window === this);\n"
            + "  alert(this == window);\n"
            + "  alert(window == this);\n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"null", "function", "null", "null"})
    public void onbeforeunload() throws Exception {
        final String html =
            "<html><head><title>First</title>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  alert(window.onbeforeunload);\n"
            + "  var handle = function() {};\n"
            + "  window.onbeforeunload = handle;\n"
            + "  alert(typeof window.onbeforeunload);\n"
            + "  window.onbeforeunload = null;\n"
            + "  alert(window.onbeforeunload);\n"
            + "  window.onbeforeunload = undefined;\n"
            + "  alert(window.onbeforeunload);\n"
            + "  \n"
            + "}\n"
            + "</script>\n"
            + "</head><body onload='test()'>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Verifies that <tt>this.arguments</tt> works from within a method invocation, in a
     * function defined on the Function prototype object. This usage is required by the
     * Ajax.NET Professional JavaScript library.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "0", "2", "2", "null"})
    public void functionPrototypeArguments() throws Exception {
        final String html =
              "<html>\n"
            + "<body onload='test()'>\n"
            + "<script>\n"
            + "  function test() {\n"
            + " \n"
            + "    Function.prototype.doAlerts = function() {\n"
            + "      alert(this == o.f);\n"
            + "      alert(arguments ? arguments.length : 'null');\n"
            + "      alert(this.arguments ? this.arguments.length : 'null');\n"
            + "    }\n"
            + " \n"
            + "    var o = function() {};\n"
            + "    o.f = function(x, y, z) {\n"
            + "      this.f.doAlerts();\n"
            + "      alert(arguments ? arguments.length : 'null');\n"
            + "      alert(this.arguments ? this.arguments.length : 'null');\n"
            + "    }\n"
            + "    o.f('a', 'b');\n"
            + "  }\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "[object Arguments]", "null", "true", "[object Arguments]", "[object Arguments]"})
    public void functionPrototypeArguments2() throws Exception {
        final String html =
              "<html>\n"
            + "<body onload='test()'>\n"
            + "<script>\n"
            + "  function test() {\n"
            + " \n"
            + "    Function.prototype.doAlerts = function() {\n"
            + "      alert(this == o.f);\n"
            + "      alert(arguments);\n"
            + "      alert(this.arguments);\n"
            + "    }\n"
            + " \n"
            + "    var o = function() {};\n"
            + "    o.f = function(x, y, z) {\n"
            + "      alert(this == o);\n"
            + "      alert(arguments);\n"
            + "      alert(this.arguments);\n"
            + "      this.f.doAlerts();\n"
            + "    }\n"
            + "    o.f('a', 'b');\n"
            + "  }\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"true", "[object Arguments]", "null", "true", "[object Arguments]", "null"})
    public void functionPrototypeArguments3() throws Exception {
        final String html =
              "<html>\n"
            + "<body onload='test()'>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var o = function() {};\n"
            + "    o.x = function() {\n"
            + "      alert(this == o);\n"
            + "      alert(arguments);\n"
            + "      alert(this.arguments);\n"
            + "    }\n"
            + "    o.f = function(x, y, z) {\n"
            + "      alert(this == o);\n"
            + "      alert(arguments);\n"
            + "      alert(this.arguments);\n"
            + "      this.x();\n"
            + "    }\n"
            + "    o.f('a', 'b');\n"
            + "  }\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"null", "function", "5"})
    public void onError() throws Exception {
        final String html
            = "<script>\n"
            + "  alert(window.onerror);\n"
            + "  window.onerror = function() { alert(arguments.length); };\n"
            + "  alert(typeof window.onerror);\n"
            + "  try { alert(undef); } catch(e) { /* caught, so won't trigger onerror */ }\n"
            + "  alert(undef);\n"
            + "</script>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                .getOptions().setThrowExceptionOnScriptError(false);
        }
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {
                "string string 7 number string",
                "string string 8 number object"},
            FF = {
                "string string 0 number string",
                "string string 0 number object"})
    @NotYetImplemented(FF)
    public void onErrorExceptionInstance() throws Exception {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    alert(typeof messageOrEvent + ' ' + typeof source + ' '"
                                + " + lineno + ' ' + typeof colno + ' ' + typeof error);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>throw 'string';</script>\n"
                + "<script>throw {'object':'property'};</script>\n"
                + "</html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                    .getOptions().setThrowExceptionOnScriptError(false);
        }
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"string string 7 number object", "string string 1 number object"})
    public void onErrorExceptionInstance2() throws Exception {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    alert(typeof messageOrEvent + ' ' + typeof source + ' '"
                                + " + lineno + ' ' + typeof colno + ' ' + typeof error);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>does.not.exist();</script>\n"
                + "<script>eval('syntax[error');</script>\n"
                + "</html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                    .getOptions().setThrowExceptionOnScriptError(false);
        }
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("success")
    public void onErrorModifyObject() throws Exception {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    error.property = 'success'\n"
                + "    alert(error.property);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>throw {};</script>\n"
                + "</html>";

        if (getWebDriver() instanceof HtmlUnitDriver) {
            getWebWindowOf((HtmlUnitDriver) getWebDriver()).getWebClient()
                    .getOptions().setThrowExceptionOnScriptError(false);
        }
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("rgb(0, 0, 0)")
    public void getComputedStyle() throws Exception {
        final String html = "<html><body>\n"
            + "<div id='myDiv'></div>\n"
            + "<script>\n"
            + "  var e = document.getElementById('myDiv');\n"
            + "  alert(window.getComputedStyle(e, null).color);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("rgb(255, 0, 0)")
    public void getComputedStyle_WithComputedColor() throws Exception {
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <style>div.x { color: red; }</style>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var e = document.getElementById('d');\n"
            + "    alert(window.getComputedStyle(e, '').color);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='d' class='x'>foo bar</div>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * JS code was throwing an exception due to the incorrect signature of getComputedStyle.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("rgb(0, 0, 0)")
    public void getComputedStyle_svg() throws Exception {
        final String html = "<html><body>\n"
            + "  <svg xmlns='http://www.w3.org/2000/svg' id='myId' version='1.1'></svg>\n"
            + "<script>\n"
            + "  var e = document.getElementById('myId');\n"
            + "  alert(window.getComputedStyle(e, null).color);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * The reason was that "top" evaluate to WindowProxy and "Object(top)" was setting the top scope as parentScope
     * of the WindowProxy which was setting it on the Window where it should always be null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("undefined")
    public void hangingObjectCallOnWindowProxy() throws Exception {
        final String html = "<html><body>\n"
            + "<iframe id='it'></iframe>;\n"
            + "<script>\n"
            + "  Object(top);\n"
            + "  alert(window.foo);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Was producing "TypeError: Object's getDefaultValue() method returned an object" due to Delegator not delegating
     * getDefaultValue(hint) to delegee when hint is null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("false")
    public void equalsString() throws Exception {
        final String html = "<html><body>\n"
            + "<script>\n"
            + "  alert('foo' == window);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Was producing "TypeError: Object's getDefaultValue() method returned an object" due to Delegator not delegating
     * getDefaultValue(hint) to delegee when hint is null.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("false")
    public void equalsInt() throws Exception {
        final String html = "<html><body>\n"
            + "<script>\n"
            + "  var i = 0;\n"
            + "  alert(i == window);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"number", "done", "result"})
    public void setTimeout() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var id = window.setTimeout( function() { log('result'); }, 20);\n"
            + "      log(typeof id);\n"
            + "      log('done');\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"number", "done", "42"})
    public void setTimeoutWithParams() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var id = window.setTimeout( function(p1) { log(p1); }, 20, 42);\n"
            + "      log(typeof id);\n"
            + "      log('done');\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"done 2", "7"})
    public void setTimeoutCode() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      try{\n"
            + "        var id = window.setTimeout('log(7)');\n"
            + "        log('done 2');\n"
            + "      } catch(e) { log(e); }\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("true")
    public void setTimeoutWrongParams() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      try{\n"
            + "        window.setTimeout();\n"
            + "        log('done');\n"
            + "      } catch(e) { log(e instanceof TypeError); }\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * As of 19.02.2013, a task started by setTimeout in an event handler could be executed before
     * all events handlers have been executed due to a missing synchronization.
     * @throws Exception if the test fails
     */
    @Test
    public void setTimeoutShouldNotBeExecutedBeforeHandlers() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "function stop() {\n"
            + "  window.stopIt = true;\n"
            + "}\n"
            + "for (var i = 0; i < 1000; i++) {\n"
            + "  var handler = function(e) {\n"
            + "    if (window.stopIt) {\n"
            + "      e.preventDefault ?  e.preventDefault() : e.returnValue = false;\n"
            + "    }\n"
            + "  }\n"
            + "  window.addEventListener('click', handler, false);\n"
            + "}\n"
            + "</script>\n"
            + "<form action='page2' method='post'>\n"
            + "<input id='it' type='submit' onclick='setTimeout(stop, 0)'>\n"
            + "</form>\n"
            + "</body></html>";
        getMockWebConnection().setDefaultResponse("Error: not found", 404, "Not Found", MimeType.TEXT_HTML);

        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("it")).click();

        assertEquals(URL_FIRST + "page2", driver.getCurrentUrl());
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"number", "done", "result"})
    public void setInterval() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    var id;\n"
            + "    function test() {\n"
            + "      id = window.setInterval( function() { log('result'); clearInterval(id); }, 20);\n"
            + "      log(typeof id);\n"
            + "      log('done');\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"number", "done", "42"})
    public void setIntervalWithParams() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    var id;\n"
            + "    function test() {\n"
            + "      id = window.setInterval( function(p1) { log(p1); clearInterval(id); }, 20, 42);\n"
            + "      log(typeof id);\n"
            + "      log('done');\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"done 2", "7"})
    public void setIntervalCode() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    var id;\n"
            + "    function test() {\n"
            + "      try{\n"
            + "        id = window.setInterval('log(7); clearInterval(id);' );\n"
            + "        log('done 2');\n"
            + "      } catch(e) { log(e); }\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("true")
    public void setIntervalWrongParams() throws Exception {
        final String html = "<html>\n"
            + "<head>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      try{\n"
            + "        window.setInterval();\n"
            + "        log('done');\n"
            + "      } catch(e) { log(e instanceof TypeError); }\n"
            + "    }\n"
            + "\n"
            + "    function log(x) {\n"
            + "      document.getElementById('log').value += x + '\\n';\n"
            + "    }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
            + "</body>\n"
            + "</html>\n";

        final WebDriver driver = loadPage2(html);
        Thread.sleep(200);
        final String text = driver.findElement(By.id("log")).getAttribute("value").trim().replaceAll("\r", "");
        assertEquals(String.join("\n", getExpectedAlerts()), text);
    }

    /**
     * As of 19.02.2013, a task started by setInterval in an event handler could be executed before
     * all events handlers have been executed due to a missing synchronization.
     * @throws Exception if the test fails
     */
    @Test
    public void setIntervalShouldNotBeExecutedBeforeHandlers() throws Exception {
        final String html
            = "<html><body>\n"
            + "<script>\n"
            + "  var id;\n"

            + "  function stop() {\n"
            + "    window.stopIt = true;\n"
            + "    clearInterval(id);\n"
            + "  }\n"

            + "  for (var i = 0; i < 1000; i++) {\n"
            + "    var handler = function(e) {\n"
            + "      if (window.stopIt) {\n"
            + "        e.preventDefault ?  e.preventDefault() : e.returnValue = false;\n"
            + "      }\n"
            + "    }\n"
            + "    window.addEventListener('click', handler, false);\n"
            + "  }\n"
            + "</script>\n"
            + "<form action='page2' method='post'>\n"
            + "  <input id='it' type='submit' onclick='id = setInterval(stop, 0)'>\n"
            + "</form>\n"
            + "</body></html>";
        getMockWebConnection().setDefaultResponse("Error: not found", 404, "Not Found", MimeType.TEXT_HTML);

        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("it")).click();

        assertEquals(URL_FIRST + "page2", driver.getCurrentUrl());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "null"})
    public void onchange_noHandler() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert('onchange' in window);\n"
            + "  alert(window.onchange);\n"
            + "</script></body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("changed")
    public void onchange_withHandler() throws Exception {
        final String html
            = "<html><body>\n"
            + "<input id='it'/>\n"
            + "<div id='tester'>Tester</div>\n"
            + "<script>\n"
            + "  window.onchange = function() {\n"
            + "    alert('changed');\n"
            + "  }\n"
            + "</script></body></html>";
        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("it")).sendKeys("X");
        driver.findElement(By.id("tester")).click();

        verifyAlerts(driver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "null"})
    public void onsubmit_noHandler() throws Exception {
        final String html
            = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<body>\n"
            + "<script>\n"
            + "  alert('onsubmit' in window);\n"
            + "  alert(window.onsubmit);\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("-onsubmit-")
    public void onsubmit_withHandler() throws Exception {
        final String html
            = HtmlPageTest.STANDARDS_MODE_PREFIX_
            + "<html>\n"
            + "<head>\n"
            + "  <title>Title</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<form>\n"
            + "  <input type='submit' id='it' value='submit' />\n"
            + "</form>\n"
            + "<script>\n"
            + "  window.onsubmit = function() {\n"
            + "    window.name = window.name + '-onsubmit-';\n" // hack
            + "  }\n"
            + "</script>\n"
            + "</body></html>";

        final WebDriver driver = loadPage2(html);
        driver.findElement(By.id("it")).click();

        // we can't use the usual alert here because of the page change
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        final Object result = jsExecutor.executeScript("return window.name");

        assertEquals(getExpectedAlerts()[0], result);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = {"type: message", "bubbles: false", "cancelable: false", "data: hello",
                "origin: ", "source: [object Window]", "lastEventId: "},
            IE = {"type: message", "bubbles: false", "cancelable: false", "data: hello",
                "origin: ", "source: [object Window]", "lastEventId: undefined"})
    public void postMessage() throws Exception {
        final String[] expectedAlerts = getExpectedAlerts();
        expectedAlerts[4] += "http://localhost:" + PORT;
        setExpectedAlerts(expectedAlerts);

        final String html
            = "<html>\n"
            + "<head><title>foo</title></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  function receiveMessage(event) {\n"
            + "    alert('type: ' + event.type);\n"
            + "    alert('bubbles: ' + event.bubbles);\n"
            + "    alert('cancelable: ' + event.cancelable);\n"
            + "    alert('data: ' + event.data);\n"
            + "    alert('origin: ' + event.origin);\n"
            + "    alert('source: ' + event.source);\n"
            + "    alert('lastEventId: ' + event.lastEventId);\n"
            + "  }\n"

            + "  window.addEventListener('message', receiveMessage, false);\n"
            + "</script>\n"
            + "  <iframe src='" + URL_SECOND + "'></iframe>\n"
            + "</body></html>";

        final String iframe = "<html><body><script>\n"
            + "  top.postMessage('hello', '*');\n"
            + "</script></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, iframe);
        loadPageWithAlerts2(html);
    }

    /**
     * Test for #1589 NullPointerException because of missing context.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("data: hello")
    public void postMessageFromClick() throws Exception {
        final String html
            = "<html>\n"
            + "<head><title>foo</title></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  function receiveMessage(event) {\n"
            + "    alert('data: ' + event.data);\n"
            + "  }\n"

            + "  window.addEventListener('message', receiveMessage, false);\n"
            + "</script>\n"
            + "  <iframe id='myFrame' src='" + URL_SECOND + "'></iframe>\n"
            + "</body></html>";

        final String iframe = "<html><body>\n"
            + "  <button id='clickme' onclick='top.postMessage(\"hello\", \"*\");'>Click me</a>\n"
            + "</body></html>";

        getMockWebConnection().setResponse(URL_SECOND, iframe);
        final WebDriver driver = loadPage2(html);
        driver.switchTo().frame("myFrame");
        driver.findElement(By.id("clickme")).click();

        verifyAlerts(driver, getExpectedAlerts());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("sync: false")
    public void postMessageSyncOrAsync() throws Exception {
        final String html
            = "<html>\n"
            + "<head><title>foo</title></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  var sync = true;\n"
            + "  function receiveMessage(event) {\n"
            + "    alert('sync: ' + sync);\n"
            + "  }\n"
            + "  window.addEventListener('message', receiveMessage, false);\n"
            + "</script>\n"
            + "  <iframe src='" + URL_SECOND + "'></iframe>\n"
            + "</body></html>";

        final String iframe = "<html><body><script>\n"
            + "  top.postMessage('hello', '*');\n"
            + "  top.sync = false;\n"
            + "</script></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, iframe);
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("posted received")
    public void postMessage_exactURL() throws Exception {
        postMessage(URL_FIRST.toExternalForm());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("posted received")
    public void postMessage_slash() throws Exception {
        postMessage("/");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("posted")
    public void postMessage_otherHost() throws Exception {
        postMessage("http://127.0.0.1:" + PORT + "/");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("posted")
    public void postMessage_otherPort() throws Exception {
        postMessage("http://localhost:" + (PORT + 1) + "/");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("posted")
    public void postMessage_otherProtocol() throws Exception {
        postMessage("https://localhost:" + PORT + "/");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void postMessage_invalidTargetOrigin() throws Exception {
        postMessage("abcdefg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("exception")
    public void postMessage_emptyTargetOrigin() throws Exception {
        postMessage("");
    }

    private void postMessage(final String url) throws Exception {
        final String html
            = "<html>\n"
            + "<head></head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  function receiveMessage(event) {\n"
            + "    document.title += ' received';\n"
            + "  }\n"
            + "  window.addEventListener('message', receiveMessage, false);\n"
            + "</script>\n"
            + "  <iframe src='" + URL_SECOND + "'></iframe>\n"
            + "</body></html>";

        final String iframe = "<html><body><script>\n"
            + "  try {\n"
            + "    top.postMessage('hello', '" + url + "');\n"
            + "    top.document.title += ' posted';\n"
            + "  } catch (e) {\n"
            + "    top.document.title += ' exception';\n"
            + "  }\n"
            + "</script></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, iframe);
        final WebDriver driver = loadPage2(html);

        assertTitle(driver, getExpectedAlerts()[0]);
    }

    /**
     * Regression test to reproduce a known bug.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("about:blank")
    public void openWindow_emptyUrl() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "var w = window.open('');\n"
            + "alert(w ? w.document.location : w);\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"true", "true", "true"})
    public void location() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "  alert(location === window.location);\n"
            + "  alert(location === document.location);\n"
            + "  alert(window.location === document.location);\n"
            + "</script></head>\n"
            + "<body></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setLocation() throws Exception {
        final String firstContent
            = "<html>\n"
            + "<head><title>First</title></head>\n"
            + "<body>\n"
            + "<form name='form1'>\n"
            + "  <a id='link' onClick='location=\"" + URL_SECOND + "\";'>Click me</a>\n"
            + "</form>\n"
            + "</body></html>";
        final String secondContent
            = "<html><head><title>Second</title></head><body></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, secondContent);

        final WebDriver driver = loadPage2(firstContent);
        assertTitle(driver, "First");
        assertEquals(1, driver.getWindowHandles().size());

        driver.findElement(By.id("link")).click();
        assertTitle(driver, "Second");

        assertEquals(1, driver.getWindowHandles().size());
        assertEquals(new String[] {"", "second/"}, getMockWebConnection().getRequestedUrls(URL_FIRST));
        assertEquals(URL_SECOND.toString(), driver.getCurrentUrl());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setWindowLocation() throws Exception {
        final String firstContent
            = "<html>\n"
            + "<head><title>First</title></head>\n"
            + "<body>\n"
            + "<form name='form1'>\n"
            + "  <a id='link' onClick='window.location=\"" + URL_SECOND + "\";'>Click me</a>\n"
            + "</form>\n"
            + "</body></html>";
        final String secondContent
            = "<html><head><title>Second</title></head><body></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, secondContent);

        final WebDriver driver = loadPage2(firstContent);
        assertTitle(driver, "First");
        assertEquals(1, driver.getWindowHandles().size());

        driver.findElement(By.id("link")).click();
        assertTitle(driver, "Second");

        assertEquals(1, driver.getWindowHandles().size());
        assertEquals(new String[] {"", "second/"}, getMockWebConnection().getRequestedUrls(URL_FIRST));
        assertEquals(URL_SECOND.toString(), driver.getCurrentUrl());
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void setDocumentLocation() throws Exception {
        final String firstContent
            = "<html>\n"
            + "<head><title>First</title></head>\n"
            + "<body>\n"
            + "<form name='form1'>\n"
            + "  <a id='link' onClick='document.location=\"" + URL_SECOND + "\";'>Click me</a>\n"
            + "</form>\n"
            + "</body></html>";
        final String secondContent
            = "<html><head><title>Second</title></head><body></body></html>";

        getMockWebConnection().setResponse(URL_SECOND, secondContent);

        final WebDriver driver = loadPage2(firstContent);
        assertTitle(driver, "First");
        assertEquals(1, driver.getWindowHandles().size());

        driver.findElement(By.id("link")).click();
        assertTitle(driver, "Second");

        assertEquals(1, driver.getWindowHandles().size());
        assertEquals(new String[] {"", "second/"}, getMockWebConnection().getRequestedUrls(URL_FIRST));
        assertEquals(URL_SECOND.toString(), driver.getCurrentUrl());
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"[object Window]", "[object Window]", ""},
            CHROME = {"[object Window]", "function Window() { [native code] }",
            "TEMPORARY, PERSISTENT, "},
            FF = {"[object Window]", "function Window() {\n    [native code]\n}", ""})
    public void enumeratedProperties() throws Exception {
        final String html
            = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var str = '';\n"
            + "    alert(window);\n"
            + "    alert(Window);\n"
            + "    var str = '';\n"
            + "    for (var i in Window) {\n"
            + "      str += i + ', ';\n"
            + "    }\n"
            + "    alert(str);\n"
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
    @Alerts(DEFAULT = "undefined",
            FF = "function")
    public void dump() throws Exception {
        final String html
            = "<html>\n"
            + "<body>\n"
            + "<script>\n"
            + "  alert(typeof window.dump);\n"
            + "</script>\n"
            + "</body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"function", "function"})
    public void requestAnimationFrame() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert(typeof window.requestAnimationFrame);\n"
            + "  alert(typeof window.cancelAnimationFrame);\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "function",
            CHROME = "undefined",
            FF60 = "undefined")
    public void showModalDialog() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert(typeof window.showModalDialog);\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "undefined",
            IE = "function")
    public void showModelessDialog() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert(typeof window.showModelessDialog);\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Another strange browser detection trick.
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "function",
            IE = "undefined")
    public void find() throws Exception {
        final String html
            = "<html><body><script>\n"
            + "  alert(typeof window.find);\n"
            + "</script></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test for Bug #1765.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("\"0.33\"")
    public void getComputedStylePseudo() throws Exception {
        final String html =
            "<html>\n"
            + "<head>\n"
            + "  <style>\n"
            + "    #mydiv:before {\n"
            + "      content: '0.33';\n"
            + "    }\n"
            + "  </style>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var div = document.getElementById('mydiv');\n"
            + "      alert(window.getComputedStyle(div, ':before').getPropertyValue('content'));\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='mydiv'></div>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * Test for Bug #1768.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("\"0.33\"")
    public void getComputedStylePseudoCache() throws Exception {
        final String html =
            "<html>\n"
            + "<head>\n"
            + "  <style>\n"
            + "    #mydiv:before {\n"
            + "      content: '0.33';\n"
            + "    }\n"
            + "  </style>\n"
            + "  <script>\n"
            + "    function test() {\n"
            + "      var div = document.getElementById('mydiv');\n"
            + "      div.getBoundingClientRect();\n"
            + "      alert(window.getComputedStyle(div, ':before').getPropertyValue('content'));\n"
            + "    }\n"
            + "  </script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "  <div id='mydiv'></div>\n"
            + "</body>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("inline")
    public void getComputedStyleElementDocument() throws Exception {
        final String html = "<html><head>\n"
            + "<style>\n"
            + "  tt { display: none; }\n"
            + "</style>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    var iframe = document.createElement('iframe');\n"
            + "    document.body.appendChild(iframe);\n"
            + "\n"
            + "    var doc = iframe.contentWindow.document;\n"
            + "    var tt = doc.createElement('tt');\n"
            + "    doc.body.appendChild(tt);\n"
            + "    alert(window.getComputedStyle(tt, null).display);\n"
            + "  }\n"
            + "</script></head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>\n";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"1", "false", "true", "false", "false"})
    public void in() throws Exception {
        final String html =
            "<html>\n"
            + "<script>\n"
            + "function test() {\n"
            + "  alert(window.length);\n"
            + "  alert(-1 in window);\n"
            + "  alert(0 in window);\n"
            + "  alert(1 in window);\n"
            + "  alert(42 in window);\n"
            + "}\n"
            + "</script>\n"
            + "<frameset rows='50,*' onload='test()'>\n"
            + "<frame name='frame1' src='about:blank'/>\n"
            + "</frameset>\n"
            + "</html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"a", "b"})
    public void calledTwice() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function info(msg) {\n"
            + "    alert(msg);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body>\n"
            + "<script>\n"
            + "  info('a');\n"
            + "  info('b');\n"
            + "</script>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("exception")
    public void constructor() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      alert(new Window());\n"
            + "    } catch(e) {alert('exception')}\n"
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
    @Alerts({"false", "false", "test2 alert"})
    public void objectCallOnFrameWindow() throws Exception {
        final String firstContent = "<html><head>\n"
                + "<script>\n"
                + "  function test1() {\n"
                + "    alert(window.frames[0].test2 === undefined);\n"
                + "    Object(window.frames[0]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <iframe src='" + URL_SECOND + "'></iframe>\n"
                + "</body></html>\n";
        final String secondContent = "<html><head>\n"
                + "<script>\n"
                + "  function test2() {\n"
                + "    alert('test2 alert');\n"
                + "  }\n"
                + "  window.top.test1();\n"
                + "  alert(test2 === undefined);\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test2()'>\n"
                + "</body></html>\n";

        getMockWebConnection().setResponse(URL_SECOND, secondContent);

        loadPageWithAlerts2(firstContent);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"[object Window]", "[object WindowProperties]", "[object EventTarget]", "[object Object]"},
            FF = {"[object WindowPrototype]", "[object WindowProperties]", "[object EventTargetPrototype]",
                "[object Object]"},
            IE = "exception")
    @NotYetImplemented
    public void test__proto__() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    try {\n"
            + "      for (var p = this.__proto__; p != null; p = p.__proto__) {\n"
            + "        alert(p);\n"
            + "      }\n"
            + "    } catch(e) {alert('exception')}\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts(DEFAULT = {"[object Navigator]", "##test##"},
              IE = {"[object Navigator]", "[object Navigator]"},
              FF = {"undefined", "##test##"})
    public void clientInformation() throws Exception {
        final String html = "<html><head>\n"
            + "<script>\n"
            + "  function test() {\n"
            + "    alert(window.clientInformation);\n"
            + "    window.clientInformation = '##test##';\n"
            + "    alert(window.clientInformation);\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'>\n"
            + "</body></html>";
        loadPageWithAlerts2(html);
    }
}

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
package com.gargoylesoftware.htmlunit.javascript.host.xml;

import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.IE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.HttpHeader;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.util.MimeType;

/**
 * Tests for Cross-Origin Resource Sharing for {@link XMLHttpRequest}.
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Ronald Brill
 * @author Frank Danek
 */
@RunWith(BrowserRunner.class)
public class XMLHttpRequestCORSTest extends WebDriverTestCase {

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"error [object ProgressEvent]", "error", "false", "0" /* "0" */},
            IE = {"error [object ProgressEvent]", "error", "true", "0" /* "4479" */})
    @NotYetImplemented(IE)
    public void noCorsHeaderCallsErrorHandler() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = '" + URL_THIRD + "';\n"
                + "    xhr.open('GET', url, true);\n"
                + "    xhr.onerror = function(event) {\n"
                + "                    alert('error ' + event);\n"
                + "                    alert(event.type);\n"
                + "                    alert(event.lengthComputable);\n"
                + "                    alert(event.loaded);\n"
                // + "                    alert(event.total);\n"
                + "                  };\n"
                + "    xhr.send();\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";
        getMockWebConnection().setDefaultResponse("Error: not found", 404, "Not Found", MimeType.TEXT_HTML);

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§"},
            IE = {"4", "200", "No Origin!"})
    public void simple() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/simple2';\n"
                + "    xhr.open('GET', url, false);\n"
                + "    xhr.send();\n"
                + "    alert(xhr.readyState);\n"
                + "    alert(xhr.status);\n"
                + "    alert(xhr.responseXML.firstChild.firstChild.nodeValue);\n"
                + "  } catch(e) { alert(e) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        SimpleServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "*";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/simple2", SimpleServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/simple1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts({"4", "200", "null"})
    @NotYetImplemented
    public void simpleHead() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/simple2';\n"
                + "    xhr.open('HEAD', url, false);\n"
                + "    xhr.send();\n"
                + "    alert(xhr.readyState);\n"
                + "    alert(xhr.status);\n"
                + "    alert(xhr.responseXML);\n"
                + "  } catch(e) { alert(e) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        SimpleServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "*";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/simple2", SimpleServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/simple1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§"},
            IE = {"4", "200", "No Origin!"})
    public void simplePost() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/simple2';\n"
                + "    xhr.open('POST', url, false);\n"
                + "    xhr.send('');\n"
                + "    alert(xhr.readyState);\n"
                + "    alert(xhr.status);\n"
                + "    alert(xhr.responseXML.firstChild.firstChild.nodeValue);\n"
                + "  } catch(e) { alert(e) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        SimpleServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "*";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/simple2", SimpleServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/simple1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "exception",
            IE = {"4", "200", "No Origin!"})
    public void simplePut() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/simple2';\n"
                + "    xhr.open('PUT', url, false);\n"
                + "    xhr.send('');\n"
                + "    alert(xhr.readyState);\n"
                + "    alert(xhr.status);\n"
                + "    alert(xhr.responseXML.firstChild.firstChild.nodeValue);\n"
                + "  } catch(e) { alert('exception') }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        SimpleServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "*";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/simple2", SimpleServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/simple1"));
    }

    /**
     * Simple CORS scenario Servlet.
     */
    public static class SimpleServerServlet extends HttpServlet {
        private static String ACCESS_CONTROL_ALLOW_ORIGIN_;
        /**
         * {@inheritDoc}
         */
        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            if (ACCESS_CONTROL_ALLOW_ORIGIN_ != null) {
                response.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN_);
            }
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(MimeType.TEXT_XML);
            String origin = request.getHeader(HttpHeader.ORIGIN);
            if (origin == null) {
                origin = "No Origin!";
            }
            response.getWriter().write("<origin>" + origin + "</origin>");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            doGet(request, response);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            doGet(request, response);
        }
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"exception", "4", "0"},
            IE = {"4", "200"})
    public void noAccessControlAllowOrigin() throws Exception {
        incorrectAccessControlAllowOrigin(null);
    }

    private void incorrectAccessControlAllowOrigin(final String header) throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/simple2';\n"
                + "    xhr.open('GET', url, false);\n"
                + "    xhr.send();\n"
                + "  } catch(e) { alert('exception') }\n"
                + "  alert(xhr.readyState);\n"
                + "  alert(xhr.status);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        SimpleServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = header;
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/simple2", SimpleServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/simple1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"exception", "4", "0"},
            IE = {"4", "200"})
    public void nonMatchingAccessControlAllowOrigin() throws Exception {
        incorrectAccessControlAllowOrigin("http://www.sourceforge.net");
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    public void preflight() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "text/plain");

        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will work on real Chrome if individually run, but will fail if run with other cases
    public void preflight_contentTypeWithCharset() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "text/plain;charset=utf-8");

        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will work on real Chrome if individually run, but will fail if run with other cases
    public void preflightUrlEncoded() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "application/x-www-form-urlencoded");

        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will work on real Chrome if individually run, but will fail if run with other cases
    public void preflightUrlEncoded_contentTypeWithCharset() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "application/x-www-form-urlencoded;charset=utf-8");

        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will work on real Chrome if individually run, but will fail if run with other cases
    public void preflightMultipart() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "multipart/form-data");

        releaseResources();
        shutDownAll();
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will work on real Chrome if individually run, but will fail if run with other cases
    public void preflightMultipart_contentTypeWithCharset() throws Exception {
        doPreflightTestAllowedMethods("POST, GET, OPTIONS", "multipart/form-data;charset=utf-8");

        releaseResources();
        shutDownAll();
    }

    /**
     * Seems that "Access-Control-Allow-Methods" is not considered by FF.
     *
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "§§URL§§", "§§URL§§", "GET", "x-pingother"},
            IE = {"4", "200", "null", "null", "null", "null"})
    //unstable test case, this will fail on real Chrome if individually run, but will succeed if run with other cases
    public void preflight_incorrect_methods() throws Exception {
        doPreflightTestAllowedMethods(null, "text/plain");

        releaseResources();
        shutDownAll();
    }

    private void doPreflightTestAllowedMethods(final String allowedMethods, final String contentType)
        throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT)); // url without trailing "/"

        final String html = "<html><head>\n"
            + "<script>\n"
            + "var xhr = new XMLHttpRequest();\n"
            + "function test() {\n"
            + "  try {\n"
            + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/preflight2';\n"
            + "    xhr.open('GET', url, false);\n"
            + "    xhr.setRequestHeader('X-PINGOTHER', 'pingpong');\n"
            + "    xhr.setRequestHeader('Content-Type' , '" + contentType + "');\n"
            + "    xhr.send();\n"
            + "    alert(xhr.readyState);\n"
            + "    alert(xhr.status);\n"
            + "    alert(xhr.responseXML.firstChild.childNodes[0].firstChild.nodeValue);\n"
            + "    alert(xhr.responseXML.firstChild.childNodes[1].firstChild.nodeValue);\n"
            + "    alert(xhr.responseXML.firstChild.childNodes[2].firstChild.nodeValue);\n"
            + "    alert(xhr.responseXML.firstChild.childNodes[3].firstChild.nodeValue);\n"
            + "  } catch(e) { alert(e) }\n"
            + "}\n"
            + "</script>\n"
            + "</head>\n"
            + "<body onload='test()'></body></html>";

        PreflightServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "http://localhost:" + PORT;
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_METHODS_ = allowedMethods;
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_HEADERS_ = "X-PINGOTHER";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/preflight2", PreflightServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/preflight1"));
    }

    /**
     * Preflight CORS scenario Servlet.
     */
    public static class PreflightServerServlet extends HttpServlet {
        private static String ACCESS_CONTROL_ALLOW_ORIGIN_;
        private static String ACCESS_CONTROL_ALLOW_METHODS_;
        private static String ACCESS_CONTROL_ALLOW_HEADERS_;
        private String options_origin_;
        private String options_method_;
        private String options_headers_;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void doOptions(final HttpServletRequest request, final HttpServletResponse response) {
            if (ACCESS_CONTROL_ALLOW_ORIGIN_ != null) {
                response.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN_);
            }
            if (ACCESS_CONTROL_ALLOW_METHODS_ != null) {
                response.setHeader("Access-Control-Allow-Methods", ACCESS_CONTROL_ALLOW_METHODS_);
            }
            if (ACCESS_CONTROL_ALLOW_HEADERS_ != null) {
                response.setHeader("Access-Control-Allow-Headers", ACCESS_CONTROL_ALLOW_HEADERS_);
            }
            options_origin_ = request.getHeader(HttpHeader.ORIGIN);
            options_method_ = request.getHeader("Access-Control-Request-Method");
            options_headers_ = request.getHeader("Access-Control-Request-Headers");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            if (ACCESS_CONTROL_ALLOW_ORIGIN_ != null) {
                response.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN_);
            }
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(MimeType.TEXT_XML);
            final Writer writer = response.getWriter();

            final String origin = request.getHeader(HttpHeader.ORIGIN);
            writer.write("<result>"
                + "<origin>" + origin + "</origin>"
                + "<options_origin>" + options_origin_ + "</options_origin>"
                + "<options_method>" + options_method_ + "</options_method>"
                + "<options_headers>" + options_headers_ + "</options_headers>"
                + "</result>");
        }
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"exception", "4", "0"},
            IE = {"4", "200"})
    //unstable test case, this will fail on real Chrome if individually run, but will succeed if run with other cases
    public void preflight_incorrect_headers() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/preflight2';\n"
                + "    xhr.open('GET', url, false);\n"
                + "    xhr.setRequestHeader('X-PINGOTHER', 'pingpong');\n"
                + "    xhr.send();\n"
                + "  } catch(e) { alert('exception') }\n"
                + "  alert(xhr.readyState);\n"
                + "  alert(xhr.status);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        PreflightServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "http://localhost:" + PORT;
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_METHODS_ = "POST, GET, OPTIONS";
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_HEADERS_ = null;
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/preflight2", PreflightServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/preflight1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = {"4", "200", "options_headers", "x-ping,x-pong"},
            IE = {"4", "200", "options_headers", "null"})
    public void preflight_many_header_values() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/preflight2';\n"
                + "    xhr.open('GET', url, false);\n"
                + "    xhr.setRequestHeader('X-PING', 'ping');\n"
                + "    xhr.setRequestHeader('X-PONG', 'pong');\n"
                + "    xhr.send();\n"
                + "  } catch(e) { alert('exception') }\n"
                + "  alert(xhr.readyState);\n"
                + "  alert(xhr.status);\n"
                + "  alert(xhr.responseXML.firstChild.childNodes[3].tagName);\n"
                + "  alert(xhr.responseXML.firstChild.childNodes[3].firstChild.nodeValue);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        PreflightServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = "http://localhost:" + PORT;
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_METHODS_ = "POST, GET, OPTIONS";
        PreflightServerServlet.ACCESS_CONTROL_ALLOW_HEADERS_ = "X-PING, X-PONG";
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/preflight2", PreflightServerServlet.class);
        startWebServer2(".", null, servlets2);

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/preflight1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts("false")
    public void withCredentials_defaultValue() throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/withCredentials2';\n"
                + "    xhr.open('GET', url, true);\n"
                + "    alert(xhr.withCredentials);\n"
                + "  } catch(e) { alert(e) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html, new URL(URL_FIRST, "/withCredentials1"));
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts({"false", "true", "false", "true"})
    public void withCredentials_setBeforeOpenSync() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    alert(xhr.withCredentials);\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = false;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=false') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.open('GET', '/foo.xml', false);\n"
                + "    } catch(e) { alert('ex: open') }\n"
                + "    alert(xhr.withCredentials);\n"
                + "  } catch(ex) { alert(ex) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts({"false", "true", "false", "true"})
    public void withCredentials_setBeforeOpenAsync() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    alert(xhr.withCredentials);\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = false;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=false') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.open('GET', '/foo.xml', true);\n"
                + "    } catch(e) { alert('ex: open') }\n"
                + "    alert(xhr.withCredentials);\n"
                + "  } catch(ex) { alert(ex) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts({"false", "false", "true", "false"})
    public void withCredentials_setAfterOpenSync() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    alert(xhr.withCredentials);\n"
                + "    xhr.open('GET', '/foo.xml', false);\n"
                + "    alert(xhr.withCredentials);\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = false;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=false') }\n"
                + "  } catch(ex) { alert(ex) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts({"false", "false", "true", "false"})
    public void withCredentials_setAfterOpenAsync() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    alert(xhr.withCredentials);\n"
                + "    xhr.open('GET', '/foo.xml', false);\n"
                + "    alert(xhr.withCredentials);\n"

                + "    try {\n"
                + "      xhr.withCredentials = true;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=true') }\n"

                + "    try {\n"
                + "      xhr.withCredentials = false;\n"
                + "      alert(xhr.withCredentials);\n"
                + "    } catch(e) { alert('ex: withCredentials=false') }\n"
                + "  } catch(ex) { alert(ex) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        loadPageWithAlerts2(html);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "1 0 4 0",
            IE = "1 0 4 200")
    public void withCredentials() throws Exception {
        testWithCredentials("*", "true");
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts("1 0 4 200")
    public void withCredentialsServer() throws Exception {
        testWithCredentials("http://localhost:" + PORT, "true");
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "1 0 4 0",
            IE = "1 0 4 200")
    public void withCredentialsServerSlashAtEnd() throws Exception {
        testWithCredentials(URL_FIRST.toExternalForm(), "true");
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "1 0 4 0",
            IE = "1 0 4 200")
    public void withCredentials_no_header() throws Exception {
        testWithCredentials("*", null);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "1 0 4 0",
            IE = "1 0 4 200")
    public void withCredentials_no_header_Server() throws Exception {
        testWithCredentials("http://localhost:" + PORT, null);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts(DEFAULT = "1 0 4 0",
            IE = "1 0 4 200")
    public void withCredentials_no_header_ServerSlashAtEnd() throws Exception {
        testWithCredentials(URL_FIRST.toExternalForm(), null);
    }

    private void testWithCredentials(final String accessControlAllowOrigin,
            final String accessControlAllowCredentials) throws Exception {
        expandExpectedAlertsVariables(new URL("http://localhost:" + PORT));

        final String html = "<html><head>\n"
                + "<script>\n"
                + "var xhr = new XMLHttpRequest();\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var url = 'http://' + window.location.hostname + ':" + PORT2 + "/withCredentials2';\n"
                + "    xhr.open('GET', url, true);\n"
                + "    xhr.withCredentials = true;\n"
                + "    xhr.onreadystatechange = onReadyStateChange;\n"
                + "    xhr.send();\n"
                + "  } catch(e) { document.title += ' ' + e }\n"
                + "  document.title += ' ' + xhr.readyState;\n"
                + "  try {\n"
                + "    document.title += ' ' + xhr.status;\n"
                + "  } catch(e) { document.title += ' ' + 'ex: status not available' }\n"

                + "  function onReadyStateChange() {\n"
                + "    if (xhr.readyState == 4) {\n"
                + "      document.title += ' ' + xhr.readyState;\n"
                + "      document.title += ' ' + xhr.status;\n"
                + "    }\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        WithCredentialsServerServlet.ACCESS_CONTROL_ALLOW_ORIGIN_ = accessControlAllowOrigin;
        WithCredentialsServerServlet.ACCESS_CONTROL_ALLOW_CREDENTIALS_ = accessControlAllowCredentials;
        final Map<String, Class<? extends Servlet>> servlets2 = new HashMap<>();
        servlets2.put("/withCredentials2", WithCredentialsServerServlet.class);
        startWebServer2(".", null, servlets2);

        final WebDriver driver = loadPage2(html, new URL(URL_FIRST, "/withCredentials1"));
        assertTitle(driver, getExpectedAlerts()[0]);
    }

    /**
     * @throws Exception if the test fails.
     */
    @Test
    @Alerts("done 200")
    public void testWithCredentialsIFrame() throws Exception {
        final String html = "<html><head>\n"
                + "<script>\n"

                + "function load() {\n"
                + "  try {\n"
                + "    var myContent = '<!DOCTYPE html><html><head></head><body>"
                            + "<script src=\"get.js\"><\\/script><p>tttttt</p></body></html>';\n"
                + "    window.asyncLoadIFrame = document.createElement('iframe');\n"
                + "    asyncLoadIFrame.id = 'asyncLoadIFrame';\n"
                + "    asyncLoadIFrame.src = 'about:blank';\n"
                + "    document.body.appendChild(asyncLoadIFrame);\n"

                + "    asyncLoadIFrame.contentWindow.document.open('text/html', 'replace');\n"
                + "    asyncLoadIFrame.contentWindow.document.write(myContent);\n"
                + "    asyncLoadIFrame.contentWindow.document.close();\n"
                + "  } catch(e) { alert(e) }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='load()'>\n"
                + "</body></html>";

        final String js = ""
                + "var xhr = new XMLHttpRequest();\n"
                + "  try {\n"
                + "    var url = '/data';\n"
                + "    xhr.open('GET', url, true);\n"
                + "    xhr.withCredentials = true;\n"
                + "    xhr.onreadystatechange = onReadyStateChange;\n"
                + "    xhr.send();\n"
                + "  } catch(e) { alert(e) }\n"

                + "  function onReadyStateChange() {\n"
                + "    if (xhr.readyState == 4) {\n"
                + "      alert('done ' + xhr.status);\n"
                + "    }\n"
                + "  }\n";

        getMockWebConnection().setDefaultResponse(js, MimeType.APPLICATION_JAVASCRIPT);
        final String xml = "<xml><content>blah</content></xml>";

        getMockWebConnection().setResponse(new URL(URL_FIRST, "/data"), xml, MimeType.TEXT_XML);

        loadPageWithAlerts2(html);
    }

    /**
     * CORS "With Credentials" scenario Servlet.
     */
    public static class WithCredentialsServerServlet extends HttpServlet {
        private static String ACCESS_CONTROL_ALLOW_ORIGIN_;
        private static String ACCESS_CONTROL_ALLOW_CREDENTIALS_;
        /**
         * {@inheritDoc}
         */
        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            if (ACCESS_CONTROL_ALLOW_ORIGIN_ != null) {
                response.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN_);
            }
            if (ACCESS_CONTROL_ALLOW_CREDENTIALS_ != null) {
                response.setHeader("Access-Control-Allow-Credentials", ACCESS_CONTROL_ALLOW_CREDENTIALS_);
            }
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(MimeType.TEXT_XML);
            String origin = request.getHeader(HttpHeader.ORIGIN);
            if (origin == null) {
                origin = "No Origin!";
            }
            response.getWriter().write("<origin>" + origin + "</origin>");
        }
    }

}

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
package com.gargoylesoftware.htmlunit;

import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.CHROME;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.FF;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.FF52;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.FF60;
import static com.gargoylesoftware.htmlunit.javascript.configuration.SupportedBrowser.IE;

import com.gargoylesoftware.htmlunit.javascript.configuration.BrowserFeature;
import com.gargoylesoftware.htmlunit.javascript.host.event.PopStateEvent;
import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;

/**
 * Constants of various features of each {@link BrowserVersion}.
 *
 * @author Ahmed Ashour
 * @author Marc Guillemot
 * @author Sudhan Moghe
 * @author Ronald Brill
 * @author Frank Danek
 * @author Carsten Steul
 */
public enum BrowserVersionFeatures {

    /**
     * If the "href" attribute of HtmlAnchor is defined but empty then IE interprets this as an empty filename.
     * Example: The page http://htmlunit.sourceforge.net/test/myFile.html contains an anchor with an empty
     * href attribute. Clicking the link in IE force the load of page http://htmlunit.sourceforge.net/test/.
     * In Firefox the URL is unchanged.
     */
    @BrowserFeature(IE)
    ANCHOR_EMPTY_HREF_NO_FILENAME,

    /** Ignore target when {@code href} is a javascript snippet. */
    @BrowserFeature(CHROME)
    ANCHOR_SEND_PING_REQUEST,

    /** Background image is 'initial'. */
    @BrowserFeature(CHROME)
    CSS_BACKGROUND_INITIAL,

    /** Background image is 'rgba(0, 0, 0, 0)'. */
    @BrowserFeature(FF60)
    CSS_BACKGROUND_RGBA,

    /** Computed {@code display} is {@code block} for non-attached elements. */
    @BrowserFeature(FF52)
    CSS_COMPUTED_BLOCK_IF_NOT_ATTACHED,

    /** Computed {@code zIndex} is not considered. */
    @BrowserFeature(CHROME)
    CSS_COMPUTED_NO_Z_INDEX,

    /** Is display style of HtmlDialog is 'none'. */
    @BrowserFeature({CHROME, FF60})
    CSS_DIALOG_NONE,

    /** Is display style 'block'. */
    @BrowserFeature(FF)
    CSS_DISPLAY_BLOCK,

    /** Is display style 'block'. */
    @BrowserFeature({CHROME, FF})
    CSS_DISPLAY_BLOCK2,

    /** {@code CSSFontFaceRule.cssText} uses {@code \r\n} to break lines. */
    @BrowserFeature(IE)
    CSS_FONTFACERULE_CSSTEXT_CRLF,

    /** {@code CSSFontFaceRule.cssText} has no {@code \n}. */
    @BrowserFeature(CHROME)
    CSS_FONTFACERULE_CSSTEXT_NO_CRLF,

    /** The default value of the display property for the 'input' tags is 'inline-block'. */
    @BrowserFeature({CHROME, IE})
    CSS_INPUT_DISPLAY_INLINE_BLOCK,

    /** The default value of the display property for the 'input' tags of type
     * radio or checkbox is 'inline-block'. */
    @BrowserFeature(FF60)
    CSS_INPUT_DISPLAY_RADIO_CHECKBOX_INLINE_BLOCK,

    /** 'initial' is a valid length value. */
    @BrowserFeature({CHROME, FF})
    CSS_LENGTH_INITIAL,

    /** Is display style of HtmlNoEmbed is 'inline'. */
    @BrowserFeature(CHROME)
    CSS_NOEMBED_INLINE,

    /** The default value of the display property for the 'noscript' tag is 'inline' instead of the default one. */
    @BrowserFeature(CHROME)
    CSS_NOSCRIPT_DISPLAY_INLINE,

    /** Unit is not required when setting outline-width style. */
    @BrowserFeature(IE)
    CSS_OUTLINE_WIDTH_UNIT_NOT_REQUIRED,

    /** The default value of the display property for the 'progress' tag is 'inline' instead of the default one. */
    @BrowserFeature(IE)
    CSS_PROGRESS_DISPLAY_INLINE,

    /** The default value of the display property for the 'rp' tag is 'none'. */
    @BrowserFeature(FF)
    CSS_RP_DISPLAY_NONE,

    /** The default value of the display property for the 'rt' tag is always 'ruby-text'. */
    @BrowserFeature({IE, FF60})
    CSS_RT_DISPLAY_RUBY_TEXT_ALWAYS,

    /** The default value of the display property for the 'ruby' tag is 'inline'. */
    @BrowserFeature(CHROME)
    CSS_RUBY_DISPLAY_INLINE,

    /** Throws exception on setting a CSS style value to null. */
    @BrowserFeature(IE)
    CSS_SET_NULL_THROWS,

    /** Internet Explorer versions 5 and later support the behavior property. The behavior property lets
     * you use CSS to attach a script to a specific element in order to implement
     * DHTML (Dynamic HTML) components.
     */
    @BrowserFeature(IE)
    CSS_SUPPORTS_BEHAVIOR_PROPERTY,

    /** 'auto' is supported when setting vertical-align style. */
    @BrowserFeature(IE)
    CSS_VERTICAL_ALIGN_SUPPORTS_AUTO,

    /** zIndex is of type Integer. Other values are ignored (''). */
    @BrowserFeature(IE)
    CSS_ZINDEX_TYPE_INTEGER,

    /** Add the 'Referer' header to a request triggered by window.showModalDialog. */
    @BrowserFeature({CHROME, FF})
    DIALOGWINDOW_REFERER,

    /** IE removes all child text nodes, but FF preserves the first. */
    @BrowserFeature(IE)
    DOM_NORMALIZE_REMOVE_CHILDREN,

    /** Indicates whether returnValue behaves HTML5-like with an empty string default. */
    @BrowserFeature({CHROME, FF})
    EVENT_BEFORE_UNLOAD_RETURN_VALUE_IS_HTML5_LIKE,

    /** Triggers the onfocus onfocusin blur onfocusout events in this order. */
    @BrowserFeature(CHROME)
    EVENT_FOCUS_FOCUS_IN_BLUR_OUT,

    /** Triggers the onfocusin onfocus onfocusout blur events in this order. */
    @BrowserFeature(IE)
    EVENT_FOCUS_IN_FOCUS_OUT_BLUR,

    /** Indicates whether returning 'null' from a property handler is meaningful. */
    @BrowserFeature(IE)
    EVENT_HANDLER_NULL_RETURN_IS_MEANINGFUL,

    /** Mouse events are triggered on disabled elements also. */
    @BrowserFeature(FF)
    EVENT_MOUSE_ON_DISABLED,

    /** Triggers "onchange" event handler after "onclick" event handler. */
    @BrowserFeature({CHROME, FF})
    EVENT_ONCHANGE_AFTER_ONCLICK,

    /** Triggers "onclick" event handler for the select only, not for the clicked option. */
    @BrowserFeature(IE)
    EVENT_ONCLICK_FOR_SELECT_ONLY,

    /** Triggers 'onclick' and 'ondblclick' event handler using <code>PointerEvent</code>. */
    @BrowserFeature(IE)
    EVENT_ONCLICK_USES_POINTEREVENT,

    /** <code>CloseEvent</code> can not be created by calling document.createEvent('CloseEvent'). */
    @BrowserFeature(FF)
    EVENT_ONCLOSE_DOCUMENT_CREATE_NOT_SUPPORTED,

    /** <code>CloseEvent</code> initCloseEvent is available but throws an exception when called. */
    @BrowserFeature(FF)
    EVENT_ONCLOSE_INIT_CLOSE_EVENT_THROWS,

    /** <code>Event.bubbles</code> and <code>Event.cancelable</code> are false in 'onhashchange' event handler. */
    @BrowserFeature({CHROME, IE})
    EVENT_ONHASHCHANGE_BUBBLES_FALSE,

    /** Triggers "onload" event if internal javascript loaded. */
    @BrowserFeature(IE)
    EVENT_ONLOAD_INTERNAL_JAVASCRIPT,

    /** MessageEvent default data value is null. */
    @BrowserFeature({CHROME, FF})
    EVENT_ONMESSAGE_DEFAULT_DATA_NULL,

    /** Does not trigger "onmousedown" event handler for the select options. */
    @BrowserFeature(IE)
    EVENT_ONMOUSEDOWN_FOR_SELECT_OPTION_TRIGGERS_ADDITIONAL_DOWN_FOR_SELECT,

    /** Does not trigger "onmousedown" event handler for the select options. */
    @BrowserFeature(IE)
    EVENT_ONMOUSEDOWN_NOT_FOR_SELECT_OPTION,

    /** FF triggers a mouseover event even if the option is disabled. */
    @BrowserFeature(FF)
    EVENT_ONMOUSEOVER_FOR_DISABLED_OPTION,

    /** IE never triggers a mouseover event for select options. */
    @BrowserFeature(IE)
    EVENT_ONMOUSEOVER_NEVER_FOR_SELECT_OPTION,

    /** Does not trigger "onmousedown" event handler for the select options. */
    @BrowserFeature(IE)
    EVENT_ONMOUSEUP_FOR_SELECT_OPTION_TRIGGERS_ADDITIONAL_UP_FOR_SELECT,

    /** Does not trigger "onmouseup" event handler for the select options. */
    @BrowserFeature(IE)
    EVENT_ONMOUSEUP_NOT_FOR_SELECT_OPTION,

    /** <code>PopStateEvent</code> can not be created by calling document.createEvent('PopStateEvent'). */
    @BrowserFeature(FF60)
    EVENT_ONPOPSTATE_DOCUMENT_CREATE_NOT_SUPPORTED,

    /** Supports event type 'BeforeUnloadEvent'. */
    @BrowserFeature({CHROME, FF})
    EVENT_TYPE_BEFOREUNLOADEVENT,

    /** Supports event type 'FocusEvent'. */
    @BrowserFeature({CHROME, FF60, IE})
    EVENT_TYPE_FOCUSEVENT,

    /** Supports event type 'HashChangeEvent'. */
    @BrowserFeature({CHROME, FF})
    EVENT_TYPE_HASHCHANGEEVENT,

    /** Supports vendor specific event type 'KeyEvents'. */
    @BrowserFeature(FF)
    EVENT_TYPE_KEY_EVENTS,

    /** Supports vendor specific event type 'MouseWheelEvent'. */
    @BrowserFeature(IE)
    EVENT_TYPE_MOUSEWHEELEVENT,

    /** Supports event type 'PointerEvent'. */
    @BrowserFeature(IE)
    EVENT_TYPE_POINTEREVENT,

    /** Supports event type 'ProgressEvent'. */
    @BrowserFeature(IE)
    EVENT_TYPE_PROGRESSEVENT,

    /** Supports event type 'SVGZoomEvent'. */
    @BrowserFeature(FF52)
    EVENT_TYPE_SVGZOOMEVENT,

    /** Supports event type 'WheelEvent'. */
    @BrowserFeature({CHROME, IE})
    EVENT_TYPE_WHEELEVENT,

    /** For new pages the focus points to the body node. */
    @BrowserFeature(IE)
    FOCUS_BODY_ELEMENT_AT_START,

    /** Indicates if a form field is directly reachable by its new name once this has been changed. */
    @BrowserFeature({CHROME, FF})
    FORMFIELD_REACHABLE_BY_NEW_NAMES,

    /** Indicates if a form field is directly reachable by its original name once this has been changed. */
    @BrowserFeature({CHROME, FF})
    FORMFIELD_REACHABLE_BY_ORIGINAL_NAME,

    /** Form elements are able to refer to the for by using the from attribute. */
    @BrowserFeature({CHROME, FF})
    FORM_FORM_ATTRIBUTE_SUPPORTED,

    /** Form formxxx parameters not supported for input type image. */
    @BrowserFeature(IE)
    FORM_PARAMETRS_NOT_SUPPORTED_FOR_IMAGE,

    /** Form submit forces a real request also if only the hash was changed. */
    @BrowserFeature(CHROME)
    FORM_SUBMISSION_DOWNLOWDS_ALSO_IF_ONLY_HASH_CHANGED,

    /** Form submit includes the Cache-Control: max-age=0 header. */
    @BrowserFeature(CHROME)
    FORM_SUBMISSION_HEADER_CACHE_CONTROL_MAX_AGE,

    /** Form submit includes the Cache-Control: cn-cache header. */
    @BrowserFeature(IE)
    FORM_SUBMISSION_HEADER_CACHE_CONTROL_NO_CACHE,

    /** Form submit includes the origin header. */
    @BrowserFeature(CHROME)
    FORM_SUBMISSION_HEADER_ORIGIN,

    /** Form submit is done without the hash part of the action url. */
    @BrowserFeature(IE)
    FORM_SUBMISSION_URL_WITHOUT_HASH,

    /** */
    @BrowserFeature(IE)
    HTMLABBREVIATED,

    /** HtmlAllCollection.item returns null instead of undefined if an element was not found. */
    @BrowserFeature({IE, FF})
    HTMLALLCOLLECTION_DO_NOT_CONVERT_STRINGS_TO_NUMBER,

    /** HtmlAllCollection.item(int) is not supported. */
    @BrowserFeature(FF)
    HTMLALLCOLLECTION_DO_NOT_SUPPORT_PARANTHESES,

    /** HtmlAllCollection.item(int) requires int parameter. */
    @BrowserFeature(CHROME)
    HTMLALLCOLLECTION_INTEGER_INDEX,

    /** HtmlCollection returns the first hit instead of a collection if many elements found. */
    @BrowserFeature(IE)
    HTMLALLCOLLECTION_NO_COLLECTION_FOR_MANY_HITS,

    /** HtmlAllCollection.namedItem returns null instead of undefined if an element was not found. */
    @BrowserFeature({CHROME, FF})
    HTMLALLCOLLECTION_NULL_IF_NAMED_ITEM_NOT_FOUND,

    /** Should {@link com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBaseFontElement#isEndTagForbidden}. */
    @BrowserFeature(FF)
    HTMLBASEFONT_END_TAG_FORBIDDEN,

    /** Base tag href attribute is empty if not defined. */
    @BrowserFeature(IE)
    HTMLBASE_HREF_DEFAULT_EMPTY,

    /** HtmlCollection.item() supports also doubles as index. */
    @BrowserFeature({IE, FF})
    HTMLCOLLECTION_ITEM_FUNCT_SUPPORTS_DOUBLE_INDEX_ALSO,

    /** HtmlCollection.item[] supports also doubles as index. */
    @BrowserFeature(IE)
    HTMLCOLLECTION_ITEM_SUPPORTS_DOUBLE_INDEX_ALSO,

    /** HtmlCollection.item searches by id also. */
    @BrowserFeature(IE)
    HTMLCOLLECTION_ITEM_SUPPORTS_ID_SEARCH_ALSO,

    /** HtmlCollection.namedItem searches by id first. */
    @BrowserFeature(CHROME)
    HTMLCOLLECTION_NAMED_ITEM_ID_FIRST,

    /** HtmlCollection returns null instead of undefined if an element was not found. */
    @BrowserFeature({CHROME, FF})
    HTMLCOLLECTION_NULL_IF_NOT_FOUND,

    /** HtmlAllCollection(int) is not supported. */
    @BrowserFeature(IE)
    HTMLCOLLECTION_SUPPORTS_PARANTHESES,

    /** Is the default display style {@code inline} for quirks mode. */
    @BrowserFeature(FF)
    HTMLDEFINITION_INLINE_IN_QUIRKS,

    /** {@code document.applets} returns a NodeList. */
    @BrowserFeature(FF60)
    HTMLDOCUMENT_APPLETS_NODELIST,

    /** Is {@code document.charset} lower-case. */
    @BrowserFeature(IE)
    HTMLDOCUMENT_CHARSET_LOWERCASE,

    /** Do document.bgColor/.alinkColor/.vlinkColor/.linkColor have value by default. */
    @BrowserFeature(IE)
    HTMLDOCUMENT_COLOR,

    /**
    /** {@code document.getElementsByName} returns an empty list if called with the empty string.
     */
    @BrowserFeature(FF60)
    HTMLDOCUMENT_ELEMENTS_BY_NAME_EMPTY,

    /** We can used function in detached documents. */
    @BrowserFeature(IE)
    HTMLDOCUMENT_FUNCTION_DETACHED,

    /** Calls to <code>document.XYZ</code> also looks at frames. */
    @BrowserFeature({IE, CHROME})
    HTMLDOCUMENT_GET_ALSO_FRAMES,

    /** Calls to <code>document.XYZ</code> looks at children with the specified ID and/or name. */
    @BrowserFeature(IE)
    HTMLDOCUMENT_GET_FOR_ID_AND_OR_NAME,

    /**
     * Calls to <code>document.XYZ</code> should first look at standard functions before looking at elements
     * named <code>XYZ</code>.
     */
    @BrowserFeature(IE)
    HTMLDOCUMENT_GET_PREFERS_STANDARD_FUNCTIONS,

    /** Allows invalid 'align' values. */
    @BrowserFeature({CHROME, FF})
    HTMLELEMENT_ALIGN_INVALID,

    /** Detaching the active element from the dom tree triggers no keyup event. */
    @BrowserFeature(IE)
    HTMLELEMENT_DETACH_ACTIVE_TRIGGERS_NO_KEYUP_EVENT,

    /** Removing the active element from the dom tree triggers the onblur event. */
    @BrowserFeature(CHROME)
    HTMLELEMENT_REMOVE_ACTIVE_TRIGGERS_BLUR_EVENT,

    /** Handle blank source like empty. */
    @BrowserFeature({IE, CHROME})
    HTMLIMAGE_BLANK_SRC_AS_EMPTY,

    /** Empty src attribute sets display to false. */
    @BrowserFeature({IE, FF})
    HTMLIMAGE_EMPTY_SRC_DISPLAY_FALSE,

    /** Is document.cretaeElement('image') an HTMLElement. */
    @BrowserFeature(FF)
    HTMLIMAGE_HTMLELEMENT,

    /** Is document.cretaeElement('image') an HTMLUnknownElement. */
    @BrowserFeature(CHROME)
    HTMLIMAGE_HTMLUNKNOWNELEMENT,

    /** Mark the image as invisible if no src attribute defined. */
    @BrowserFeature({CHROME, FF})
    HTMLIMAGE_INVISIBLE_NO_SRC,

    /** Clicking an image input submits the value as param if defined. */
    @BrowserFeature(CHROME)
    HTMLIMAGE_NAME_VALUE_PARAMS,

    /** When clicking a {@code checkbox} or {@code radio} input the surrounding anchor is not clicked. */
    @BrowserFeature(CHROME)
    HTMLINPUT_CHECKBOX_DOES_NOT_CLICK_SURROUNDING_ANCHOR,

    /** When clicking a input the surrounding anchor is not clicked. */
    @BrowserFeature(IE)
    HTMLINPUT_DOES_NOT_CLICK_SURROUNDING_ANCHOR,

    /** HTMLInputElement: {@code files} to be {@code undefined}. */
    @BrowserFeature(IE)
    HTMLINPUT_FILES_UNDEFINED,

    /** HTMLInputElement: type {@code file} selectionSart/End are null. */
    @BrowserFeature({CHROME, FF})
    HTMLINPUT_FILE_SELECTION_START_END_NULL,

    /** HTMLInputElement: type {@code file} value to be {@code fakepath}. */
    @BrowserFeature({CHROME, FF60, IE})
    HTMLINPUT_FILE_VALUE_FAKEPATH,

    /** HTMLInputElement date and time types are supported. */
    @BrowserFeature({CHROME, FF60})
    HTMLINPUT_TYPE_DATETIME_SUPPORTED,

    /** HTMLInputElement date and time types are supported. */
    @BrowserFeature(FF60)
    HTMLINPUT_TYPE_MONTH_NOT_SUPPORTED,

    /** Should the HTMLElement of {@code keygen} have no end tag. */
    @BrowserFeature(IE)
    HTMLKEYGEN_END_TAG_FORBIDDEN,

    /** If the type is present for a link only use if type is text/css. */
    @BrowserFeature(CHROME)
    HTMLLINK_CHECK_TYPE_FOR_STYLESHEET,

    /** */
    @BrowserFeature(IE)
    HTMLOPTION_PREVENT_DISABLED,

    /** Removing the selected attribute, de selects the option. */
    @BrowserFeature({CHROME, FF})
    HTMLOPTION_REMOVE_SELECTED_ATTRIB_DESELECTS,

    /** Trims the value of the type attribute before to verify it. */
    @BrowserFeature({CHROME, FF})
    HTMLSCRIPT_TRIM_TYPE,

    /** Setting defaultValue updates the value also. */
    @BrowserFeature({CHROME, FF})
    HTMLTEXTAREA_SET_DEFAULT_VALUE_UPDATES_VALUE,

    /** When calculation the value of a text area ie uses a recursive approach. */
    @BrowserFeature(IE)
    HTMLTEXTAREA_USE_ALL_TEXT_CHILDREN,

    /** Should {@link com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTrackElement#isEndTagForbidden}. */
    @BrowserFeature({FF, IE})
    HTMLTRACK_END_TAG_FORBIDDEN,

    /** HTML attributes are always lower case. */
    @BrowserFeature({CHROME, FF})
    HTML_ATTRIBUTE_LOWER_CASE,

    /** Expand #0 to #000000. */
    @BrowserFeature(IE)
    HTML_COLOR_EXPAND_ZERO,

    /** Do not allow anything invalid in color, but restrict to valid values (names and hex digits) only. */
    @BrowserFeature(IE)
    HTML_COLOR_RESTRICT,

    /** Convert the color (name and hex code) to lower case. */
    @BrowserFeature(IE)
    HTML_COLOR_TO_LOWER,

    /** HTML parser supports the 'command' tag. */
    @BrowserFeature({CHROME, IE})
    HTML_COMMAND_TAG,

    /** HTML parser supports the 'isindex' tag. */
    @BrowserFeature({CHROME, FF60})
    HTML_ISINDEX_TAG,

    /** HTML parser supports the 'main' tag. */
    @BrowserFeature(IE)
    HTML_MAIN_TAG,

    /** Supports &lt;object&gt; {@code classid} attribute. */
    @BrowserFeature(IE)
    HTML_OBJECT_CLASSID,

    /** Additionally support dates in format "d/M/yyyy". */
    @BrowserFeature(FF)
    HTTP_COOKIE_EXTENDED_DATE_PATTERNS_1,

    /** Dates format pattern 2. */
    @BrowserFeature(CHROME)
    HTTP_COOKIE_EXTENDED_DATE_PATTERNS_2,

    /** Indicates that the path is extracted from the location.
     * Sample: from the location /foo/boo only /foo is used.
     */
    @BrowserFeature(IE)
    HTTP_COOKIE_EXTRACT_PATH_FROM_LOCATION,

    /** domain '.org' is handled as 'org'. */
    @BrowserFeature({FF, IE})
    HTTP_COOKIE_REMOVE_DOT_FROM_ROOT_DOMAINS,

    /** Indicates that the start date for two digits cookies is 1970
     * instead of 2000 (Two digits years are interpreted as 20xx
     * if before 1970 and as 19xx otherwise).
     */
    @BrowserFeature({CHROME, FF})
    HTTP_COOKIE_START_DATE_1970,

    /** Browser sends Upgrade-Insecure-Requests header. */
    @BrowserFeature({CHROME, FF})
    HTTP_HEADER_UPGRADE_INSECURE_REQUEST,

    /** Supports redirect via 308 code. */
    @BrowserFeature({CHROME, FF})
    HTTP_REDIRECT_308,

    /** Setting the property align to arbitrary values is allowed. */
    @BrowserFeature({CHROME, FF})
    JS_ALIGN_ACCEPTS_ARBITRARY_VALUES,

    /** Setting the property align of an input element ignores the value
     * if the value is one of center, justify, left or right.
     * For all other values an exception is still thrown.
     */
    @BrowserFeature(IE)
    JS_ALIGN_FOR_INPUT_IGNORES_VALUES,

    /**
     * Javascript property anchors includes all anchors with a name or an id property.
     * If not set name property is required.
     */
    @BrowserFeature(IE)
    JS_ANCHORS_REQUIRES_NAME_OR_ID,

    /** The anchor pathname detects url's starting with one letter as file url's. */
    @BrowserFeature({CHROME, IE})
    JS_ANCHOR_PATHNAME_DETECT_WIN_DRIVES_URL,

    /** The anchor pathname property returns nothing for broken http(s) url's. */
    @BrowserFeature({CHROME, FF60})
    JS_ANCHOR_PATHNAME_NONE_FOR_BROKEN_URL,

    /** The anchor pathname property returns nothing for none http(s) url's. */
    @BrowserFeature(FF)
    JS_ANCHOR_PATHNAME_NONE_FOR_NONE_HTTP_URL,

    /** The anchor pathname prefixes file url's with '/'. */
    @BrowserFeature({CHROME, IE})
    JS_ANCHOR_PATHNAME_PREFIX_WIN_DRIVES_URL,

    /** The anchor protocol property returns ':' for broken http(s) url's. */
    @BrowserFeature(CHROME)
    JS_ANCHOR_PROTOCOL_COLON_FOR_BROKEN_URL,

    /** The anchor protocol property converts drive letters to uppercase. */
    @BrowserFeature(CHROME)
    JS_ANCHOR_PROTOCOL_COLON_UPPER_CASE_DRIVE_LETTERS,

    /** The anchor protocol property returns 'http' for broken http(s) url's. */
    @BrowserFeature(FF60)
    JS_ANCHOR_PROTOCOL_HTTP_FOR_BROKEN_URL,

    /** Indicates that "someFunction.arguments" is a read-only view of the function's argument. */
    @BrowserFeature({CHROME, FF})
    JS_ARGUMENTS_READ_ONLY_ACCESSED_FROM_FUNCTION,

    /** Indicates that the {@code Array} supports construction properties. */
    @BrowserFeature(FF)
    JS_ARRAY_CONSTRUCTION_PROPERTIES,

    /** Indicates that Array.from() is supported. */
    @BrowserFeature({CHROME, FF})
    JS_ARRAY_FROM,

    /** firstChild and lastChild returns null for Attr (like IE does). */
    @BrowserFeature({CHROME, FF})
    JS_ATTR_FIRST_LAST_CHILD_RETURNS_NULL,

    /** HTMLBGSoundElement reported as HTMLUnknownElement. */
    @BrowserFeature(FF)
    JS_BGSOUND_AS_UNKNOWN,

    /** Body {@code margin} is 8px. */
    @BrowserFeature(IE)
    JS_BODY_MARGINS_8,

    /** HtmlElement.getBoundingClientRect throws an error if the element is not attached to the page. */
    @BrowserFeature(IE)
    JS_BOUNDINGCLIENTRECT_THROWS_IF_DISCONNECTED,

    /** toDataURL for canvas returns the CHROME version of the PNG. */
    @BrowserFeature(CHROME)
    JS_CANVAS_DATA_URL_CHROME_PNG,

    /** toDataURL for canvas returns the IE version of the PNG. */
    @BrowserFeature(IE)
    JS_CANVAS_DATA_URL_IE_PNG,

    /** Do not allow invalid clear values. */
    @BrowserFeature(IE)
    JS_CLEAR_RESTRICT,

    /** ClientHeight for input is 17. */
    @BrowserFeature(CHROME)
    JS_CLIENTHIGHT_INPUT_17,

    /** ClientRectList toString reports the first item. */
    @BrowserFeature(FF)
    JS_CLIENTRECTLIST_DEFAUL_VALUE_FROM_FIRST,

    /** ClientRectList.item throws instead of returning null if an element was not found. */
    @BrowserFeature(IE)
    JS_CLIENTRECTLIST_THROWS_IF_ITEM_NOT_FOUND,

    /** ClientWidth for text/password input is 143. */
    @BrowserFeature(IE)
    JS_CLIENTWIDTH_INPUT_TEXT_143,

    /** ClientWidth for text/password input is 169. */
    @BrowserFeature(CHROME)
    JS_CLIENTWIDTH_INPUT_TEXT_169,

    /** Is window can be used as Console. */
    @BrowserFeature({CHROME, FF})
    JS_CONSOLE_HANDLE_WINDOW,

    /** item is enumerated before length property of CSSRuleList. */
    @BrowserFeature(FF)
    JS_CSSRULELIST_ENUM_ITEM_LENGTH,

    /** <code>Date.toLocaleDateString()</code> returns a short form (d.M.yyyy). */
    @BrowserFeature({CHROME, FF})
    JS_DATE_LOCALE_DATE_SHORT,

    /** {@link DateTimeFormat} uses the Unicode Character {@code 'LEFT-TO-RIGHT MARK'}. */
    @BrowserFeature(IE)
    JS_DATE_WITH_LEFT_TO_RIGHT_MARK,

    /** */
    @BrowserFeature(IE)
    JS_DEFERRED,

    /** Javascript doctyp.entities returns null (FF10). */
    @BrowserFeature(IE)
    JS_DOCTYPE_ENTITIES_NULL,

    /** Javascript doctyp.notations returns null (FF10). */
    @BrowserFeature(IE)
    JS_DOCTYPE_NOTATIONS_NULL,

    /** Indicates that document.createAttribute converts the local name to lowercase. */
    @BrowserFeature({CHROME, FF})
    JS_DOCUMENT_CREATE_ATTRUBUTE_LOWER_CASE,

    /** The browser supports the design mode 'Inherit'. */
    @BrowserFeature(IE)
    JS_DOCUMENT_DESIGN_MODE_INHERIT,

    /** Javascript document.forms(...) supported. */
    @BrowserFeature(IE)
    JS_DOCUMENT_FORMS_FUNCTION_SUPPORTED,

    /** The browser has selection {@code rangeCount}. */
    @BrowserFeature({FF, IE})
    JS_DOCUMENT_SELECTION_RANGE_COUNT,

    /** Javascript property document.domain doesn't allow to set domain of {@code about:blank}. */
    @BrowserFeature(IE)
    JS_DOCUMENT_SETTING_DOMAIN_THROWS_FOR_ABOUT_BLANK,

    /** createHTMLDucument requires a title. */
    @BrowserFeature(IE)
    JS_DOMIMPLEMENTATION_CREATE_HTMLDOCOMENT_REQUIRES_TITLE,

    /** If document.implementation.hasFeature() supports 'Core 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CORE_3,

    /** If document.implementation.hasFeature() supports 'CSS2 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS2_1,

    /** If document.implementation.hasFeature() supports 'CSS2 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS2_3,

    /** If document.implementation.hasFeature() supports 'CSS3 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS3_1,

    /** If document.implementation.hasFeature() supports 'CSS3 2.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS3_2,

    /** If document.implementation.hasFeature() supports 'CSS3 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS3_3,

    /** If document.implementation.hasFeature() supports 'CSS 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS_1,

    /** If document.implementation.hasFeature() supports 'CSS 2.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS_2,

    /** If document.implementation.hasFeature() supports 'CSS 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_CSS_3,

    /** If document.implementation.hasFeature() supports 'Events 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_EVENTS_1,

    /** If document.implementation.hasFeature() supports 'KeyboardEvents'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_KEYBOARDEVENTS,

    /** If document.implementation.hasFeature() supports 'LS'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_LS,

    /** If document.implementation.hasFeature() supports 'MutationNameEvents'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_MUTATIONNAMEEVENTS,

    /** If document.implementation.hasFeature() supports 'Range 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_RANGE_1,

    /** If document.implementation.hasFeature() supports 'Range 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_RANGE_3,

    /** If document.implementation.hasFeature() supports 'StyleSheets 2.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_STYLESHEETS,

    /** If document.implementation.hasFeature() supports 'http://www.w3.org/TR/SVG11/feature#BasicStructure 1.2'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_SVG_BASICSTRUCTURE_1_2,

    /** If document.implementation.hasFeature() supports 'MutationNameEvents'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_TEXTEVENTS,

    /** If document.implementation.hasFeature() supports 'UIEvents 2.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_UIEVENTS_2,

    /** If document.implementation.hasFeature() supports 'Validation'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_VALIDATION,

    /** If document.implementation.hasFeature() supports 'Views 1.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_VIEWS_1,

    /** If document.implementation.hasFeature() supports 'Views 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_VIEWS_3,

    /** If document.implementation.hasFeature() supports 'XPath 3.0'. */
    @BrowserFeature({CHROME, FF})
    JS_DOMIMPLEMENTATION_FEATURE_XPATH,

    /** <code>DOMParser.parseFromString(..)</code> handles an empty String as error. */
    @BrowserFeature({CHROME, FF})
    JS_DOMPARSER_EMPTY_STRING_IS_ERROR,

    /** <code>DOMParser.parseFromString(..)</code> throws an exception if an error occurs. */
    @BrowserFeature(IE)
    JS_DOMPARSER_EXCEPTION_ON_ERROR,

    /** {@code DOMParser.parseFromString(..)} creates a document containing a {@code parsererror} element. */
    @BrowserFeature({CHROME, FF})
    JS_DOMPARSER_PARSERERROR_ON_ERROR,

    /** DOMTokenList returns false instead of throwing an exception when receiver is blank. */
    @BrowserFeature({CHROME, FF})
    JS_DOMTOKENLIST_CONTAINS_RETURNS_FALSE_FOR_BLANK,

    /** DOMTokenList uses an enhanced set of whitespace chars. */
    @BrowserFeature(IE)
    JS_DOMTOKENLIST_ENHANCED_WHITESPACE_CHARS,

    /** DOMTokenList index access returns null if index is outside. */
    @BrowserFeature(IE)
    JS_DOMTOKENLIST_GET_NULL_IF_OUTSIDE,

    /** DOMTokenList ignores duplicates when determining the length. */
    @BrowserFeature({CHROME, FF60})
    JS_DOMTOKENLIST_LENGTH_IGNORES_DUPLICATES,

    /** DOMTokenList removed all whitespace chars during add. */
    @BrowserFeature({CHROME, FF60})
    JS_DOMTOKENLIST_REMOVE_WHITESPACE_CHARS_ON_ADD,

    /** DOMTokenList removed all whitespace chars during edit. */
    @BrowserFeature({CHROME, FF60, IE})
    JS_DOMTOKENLIST_REMOVE_WHITESPACE_CHARS_ON_EDIT,

    /** DOMTokenList removed all whitespace chars during remove. */
    @BrowserFeature({CHROME, FF})
    JS_DOMTOKENLIST_REMOVE_WHITESPACE_CHARS_ON_REMOVE,

    /** Javascript property function {@code delete} throws an exception if the given count is negative. */
    @BrowserFeature(IE)
    JS_DOM_CDATA_DELETE_THROWS_NEGATIVE_COUNT,

    /** Indicates that attributeNS returns an empty string instead of null if not found. */
    @BrowserFeature(IE)
    JS_ELEMENT_GET_ATTRIBUTE_RETURNS_EMPTY,

    /** The Enumerator constructor throws an exception if called with HtmlCollections as parameter. */
    @BrowserFeature(IE)
    JS_ENUMERATOR_CONSTRUCTOR_THROWS,

    /** Javascript {@code Error.captureStackTrace}. */
    @BrowserFeature(CHROME)
    JS_ERROR_CAPTURE_STACK_TRACE,

    /** Javascript {@code Error.stack}. */
    @BrowserFeature({CHROME, FF})
    JS_ERROR_STACK,

    /** Javascript {@code Error.stackTraceLimit}. */
    @BrowserFeature({CHROME, IE})
    JS_ERROR_STACK_TRACE_LIMIT,

    /** Javascript event.keyCode and event.charCode distinguish between printable and not printable keys. */
    @BrowserFeature(FF)
    JS_EVENT_DISTINGUISH_PRINTABLE_KEY,

    /** Whether {@code FileReader} includes content type or not. */
    @BrowserFeature(FF)
    JS_FILEREADER_CONTENT_TYPE,

    /** Whether {@code FileReader} includes {@code base64} for empty content or not. */
    @BrowserFeature(IE)
    JS_FILEREADER_EMPTY_NULL,

    /** FF uses a different date format for file.lastModifiedDate. */
    @BrowserFeature(FF)
    JS_FILE_SHORT_DATE_FORMAT,

    /** Indicates that the action property will not be expanded if defined as empty string. */
    @BrowserFeature(FF52)
    JS_FORM_ACTION_EXPANDURL_IGNORE_EMPTY,

    /** Indicates that the action property will not be expanded if defined as empty string. */
    @BrowserFeature({CHROME, FF60})
    JS_FORM_ACTION_EXPANDURL_NOT_DEFINED,

    /** form.dispatchEvent(e) submits the form if the event is of type 'submit'. */
    @BrowserFeature(FF)
    JS_FORM_DISPATCHEVENT_SUBMITS,

    /** Setting form.encoding only allowed for valid encodings. */
    @BrowserFeature(IE)
    JS_FORM_REJECT_INVALID_ENCODING,

    /** Calling form.submit() twice forces double download. */
    @BrowserFeature(IE)
    JS_FORM_SUBMIT_FORCES_DOWNLOAD,

    /** Support for document.formName('inputName'). */
    @BrowserFeature(IE)
    JS_FORM_USABLE_AS_FUNCTION,

    /** Indicates if the method toSource exists on the native objects. */
    @BrowserFeature(FF)
    JS_FUNCTION_TOSOURCE,

    /** HTMLElement instead of HTMLUnknownElement for elements with hyphen ('-'). */
    @BrowserFeature({CHROME, FF})
    JS_HTML_HYPHEN_ELEMENT_CLASS_NAME,

    /** HTMLElement instead of HTMLUnknownElement for ruby elements. */
    @BrowserFeature({CHROME, FF})
    JS_HTML_RUBY_ELEMENT_CLASS_NAME,

    /** Executes the {@code onload} handler, regardless of the whether the element was already attached to the page. */
    @BrowserFeature({FF, IE})
    JS_IFRAME_ALWAYS_EXECUTE_ONLOAD,

    /** Ignore the last line containing uncommented. */
    @BrowserFeature(IE)
    JS_IGNORES_LAST_LINE_CONTAINING_UNCOMMENTED,

    /** Ignore the UTF8 BOM header when loading external js in some situations. */
    @BrowserFeature(IE)
    JS_IGNORES_UTF8_BOM_SOMETIMES,

    /**
     * The complete property returns also true, if the image download was failing
     * or if there was no src at all.
     */
    @BrowserFeature({CHROME, FF})
    JS_IMAGE_COMPLETE_RETURNS_TRUE_FOR_NO_REQUEST,

    /**
     * Is the prototype of {@link com.gargoylesoftware.htmlunit.javascript.host.html.Image} the same as
     * {@link com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement}.
     */
    @BrowserFeature({FF, IE})
    JS_IMAGE_PROTOTYPE_SAME_AS_HTML_IMAGE,

    /**
     * Getting the width and height of an image tag with an empty source returns 0x0.
     */
    @BrowserFeature({CHROME, FF60})
    JS_IMAGE_WIDTH_HEIGHT_EMPTY_SOURCE_RETURNS_0x0,

    /**
     * Getting the width and height of an image tag without a source returns 16x16;
     * for invalid values returns 0.
     */
    @BrowserFeature(CHROME)
    JS_IMAGE_WIDTH_HEIGHT_RETURNS_16x16_0x0,

    /**
     * Getting the width and height of an image tag without a source returns 24x24;
     * for invalid values returns 0x0.
     */
    @BrowserFeature(FF)
    JS_IMAGE_WIDTH_HEIGHT_RETURNS_24x24_0x0,

    /**
     * Getting the width and height of an image tag without a source returns 28x30;
     * for invalid values returns same.
     */
    @BrowserFeature(IE)
    JS_IMAGE_WIDTH_HEIGHT_RETURNS_28x30_28x30,

    /** Indicates that innerHTML adds the child also for null values. */
    @BrowserFeature(IE)
    JS_INNER_HTML_ADD_CHILD_FOR_NULL_VALUE,

    /** Indicates that innerHTML uses {@code lf} instead of {@code lf}. */
    @BrowserFeature(IE)
    JS_INNER_TEXT_LF,

    /** Indicates that innerText setter supports null values. */
    @BrowserFeature({CHROME, FF})
    JS_INNER_TEXT_VALUE_NULL,

    /** Ignore negative selection starts. */
    @BrowserFeature({CHROME, FF60})
    JS_INPUT_IGNORE_NEGATIVE_SELECTION_START,

    /** Chrome/FF returns null for selectionStart/selectionEnd. */
    @BrowserFeature({CHROME, FF})
    JS_INPUT_NUMBER_SELECTION_START_END_NULL,

    /** Setting the type property of an input converts the type to lowercase. */
    @BrowserFeature(IE)
    JS_INPUT_SET_TYPE_LOWERCASE,

    /** Setting the value of an Input Date will check for correct format. */
    @BrowserFeature({CHROME, FF60})
    JS_INPUT_SET_VALUE_DATE_SUPPORTED,

    /** Setting the value of an Input Email to blank will result in an empty value. */
    @BrowserFeature({CHROME, FF})
    JS_INPUT_SET_VALUE_EMAIL_TRIMMED,

    /** Setting the value of an Input Text/Password/TextArea resets the selection. */
    @BrowserFeature(IE)
    JS_INPUT_SET_VALUE_MOVE_SELECTION_TO_START,

    /** Setting the value of an Input URL to blank will result in an empty value. */
    @BrowserFeature({CHROME, FF})
    JS_INPUT_SET_VALUE_URL_TRIMMED,

    /** Indicates that Intl.v8BreakIterator is supported. */
    @BrowserFeature(CHROME)
    JS_INTL_V8_BREAK_ITERATOR,

    /** Supports Iterator and StopIteration. */
    @BrowserFeature(FF52)
    JS_Iterator,

    /** Property form for label always returns null. */
    @BrowserFeature({CHROME, FF})
    JS_LABEL_FORM_NULL,

    /** location.hash returns an encoded hash. */
    @BrowserFeature({CHROME, FF})
    JS_LOCATION_HASH_HASH_IS_ENCODED,

    /**
     * Set this property if the browser evaluates<br>
     * window.location.hash to #&uuml; (like Firefox)<br>
     * for url 'http://localhost/something/#%C3%BC'.<br>
     * IE evaluates to #%C3%BC.
     */
    @BrowserFeature({CHROME, FF})
    JS_LOCATION_HASH_IS_DECODED,

    /**
     * Property location.hash returns '#' for urls ending with a hash
     * sign (e.g. http://localhost/something/#).
     */
    @BrowserFeature(IE)
    JS_LOCATION_HASH_RETURNS_HASH_FOR_EMPTY_DEFINED,

    /**
     * Set this property if the browser evaluates<br>
     * window.location.hash to #%C3%BC; (like Firefox)<br>
     * for url 'http://localhost/something/#&uuml;'.<br>
     * IE evaluates to #&uuml;.
     */
    @BrowserFeature({CHROME, FF})
    JS_LOCATION_HREF_HASH_IS_ENCODED,

    /** Reload sends a referrer header. */
    @BrowserFeature(CHROME)
    JS_LOCATION_RELOAD_REFERRER,

    /** Indicates that an empty media list is represented by the string 'all'. */
    @BrowserFeature(IE)
    JS_MEDIA_LIST_ALL,

    /** Indicates that an empty media list is represented by the string 'all'. */
    @BrowserFeature({CHROME, FF})
    JS_MEDIA_LIST_EMPTY_STRING,

    /** Type property of menu has always '' as value. */
    @BrowserFeature(IE)
    JS_MENU_TYPE_EMPTY,

    /** Type property of menu returns the current (maybe invalid) value. */
    @BrowserFeature(FF60)
    JS_MENU_TYPE_PASS,

    /** Indicates if the String representation of a native function is without newline. */
    @BrowserFeature(CHROME)
    JS_NATIVE_FUNCTION_TOSTRING_COMPACT,

    /** Indicates if the String representation of a native function begins and ends with a {@code \n}.*/
    @BrowserFeature(IE)
    JS_NATIVE_FUNCTION_TOSTRING_NEW_LINE,

    /** Indicates if the String representation of a native function has a newline for empty parameter list. */
    @BrowserFeature(FF60)
    JS_NATIVE_FUNCTION_TOSTRING_NL,

    /** Navigator.doNotTrack returns unspecified if not set. */
    @BrowserFeature(FF)
    JS_NAVIGATOR_DO_NOT_TRACK_UNSPECIFIED,

    /** <code>Node.contains</code> returns false instead of throwing an exception. */
    @BrowserFeature(IE)
    JS_NODE_CONTAINS_RETURNS_FALSE_FOR_INVALID_ARG,

    /** The reference argument of <code>Node.insertBefore(..)</code> is optional. */
    @BrowserFeature(IE)
    JS_NODE_INSERT_BEFORE_REF_OPTIONAL,

    /** Children are enumerated. */
    @BrowserFeature(IE)
    JS_NODE_LIST_ENUMERATE_CHILDREN,

    /** Functions are enumerated. */
    @BrowserFeature({CHROME, FF})
    JS_NODE_LIST_ENUMERATE_FUNCTIONS,

    /** Indicates that Object.getOwnPropertySymbols() is supported. */
    @BrowserFeature({CHROME, FF})
    JS_OBJECT_GET_OWN_PROPERTY_SYMBOLS,

    /** Indicates that someObj.offsetParent returns null, it someObj has fixed style. */
    @BrowserFeature({CHROME, IE})
    JS_OFFSET_PARENT_NULL_IF_FIXED,

    /** element.outerHTML handles null value as string "null". */
    @BrowserFeature(IE)
    JS_OUTER_HTML_NULL_AS_STRING,

    /** element.outerHTML removes all children from detached node. */
    @BrowserFeature(IE)
    JS_OUTER_HTML_REMOVES_CHILDREN_FOR_DETACHED,

    /** element.outerHTML removes all children from detached node. */
    @BrowserFeature(CHROME)
    JS_OUTER_HTML_THROWS_FOR_DETACHED,

    /** Indicates that HTMLPhraseElements returning 'HTMLElement'
     * as class name. */
    @BrowserFeature(FF)
    JS_PHRASE_COMMON_CLASS_NAME,

    /** Indicates that the {@link PopStateEvent}.{@code state} is cloned. */
    @BrowserFeature({CHROME, IE})
    JS_POP_STATE_EVENT_CLONE_STATE,

    /** Indicates that the {@code pre.width} is string. */
    @BrowserFeature(IE)
    JS_PRE_WIDTH_STRING,

    /** Indicates that the {@code Object.getOwnPropertyDescriptor.get} contains name. */
    @BrowserFeature({FF, IE})
    JS_PROPERTY_DESCRIPTOR_NAME,

    /** Indicates that the {@code Object.getOwnPropertyDescriptor.get} starts with a new line. */
    @BrowserFeature(IE)
    JS_PROPERTY_DESCRIPTOR_NEW_LINE,

    /** Support {@code Reflect}. */
    @BrowserFeature({CHROME, FF})
    JS_REFLECT,

    /** <code>RegExp.lastParen</code> returns an empty string if the RegExp has too many groups. */
    @BrowserFeature(IE)
    JS_REGEXP_EMPTY_LASTPAREN_IF_TOO_MANY_GROUPS,

    /** RegExp group <code>$0</code> returns the whole previous match (see {@link java.util.regex.Matcher#group()}. */
    @BrowserFeature(IE)
    JS_REGEXP_GROUP0_RETURNS_WHOLE_MATCH,

    /**
     * Javascript script tags handles a 204 (no content) response for the src
     * attrib as error.
     */
    @BrowserFeature(IE)
    JS_SCRIPT_HANDLE_204_AS_ERROR,

    /** Javascript script tags supports the 'for' and the 'event' attribute. */
    @BrowserFeature(IE)
    JS_SCRIPT_SUPPORTS_FOR_AND_EVENT_WINDOW,

    /** Javascript selectorText property returns selectors in lower case. */
    @BrowserFeature({CHROME, IE})
    JS_SELECTOR_TEXT_LOWERCASE,

    /** Indicates that setting the value to null has no effect. */
    @BrowserFeature({CHROME, FF})
    JS_SELECT_FILE_THROWS,

    /** When expanding the collection by setting the length don't add
     * an empty text node. */
    @BrowserFeature({CHROME, IE})
    JS_SELECT_OPTIONS_DONT_ADD_EMPTY_TEXT_CHILD_WHEN_EXPANDING,

    /** Indicates that select.options has a wong class name. */
    @BrowserFeature(IE)
    JS_SELECT_OPTIONS_HAS_SELECT_CLASS_NAME,

    /** Ignore negative value when setting the length. */
    @BrowserFeature({CHROME, FF52})
    JS_SELECT_OPTIONS_IGNORE_NEGATIVE_LENGTH,

    /** The 'in' operator returns always true for HtmlOptionsCollection. */
    @BrowserFeature(IE)
    JS_SELECT_OPTIONS_IN_ALWAYS_TRUE,

    /** Indicates that select.options returns null if requested index is outside. */
    @BrowserFeature(IE)
    JS_SELECT_OPTIONS_NULL_FOR_OUTSIDE,

    /** Indicates that select.options.remove ignores the call if index is too large. */
    @BrowserFeature(CHROME)
    JS_SELECT_OPTIONS_REMOVE_IGNORE_IF_INDEX_NEGATIVE,

    /** Indicates that select.options.remove ignores the call if index is too large. */
    @BrowserFeature({CHROME, IE})
    JS_SELECT_OPTIONS_REMOVE_IGNORE_IF_INDEX_TOO_LARGE,

    /** Indicates that select.options[i] throws an exception if the requested index is neagtive. */
    @BrowserFeature(IE)
    JS_SELECT_OPTIONS_REMOVE_THROWS_IF_NEGATIV,

    /** Indicates that select.options.remove ignores the call if index is too large. */
    @BrowserFeature(FF)
    JS_SELECT_REMOVE_IGNORE_IF_INDEX_OUTSIDE,

    /** Indicates that select.value = 'val' only checks the value attribute and
     * not the option text. */
    @BrowserFeature(IE)
    JS_SELECT_SET_VALUES_CHECKS_ONLY_VALUE_ATTRIBUTE,

    /** Whether to get any property from the items first. */
    @BrowserFeature(IE)
    JS_STORAGE_GET_FROM_ITEMS,

    /** Whether to add to the storage even preserved words. */
    @BrowserFeature({FF, IE})
    JS_STORAGE_PRESERVED_INCLUDED,

    /** Stylesheet list contains only active style sheets. */
    @BrowserFeature(CHROME)
    JS_STYLESHEETLIST_ACTIVE_ONLY,

    /** Indicates if style.setProperty ignores case when determining the priority. */
    @BrowserFeature({CHROME, IE})
    JS_STYLE_SET_PROPERTY_IMPORTANT_IGNORES_CASE,

    /** IE supports accessing unsupported style elements via getter
     * like val = elem.style.htmlunit;.
     */
    @BrowserFeature(IE)
    JS_STYLE_UNSUPPORTED_PROPERTY_GETTER,

    /** Indicates if style properties are always in lower case. */
    @BrowserFeature({CHROME, FF60, IE})
    JS_STYLE_VALUES_LOWERCASE,

    /** Indicates wordSpacing support percent values. */
    @BrowserFeature(FF)
    JS_STYLE_WORD_SPACING_ACCEPTS_PERCENT,

    /** Indicates that trying to access the style property with a wrong index returns undefined
     * instead of "". */
    @BrowserFeature({CHROME, FF})
    JS_STYLE_WRONG_INDEX_RETURNS_UNDEFINED,

    /** Supports Symbol. */
    @BrowserFeature({CHROME, FF})
    JS_SYMBOL,

    /** The width cell height does not return negative values. */
    @BrowserFeature(IE)
    JS_TABLE_CELL_HEIGHT_DOES_NOT_RETURN_NEGATIVE_VALUES,

    /** The width cell offset calculation takes border into account. */
    @BrowserFeature(IE)
    JS_TABLE_CELL_OFFSET_INCLUDES_BORDER,

    /** The width cell property does not return negative values. */
    @BrowserFeature(IE)
    JS_TABLE_CELL_WIDTH_DOES_NOT_RETURN_NEGATIVE_VALUES,

    /** The width column property does not return negative values. */
    @BrowserFeature(IE)
    JS_TABLE_COLUMN_WIDTH_NO_NEGATIVE_VALUES,

    /** The width column property has a value of 'null' for null. */
    @BrowserFeature({CHROME, FF})
    JS_TABLE_COLUMN_WIDTH_NULL_STRING,

    /** Calling deleteCell without an index throws an exception. */
    @BrowserFeature({CHROME, FF})
    JS_TABLE_ROW_DELETE_CELL_REQUIRES_INDEX,

    /** Set span zo zero if provided value is invalid. */
    @BrowserFeature(CHROME)
    JS_TABLE_SPAN_SET_ZERO_IF_INVALID,

    /** Throws an exception if the value for column span is less than one. */
    @BrowserFeature(IE)
    JS_TABLE_SPAN_THROWS_EXCEPTION_IF_INVALID,

    /** Indicates that table elements supports the values "top", "bottom", "middle", "baseline". */
    @BrowserFeature(IE)
    JS_TABLE_VALIGN_SUPPORTS_IE_VALUES,

    /** Getting the property maxLength if it is not defined in the DOM returns MAX_INT.
     * FF and Chrome return -1.
     */
    @BrowserFeature(IE)
    JS_TEXT_AREA_GET_MAXLENGTH_MAX_INT,

    /** Setting the property cols throws an exception, if the provided value is less than 0.
     * FF ignores the provided value in this case.
     */
    @BrowserFeature(IE)
    JS_TEXT_AREA_SET_COLS_NEGATIVE_THROWS_EXCEPTION,

    /** Setting the property cols throws an exception, if the provided value is not convertible into an integer.
     * FF ignores the provided value in this case and sets cols to 0.
     */
    @BrowserFeature(IE)
    JS_TEXT_AREA_SET_COLS_THROWS_EXCEPTION,

    /** Setting the property {@code maxLength} throws an exception, if the provided value is less than 0. */
    @BrowserFeature({CHROME, FF})
    JS_TEXT_AREA_SET_MAXLENGTH_NEGATIVE_THROWS_EXCEPTION,

    /** Setting the property {@code rows} throws an exception, if the provided value is less than 0. */
    @BrowserFeature(IE)
    JS_TEXT_AREA_SET_ROWS_NEGATIVE_THROWS_EXCEPTION,

    /** Setting the property rows throws an exception, if the provided value is not convertible into an integer.
     * FF ignores the provided value in this case and sets rows to 0.
     */
    @BrowserFeature(IE)
    JS_TEXT_AREA_SET_ROWS_THROWS_EXCEPTION,

    /** Setting the value processes null as null value. */
    @BrowserFeature({CHROME, FF})
    JS_TEXT_AREA_SET_VALUE_NULL,

    /** Indicates that <code>TreeWalker.expandEntityReferences</code> is always {@code false}. */
    @BrowserFeature({CHROME, FF})
    JS_TREEWALKER_EXPAND_ENTITY_REFERENCES_FALSE,

    /**
     * Indicates that the filter to be used by the TreeWalker has to be a function (so no object with a method
     * <code>acceptNode(..)</code> is supported).
     */
    @BrowserFeature(IE)
    JS_TREEWALKER_FILTER_FUNCTION_ONLY,

    /** Setting the property align to arbitrary values is allowed. */
    @BrowserFeature({CHROME, FF})
    JS_TYPE_ACCEPTS_ARBITRARY_VALUES,

    /** Allow inheriting parent constants
     * in {@link com.gargoylesoftware.htmlunit.javascript.host.event.WebGLContextEvent}. */
    @BrowserFeature({CHROME, FF})
    JS_WEBGL_CONTEXT_EVENT_CONSTANTS,

    /** Setting the property width/height to arbitrary values is allowed. */
    @BrowserFeature({CHROME, FF})
    JS_WIDTH_HEIGHT_ACCEPTS_ARBITRARY_VALUES,

    /**
     * The window.ActiveXObject is special in IE
     * http://msdn.microsoft.com/en-us/library/ie/dn423948%28v=vs.85%29.aspx.
     */
    @BrowserFeature(IE)
    JS_WINDOW_ACTIVEXOBJECT_HIDDEN,

    /** Changing the opener of a window to something not null and not a window is not valid. */
    @BrowserFeature(IE)
    JS_WINDOW_CHANGE_OPENER_ONLY_WINDOW_OBJECT,

    /** window.getComputedStyle works with pseudo selectors without colon in front. */
    @BrowserFeature(CHROME)
    JS_WINDOW_COMPUTED_STYLE_PSEUDO_ACCEPT_WITHOUT_COLON,

    /** <code>window.name</code> returns also form fields (e.g. input, textarea). */
    @BrowserFeature(IE)
    JS_WINDOW_FORMFIELDS_ACCESSIBLE_BY_NAME,

    /** Support for accessing the frame of a window by id additionally to using the name (FF). */
    @BrowserFeature(IE)
    JS_WINDOW_FRAMES_ACCESSIBLE_BY_ID,

    /** <code>window..frames['id']</code> returns the frame window instead of the frame element. */
    @BrowserFeature(IE)
    JS_WINDOW_FRAME_BY_ID_RETURNS_WINDOW,

    /**
     * Difference of window.outer/inner height is 89.
     */
    @BrowserFeature(CHROME)
    JS_WINDOW_OUTER_INNER_HEIGHT_DIFF_136,

    /**
     * Difference of window.outer/inner height is 63.
     */
    @BrowserFeature(IE)
    JS_WINDOW_OUTER_INNER_HEIGHT_DIFF_63,

    /**
     * Difference of window.outer/inner height is 94.
     */
    @BrowserFeature(FF)
    JS_WINDOW_OUTER_INNER_HEIGHT_DIFF_94,

    /** Window.getSelection returns null, if the window is not visible. */
    @BrowserFeature(FF)
    JS_WINDOW_SELECTION_NULL_IF_INVISIBLE,

    /** Window.top property is writable. */
    @BrowserFeature(IE)
    JS_WINDOW_TOP_WRITABLE,

    /** Supports XML. */
    @BrowserFeature({CHROME, FF})
    JS_XML,

    /** XMLDocument: .getElementsByTagName() to search the nodes by their local name. */
    @BrowserFeature(IE)
    JS_XML_GET_ELEMENTS_BY_TAG_NAME_LOCAL,

    /** XMLDocument: .getElementById() to return any element, not HTML specifically. */
    @BrowserFeature({CHROME, FF})
    JS_XML_GET_ELEMENT_BY_ID__ANY_ELEMENT,

    /** Indicates that new XMLSerializer().serializeToString(..) inserts a blank before self-closing a tag. */
    @BrowserFeature(IE)
    JS_XML_SERIALIZER_BLANK_BEFORE_SELF_CLOSING,

    /**
     * Indicates that new XMLSerializer().serializeToString(..) called with a document fragment created by an
     * HTMLPage always returns ''.
     */
    @BrowserFeature(IE)
    JS_XML_SERIALIZER_HTML_DOCUMENT_FRAGMENT_ALWAYS_EMPTY,

    /** Indicates that <code>XMLSerializer.serializeToString(..)</code> serializes a single CDataSection as escaped
     * text instead of <code>&lt;![CDATA[xxx]]&gt;</code>. */
    @BrowserFeature(IE)
    JS_XML_SERIALIZER_ROOT_CDATA_AS_ESCAPED_TEXT,

    /** Indicates that the browser uses the ActiveXObject for implementing XML support. */
    @BrowserFeature(IE)
    JS_XML_SUPPORT_VIA_ACTIVEXOBJECT,

    /** With special keys [in .type(int)], should we trigger onkeypress event or not. */
    @BrowserFeature(FF)
    KEYBOARD_EVENT_SPECIAL_KEYPRESS,

    /** Handle {@code <keygen>} as {@code <block>}. */
    @BrowserFeature(IE)
    KEYGEN_AS_BLOCK,

    /** Handle {@code <keygen>} as {@code <select>}. */
    @BrowserFeature(FF)
    KEYGEN_AS_SELECT,

    /**
     * Indicates that the browser considers the meta X-UA-Compatible when determining
     * compatibility/quirks mode.
     */
    @BrowserFeature(IE)
    META_X_UA_COMPATIBLE,

    /**
     * The default display style of multicol is 'block'.
     */
    @BrowserFeature(FF)
    MULTICOL_BLOCK,

    /** */
    @BrowserFeature(IE)
    PAGE_SELECTION_RANGE_FROM_SELECTABLE_TEXT_INPUT,

    /** Wait for the whole page to load before initializing bodies for frames. */
    @BrowserFeature(IE)
    PAGE_WAIT_LOAD_BEFORE_BODY,

    /** Indicates <code>.querySelectorAll()</code> and <code>.querySelector()</code> is not supported in quirks mode. */
    @BrowserFeature(IE)
    QUERYSELECTORALL_NOT_IN_QUIRKS,

    /** IE throws a syntax error if a css3 pseudo selector is used on a detached node. */
    @BrowserFeature(IE)
    QUERYSELECTOR_CSS3_PSEUDO_REQUIRE_ATTACHED_NODE,

    /** Set the value attribute of a reset input to 'Reset' if no value attribute specified. */
    @BrowserFeature(IE)
    RESETINPUT_DEFAULT_VALUE_IF_VALUE_NOT_DEFINED,

    /** The default display style of slot is 'content'. */
    @BrowserFeature({CHROME, FF60})
    SLOT_CONTENTS,

    /** Indicates that string.includes() is supported. */
    @BrowserFeature({CHROME, FF})
    STRING_INCLUDES,

    /** Indicates that string.startsWith() and .endWith() are supported. */
    @BrowserFeature({CHROME, FF})
    STRING_REPEAT,

    /** Indicates that string.startsWith() and .endWith() are supported. */
    @BrowserFeature({CHROME, FF})
    STRING_STARTS_ENDS_WITH,

    /** Indicates that string.trimLeft() and .trimRight() are supported. */
    @BrowserFeature({CHROME, FF})
    STRING_TRIM_LEFT_RIGHT,

    /**
     * Method addRule returns the rule position instead of -1.
     * (href empty) is null.
     */
    @BrowserFeature(IE)
    STYLESHEET_ADD_RULE_RETURNS_POS,

    /**
     * Indicates that the href property for a &lt;link rel="stylesheet" type="text/css" href="" /&gt;
     * (href empty) is null.
     */
    @BrowserFeature(IE)
    STYLESHEET_HREF_EMPTY_IS_NULL,

    /** Set the value attribute of a submit input to 'Submit Query' if no value attribute specified. */
    @BrowserFeature(IE)
    SUBMITINPUT_DEFAULT_VALUE_IF_VALUE_NOT_DEFINED,

    /** Indicates that unknown tags inside an SVG element are handled as DOM elements, not SVG elements. */
    @BrowserFeature(IE)
    SVG_UNKNOWN_ARE_DOM,

    /**
     * Indicates, that the pathname for the url 'blank' is empty;
     * instead of 'blank'.
     */
    @BrowserFeature({CHROME, FF})
    URL_ABOUT_BLANK_HAS_BLANK_PATH,

    /**
     * Indicates, that the browser supports username and password as
     * part of the url (e.g. http://john.smith:secret@localhost).
     */
    @BrowserFeature({CHROME, FF})
    URL_AUTH_CREDENTIALS,

    /** Replace only ' ' with %20 when encode the query part of a url. */
    @BrowserFeature(IE)
    URL_MINIMAL_QUERY_ENCODING,

    /** Handles missing slashes. */
    @BrowserFeature({CHROME, FF})
    URL_MISSING_SLASHES,

    /** Set the origin property for web socket events. */
    @BrowserFeature({CHROME, FF})
    WEBSOCKET_ORIGIN_SET,

    /** Set the srcElement property for web socket events. */
    @BrowserFeature({CHROME, IE})
    WEBSOCKET_SRC_ELEMENT_SET,
    /** Execute window events. */
    @BrowserFeature(IE)
    WINDOW_EXECUTE_EVENTS,

    /** XMLHttpRequest.getAllResponseHeaders() has a trailing separator. */
    @BrowserFeature(IE)
    XHR_ALL_RESPONSE_HEADERS_APPEND_SEPARATOR,

    /** XMLHttpRequest.getAllResponseHeaders() uses only Lf as separator. */
    @BrowserFeature({FF60, IE})
    XHR_ALL_RESPONSE_HEADERS_SEPARATE_BY_LF,

    /** XMLHttpRequest triggers the opened event at the beginning of the send method again. */
    @BrowserFeature(IE)
    XHR_FIRE_STATE_OPENED_AGAIN_IN_ASYNC_MODE,

    /**
     * Indicates if the port should be ignored during origin check.
     * For IE: this is currently a bug, see
     * http://connect.microsoft.com/IE/feedback/details/781303/
     * origin-header-is-not-added-to-cors-requests-to-same-domain-but-different-port
     */
    @BrowserFeature(IE)
    XHR_IGNORE_PORT_FOR_SAME_ORIGIN,

    /** ProgressEvent.lengthComputable is true. */
    @BrowserFeature(IE)
    XHR_LENGTH_COMPUTABLE,

    /** A cross origin request to {@code about:blank} is not allowed. */
    @BrowserFeature(IE)
    XHR_NO_CROSS_ORIGIN_TO_ABOUT,

    /** Indicates if an empty url is allowed as url param for the open method. */
    @BrowserFeature({CHROME, FF})
    XHR_OPEN_ALLOW_EMTPY_URL,

    /** Indicates that the content charset is used for response parsing. */
    @BrowserFeature({FF, CHROME})
    XHR_USE_CONTENT_CHARSET,

    /** Indicates that the "*" pattern is allowed when withCredential is enabled. */
    @BrowserFeature(IE)
    XHR_WITHCREDENTIALS_ALLOW_ORIGIN_ALL,

    /** Indicates that the XPath attribute is case sensitive. */
    @BrowserFeature(CHROME)
    XPATH_ATTRIBUTE_CASE_SENSITIVE,

    /** Indicates that the 'SelectionNamespaces' property is supported by XPath expressions. */
    @BrowserFeature({IE, CHROME})
    XPATH_SELECTION_NAMESPACES,
}

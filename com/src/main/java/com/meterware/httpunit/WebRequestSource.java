package com.meterware.httpunit;

import com.meterware.httpunit.scripting.ScriptableDelegate;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class WebRequestSource extends ParameterHolder
        implements HTMLElement
{
    private FrameSelector _frame;
    private static final String PARAM_DELIM = "&";
    private WebResponse _baseResponse;
    private String _defaultTarget;
    private URL _baseURL;
    private String _destination;
    private Node _node;

    public String getID()
    {
        return getAttribute("id");
    }

    public String getClassName()
    {
        return getAttribute("class");
    }

    public String getName()
    {
        return getAttribute("name");
    }

    public String getTitle()
    {
        return getAttribute("title");
    }

    public String getTarget()
    {
        if (getSpecifiedTarget().length() == 0)
            return this._defaultTarget;

        return getSpecifiedTarget();
    }

    /**
     * @deprecated
     */
    public String getPageFrame()
    {
        return this._frame.getName();
    }

    public FrameSelector getFrame()
    {
        return this._frame;
    }

    public String getFragmentIdentifier()
    {
        int hashIndex = getDestination().indexOf(35);
        if (hashIndex < 0)
            return "";

        return getDestination().substring(hashIndex + 1);
    }

    public Node getDOMSubtree()
    {
        return this._node.cloneNode(true);
    }

    public abstract WebRequest getRequest();

    public abstract String[] getParameterNames();

    public abstract String[] getParameterValues(String paramString);

    String getRelativePage()
    {
        String url = getRelativeURL();
        if (HttpUnitUtils.isJavaScriptURL(url)) return url;
        int questionMarkIndex = url.indexOf("?");
        if ((questionMarkIndex >= 1) && (questionMarkIndex < url.length() - 1))
            return url.substring(0, questionMarkIndex);

        return url;
    }

    protected String getRelativeURL()
    {
        String result = HttpUnitUtils.trimAll(HttpUnitUtils.trimFragment(getDestination()));
        if (result.trim().length() == 0) result = getBaseURL().getFile();
        return result;
    }

    WebRequestSource(WebResponse response, Node node, URL baseURL, String destination, FrameSelector frame, String defaultTarget)
    {
        if (node == null) throw new IllegalArgumentException("node must not be null");
        this._baseResponse = response;
        this._node = node;
        this._baseURL = baseURL;
        this._destination = destination;
        this._frame = frame;
        this._defaultTarget = defaultTarget;
    }

    protected URL getBaseURL()
    {
        return this._baseURL;
    }

    protected String getDestination()
    {
        return this._destination;
    }

    protected void setDestination(String destination)
    {
        this._destination = destination;
    }

    protected Node getNode()
    {
        return this._node;
    }

    protected HTMLPage getHTMLPage()
            throws SAXException
    {
        return this._baseResponse.getReceivedPage();
    }

    protected final void loadDestinationParameters()
    {
        StringTokenizer st = new StringTokenizer(getParametersString(), "&");
        for (; st.hasMoreTokens(); stripOneParameter(st.nextToken()));
    }

    protected WebResponse submitRequest(String event, WebRequest request)
            throws IOException, SAXException
    {
        WebResponse response = null;
        //if ((event.length() == 0) || (getScriptableDelegate().doEvent(event)))
        response = submitRequest(request);
        if (response == null) response = getCurrentFrameContents();
        return response;
    }

    protected WebResponse getCurrentFrameContents()
    {
        return getCurrentFrame(getBaseResponse().getWindow(), this._frame);
    }

    private WebResponse getCurrentFrame(WebWindow window, FrameSelector pageFrame)
    {
        return ((window.hasFrame(pageFrame)) ? window.getFrameContents(pageFrame) : window.getCurrentPage());
    }

    protected final WebResponse submitRequest(WebRequest request)
            throws IOException, SAXException
    {
        return ((getDestination().equals("#")) ? this._baseResponse : this._baseResponse.getWindow().sendRequest(request));
    }

    protected final WebResponse getBaseResponse()
    {
        return this._baseResponse;
    }

    protected abstract void addPresetParameter(String paramString1, String paramString2);

    public String getAttribute(String name)
    {
        return NodeUtils.getNodeAttribute(this._node, name);
    }

    public boolean isSupportedAttribute(String name)
    {
        return false;
    }

    public String getText()
    {
        if (this._node.getNodeType() == 3)
            return this._node.getNodeValue().trim();
        if ((this._node == null) || (!(this._node.hasChildNodes())))
            return "";

        return NodeUtils.asText(this._node.getChildNodes()).trim();
    }

    public String getTagName()
    {
        return this._node.getNodeName();
    }

    String getAttribute(String name, String defaultValue)
    {
        return NodeUtils.getNodeAttribute(this._node, name, defaultValue);
    }

    private String getSpecifiedTarget()
    {
        return getAttribute("target");
    }

    protected void setTargetAttribute(String value)
    {
        ((Element)this._node).setAttribute("target", value);
    }

    private String getParametersString()
    {
        String url = HttpUnitUtils.trimFragment(getDestination());
        if (url.trim().length() == 0) url = getBaseURL().toExternalForm();
        if (HttpUnitUtils.isJavaScriptURL(url)) return "";
        int questionMarkIndex = url.indexOf("?");
        if ((questionMarkIndex >= 1) && (questionMarkIndex < url.length() - 1))
            return url.substring(questionMarkIndex + 1);

        return "";
    }

    private void stripOneParameter(String param)
    {
        int index = param.indexOf("=");
        String value = (index == param.length() - 1) ? getEmptyParameterValue() : (index < 0) ? null : decode(param.substring(index + 1));

        String name = (index < 0) ? decode(param) : decode(param.substring(0, index));
        addPresetParameter(name, value);
    }

    private String decode(String string)
    {
        return HttpUnitUtils.decode(string, this._baseResponse.getCharacterSet()).trim();
    }

    protected abstract String getEmptyParameterValue();
}
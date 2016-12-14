package framework.struts.ext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.SerializationParams;
import org.apache.struts2.views.util.UrlHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class JsonResultExt extends JSONResult {

	/**
	 * 增加跳转action参数
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(JsonResultExt.class);
	private static final long serialVersionUID = -2939754567815042882L;
	private String forwardAction;
	private boolean parse;
	private boolean encode;
	private String finalLocation;
	private ActionMapper actionMapper;
	protected boolean supressEmptyParameters = false;
	private boolean prependServletContext = true;

	protected Map<String, String> requestParameters = new LinkedHashMap<String, String>();

	public void setForwardAction(String forwardAction) {
		this.forwardAction = forwardAction;

	}

	public void setSupressEmptyParameters(boolean supressEmptyParameters) {
		this.supressEmptyParameters = supressEmptyParameters;
	}

	@Inject
	public void setActionMapper(ActionMapper mapper) {
		this.actionMapper = mapper;
	}

	/**
	 * Set parse to <tt>true</tt> to indicate that the location should be parsed
	 * as an OGNL expression. This is set to <tt>true</tt> by default.
	 * 
	 * @param parse
	 *            <tt>true</tt> if the location parameter is an OGNL expression,
	 *            <tt>false</tt> otherwise.
	 */
	public void setParse(boolean parse) {
		this.parse = parse;
	}

	public void setPrependServletContext(boolean prependServletContext) {
		this.prependServletContext = prependServletContext;
	}

	/**
	 * Set encode to <tt>true</tt> to indicate that the location should be url
	 * encoded. This is set to <tt>true</tt> by default
	 * 
	 * @param encode
	 *            <tt>true</tt> if the location parameter should be url encode,
	 *            <tt>false</tt> otherwise.
	 */
	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	protected List<String> getProhibitedResultParams() {
		return Arrays.asList(new String[] { "defaultEncoding",
				"includeProperties", "excludeProperties", "root",
				"wrapWithComments", "prefix", "statusCode", "errorCode",
				"callbackParameter", "contentType", "wrapPrefix", "wrapSuffix",
				"enableSMD", "enableGZIP", "ignoreHierarchy",
				"ignoreInterfaces", "enumAsBean", "noCache",
				"excludeNullProperties", "parse", "encode",
				"supressEmptyParameters", "prependServletContext",
				"forwardAction" });
	}

	protected boolean isPathUrl(String url) {
		try {
			String rawUrl = url;
			if (url.contains("?")) {
				rawUrl = url.substring(0, url.indexOf("?"));
			}
			URI uri = URI.create(rawUrl.replaceAll(" ", "%20"));
			if (uri.isAbsolute()) {
				URL validUrl = uri.toURL();
				if (LOG.isDebugEnabled()) {
					LOG.debug("[#0] is full url, not a path", url);
				}
				return validUrl.getProtocol() == null;
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug("[#0] isn't absolute URI, assuming it's a path",
							url);
				}
				return true;
			}
		} catch (IllegalArgumentException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[#0] isn't a valid URL, assuming it's a path", e,
						url);
			}
			return true;
		} catch (MalformedURLException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[#0] isn't a valid URL, assuming it's a path", e,
						url);
			}
			return true;
		}
	}

	private String getForwardLocation(ActionInvocation invocation) {
		String actionName;
		String namespace = "";
		actionName = forwardAction;
	
		if (forwardAction.indexOf("/") > -1) {
			actionName = forwardAction
					.substring(forwardAction.indexOf("/") + 1);
			namespace = forwardAction.substring(0, forwardAction.indexOf("/"));
		}
		if (StringUtils.isBlank(namespace)) {
			namespace = invocation.getProxy().getNamespace();
		}
		StringBuilder tmpLocation = new StringBuilder(actionMapper
				.getUriFromActionMapping(new ActionMapping(actionName,
						namespace, "", null)));

		return tmpLocation.toString();

	}

	public void execute(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
		if(!StringUtils.isBlank(forwardAction))
		{
			finalLocation = getForwardLocation(invocation);
			
			if (isPathUrl(finalLocation)) {
				if (!finalLocation.startsWith("/")) {
					ActionMapping mapping = actionMapper.getMapping(request,
							Dispatcher.getInstance().getConfigurationManager());
					String namespace = null;
					if (mapping != null) {
						namespace = mapping.getNamespace();
					}

					if ((namespace != null) && (namespace.length() > 0)
							&& (!"/".equals(namespace))) {
						finalLocation = namespace + "/" + finalLocation;
					} else {
						finalLocation = "/" + finalLocation;
					}
				}

				// if the URL's are relative to the servlet context, append the
				// servlet context path
				if (prependServletContext && (request.getContextPath() != null)
						&& (request.getContextPath().length() > 0)) {
					finalLocation = request.getContextPath() + finalLocation;
				}

				ResultConfig resultConfig = invocation.getProxy().getConfig()
						.getResults().get(invocation.getResultCode());
				if (resultConfig != null) {
					Map resultConfigParams = resultConfig.getParams();
					for (Iterator i = resultConfigParams.entrySet().iterator(); i
							.hasNext();) {
						Map.Entry e = (Map.Entry) i.next();

						if (!getProhibitedResultParams().contains(e.getKey())) {
							requestParameters.put(e.getKey().toString(), e
									.getValue() == null ? "" : conditionalParse(e
									.getValue().toString(), invocation));
							String potentialValue = e.getValue() == null ? ""
									: conditionalParse(e.getValue().toString(),
											invocation);
							if (!supressEmptyParameters
									|| ((potentialValue != null) && (potentialValue
											.length() > 0))) {
								requestParameters.put(e.getKey().toString(),
										potentialValue);
							}
						}
					}
				}

				StringBuilder tmpLocation = new StringBuilder(finalLocation);
				UrlHelper
						.buildParametersString(requestParameters, tmpLocation, "&");

				finalLocation = response.encodeRedirectURL(tmpLocation.toString());
			}
		}
		
		super.execute(invocation);

	}
	protected void writeToResponse(HttpServletResponse response, String json, boolean gzip)  throws IOException {
		if(!StringUtils.isBlank(forwardAction))
		{
			StringBuilder sbJson=new StringBuilder(json);
			sbJson.replace(sbJson.length()-1,sbJson.length(),",\"forwardAction\":\"");
			
			sbJson.append(finalLocation);
			sbJson.append("\"}");
			super.writeToResponse(response, sbJson.toString(), gzip);
		}
		else
		{
			super.writeToResponse(response, json, gzip);
		}
		
		
	}

	/**
	 * Parses the parameter for OGNL expressions against the valuestack
	 * 
	 * @param param
	 *            The parameter value
	 * @param invocation
	 *            The action invocation instance
	 * @return The resulting string
	 */
	protected String conditionalParse(String param, ActionInvocation invocation) {
		if (parse && param != null && invocation != null) {
			return TextParseUtil.translateVariables(param, invocation
					.getStack(), new TextParseUtil.ParsedValueEvaluator() {
				public Object evaluate(Object parsedValue) {
					if (encode) {
						if (parsedValue != null) {
							try {
								// use UTF-8 as this is the recommended encoding
								// by W3C to
								// avoid incompatibilities.
								return URLEncoder.encode(
										parsedValue.toString(), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								LOG.warn("error while trying to encode ["
										+ parsedValue + "]", e);
							}
						}
					}
					return parsedValue;
				}
			});
		} else {
			return param;
		}
	}
}

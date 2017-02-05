package spet.sbwo.layer;

import spet.sbwo.api.filter.AjaxFilter;
import spet.sbwo.api.filter.AuthConditionalFilter;
import spet.sbwo.api.filter.CsrfTokenFilter;
import spet.sbwo.api.filter.LocalAddressFilter;

public class Filter {
	private static final String WEB_INDEX_PATH = "/private/web/index.html";
	private static final String LOGIN_PATH = "/public/login/index.html";

	private final CsrfTokenFilter csrfTokenFilter = new CsrfTokenFilter();
	private final LocalAddressFilter localAddressFilter = new LocalAddressFilter();
	private final AuthConditionalFilter authConditionalFilter = new AuthConditionalFilter(LOGIN_PATH, WEB_INDEX_PATH);
	private final AjaxFilter ajaxDenyFilter = new AjaxFilter(false);

	public CsrfTokenFilter getCsrfTokenFilter() {
		return csrfTokenFilter;
	}

	public LocalAddressFilter getLocalAddressFilter() {
		return localAddressFilter;
	}

	public AuthConditionalFilter getAuthConditionalFilter() {
		return authConditionalFilter;
	}

	public AjaxFilter getAjaxDenyFilter() {
		return ajaxDenyFilter;
	}
}

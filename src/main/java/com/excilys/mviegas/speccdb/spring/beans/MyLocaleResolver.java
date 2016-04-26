package com.excilys.mviegas.speccdb.spring.beans;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * LocaleResolver Customisé pour choisir comme valeur par défaut du cookie, le AcceptLocaleHeader
 * Created by excilys on 26/04/16.
 */

public class MyLocaleResolver implements LocaleContextResolver {

	private CookieLocaleResolver mCookieLocaleResolver = new CookieLocaleResolver();
	private AcceptHeaderLocaleResolver mAcceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();

	@Override
	public LocaleContext resolveLocaleContext(HttpServletRequest request) {
		return mCookieLocaleResolver.resolveLocaleContext(request);
	}

	@Override
	public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.spring.beans.MyLocaleResolver#setLocaleContext not implemented yet.");
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.spring.beans.MyLocaleResolver#resolveLocale not implemented yet.");
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.spring.beans.MyLocaleResolver#setLocale not implemented yet.");
	}
}

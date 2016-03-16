package com.excilys.mviegas.speccdb.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by excilys on 15/03/16.
 */
public class LinkTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();

		JspWriter out = getJspContext().getOut();
		out.append("<div>pagination</div>");

	}
}

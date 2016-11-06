package lyu.klt.frame.tags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IsEmptyTag extends SimpleTagSupport {

	private String value;
	private String defaultValue;

	public IsEmptyTag() {
		super();
		this.value = "";
		this.defaultValue = "";
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext ctx = this.getJspContext();
		JspWriter out = ctx.getOut();
		if (this.value == null || this.value.trim().equals(""))
			out.write(this.defaultValue);
		else
			out.write(this.value);
	}

}
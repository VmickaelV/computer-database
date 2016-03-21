package com.excilys.mviegas.speccdb.taglib;

import com.excilys.mviegas.speccdb.managers.ListManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Classe traitant le tag JSTL <viegasLib:pagination />
 * 
 * @author VIEGAS Mickael
 */
public class PaginationTag extends SimpleTagSupport {
	public static final Logger LOGGER = LoggerFactory.getLogger(PaginationTag.class);
	
	//===========================================================
	// Attribut - private
	//===========================================================
	
	private String mUrl;
	
	private int mCurrentPage = 1;
	
	private int mCountByPages = ListManager.DEFAULT_SIZE_PAGE;
	
	private int mPagesCountAround = 3;
	
	private int mCount;
	
	private Map<String, String> mParameters;
	
	//===========================================================
	// Getters & Setters
	//===========================================================
	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String pUrl) {
		mUrl = pUrl;
	}

	public int getCurrentPage() {
		return mCurrentPage;
	}

	public void setCurrentPage(int pCurrentPage) {
		if (pCurrentPage == 0) {
			pCurrentPage++;
		}
		mCurrentPage = pCurrentPage;
	}
	
	public int getCountByPages() {
		return mCountByPages;
	}

	public void setCountByPages(int pCountByPages) {
		mCountByPages = pCountByPages;
	}

	public int getPagesCountAround() {
		return mPagesCountAround;
	}

	public void setPagesCountAround(int pPagesCountAround) {
		mPagesCountAround = pPagesCountAround;
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int pCount) {
		mCount = pCount;
	}
	
	

	public Map<String, String> getParameters() {
		return mParameters;
	}

	public void setParameters(Map<String, String> pParameters) {
		mParameters = pParameters;
	}

	//===========================================================
	// Ovverride - SimpleTagSupport
	//===========================================================
	@Override
	public void doTag() throws JspException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("PaginationTag#doTag");
			LOGGER.debug("");
			LOGGER.debug(this.toString());
		}
				
		
		// TODO attention risque d'injection ?
		StringBuilder builder = new StringBuilder("?");
		
		if (mParameters != null) {
			Iterator<Entry<String, String>> iterator = mParameters.entrySet().iterator();
			Entry<String, String> entry;
			while (iterator.hasNext()) {
				entry = iterator.next();
				
				if (!entry.getKey().equals("page")) {
					builder.append(entry.getKey()).append("=").append(entry.getValue());
					builder.append("&");
				}
				
			}
		}
		
		if (builder.length() > 0) {
			builder.append("page=");
		}
		
//		mParameters.entrySet().stream().map(mapper -> mapper.getKey()+"="+mapper.getValue())
		
		JspWriter out = getJspContext().getOut();
		out.append("<ul class=\"pagination\">");
		
		if (mCurrentPage > 1) {
			out.append("<li><a href=\"")
					.append(builder.toString())
					.append(String.valueOf(mCurrentPage-1))
					.append("\" aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span></a></li>");
		}
		
		// TODO à retirer jusqu'à ce que je treouve un moyen de mettre une val par défaut
		if (mCountByPages == 0) {
			mCountByPages = ListManager.DEFAULT_SIZE_PAGE;
		}
		
		int pageMax = mCount/mCountByPages;
		if (mCount % mCountByPages != 0) {
			pageMax++;
		}
		
		int min = mCurrentPage - mPagesCountAround;
		if (min < 1) {
			min = 1;
		}
		int max = mCurrentPage + mPagesCountAround;
		if (max > pageMax) {
			max = pageMax;
		}
		
		for(int i = min; i <= max; i++) {
			out.append("<li")
				.append(mCurrentPage == i ? " class=\"active\"": "")
				.append("><a href=\"")
				.append(builder.toString())
				.append(String.valueOf(i))
				.append("\">")
				.append(String.valueOf(i))
				.append("</a></li>");
		}
		
		if (mCurrentPage < pageMax) {
			out.append("<li><a href=\"")
				.append(builder.toString())
				.append(String.valueOf(mCurrentPage+1))
				.append("\" aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li>");
		}
		
		
		
		out.append("</ul>");
//		out.append("<div>Un commentaire "+mCurrentPage+" link:"+mUrl+"</div>");
	}

	
	
	//===========================================================
	// Méthodes - Object
	//===========================================================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaginationTag [mUrl=");
		builder.append(mUrl);
		builder.append(", mCurrentPage=");
		builder.append(mCurrentPage);
		builder.append(", mCountByPages=");
		builder.append(mCountByPages);
		builder.append(", mPagesCountAround=");
		builder.append(mPagesCountAround);
		builder.append(", mCount=");
		builder.append(mCount);
		builder.append(", mParameters=");
		builder.append(mParameters);
		builder.append("]");
		return builder.toString();
	}

	
	
}

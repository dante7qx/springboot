package org.dante.springboot.security.pub;

import java.util.Map;

public class PageReq {

	public final static String ASC = "asc";
	public final static String DESC = "desc";

	private int pageNo = 1;
	private int pageSize = 20;
	private String sort;
	private String order;

	private Map<String, Object> q;

	public void setPage(int page) {
		this.pageNo = page;
	}

	public void setRows(int rows) {
		this.pageSize = rows;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getQ() {
		return q;
	}

	public void setQ(Map<String, Object> q) {
		this.q = q;
	}

}

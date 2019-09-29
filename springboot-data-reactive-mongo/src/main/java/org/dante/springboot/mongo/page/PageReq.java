package org.dante.springboot.mongo.page;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 分页公共请求类
 * 
 * @author dante
 *
 */
public class PageReq implements Serializable {

	private static final long serialVersionUID = -3216842465545081512L;

	private static final int DEFAULT_PAGENO = 1;
	private static final int DEFAULT_PAGESIZE = 20;
	// 最大每页查询记录数
	private static final int MAX_PAGESIZE = 500;
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private int pageNo = DEFAULT_PAGENO;
	private int pageSize = DEFAULT_PAGESIZE;
	private String sort;
	private String order = ASC;

	// 查询参数
	private Map<String, Object> q;
	
	public PageReq() {
		super();
	}

	public void setPage(int page) {
		if(page > MAX_PAGESIZE) {
			this.pageNo = MAX_PAGESIZE;
		} 
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
		if(q == null) {
			q = Maps.newHashMap();
		}
		return q;
	}

	public void setQ(Map<String, Object> q) {
		this.q = q;
	}

	@Override
	public String toString() {
		return "PageReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", sort=" + sort + ", order=" + order + ", q="
				+ q + "]";
	}

	
}

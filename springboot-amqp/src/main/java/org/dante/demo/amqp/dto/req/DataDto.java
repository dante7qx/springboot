package org.dante.demo.amqp.dto.req;

import java.io.Serializable;
import java.util.List;

public class DataDto implements Serializable {
	
	private static final long serialVersionUID = 819351036636453798L;
	private Integer id;
	private String search;
	private Boolean enable;
	private List<Data> datas;
	
	public DataDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "DataDto [id=" + id + ", search=" + search + ", enable=" + enable + ", datas=" + datas + "]";
	}
	
}

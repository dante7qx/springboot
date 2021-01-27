package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GitDocChapterVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String gitDocId;
	private String title;
	private int level;
	private int seq;
	private String pid;
	private List<GitDocChapterVO> children;
	private String sourceDocAddr;
	private String docPageAddr;

	public GitDocChapterVO() {
	}

	public void addChildren(GitDocChapterVO childVO) {
		if(CollectionUtils.isEmpty(this.children)) {
			this.children = new LinkedList<>();
		}
		this.children.add(childVO);
	}
	
}

package com.cafe24.mysite.vo;

public class GalleryVo {
	
	private Long no;
	private String url;
	private String description;
	
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", url=" + url + ", description=" + description + "]";
	}
	

}

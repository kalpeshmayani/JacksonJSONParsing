package com.kpinfotech.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Files implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String fileid, typeid;
	private String filename, filetype, fileurl, uploadeddate;
	private Boolean selected = false;
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@JsonProperty("fileid")
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	
	@JsonProperty("typeid")
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	@JsonProperty("filename")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@JsonProperty("filetype")
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	@JsonProperty("fileurl")
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	@JsonProperty("uploadeddate")
	public String getUploadeddate() {
		return uploadeddate;
	}
	public void setUploadeddate(String uploadeddate) {
		this.uploadeddate = uploadeddate;
	}

}
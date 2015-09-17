package com.kpinfotech.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String status, message;
	
	private ArrayList<Files> fileList;
	

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonProperty("msg")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonProperty("sentfiles")
	public ArrayList<Files> getFileList() {
		return fileList;
	}
	public void setFileList(ArrayList<Files> fileList) {
		this.fileList = fileList;
	}

}
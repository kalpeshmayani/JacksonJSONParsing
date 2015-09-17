package com.kpinfotech.interfaces;

public interface WSAsync {
	void onWSResponse(String serviceType, Object data, Exception error);
}
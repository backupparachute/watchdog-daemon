package com.kylemiller.watchdogd.model;

import org.json.simple.JSONObject;

public interface JsonSerializable {
	public JSONObject toJSON();
}

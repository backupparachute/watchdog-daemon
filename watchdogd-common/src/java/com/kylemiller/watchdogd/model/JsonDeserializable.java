package com.kylemiller.watchdogd.model;

public interface JsonDeserializable<T> {
	public T fromJSON(String s);
}

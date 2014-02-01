package com.kylemiller.watchdogd.probe.service;

import com.kylemiller.watchdogd.probe.Probe;

public interface ProbeBeanFactory {
	public Probe getProbe(String id);
}

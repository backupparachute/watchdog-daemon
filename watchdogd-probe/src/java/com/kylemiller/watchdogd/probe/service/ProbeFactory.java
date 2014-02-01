package com.kylemiller.watchdogd.probe.service;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.probe.Probe;

public interface ProbeFactory {
	public Probe findProbe(Site site);
}
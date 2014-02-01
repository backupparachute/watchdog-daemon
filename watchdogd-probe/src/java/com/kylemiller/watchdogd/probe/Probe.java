package com.kylemiller.watchdogd.probe;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.probe.service.ProbeCallback;

public interface Probe extends Runnable {
	public void setSite(Site site);
	public void setCallback(ProbeCallback callback);
}

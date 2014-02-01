package com.kylemiller.watchdogd.probe.service;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.probe.Probe;

public class ProbeFactoryImpl implements ProbeFactory {

	private ProbeBeanFactory probeBeanFactory;
	
	public Probe findProbe(Site site) {
		Probe probe = getProbeBeanFactory().getProbe(site.getType());
		if (null == probe) throw new IllegalArgumentException(String.format("site type %s is invalid, no bean found for type", site.getType()));
		probe.setSite(site);
		return probe;
	}
	
	public ProbeBeanFactory getProbeBeanFactory() {
		return probeBeanFactory;
	}

	public void setProbeBeanFactory(ProbeBeanFactory probeBeanFactory) {
		this.probeBeanFactory = probeBeanFactory;
	}
}

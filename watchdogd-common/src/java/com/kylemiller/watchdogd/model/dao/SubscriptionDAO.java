package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Subscription;

public interface SubscriptionDAO extends BaseDAO<Subscription> {
	public List<Subscription> findSubscriptions();
	public Subscription findByName(final String name);
}

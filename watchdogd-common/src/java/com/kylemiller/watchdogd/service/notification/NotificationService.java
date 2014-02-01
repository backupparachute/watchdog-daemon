package com.kylemiller.watchdogd.service.notification;

import com.kylemiller.watchdogd.model.SiteReport;

public interface NotificationService {

	public abstract void notify(SiteReport report);

}
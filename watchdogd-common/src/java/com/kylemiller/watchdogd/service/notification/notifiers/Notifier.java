package com.kylemiller.watchdogd.service.notification.notifiers;

import com.kylemiller.watchdogd.model.SiteNotification;

public interface Notifier {
	
	public void notify(SiteNotification notify);

}

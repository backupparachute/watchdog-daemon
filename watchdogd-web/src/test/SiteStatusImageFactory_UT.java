import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.SiteStatus;
import com.kylemiller.watchdogd.web.util.SiteStatusImageFactory;



public class SiteStatusImageFactory_UT {
	
	@Test
	public void testStatus() {
		SiteStatusImageFactory image = new SiteStatusImageFactory();
		
		Site site = new Site();
		SiteStatus status = new SiteStatus();
		site.setStatus(status);
		
		status.setStatus(SiteReport.SUCCESS_TYPE);
		String s = image.find(site);
		assertEquals("checkmark.png", s);
		
		status.setStatus(SiteReport.FAILURE_TYPE);
		s = image.find(site);
		assertEquals("error.png", s);
		
		status.setStatus("foo");
		s = image.find(site);
		assertEquals("pause.png", s);
		
		site.setStatus(null);
		s = image.find(site);
		assertEquals("warning.png", s);
	}

}

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;



public class DateIntervalTest {

	@Test
	public void testDate() {
		Date now = new Date();
		System.out.println(now);
		System.out.println(DateUtils.addMinutes(now, -5));
	}
}

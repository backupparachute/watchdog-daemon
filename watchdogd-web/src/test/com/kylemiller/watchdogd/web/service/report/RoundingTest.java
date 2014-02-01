package com.kylemiller.watchdogd.web.service.report;
import org.junit.Test;



public class RoundingTest {

	@Test
	public void testPseudoPercents() {
		System.out.println("****** PSEUDO ******");
		
		//for (int i = 0; i < 145;i++) {
		//for (int i = 0; i < 289;i++) {
		for (int i = 0; i < 6;i++) {
			//Float x = 288f;
			//Float x = 576f;
			Float x = 10f;
			Float y = new Float(i);
			x = x-y;
			
			Float total = x+y;
			
			Float tx = x/total;
			Float ty = y/total;
			
			tx = tx*100;
			ty = ty*100;
			
			Integer ix = tx.intValue();
			Integer iy = ty.intValue();
			
			Integer it = ix + iy;
			
			if (it > 100) {
				if (ix > 50) ix --;
				else if (iy > 50) iy--;
			} else if (it < 100) {
				if (ix < 50) ix++;
				else if (iy < 50) iy++;
			}
			
			System.out.printf("x=%s, y=%s, ix=%s, iy=%s, total=%s\n",x,y, ix, iy, ix+iy);
		}
		
	}
}

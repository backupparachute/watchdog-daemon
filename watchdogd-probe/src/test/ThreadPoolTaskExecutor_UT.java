import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


public class ThreadPoolTaskExecutor_UT {
	
	@Test
	public void testPool() throws InterruptedException {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(0);
		pool.setMaxPoolSize(100);
		pool.setKeepAliveSeconds(60);
		pool.setQueueCapacity(0);
		pool.initialize();
		
		StopWatch watch = new StopWatch();
		
		CountDownLatch latch = new CountDownLatch(2);
		
		System.out.println("starting...");
		
		watch.start();
		pool.execute(new TestRunnable(latch, 1000l, "t1"));
		pool.execute(new TestRunnable(latch, 1000l, "t2"));
		
		System.out.println("waiting...."+watch.toString());
		latch.await();
		System.out.println("finished..."+watch.toString());
		
		
	}
	
	
	class TestRunnable implements Runnable {
		private CountDownLatch latch;
		private Long sleep;
		private String name;
		
		public TestRunnable(CountDownLatch latch, Long sleep, String name) {
			this.latch = latch;
			this.sleep = sleep;
			this.name = name;
		}

		@Override
		public void run() {
			
			try {
				Thread.sleep(sleep);
				System.out.println("### "+name+" finished sleeping for "+ sleep);
			} catch (Exception e) {
				throw new RuntimeException("whoops!", e);
			} finally {
				latch.countDown();
			}
		}
		
	}

}

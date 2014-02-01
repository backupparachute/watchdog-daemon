import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/base-context.xml" in the root of the classpath
@ContextConfiguration(locations={"/spring-monitor-common.xml"})
public class PostfixMailSenderTest implements ApplicationContextAware {

	public ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
		
	}
	
	@Test
	public void testSend() {
		
		MailSender mail = (MailSender) ctx.getBean("mailSender");
		
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setFrom("info@watchdogd.com");
		msg.setTo("test@kylemiller.com");
		msg.setSubject("test mail");
		msg.setText("site test...\nfoobar....");
		
		mail.send(msg);
		
	}

}

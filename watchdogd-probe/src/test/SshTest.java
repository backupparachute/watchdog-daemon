import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

import org.junit.Test;



public class SshTest {
	
	@Test
	public void testConnect() throws IOException {
	     final SSHClient ssh = new SSHClient();

	        //ssh.loadKnownHosts();
	     ssh.addHostKeyVerifier("67:70:6d:12:d8:82:02:0c:f6:a9:1d:83:c0:b0:6b:75");
	        
	        ssh.connect("vps.kylemiller.com");
	        try {
//	        	if (ssh.isConnected()) {
	            //ssh.authPublickey(System.getProperty("user.name"));
	        		ssh.authPassword("kyle", "P@$$W0rd");
	        		if (ssh.isConnected() && ssh.isAuthenticated()) {
	        			return;
	        		}
	        			throw new IllegalArgumentException("not authenticated...");
//	        	}
	        	
//	        	throw new IllegalArgumentException("not connected...");
	        	
//	            final Session session = ssh.startSession();
//	            try {
//	                final Command cmd = session.exec("ping -c 1 google.com");
//	                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
//	                cmd.join(5, TimeUnit.SECONDS);
//	                System.out.println("\n** exit status: " + cmd.getExitStatus());
//	            } finally {
//	                session.close();
//	            }
	        } finally {
	            ssh.disconnect();
	        }
	    }
	}

import java.net.UnknownHostException;

import org.junit.Test;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;



public class DnsTest {
	
	@Test
	public void testDNS() throws UnknownHostException, TextParseException {
		Resolver res = new SimpleResolver("8.8.8.8");
		res.setTimeout(12000);
		
		Lookup lookup = new Lookup("vps.kylemiller.comfoo");
		lookup.setResolver(res);
		lookup.setCache(null);
		
		Record[] recs = lookup.run();
		
		
		if (lookup.getResult() == Lookup.HOST_NOT_FOUND) {
			throw new IllegalStateException("host not found...");
			
		}
		
		if (lookup.getResult() == Lookup.TYPE_NOT_FOUND) {
			throw new IllegalStateException("type not found...");
			
		}
		
		if (lookup.getResult() == Lookup.TRY_AGAIN) {
			throw new IllegalStateException("try again...");
			
		}
		
		
		if (lookup.getResult() != Lookup.SUCCESSFUL) {
			throw new IllegalStateException("didn't work...");
		}
		
		
		System.out.println("ttl: "+((ARecord)recs[0]).getTTL());
		System.out.println("dclass: "+((ARecord)recs[0]).getDClass());
		System.out.println("rrset: "+((ARecord)recs[0]).getRRsetType());
		System.out.println("type: "+((ARecord)recs[0]).getType());
		System.out.println("add name: "+((ARecord)recs[0]).getAdditionalName());
		System.out.println("addr: "+((ARecord)recs[0]).getAddress());
		System.out.println("name: "+((ARecord)recs[0]).getName());
	
	}

}

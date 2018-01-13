package camel.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.BasicConfigurator;

public class CamelRestServiceConsumer {
	
	public static void main(String[] args) {
		
//		to enable log messages for execution
		BasicConfigurator.configure(); 
//		to use proxy for accessing RSS feed
		System.setProperty("http.proxyHost","proxy.gbs.pro");
		System.setProperty("http.proxyPort","8080");
		System.setProperty("https.proxyHost","proxy.gbs.pro");
		System.setProperty("https.proxyPort","8080"); 

		CamelContext camelContext = new DefaultCamelContext();
		
		try {
			
			ProducerTemplate template = camelContext.createProducerTemplate();
			
			final Exchange exchange = template.send("http://34.196.163.71:8080/biot/rest/notifications/GBS00003/ae9f535968375182be8247f6c18d195bc77d951f3bda52cbd27c730e8899ec76436218a272dc8f27/notification/0", 
					new Processor() { 
				public void process(Exchange exchange) throws Exception { 
				} }); 
			
			Message out = exchange.getOut(); 
			System.out.println("############### exception:"+exchange.getException());
			String body = out.getBody(String.class);
			System.out.println("########## OUTPUT FROM SELLA CONNECT SERVICE :"+body+":#######");

			Map<String, Object> headers = new HashMap<String, Object>();
	        headers.put(KafkaConstants.PARTITION_KEY, "Announcement");
	        headers.put(KafkaConstants.KEY, "1");
	        template.sendBodyAndHeaders("kafka:localhost:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder", body, headers);

/*			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
*/					

			        // START SNIPPET: consumer_route
/*			        from("restlet:http://34.196.163.71:8080/biot/rest/notifications/GBS00003/ae9f535968375182be8247f6c18d195bc77d951f3bda52cbd27c730e8899ec76436218a272dc8f27/notification/0?synchronous=true").
			        	process(new Processor() {
			            public void process(Exchange exchange) throws Exception {
			               String message = exchange.getOut().getBody(String.class);
			               System.out.println("########## OUTPUT FROM SELLA CONNECT SERVICE :"+message+":#######");
			               exchange.getOut().setBody("DUMMY OUTPUT SET");
			            }
			        }).
			        to("kafka:localhost:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
*///					to("kafka:nbkps133:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
					
					
/*					from("direct:start")
					  .to("http://34.196.163.71:8080/biot/rest/notifications/GBS00003/ae9f535968375182be8247f6c18d195bc77d951f3bda52cbd27c730e8899ec76436218a272dc8f27/notification/0").process(new Processor() {
						
						@Override
						public void process(Exchange exchange) throws Exception {
							String message = exchange.getOut().getBody(String.class);
							System.out.println("########## OUTPUT FROM SELLA CONNECT SERVICE :"+message+":#######");
							
						}
					});
*/
					
					
					
					
/*					from("direct:start").process(new Processor() {
	                    @Override
	                    public void process(Exchange exchange) throws Exception {
	    					System.out.println("########## OUTPUT FROM SELLA CONNECT SERVICE :"+body+":#######");
	                        exchange.getIn().setBody(body,String.class);
	                        exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, "Announcement");
	                        exchange.getIn().setHeader(KafkaConstants.KEY, "1");
	                    }
	                }).to("kafka:localhost:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
*/					
					
					
/*				}
			});
			
			System.out.println("#################### Starting the context");
			camelContext.start();
			Thread.sleep(10000);
			System.out.println("#################### stopping the context");
			camelContext.stop();
*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

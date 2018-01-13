package camel.sample;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.BasicConfigurator;

import camel.sample.json.XMLToJSONConverter;

public class CamelRSSFeedConsumer {
	
	public static void main(String[] args) {

//		to enable log messages for execution
		BasicConfigurator.configure(); 
//		to use proxy for accessing RSS feed
		System.setProperty("http.proxyHost","proxy.gbs.pro");
		System.setProperty("http.proxyPort","8080");
		System.setProperty("https.proxyHost","proxy.gbs.pro");
		System.setProperty("https.proxyPort","8080"); 

		final CamelContext camelContext = new DefaultCamelContext();

		try {
			
			
// For file transfer
/*			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					from("file:C:\\Personal\\Downloads?noop=true").to("file:C:\\Personal\\Downloads\\cameltest");
				}
			});
*/			
			
			
// For twitter feeds
/*			
 			camelContext.addComponent("activemq",
					ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					String consumer_key = "p41m6C8DqZBKC3wF050AsPYYn";
					String consumer_secret = "ufdmD31c0lGxmbKcwfXiPs3q012pFh8Y0TvUdKQpuhwZ30pudw";
					String access_key = "3993021612-pY6FmflNkTCdS2oMatPkRAj6tDAiLiMkA78erfH";
					String access_secret = "BCqpjbmx9jI3o0cCGsFMHaJpGKIzyNYr7jlfXX6Q9qBjo";
					String user = "cartoffers";

					from("twitter://timeline/home?user="+user+"type=direct&delay=60&consumerKey="+consumer_key+"&consumerSecret="+consumer_secret+
							"&accessToken="+access_key+"&accessTokenSecret="+access_secret)
					  .to("activemq:queue:TWITTER_FEEDS");				
					}
				}
			);
			
*/
			
			
// For consuming RSS feeds
			camelContext.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					String rssURL = "https://www.finextra.com/rss/headlines.aspx?splitEntries=true&UpdatedDateFilter=2017-12-30T00:00:01&consumer.delay=1000";
					from("rss:" + rssURL).
			        process(new Processor() {
						
						@Override
						public void process(Exchange exchange) throws Exception {
							String partition = "Announcement";
							exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, partition);
							Message message = exchange.getIn();
							String body = message.getBody(String.class).trim();
							System.out.println("############## RSS FEE XML OUTPUT :"+body+":###########");
							String jsonFormat = new XMLToJSONConverter().getJsonString(body);
							System.out.println("############## MESSAGE BODY jsonFormat :"+jsonFormat+":########");
							message.setBody(jsonFormat);
							exchange.setIn(message);
						}
					}).
//			        marshal().rss().
//			        to("kafka:nbkps133:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");
	                to("kafka:localhost:9092?topic=my-failsafe-topic&serializerClass=kafka.serializer.StringEncoder");

					
				}
			});
			
			System.out.println("#################### Starting the context");
			camelContext.start();
			Thread.sleep(20000);
			System.out.println("#################### stopping the context");
			camelContext.stop();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

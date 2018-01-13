package camel.sample.json;


import java.io.IOException;
import java.io.StringReader;

import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import camel.sample.util.XMLHandler;
import camel.sample.view.XMLView;

public class XMLToJSONConverter {
	
/*	public static void main(String[] args) throws IOException {
		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" version=\"2.0\"><channel><title>Finextra Research Headlines</title><link>https://www.finextra.com/</link><description>The Latest Financial IT News Headlines</description><language>en-GB</language><copyright>Copyright 2018 Finextra Research Ltd</copyright><dc:language>en-GB</dc:language><dc:rights>Copyright 2018 Finextra Research Ltd</dc:rights><image><title>Finextra Research Headlines</title><url>https://www.finextra.com/finextra-images/site/finextra.png</url><link>https://www.finextra.com</link></image><item><title>On demand webinar: Payments compliance in the post-data breach era.</title><link>https://www.finextra.com/featurearticle/2254/on-demand-webinar-payments-compliance-in-the-post-data-breach-era?utm_medium=rss&amp;utm_source=finextrafeed</link><description>Join Finextra, Mitek and leading financial institutions for our on demand webinar, where we will ans...</description><pubDate>Mon, 13 Nov 2017 10:13:00 GMT</pubDate><guid isPermaLink=\"false\">https://www.finextra.com/featurearticle/2254/on-demand-webinar-payments-compliance-in-the-post-data-breach-era?utm_medium=rss&amp;utm_source=finextrafeed</guid> <dc:date>2017-11-13T10:13:00Z</dc:date></item></channel></rss>";
		XMLToJSONConverter xmlToJSONConverter = new XMLToJSONConverter();
		String jsonStr = xmlToJSONConverter.getJsonString(xmlData);
		System.out.println(jsonStr);
	}
*/
	public String getJsonString(final String xmlString) throws IOException {
		try {
			final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			XMLHandler handler = new XMLHandler();
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(new StringReader(xmlString)));

			XMLView xmlView = handler.getXmlView();

			JSONObject json = new JSONObject();
			json.accumulate("headline", xmlView.getItemView().getTitle());
			json.accumulate("description", xmlView.getItemView()
					.getDescription());
			json.accumulate("link", xmlView.getItemView().getLink());
			json.accumulate("image", xmlView.getItemView().getImgUrl());
			json.accumulate("source", "PYMNTS");
			json.accumulate("publishedDate", xmlView.getItemView().getPubDate());
			json.accumulate("categories", "FINTECH");
			json.accumulate("audience", "station1");
			json.accumulate("audience", "station2");
			return json.toString();
		} catch (SAXException ex) {
			System.out.println(ex);
		}
		return null;
	}
}


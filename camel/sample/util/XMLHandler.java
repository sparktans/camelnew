package camel.sample.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import camel.sample.view.ItemView;
import camel.sample.view.XMLView;

public class XMLHandler extends DefaultHandler {

	private XMLView infoChannelView = new XMLView();
	private ItemView itemTagInfo = null;
	private StringBuffer elementValueBuffer = new StringBuffer();
	private String elementName = "";
	private String elementValue = null;

	public XMLHandler() {

	}

	public XMLHandler(final XMLView channelView) {
		this.infoChannelView = channelView;
	}

	public void startElement(final String uri, final String localName,
			final String element, final Attributes attributes)
			throws SAXException {
		elementValueBuffer.setLength(0);
		if ("item".equalsIgnoreCase(localName)) {
			itemTagInfo = new ItemView();
			elementName = localName;
		}
	}

	public void characters(final char buffer[], final int offset,
			final int length) throws SAXException {
		elementValueBuffer.append(buffer, offset, length);
	}

	public void endElement(final String namespaceURI, final String localName,
			final String qName) throws SAXException {

		elementValue = elementValueBuffer.toString();
		if ("item".equalsIgnoreCase(elementName)) {
			if ("title".equalsIgnoreCase(localName)) {
				itemTagInfo.setTitle(elementValue);
			} else if ("link".equalsIgnoreCase(localName)) {
				itemTagInfo.setLink(elementValue);
			} else if ("description".equalsIgnoreCase(localName)) {
				String descValue = elementValue;
				int startIndex = descValue.indexOf("<img");
				int endIndex = descValue.indexOf("/>");
				if (startIndex != -1) {

					String imgDetails = descValue.substring(startIndex,
							endIndex + 2);
					itemTagInfo.setImgUrl(getImageUrl(imgDetails));
					
					if ((endIndex + 2) < descValue.length()) {
						elementValue = descValue.substring(endIndex + 2,
								descValue.length());
					} else {
						elementValue = descValue.substring(0, startIndex);
					}

				}
				itemTagInfo.setDescription(elementValue);

			} else if ("pubDate".equalsIgnoreCase(localName)) {
				itemTagInfo.setPubDate(elementValue);
			}
		}
	}

	public void endDocument() throws SAXException {
		infoChannelView.setItemView(itemTagInfo);
	}

	public XMLView getXmlView() {
		return infoChannelView;
	}

	public String getImageUrl(final String imgDetails) {
		String url = "";
		String[] imgArr = imgDetails.split(" ");
		for (String imgStr : imgArr) {
			if (imgStr.indexOf("src") != -1) {
				int startIndex = imgStr.indexOf("src=");
				int endIndex = imgStr.length();
				url = imgStr.substring(startIndex + 5, endIndex - 1);
				break;
			}
		}
		System.out.println(url);
		return url;
	}
}

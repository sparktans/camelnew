package camel.sample.view;

public class XMLView {
	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String dclanguage;
	private String dcrights;
	private String dcdate;
	private ItemView itemView;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getDclanguage() {
		return dclanguage;
	}
	public void setDclanguage(String dclanguage) {
		this.dclanguage = dclanguage;
	}
	public String getDcrights() {
		return dcrights;
	}
	public void setDcrights(String dcrights) {
		this.dcrights = dcrights;
	}
	public String getDcdate() {
		return dcdate;
	}
	public void setDcdate(String dcdate) {
		this.dcdate = dcdate;
	}
	public ItemView getItemView() {
		return itemView;
	}
	public void setItemView(ItemView itemView) {
		this.itemView = itemView;
	}
}


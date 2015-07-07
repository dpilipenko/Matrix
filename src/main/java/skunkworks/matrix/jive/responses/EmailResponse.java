package skunkworks.matrix.jive.responses;

import java.util.List;

public class EmailResponse {
	public String id;
	public String[] tags;
	public String displayName;
	public List<Email> emails;
	public List<Photos> photos;
	
	private class Email{
		public String jive_label;
		public String value;
	}
	
	private class Photos{
		public String value;
	}
}

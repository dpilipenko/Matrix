package skunkworks.matrix.jive.responses;

import com.google.gson.annotations.SerializedName;

public class PeopleResponse { 
	@SerializedName("displayName") public String name;
	@SerializedName("tags") public String[] skills;
}

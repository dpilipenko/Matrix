package skunkworks.matrix.jive;

import retrofit.http.GET;
import retrofit.http.Path;
import skunkworks.matrix.jive.responses.PeopleResponse;
import skunkworks.matrix.jive.responses.EmailResponse;

public interface JiveService {
	
	@GET("/people/@me")
	PeopleResponse getMyself();
	
	@GET("/people/email/{emailAddress}")
	EmailResponse getPeopleByEmail(@Path("emailAddress") String emailAddress);

}

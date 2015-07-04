package skunkworks.matrix.jive;

import retrofit.http.GET;
import retrofit.http.Path;
import skunkworks.matrix.jive.responses.PeopleResponse;

public interface JiveService {
	
	@GET("/people/@me")
	PeopleResponse getMyself();
	
	@GET("/people/email/{emailAddress}")
	PeopleResponse getPeopleByEmail(@Path("emailAddress") String emailAddress);

}

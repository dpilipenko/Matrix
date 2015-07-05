package skunkworks.matrix.jive;


import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class JiveAdapter {
	
	protected static final String ENDPOINT = "https://rosetta.jiveon.com/api/core/v3";
	
	public static JiveService generate() {
		
		Builder builder = new Builder()
				.setEndpoint(ENDPOINT)
				.setClient(new OkClient(new OkHttpClient())) // use OkHttp
				.setConverter(new GsonConverter(new Gson()))
				
				.setRequestInterceptor(new RequestInterceptor() { // Add HTTP Authorization Header to each Request
					@Override
		            public void intercept(RequestFacade request) {
						final String username = JiveAccount.getUsername();
						final String password = JiveAccount.getPassword();
						final String credentials = username + ":" + password;
		                String string = "Basic " + Base64Utils.encodeToString(credentials.getBytes());
		                request.addHeader("Accept", "application/json");
		                request.addHeader("Authorization", string);
		            }
				});
		
		RestAdapter adapter = builder.build();
		return adapter.create(JiveService.class);
	}
	
}

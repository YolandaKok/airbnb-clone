package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class CustomJSONProvider extends JacksonJsonProvider {

	// this class exists so we can customize the object mapper
	// and therefore the way that the JSON is produced
	 public CustomJSONProvider() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
	    
	    this._mapperConfig.setMapper(mapper);
	 }
}
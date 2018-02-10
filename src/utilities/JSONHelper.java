package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONHelper {
	
	public static <T> T fromJSON(final TypeReference<T> type, final String jsonPacket) {
		   T data = null;

		   try 
		   {
		      data = new ObjectMapper().readValue(jsonPacket, type);
		   } 
		   catch (Exception e) {
		      e.printStackTrace();
		   }
		   return data;
	}

}

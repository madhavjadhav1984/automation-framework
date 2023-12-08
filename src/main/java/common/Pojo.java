package common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface Pojo {
    default String toJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e){
            return this.toString();
        }
    }

    default String toPrettyJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        }
        catch (JsonProcessingException e){
            return this.toString();
        }
    }
}

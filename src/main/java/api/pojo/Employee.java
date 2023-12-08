package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import common.Pojo;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Employee(
        String userId,
        String id,
        String title,
        Boolean completed
) implements Pojo {
}

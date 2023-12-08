package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import common.Pojo;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Product(
        String brand,
        String title,
        String description
) implements Pojo {
}

package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
public class IdListJson {

    private final List<Integer> ids = new ArrayList<>();

}

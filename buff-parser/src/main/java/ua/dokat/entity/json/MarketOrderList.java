package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MarketOrderList {

    private Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Data{

        private List<Order> items;

    }

}

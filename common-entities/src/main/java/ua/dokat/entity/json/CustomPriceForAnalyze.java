package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CustomPriceForAnalyze {

    private final List<Double> prices;

    @JsonCreator
    public CustomPriceForAnalyze(@JsonProperty("orders") List<Orders> orders){
        this.prices = orders.stream()
                .map(Orders::getPrice)
                .collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    private static class Orders{

        private final double price;

        @JsonCreator
        public Orders(@JsonProperty("price") String price){
            this.price = Double.parseDouble(price);
        }
    }
}
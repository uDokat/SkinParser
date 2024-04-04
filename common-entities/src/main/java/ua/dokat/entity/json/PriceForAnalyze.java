package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PriceForAnalyze extends JsonEntity {

    private Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Data {

        private final List<Double> prices;

        @JsonCreator
        public Data(@JsonProperty("items") List<Items> items) {
            this.prices = items.stream()
                    .map(Items::getPrice)
                    .collect(Collectors.toList());

            this.prices.remove(0);
        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Items {

            private final double price;

            @JsonCreator
            public Items(@JsonProperty("price") String price) {
                this.price = Double.parseDouble(price);
            }
        }
    }
}

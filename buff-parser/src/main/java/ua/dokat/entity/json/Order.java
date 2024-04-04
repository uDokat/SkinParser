package ua.dokat.entity.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ua.dokat.entity.json.custom.CustomOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Order {

    private int id;
    private int sell_num;

    public CustomOrder toCustom(){
        return CustomOrder.builder()
                .id(id)
                .sell_num(sell_num)
                .build();
    }

}

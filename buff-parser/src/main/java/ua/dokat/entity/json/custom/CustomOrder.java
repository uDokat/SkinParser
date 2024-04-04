package ua.dokat.entity.json.custom;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomOrder {

    private int id;
    private int sell_num;

}

package ua.dokat.util;

import ua.dokat.entity.json.MarketOrderList;
import ua.dokat.entity.json.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtil {

    public static List<Order> toList(MarketOrderList orderList, List<Integer> ids){
        return orderList.getData().getItems().stream()
                .filter(order -> ids.contains(order.getId()))
                .collect(Collectors.toList());
    }

}

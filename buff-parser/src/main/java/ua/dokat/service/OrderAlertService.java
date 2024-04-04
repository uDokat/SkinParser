package ua.dokat.service;

import ua.dokat.entity.json.Order;

import java.util.List;

public interface OrderAlertService {

    void notifyOfOrder(List<Order> ordersToBeNotified);

}

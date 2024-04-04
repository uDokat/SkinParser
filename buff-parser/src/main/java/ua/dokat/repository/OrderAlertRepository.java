package ua.dokat.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ua.dokat.entity.json.Order;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class OrderAlertRepository {

    private final List<Order> oldNotify = new ArrayList<>();

    public Order findOldOrder(int id){
        for (Order order : oldNotify){
            if (order.getId() == id) return order;
        }

        return null;
    }

    public void addOldNotifications(List<Order> orders){
        oldNotify.addAll(orders);
    }

    public void addOldNotifications(Order order){
        oldNotify.add(order);
    }

    public void overwriteOldNotifications(List<Order> ordersToBeNotified){
        clearOldNotifications();
        addOldNotifications(ordersToBeNotified);
    }

    public void clearOldNotifications(){
        oldNotify.clear();
    }

    public void clearIfNotEmpty(){
        if (isEmpty()) clearOldNotifications();
    }

    public boolean isEmpty(){
        return oldNotify.isEmpty();
    }
}

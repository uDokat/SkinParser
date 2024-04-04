package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ua.dokat.entity.json.Order;
import ua.dokat.repository.OrderAlertRepository;
import ua.dokat.service.OrderAlertService;
import ua.dokat.setvice.impl.TelegramApiServiceImpl;

import java.util.List;

@Service
@Log4j
public class OrderAlertServiceImpl implements OrderAlertService {

    private final OrderAlertRepository repository;

    private final TelegramApiServiceImpl telegramApiService;
    private final OrderPriceServiceImpl priceService;

    public OrderAlertServiceImpl(OrderAlertRepository repository, TelegramApiServiceImpl telegramApiService, OrderPriceServiceImpl priceService) {
        this.repository = repository;
        this.telegramApiService = telegramApiService;
        this.priceService = priceService;
    }

    @Override
    public void notifyOfOrder(List<Order> ordersToBeNotified) {

        ordersToBeNotified.forEach(order -> {
            if (repository.findOldOrder(order.getId()) == null){
                telegramApiService.notify(order.getId());
                log.info("The parser reported the skin found.\n" +
                        "Found SkinID: " + order.getId());
            }
        });

        repository.overwriteOldNotifications(ordersToBeNotified);
    }
}

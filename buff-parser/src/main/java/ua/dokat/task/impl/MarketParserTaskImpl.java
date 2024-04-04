package ua.dokat.task.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ua.dokat.entity.Entity;
import ua.dokat.entity.http.HttpRequestInfo;
import ua.dokat.entity.http.TraderInformation;
import ua.dokat.entity.json.MarketOrderList;
import ua.dokat.entity.json.Order;
import ua.dokat.service.IdMonitoringService;
import ua.dokat.service.OrderAlertService;
import ua.dokat.task.MarketParserTask;
import ua.dokat.util.OrderUtil;
import ua.dokat.utils.WebClientUtils;

import java.util.List;

@Component
@Log4j
@PropertySource("classpath:parser.properties")
public class MarketParserTaskImpl implements MarketParserTask {

    @Value("${buff.url}")
    private String buffUrl;
    @Value("${buff.params.goods}")
    private String buffParams;
    @Value("${cookie}")
    private String cookie;
    @Value("${token}")
    private String token;

    private final OrderAlertService alertService;
    private final IdMonitoringService idMonitoringService;
    private final WebClientUtils webClientUtils;

    public MarketParserTaskImpl(OrderAlertService alertService, IdMonitoringService idMonitoringService, WebClientUtils webClientUtils) {
        this.alertService = alertService;
        this.idMonitoringService = idMonitoringService;
        this.webClientUtils = webClientUtils;
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void parse() {

        try {

            Entity<MarketOrderList> orders = webClientUtils.sendGetAndGetResponse(new HttpRequestInfo(buffUrl, buffParams), new TraderInformation(token, cookie), MarketOrderList.class);

            if (!orders.isValid() || orders.getEntityObject().getData().getItems().isEmpty()) return;

            List<Order> ordersToBeNotified = OrderUtil.toList(orders.getEntityObject(), idMonitoringService.findList().getIds().getIds());
            alertService.notifyOfOrder(ordersToBeNotified);

        }catch (WebClientResponseException e){

            log.error("Parser got status" + e.getStatusCode() +": " + e.getStatusText(), e);
            log.debug("Request URI: " + e.getRequest().getURI());
            log.debug("Response body: " + e.getResponseBodyAsString());

        }
    }
}
package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.dokat.entity.Entity;
import ua.dokat.entity.http.HttpRequestInfo;
import ua.dokat.entity.http.TraderInformation;
import ua.dokat.entity.json.CustomPriceForAnalyze;
import ua.dokat.entity.json.OrderPriceHistory;
import ua.dokat.service.OrderPriceService;
import ua.dokat.test.PriceCalculator;
import ua.dokat.utils.WebClientUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j
@PropertySource("classpath:parser.properties")
public class OrderPriceServiceImpl implements OrderPriceService {


    private final PriceCalculator calculator;
    private final WebClientUtils webClientUtils;

    @Value("${url}")
    private String url;
    @Value("${cookie}")
    private String cookie;
    @Value("${token}")
    private String token;
    private final TraderInformation information = new TraderInformation(token, cookie);

    public OrderPriceServiceImpl(PriceCalculator calculator, WebClientUtils webClientUtils) {
        this.calculator = calculator;
        this.webClientUtils = webClientUtils;
    }


    @Override
    public String priceEstimate(String skinId) {
        return calculate(skinId);
    }

    private String calculate(String skinId){
        try {

            //todo: лист содержит 10 элементов, а должно быть 3
            List<Double> currentPrices = test1(skinId).get().getEntityObject().getPrices();
            List<Double> historyPrices = test2(skinId).get().getEntityObject().getData().getPrice_history();

            double currentPrice = calculator.calculateAverage(currentPrices);
            double averagePrice = calculator.calculateAverage(historyPrices);
            double minBuyPrice = calculator.analyseMinBuyPrice(historyPrices);

            double maxProfit = calculateProfit(currentPrice, minBuyPrice);

            return buildMessage(currentPrice, averagePrice, minBuyPrice, maxProfit);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public CompletableFuture<Entity<CustomPriceForAnalyze>> test1(String skinId){
        Entity<CustomPriceForAnalyze> currentPrices = webClientUtils.sendGetAndGetResponse(new HttpRequestInfo(url, "/api/buff/order?skinId=" + skinId + "&pageSize=3"), information, CustomPriceForAnalyze.class);
        return CompletableFuture.completedFuture(currentPrices);
    }

    @Async
    public CompletableFuture<Entity<OrderPriceHistory>> test2(String skinId){
        Entity<OrderPriceHistory> priceHistory = webClientUtils.sendPostAndGetResponse(new HttpRequestInfo(url, "/api/buff/price_history?skinId=" + skinId), information, OrderPriceHistory.class);
        return CompletableFuture.completedFuture(priceHistory);
    }

    public double calculateProfit(double sell, double buy) {
        return ((sell - buy) / buy) * 100 - 5;
    }

    private String buildMessage(double currentPrice, double averagePrice, double minBuyPrice, double maxProfit){
        return "Актуальная цена: " + format(currentPrice) + "\n" +
                "Средняя цена продажи: " + format(averagePrice) + "$\n" +
                "Профит при покупке за " + format(minBuyPrice) + "$ составляет: " + format(maxProfit) + "%";
    }

    private String format(double value){
        return String.format("%." + 2 + "f", value);
    }
}

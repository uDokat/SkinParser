package ua.dokat.test;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j
public class PriceCalculator {

    public List<Double> calculate(List<Double> prices){

        List<Double> calculatePrices = new ArrayList<>();
        double oldPrice = Double.MAX_VALUE;
        double percentage = 1.15;

        for (double price : prices){

            if (calculatePrices.isEmpty()){
                calculatePrices.add(price);
            } else if (oldPrice * percentage < price) {
                System.out.println(oldPrice * percentage + "<" + price);
            }else {
                calculatePrices.add(price);
                System.out.println(price);
            }

            oldPrice = price;
        }

        return calculatePrices;
    }

    public double analyseBuyPrice(double currentSellPrice){
        return currentSellPrice / 1.2;
    }

    public double analyseMinBuyPrice(List<Double> prices){
        double minPrice = Double.MAX_VALUE;

        // Находим минимальную цену
        for (double price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
        }

        return minPrice;
    }

    public double calculateAverage(List<Double> prices) {
        if (prices == null || prices.isEmpty()) {
            return 0.0; // если массив пуст или равен null, возвращаем 0
        }

        double sum = 0.0;
        for (double num : prices) {
            sum += num;
        }

        return sum / prices.size(); // возвращаем среднее значение
    }
}

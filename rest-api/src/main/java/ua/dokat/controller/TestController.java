package ua.dokat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dokat.entity.Entity;
import ua.dokat.entity.http.HttpRequestInfo;
import ua.dokat.entity.json.JsonEntity;
import ua.dokat.entity.json.PriceForAnalyze;
import ua.dokat.service.UsersByMonitoredSkinsService;
import ua.dokat.service.impl.OrderPriceServiceImpl;
import ua.dokat.utils.WebClientUtils;

@RestController
public class TestController {

    private final UsersByMonitoredSkinsService usersByMonitoredSkinsService;
    private final OrderPriceServiceImpl priceService;

    public TestController(UsersByMonitoredSkinsService usersByMonitoredSkinsService, OrderPriceServiceImpl priceService) {
        this.usersByMonitoredSkinsService = usersByMonitoredSkinsService;
        this.priceService = priceService;
    }

    @GetMapping("/testt")
    public ResponseEntity<JsonEntity> test(@RequestParam int skinId) {
        WebClientUtils clientUtils = new WebClientUtils();
        HttpRequestInfo info = new HttpRequestInfo("https://api.buff.market", "/api/market/goods/sell_order?game=csgo&page_num=1&page_size=4&goods_id=" + skinId +"&sort_by=default");
        Entity<PriceForAnalyze> entity = clientUtils.sendGetAndGetResponse(info, PriceForAnalyze.class);
        return entity.toResponseEntity();
    }

    @GetMapping("/test/c")
    public ResponseEntity<String> response(@RequestParam String skinId){
        return ResponseEntity.ok(priceService.priceEstimate(skinId));
    }

}

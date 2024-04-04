package ua.dokat.test;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ua.dokat.entity.json.Order;
import ua.dokat.entity.json.OrderPriceHistory;
import ua.dokat.setvice.impl.TelegramApiServiceImpl;


@RestController
@Log4j
@PropertySource("classpath:parser.properties")
public class TestParser {

    private static final String URL = "http://localhost:10001";
    private static final String PARAMS = "/api/buff/price_history?skinId=";
    private static final String COOKIE = "Device-Id=r3xCiios2TsoXtQKrjat; forterToken=0eea571560ff48d9b781071530204eef_1707242263911__UDF43-m4_13ck_; ftr_ncd=6; fblo_881005522527906=y; session=1-aZUzFrnCkGhzAajVbaoI1Z6Q8YhRtjXkAuPv6QjTTuo82045652511; Locale-Supported=ru; csrf_token=IjY0Nzc3OGM2YWRmZTI3ZmRiMTZmYjlmMDQ5OTNlYWQwZmZlMzBiZGQi.GKQGYw.YGNST0F_QHEW3qD8Mh8mlX1Mi_g";
    private static final String TOKEN = "IjY0Nzc3OGM2YWRmZTI3ZmRiMTZmYjlmMDQ5OTNlYWQwZmZlMzBiZGQi.GKDjYg.3AazTNABGiUOjNrzbBwb5910Wb0";

    @Value("${buff.url}")
    private String buffUrl;
    @Value("${buff.params.goods}")
    private String buffParams;
    @Value("${cookie}")
    private String cookie;
    @Value("${token}")
    private String token;

    @GetMapping("test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body(buffUrl + "\n" + buffParams + "\n" + cookie + "\n" + token);
    }

    public static OrderPriceHistory sendPostRequest(String url, String params, String cookie, String token){
        WebClient client = WebClient.create(url);

        try{

            return client.post()
                    .uri(params)
                    .headers(httpHeaders -> httpHeaders.putAll(createHeaders(cookie, token)))
                    .retrieve()
                    .bodyToMono(OrderPriceHistory.class)
//                    .toBodilessEntity()
                    .block();

        }catch (WebClientResponseException e){
            System.out.println(e);
        }

        return null;
    }


    private static HttpHeaders createHeaders(String cookie, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Buff-token", token);
        return headers;
    }
}

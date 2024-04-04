package ua.dokat.setvice.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.dokat.entity.http.HttpRequestInfo;
import ua.dokat.exeption.TestException;
import ua.dokat.setvice.TelegramApiService;
import ua.dokat.utils.WebClientUtils;

@Service
@Log4j
@PropertySource("classpath:api.properties")
public class TelegramApiServiceImpl implements TelegramApiService {

    @Value("${api.notify}")
    private String apiUrl;
    @Value("${api.notify.params}")
    private String params;

    private final WebClientUtils webClient;

    public TelegramApiServiceImpl(WebClientUtils webClient) {
        this.webClient = webClient;
    }

    @Override
    public void notify(int skinId) {
        try {

            webClient.sendSyncRequest(webClient.post(apiUrl), new HttpRequestInfo(apiUrl, params));

        } catch (TestException e) {
            //todo: сделать нормальное описание.
            log.error("", e);
        }
    }
}
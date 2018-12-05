package io.project.frontend.services;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Component
@Slf4j
public class ExternalService {

    @Autowired
    private RestTemplate restTemplate;

    public Try<String> fetchDataFromStorage(String token) {
        log.info("Get data with token " + token);
           log.info("I will try again may be 10 times");
        Try<String> fetchData = Try.of(() -> restTemplate.getForObject("http://backend:2019/api/v2/backend/data?token=" + token, String.class));
        if (!fetchData.isSuccess()) {
            log.info("This is a fail, fail fail " + token);
            return Try.failure(fetchData.getCause());
        }
        return fetchData;
    }

}

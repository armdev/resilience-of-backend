package io.project.frontend.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armdev
 */
@Service
@CircuitBreaker(name = "backendService")
@Component
@Slf4j
public class BackendService {

    @Autowired
    private ExternalService externalService;


    public Try<String> methodWithRecovery(String token) {
        log.info("Method With Recovery");

        Supplier<String> fetchTarget = () -> externalService.fetchDataFromStorage(token).get();
        RetryConfig retryConfig = RetryConfig.custom().maxAttempts(10).build();

        Retry retry = Retry.of("externalService", retryConfig);

        fetchTarget = Retry.decorateSupplier(retry, fetchTarget);
        Try<String> profileData = Try.ofSupplier(fetchTarget).recover((throwable) -> recovery(throwable).get());
        return profileData;
    }

    private Try<String> recovery(Throwable throwable) {
        // Handle exception and invoke fallback
        log.error("Salute Absolute, your backend seems down.");
        return Try.success("You are successfully failed");
    }
    //https://dzone.com/articles/resilience4j-intro
}

package org.example.expense_tracker_application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class CurrencyConverterService {
    @Value("${exchangerate.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate class is synchronous Client to perform HTTPS request  to RESTful web services.. It simplifies communication with external APIs

    public double convert(String fromCurrency, String toCurrency, Double amount ) {
        String urlString = String.format(
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s",
                apiKey,
                fromCurrency,
                toCurrency
        );
        Map<String, Object> response = restTemplate.getForObject(urlString,Map.class);

        if (response != null
                &&
           response.get("conversion_rate") != null) {
            double conversionRate = (double) response.get("conversion_rate");
            return amount = conversionRate;
        }
        throw new RuntimeException("Failed to get conversion rate");
    }
}

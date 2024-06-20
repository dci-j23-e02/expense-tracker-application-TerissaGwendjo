package org.example.expense_tracker_application.contoller;

import org.example.expense_tracker_application.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyConverterController {
    @Autowired
    private CurrencyConverterService currencyConverterService;

    @GetMapping ("/currency-converter")
    public String showCurrencyConverter() {
        return "currency-converter";
    }

    @PostMapping("/currency-converter")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double amount,
            Model model
    ){
        double convertedAmount = currencyConverterService.convert(fromCurrency, toCurrency, amount);
        model.addAttribute("convertedAmount", convertedAmount);
        return "currency-converter";
    }
}

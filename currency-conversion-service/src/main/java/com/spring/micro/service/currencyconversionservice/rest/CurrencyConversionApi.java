package com.spring.micro.service.currencyconversionservice.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.micro.service.currencyconversionservice.data.CurrencyConversion;

@RestController
@RequestMapping("/currency-conversion")
class CurrencyConversionApi {

	@GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		CurrencyConversion conversion = new RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}/", CurrencyConversion.class,
						uriVariables)
				.getBody();

		return new CurrencyConversion(conversion.getId(), from, to, conversion.getConversionMultiple(), quantity,
				quantity.multiply(conversion.getConversionMultiple()), conversion.getPort());
	}
}

package tk.sayantanbanerjee.microservices.currencyconversionservices.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import tk.sayantanbanerjee.microservices.currencyconversionservices.CurrencyConversionBean;
import tk.sayantanbanerjee.microservices.currencyconversionservices.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@Autowired
	private DiscoveryClient discoveryClient;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	@HystrixCommand(fallbackMethod = "RetrieveExchangeValue")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity,
			@RequestHeader(required = true, name = "bearerToken") String bearerToken) {

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to, bearerToken, bearerToken);

		logger.info("{}", response);

		return new CurrencyConversionBean(1L, from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	@HystrixCommand(fallbackMethod = "RetrieveExchangeValue")
	public CurrencyConversionBean convertCurrency(
			@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity,
			@RequestHeader(required = true, name = "bearerToken") String bearerToken) {
		String url = null;
		String app = discoveryClient.getServices().get(1);
		List<ServiceInstance> instances = this.discoveryClient.getInstances(app);
		for (ServiceInstance instance : instances) {
			System.out.println(instance.getUri());
			url = "http://" + instance.getHost() + ":" + instance.getPort();
		}

		System.out.println(url);
		// Feign - Problem1
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerToken);
		headers.add("bearerToken", bearerToken);
		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		URI uri = UriComponentsBuilder
				.fromUriString(url + "/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
				.buildAndExpand(uriVariables).toUri();

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().exchange(uri, HttpMethod.GET, entity,
				CurrencyConversionBean.class);

		CurrencyConversionBean response = responseEntity.getBody();

		return new CurrencyConversionBean(1L, from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
	
	public CurrencyConversionBean RetrieveExchangeValue(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity,
			@RequestHeader(required = true, name = "bearerToken") String bearerToken) {

		BigDecimal conversionRate = BigDecimal.valueOf(0) ;

		if (from.contains("USD")) {
			if (to.contains("INR")) {
				conversionRate = BigDecimal.valueOf(65);
			}
		} else if (from.contains("EUR")) {
			if (to.contains("INR")) {
				conversionRate = BigDecimal.valueOf(75);
			}
		} else {
			conversionRate = BigDecimal.valueOf(0);
		}

		return new CurrencyConversionBean(1L, from, to, conversionRate, quantity, quantity.multiply(conversionRate),
				0);
	}
}

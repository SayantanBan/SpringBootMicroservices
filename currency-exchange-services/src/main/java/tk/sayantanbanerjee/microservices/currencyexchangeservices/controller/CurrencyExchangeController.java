package tk.sayantanbanerjee.microservices.currencyexchangeservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import tk.sayantanbanerjee.microservices.currencyexchangeservices.ExchangeValue;
import tk.sayantanbanerjee.microservices.currencyexchangeservices.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@RequestHeader(required = false, name = "bearerToken") String bearerToken, @PathVariable String from, @PathVariable String to) {
			
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		logger.info("{}", exchangeValue);
		
		return exchangeValue;

	}
}

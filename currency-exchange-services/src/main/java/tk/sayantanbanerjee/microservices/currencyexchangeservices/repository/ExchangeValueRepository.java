package tk.sayantanbanerjee.microservices.currencyexchangeservices.repository;

import org.springframework.data.repository.CrudRepository;

import tk.sayantanbanerjee.microservices.currencyexchangeservices.ExchangeValue;

public interface ExchangeValueRepository extends CrudRepository<ExchangeValue, Long>{

	ExchangeValue findByFromAndTo(String from, String to);
}

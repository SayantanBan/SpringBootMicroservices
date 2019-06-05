package tk.sayantanbanerjee.microservices.currencyexchangeservices.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import tk.sayantanbanerjee.currencyconversionsoapservice.model.CurrencyConversionBean;
import tk.sayantanbanerjee.microservices.currencyexchangeservices.ExchangeValue;
import tk.sayantanbanerjee.microservices.currencyexchangeservices.repository.ExchangeValueRepository;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	private Environment environment;
		
	@Autowired
	private ExchangeValueRepository repository;
	
	@Autowired
	private Sender sender;
    
	@Value("${spring.kafka.topic.userRegistered1}")
	String topic;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.userRegistered}", partitions = { "0", "1" }))
    public void receive(@Payload CurrencyConversionBean bean, 
    		  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
    	System.out.println(
    	        "Received Messasge: " + bean
    	        + "from partition: " + partition);
    	System.out.println(bean.getFrom());
    	ExchangeValue exchangeValue = repository.findByFromAndTo(bean.getFrom(), bean.getTo());
		CurrencyConversionBean newBean = new CurrencyConversionBean();
		newBean.setConversionMultiple(exchangeValue.getConversionMultiple());
		newBean.setFrom(bean.getFrom());
		newBean.setTo(bean.getTo());
		newBean.setId((long) 123);
		newBean.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		newBean.setQuantity(bean.getQuantity());
		newBean.setTotalCalculatedAmount(bean.getQuantity().multiply(exchangeValue.getConversionMultiple()));
    	System.out.println(newBean.getFrom());
		sender.send(topic, newBean);
    }
}

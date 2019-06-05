package tk.sayantanbanerjee.currencyconversionsoapservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import tk.sayantanbanerjee.currencyconversionsoapservice.model.CurrencyConversionBean;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	private CurrencyConversionBean obj = new CurrencyConversionBean();

	@Autowired
	private Environment environment;
	
	@Value("${spring.kafka.topic.userRegistered}")
	String topic;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.userRegistered1}", partitions = { "0", "1", "2"}))
    public void receive(@Payload CurrencyConversionBean bean, 
    		  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
    	System.out.println(
    	        "Received Messasge: " + bean
    	        + "from partition: " + partition);
    	obj = bean;
    	System.out.println(obj.getTotalCalculatedAmount());
    }
    
    public CurrencyConversionBean getObj()
    {
    	return obj;
    }
}

package tk.sayantanbanerjee.microservices.currencyexchangeservices.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import tk.sayantanbanerjee.currencyconversionsoapservice.model.CurrencyConversionBean;


public class Sender {
private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	
	
    @Autowired
    private KafkaTemplate<String, CurrencyConversionBean> simpleKafkaTemplate;
    
    
    
    public void send(String topic, CurrencyConversionBean bean) {
    	System.out.println("  "+bean);
    	System.out.println("topic" + topic);
        LOGGER.info("sending payload='{}' to topic='{}'", bean, topic);
        ListenableFuture<SendResult<String, CurrencyConversionBean>> future = simpleKafkaTemplate.send(topic, bean);
    
        future.addCallback(new ListenableFutureCallback<SendResult<String, CurrencyConversionBean>>() {

			@Override
            public void onFailure(Throwable ex) {
//                handleFailure(data, record, ex);
            }

			@Override
			public void onSuccess(SendResult<String, CurrencyConversionBean> result) {
				// TODO Auto-generated method stub
				System.out.println("Success");
			}

        });
    }
}

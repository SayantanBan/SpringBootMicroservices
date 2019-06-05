package tk.sayantanbanerjee.currencyconversionsoapservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.splunk.Args;
import com.splunk.SplunkException;

import currencyconversionsoapservice.sayantanbanerjee.tk.jaxb2.CurrencyConversion;
import currencyconversionsoapservice.sayantanbanerjee.tk.jaxb2.GetCurrencyConversionRequest;
import currencyconversionsoapservice.sayantanbanerjee.tk.jaxb2.GetCurrencyConversionResponse;
import tk.sayantanbanerjee.currencyconversionsoapservice.kafka.Receiver;
import tk.sayantanbanerjee.currencyconversionsoapservice.kafka.Sender;
import tk.sayantanbanerjee.currencyconversionsoapservice.model.CurrencyConversionBean;

@Endpoint
public class CurrencyConversionController {

	@Value("${spring.kafka.topic.userRegistered}")
	String topic;
	
	@Autowired
	Sender sender;
	
	@Autowired
	Receiver receiver;
	
	boolean flag = true;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PayloadRoot(namespace = "http://tk.sayantanbanerjee.currencyconversionsoapservice/jaxb2", localPart="GetCurrencyConversionRequest")
	@ResponsePayload
	public GetCurrencyConversionResponse processCurrencyConversionRequest(@RequestPayload GetCurrencyConversionRequest request) {
		GetCurrencyConversionResponse response = new GetCurrencyConversionResponse();
		CurrencyConversion conversion = new CurrencyConversion();
		CurrencyConversionBean currency = new CurrencyConversionBean();
		currency.setId((long) 1);
		currency.setFrom(request.getCurrencyForm());
		currency.setTo(request.getCurrencyTo());
		currency.setQuantity(request.getQuantity());
				
		CurrencyConversionBean newCurrency = new CurrencyConversionBean();

		sender.send(topic, currency);
		
		while(flag)
		{
			if(receiver.getObj().getTotalCalculatedAmount()!= null) {
				newCurrency = receiver.getObj();
				System.out.println("check" + newCurrency.getTotalCalculatedAmount());
				flag=false;				
				conversion.setConversionMultiple(newCurrency.getConversionMultiple());
				conversion.setCurrencyForm(newCurrency.getFrom());
				conversion.setCurrencyTo(newCurrency.getTo());
				conversion.setId(1);
				conversion.setPort(newCurrency.getPort());
				conversion.setQuantity(newCurrency.getQuantity());
				conversion.setTotalCalculatedAmount(newCurrency.getTotalCalculatedAmount());
			}			
		}
		
		response.setCurrencyConversion(conversion);
		
		return response;
	}
	
}

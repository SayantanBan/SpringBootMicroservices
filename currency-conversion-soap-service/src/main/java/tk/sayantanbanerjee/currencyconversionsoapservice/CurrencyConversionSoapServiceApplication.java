package tk.sayantanbanerjee.currencyconversionsoapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.splunk.Application;
import com.splunk.Args;
import com.splunk.HttpService;
import com.splunk.Receiver;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;

@SpringBootApplication(scanBasePackages={
		"tk.sayantanbanerjee.currencyconversionsoapservice", "currencyconversionsoapservice.sayantanbanerjee.tk.jaxb2"})
@EnableEurekaClient
public class CurrencyConversionSoapServiceApplication {

	public static void main(String[] args) {
		
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

		// Create a map of arguments and add login parameters
        ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setUsername("Sayantan");
        loginArgs.setPassword("P@nzer54");
        loginArgs.setHost("localhost");
        loginArgs.setPort(8089);
        
        // Create a Service instance and log in with the argument map
        Service service = Service.connect(loginArgs);
        					
		Receiver receiver = service.getReceiver();
		Args logArgs = new Args();
		logArgs.put("sourceType", "hellosplunk");
		receiver.log("main", logArgs, "HelloSpunkEvent");
        // A second way to create a new Service object and log in
        // Service service = new Service("localhost", 8089);
        // service.login("admin", "yourpassword");

        // A third way to create a new Service object and log in
        // Service service = new Service(loginArgs);
        // service.login();

        // Print installed apps to the console to verify login
        for (Application app : service.getApplications().values()) {
            System.out.println(app.getName());
        }
		SpringApplication.run(CurrencyConversionSoapServiceApplication.class, args);
	}

}

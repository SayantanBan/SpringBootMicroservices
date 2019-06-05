package tk.sayantanbanerjee.microservices.currencyexchangeservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.splunk.Application;
//import com.splunk.HttpService;
//import com.splunk.SSLSecurityProtocol;
//import com.splunk.Service;
//import com.splunk.ServiceArgs;

import brave.sampler.Sampler;

@SpringBootApplication(scanBasePackages = { "tk.sayantanbanerjee.currencyconversionsoapservice",
		"tk.sayantanbanerjee.microservices.currencyexchangeservices" })
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
public class CurrencyExchangeServicesApplication {

	public static void main(String[] args) {
//        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
//
//		// Create a map of arguments and add login parameters
//        ServiceArgs loginArgs = new ServiceArgs();
//        loginArgs.setUsername("Sayantan");
//        loginArgs.setPassword("P@nzer54");
//        loginArgs.setHost("localhost");
//        loginArgs.setPort(8089);
//        
//        // Create a Service instance and log in with the argument map
//        Service service = Service.connect(loginArgs);
//
//        // A second way to create a new Service object and log in
//        // Service service = new Service("localhost", 8089);
//        // service.login("admin", "yourpassword");
//
//        // A third way to create a new Service object and log in
//        // Service service = new Service(loginArgs);
//        // service.login();
//
//        // Print installed apps to the console to verify login
//        for (Application app : service.getApplications().values()) {
//            System.out.println(app.getName());
//        }
		SpringApplication.run(CurrencyExchangeServicesApplication.class, args);
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}

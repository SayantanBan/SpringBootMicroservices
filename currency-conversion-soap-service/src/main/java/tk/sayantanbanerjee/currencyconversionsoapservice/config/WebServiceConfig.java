package tk.sayantanbanerjee.currencyconversionsoapservice.config;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable Spring Web Services
//Spring Configuration

@Configuration
@EnableWs
public class WebServiceConfig {
	
	//MessageDispatcherServlet
	//ApplicationContext
	//url -> /ws/*
	
	@Bean
	public ServletRegistrationBean<Servlet> messageDispatcherServlet(ApplicationContext applicationContext){
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(applicationContext);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<Servlet>(messageDispatcherServlet, "/ws/*");
	}
	
	// /ws/courses.wsdl
	//PortType - CoursePort
	// Namespace - http://tk.sayantanbanerjee/courses
	// course-details.xsd
	@Bean(name="currency")
	public DefaultWsdl11Definition defaultWsdl11Defination(XsdSchema courseSchema)
	{
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		

		//PortType - CoursePort
		definition.setPortTypeName("CurrencyPort");
		// Namespace - http://tk.sayantanbanerjee/courses
		definition.setTargetNamespace("http://tk.sayantanbanerjee.currencyconversionsoapservice/jaxb2");
		// /ws
		definition.setLocationUri("/ws");
		//schema
		definition.setSchema(courseSchema);
		
		return definition;
	}
	
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("currency-conversion.xsd"));
	}
}

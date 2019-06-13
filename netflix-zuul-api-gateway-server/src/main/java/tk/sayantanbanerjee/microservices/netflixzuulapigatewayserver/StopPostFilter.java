package tk.sayantanbanerjee.microservices.netflixzuulapigatewayserver;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import static com.netflix.zuul.context.RequestContext.getCurrentContext;

import com.netflix.zuul.exception.ZuulException;

@Component
public class StopPostFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		
		Instant stop = Instant.now();
		
		//get start value
		
		RequestContext ctx = getCurrentContext();
		Instant start = (Instant)ctx.get("starttime");
		long secondsDifference = ChronoUnit.MILLIS.between(start, stop);
		System.out.println("Call took " + secondsDifference);
		
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}

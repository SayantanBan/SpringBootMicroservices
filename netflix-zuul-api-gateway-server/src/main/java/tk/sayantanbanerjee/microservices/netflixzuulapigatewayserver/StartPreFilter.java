package tk.sayantanbanerjee.microservices.netflixzuulapigatewayserver;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import static com.netflix.zuul.context.RequestContext.getCurrentContext;

import java.time.Instant;

import com.netflix.zuul.exception.ZuulException;

@Component
public class StartPreFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = getCurrentContext();
		ctx.set("starttime", Instant.now());
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}

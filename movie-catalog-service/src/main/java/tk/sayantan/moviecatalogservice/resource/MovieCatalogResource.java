package tk.sayantan.moviecatalogservice.resource;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.discovery.DiscoveryClient;

import tk.sayantan.moviecatalogservice.model.CatalogItem;
import tk.sayantan.moviecatalogservice.model.Movie;
import tk.sayantan.moviecatalogservice.model.Rating;
import tk.sayantan.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient; //Load balance by your own
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		// get all rated movie IDs
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		// for each movie ID, call movie info service and get details
		return ratings.getUserRating().stream().map(rating -> {
					Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

					return new CatalogItem(movie.getName(), "Test", rating.getRating());
				})
				// Put them all together
				.collect(Collectors.toList());
	}
}


//Movie movie = restTemplate.getForObject("localhost:8082/movies/foo", Movie.class);
//WebClient.Builder builder = WebClient.builder();


//Movie movie = webClientBuilder.build()
//.get()
//.uri("localhost:8082/movies/foo")
//.retrieve()
//.bodyToMono(Movie.class)
//.block();

//return Collections.singletonList(
//new CatalogItem("Transformer", "Test", 4)
//);
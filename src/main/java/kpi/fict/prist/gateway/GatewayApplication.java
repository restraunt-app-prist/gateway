package kpi.fict.prist.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
										   @Value("${services.url.core}") String coreUrl,
										   @Value("${services.url.core-frontend}") String coreFrontendUrl) {
		return builder.routes()
			.route("core", r -> r.order(1)
				.path("/api/**")
				.filters(filters -> filters.stripPrefix(1)) // strip 1 means we remove one url segment (/api/... => /...)
				.uri(coreUrl)
			)
			.route("core-frontend", r -> r.order(10)
				.path("/**")
				.uri(coreFrontendUrl)
			)
			.build();
	}

}

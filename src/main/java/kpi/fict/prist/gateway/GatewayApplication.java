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
										   @Value("${services.url.core-frontend}") String coreFrontendUrl,
										   @Value("${services.url.admintool-core}") String admintoolCoreUrl,
										   @Value("${services.url.admintool-frontend}") String admintoolFrontendUrl) {
		return builder.routes()
			.route("core", r -> r.order(30)
				.path("/api/**")
				.filters(filters -> filters.stripPrefix(1))
				.uri(coreUrl)
			)
			.route("core-frontend", r -> r.order(40)
				.path("/**")
				.uri(coreFrontendUrl)
			)
			.route("admin-core", r -> r.order(20)
				.path("/api/admin/**")
				.filters(filters -> filters.stripPrefix(2))
				.uri(admintoolCoreUrl)
			)
			.route("admin-frontend", r -> r.order(10)
				.path("/admin/**")
				.filters(filters -> filters.stripPrefix(1))
				.uri(admintoolFrontendUrl)
			)
			.build();
	}

}

package io.list.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.list.server.enumeration.Status;
import io.list.server.model.Server;
import io.list.server.repo.ServerRepo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

	@Bean
    CommandLineRunner run(ServerRepo serverRepo) {
        return args -> {
                   serverRepo.save(new Server(null,
                                              "192.168.1.160",
                                              "Ubuntu Linux",
                                              "8 GB",
                                              "Web server",
                                              "http://localhost:8080/server/image/server1.png",
                                              Status.SERVER_UP));
                   serverRepo.save(new Server(null,
                                              "192.168.1.10",
                                              "Red Hat Enterprise Linux",
                                              "64 GB",
                                              "Dell tower",
                                              "http://localhost:8080/server/image/server2.png",
                                              Status.SERVER_DOWN));
                   serverRepo.save(new Server(null,
                                              "192.168.1.98",
                                              "Fedora Linux",
                                              "16 GB",
                                              "Mail Server",
                                              "http://localhost:8080/server/image/server3.png",
                                              Status.SERVER_UP));
                   serverRepo.save(new Server(null,
                                              "192.168.1.197",
                                              "MAC OS",
                                              "32 GB",
                                              "Personal PC",
                                              "http://localhost:8080/server/image/server4.png",
                                              Status.SERVER_DOWN));
               };
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-type", "Accept", "Jwt-Token", "Authorization", "Origin, Accept"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-type", "Accept", "Access-Control-Allow-Origin"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

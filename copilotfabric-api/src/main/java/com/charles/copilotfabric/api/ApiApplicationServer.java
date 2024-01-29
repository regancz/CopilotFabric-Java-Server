package com.charles.copilotfabric.api;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.EnumSet;
import java.util.Properties;

import static com.charles.copilotfabric.api.configuration.FabricConfiguration.getPrivateKey;
import static com.charles.copilotfabric.api.configuration.FabricConfiguration.readX509Certificate;

/**
 * @author charles
 * @date 2024/1/22 21:12
 */

@SpringBootApplication
public class ApiApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplicationServer.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");//替换这个
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

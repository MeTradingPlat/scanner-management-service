package com.metradingplat.scanner_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScannerManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScannerManagementServiceApplication.class, args);
	}

}

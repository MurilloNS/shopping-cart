package com.ollirum.ms_profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.ollirum.ms_profiles.client")
@SpringBootApplication
public class MsProfilesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsProfilesApplication.class, args);
	}
}
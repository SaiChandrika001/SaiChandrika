package com.support.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.support.binding.SupportResponse;

@FeignClient(name = "notification-service", url = "http://localhost:8087/api/notify")
public interface NotificationClient {
	@PostMapping("/send")
	void sendNotification(@RequestBody SupportResponse response);
}

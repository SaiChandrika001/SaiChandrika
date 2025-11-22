package com.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "support-service")
//, url = "${support.service.url}")
public interface SupportClient {
	@GetMapping("/api/support/tickets/{id}")
	Object getTicketById(@PathVariable("id") String id); // simple object for demo
}

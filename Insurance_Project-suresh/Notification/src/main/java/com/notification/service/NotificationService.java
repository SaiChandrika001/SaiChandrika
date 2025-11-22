package com.notification.service;

import java.util.List;

import com.notification.binding.NotificationRequest;
import com.notification.binding.NotificationResponse;

public interface NotificationService {
	NotificationResponse send(NotificationRequest request);
	List<NotificationResponse> listAll();
}

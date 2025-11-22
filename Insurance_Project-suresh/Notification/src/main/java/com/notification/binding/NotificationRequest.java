package com.notification.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
	private String ticketId;
	private String message;
	private String type; // EMAIL or SMS
	private String recipient;
}

package com.notification.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
	private Long id;
	private String ticketId;
	private String status; // SENT / PENDING / FAILED
	private String description;
}

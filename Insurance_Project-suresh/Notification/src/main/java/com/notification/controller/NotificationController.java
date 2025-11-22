package com.notification.controller;

import com.notification.binding.NotificationRequest;
import com.notification.binding.NotificationResponse;
import com.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    public NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    // ✅ Send a new notification
    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationRequest request) {
        NotificationResponse send = notificationService.send(request);

        return new ResponseEntity<>(send, HttpStatus.OK);

    }

    // ✅ Get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        List<NotificationResponse> notificationResponses = notificationService.listAll();
        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }

}

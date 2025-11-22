package com.support.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.support.binding.SupportRequest;
import com.support.binding.SupportResponse;
import com.support.client.NotificationClient;
import com.support.entity.SupportTicket;
import com.support.repo.SupportRepo;

@Service
public class SupportServiceImpl implements SupportService {

    private final SupportRepo repo;
    private final NotificationClient client;

    public SupportServiceImpl(SupportRepo repo, NotificationClient client) {
        this.repo = repo;
        this.client = client;
    }

    @Override
    public SupportResponse createTicket(SupportRequest request) {
        // Create new ticket
        SupportTicket ticket = new SupportTicket();
        ticket.setUserId(request.getUserId());
        ticket.setIssueType(request.getIssueType());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus("OPEN");

        // Save ticket in DB
        SupportTicket saved = repo.save(ticket);

        // Create response
        SupportResponse response = new SupportResponse(
            saved.getTicketId(),
            saved.getStatus(),
            "Ticket created successfully"
        );

        // Call Notification service
        client.sendNotification(response);

        return response;
    }

    @Override
    public List<SupportResponse> getAllTickets() {
        return repo.findAll().stream()
                   .map(t -> new SupportResponse(
                       t.getTicketId(),
                       t.getStatus(),
                       t.getDescription()
                   ))
                   .collect(Collectors.toList());
    }
}

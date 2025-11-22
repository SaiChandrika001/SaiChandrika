package com.support.service;

import java.util.List;

import com.support.binding.SupportRequest;
import com.support.binding.SupportResponse;

public interface SupportService {
	SupportResponse createTicket(SupportRequest request);
	List<SupportResponse> getAllTickets();
}

package com.account.accountservice.controller;

import com.account.accountservice.model.action.Event;
import com.account.accountservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {
    private final EventService eventService;
    @Autowired
    public SecurityController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<Event> showAllEvents() {
        return eventService.showAllLogs();
    }
}

package com.account.accountservice.service;

import com.account.accountservice.model.action.Action;
import com.account.accountservice.model.action.Event;
import com.account.accountservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveLog(Action action, String subject, String object, String path) {
        eventRepository.save(Event.builder()
                .action(action)
                .subject(subject)
                .object(object)
                .path(path)
                .build());
    }

    public List<Event> showAllLogs() {
        return eventRepository.findAll();
    }
}

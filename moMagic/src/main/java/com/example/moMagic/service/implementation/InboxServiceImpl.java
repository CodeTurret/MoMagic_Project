package com.example.moMagic.service.implementation;

import com.example.moMagic.model.Inbox;
import com.example.moMagic.repository.InboxRepository;
import com.example.moMagic.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InboxServiceImpl implements InboxService {

    private final InboxRepository inboxRepository;

    public InboxServiceImpl(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    /**
     * Have to Use pageable because need to fetch data 200 rows
     * @param pageSize - int
     * @param pageNumber - int
     * @return Page<Inbox> - Pageable
     */
    public Page<Inbox> getChunkOfInbox(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return inboxRepository.findAll(pageable);
    }
}

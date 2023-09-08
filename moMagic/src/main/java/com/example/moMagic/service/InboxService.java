package com.example.moMagic.service;

import com.example.moMagic.model.Inbox;
import org.springframework.data.domain.Page;

public interface InboxService {
    public Page<Inbox> getChunkOfInbox(int pageSize, int pageNumber);
}

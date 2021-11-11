package io.list.server.service.implementation;

import io.list.server.model.AGGrid;
import io.list.server.repo.AGGridRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AGGridServiceImpl {

    private final AGGridRepo aggridRepo;

    public Collection<AGGrid> getInfo() {
        log.info("Saving new student");
        return aggridRepo.findAll(PageRequest.of(0, 50)).toList();
    }
}

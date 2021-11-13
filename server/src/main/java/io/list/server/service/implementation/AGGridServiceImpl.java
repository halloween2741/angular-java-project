package io.list.server.service.implementation;

import io.list.server.model.AGGrid;
import io.list.server.repo.AGGridRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AGGridServiceImpl {

    private final AGGridRepo aggridRepo;

    public Optional<AGGrid> getInfo() {
        log.info("Saving new aggrid");
        return aggridRepo.findById(0L);
    }

    public AGGrid updateAGGrid(AGGrid agGrid) {
        log.info("Updating aggrid");
        return aggridRepo.save(agGrid);
    }
}

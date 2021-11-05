package io.list.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import io.list.server.model.Student;
import io.list.server.repo.StudentRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.list.server.enumeration.Status;
import io.list.server.model.Server;
import io.list.server.repo.ServerRepo;
import io.list.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo serverRepo;
    private final StudentRepo studentRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        return serverRepo.save(server);
    }

    public Student createStudent(Student student) {
        log.info("Saving new student");
        return studentRepo.save(student);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        Server server = serverRepo.findByIpAddress(ipAddress);
        server.setImageUrl(setServerImageUrl());
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    public Collection<Student> students(int limit) {
        log.info("Fetching all students");
        return studentRepo.findAll(PageRequest.of(0, limit)).toList();
    }


    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    public Student updateStudent(Student student) {
        log.info("Updating student");
        return studentRepo.save(student);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting server by ID: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    public Boolean deleteStudent(Long id) {
        log.info("deleting student by ID: {}", id);
        studentRepo.deleteById(id);
        return TRUE;
    }


    private Object setServerImageUrl() {
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}

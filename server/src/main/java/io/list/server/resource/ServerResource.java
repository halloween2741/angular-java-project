package io.list.server.resource;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;

import static java.util.Map.of;

import io.list.server.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import io.list.server.model.Response;
import io.list.server.service.implementation.ServerServiceImpl;

@RestController
@RequestMapping("/server")
public class ServerResource {
    private final ServerServiceImpl serverService;

    public ServerResource(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Response> deleteStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.deleteStudent(id)))
                .message("Server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping("/saveStudent")
    public ResponseEntity<Response> saveStudentServer(@RequestBody
                                               @Valid Student student) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.createStudent(student)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<Response> updateStudentServer(@RequestBody
                                                      @Valid Student student) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.updateStudent(student)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @GetMapping("/listStudents")
    public ResponseEntity<Response> getStudents() throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.students(30)))
                .message("Servers retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
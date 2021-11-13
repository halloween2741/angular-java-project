package io.list.server.resource;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;

import static java.util.Map.of;

import io.list.server.model.AGGrid;
import io.list.server.model.Student;
import io.list.server.service.implementation.AGGridServiceImpl;
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
    private final AGGridServiceImpl agGridService;

    public ServerResource(ServerServiceImpl serverService, AGGridServiceImpl agGridService) {
        this.serverService = serverService;
        this.agGridService = agGridService;
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
                .data(of("student", serverService.createStudent(student)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @PatchMapping("/updateStudent")
    public ResponseEntity<Response> updateStudentServer(@RequestBody
                                                      @Valid Student student) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.updateStudent(student)))
                .message("Student updated")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }

    @GetMapping("/listStudents")
    public ResponseEntity<Response> getStudents() throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("students", serverService.students(50)))
                .message("Servers retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/agGridInfo")
    public ResponseEntity<Response> getAGGridInfo() throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("agGrid", agGridService.getInfo()))
                .message("agGrid info retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PatchMapping("/updateAGGridInfo")
    public ResponseEntity<Response> updateAGGridInfo(@RequestBody
                                                         @Valid AGGrid aggrid) throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("agGrid", agGridService.updateAGGrid(aggrid)))
                .message("agGrid info retrieved")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build());
    }
}
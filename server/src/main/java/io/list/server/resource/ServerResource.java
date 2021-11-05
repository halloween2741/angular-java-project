package io.list.server.resource;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;

import static java.util.Map.of;

import io.list.server.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import io.list.server.enumeration.Status;
import io.list.server.model.Response;
import io.list.server.model.Server;
import io.list.server.service.implementation.ServerServiceImpl;

@RestController
@RequestMapping("/server")
public class ServerResource {
    private final ServerServiceImpl serverService;

    public ServerResource(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                                         .timeStamp(now())
                                         .data(of("deleted", serverService.delete(id)))
                                         .message("Server deleted")
                                         .status(OK)
                                         .statusCode(OK.value())
                                         .build());
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Response> deleteStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(of("deleted", serverService.deleteStudent(id)))
                .message("Server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> pingServer(@RequestBody
    @Valid Server server) {
        return ResponseEntity.ok(Response.builder()
                                         .timeStamp(now())
                                         .data(of("server", serverService.create(server)))
                                         .message("Server created")
                                         .status(CREATED)
                                         .statusCode(CREATED.value())
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

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);

        return ResponseEntity.ok(Response.builder()
                                         .timeStamp(now())
                                         .data(of("server", server))
                                         .message((server.getStatus() == Status.SERVER_UP)
                                                  ? "Ping Success"
                                                  : "Ping failed")
                                         .status(OK)
                                         .statusCode(OK.value())
                                         .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.builder()
                                         .timeStamp(now())
                                         .data(of("server", serverService.get(id)))
                                         .message("Server retrieved")
                                         .status(OK)
                                         .statusCode(OK.value())
                                         .build());
    }

    @GetMapping(
        path     = "/image/{fileName}",
        produces = IMAGE_PNG_VALUE
    )
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get("" + fileName));
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
        return ResponseEntity.ok(Response.builder()
                                         .timeStamp(now())
                                         .data(of("servers", serverService.list(30)))
                                         .message("Servers retrieved")
                                         .status(OK)
                                         .statusCode(OK.value())
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


//~ Formatted by Jindent --- http://www.jindent.com

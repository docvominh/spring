package com.vominh.example.spring.mongo.api;

import com.vominh.example.spring.mongo.data.model.GenericModel;
import com.vominh.example.spring.mongo.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GenericApi<MODEL extends GenericModel> {

    private final GenericService service;

    public GenericApi(GenericService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public ResponseEntity<MODEL> get(@PathVariable("id") String id) {
        return ResponseEntity.ok((MODEL) service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<MODEL> create(@RequestBody MODEL model) {
        return ResponseEntity.ok((MODEL) service.save(model));
    }

    @PutMapping("")
    public ResponseEntity<MODEL> update(@RequestBody MODEL model) {
        return ResponseEntity.ok((MODEL) service.update(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] export(HttpServletResponse response) throws IOException {
        response.setHeader("Content-disposition", "attachment; filename=export.json");
        return service.export();
    }

    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importJson(@RequestParam("file") MultipartFile file) throws IOException {
        int documentInserted = service.importFromFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Import %s document", documentInserted));
    }
}

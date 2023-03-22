package com.vominh.example.spring.mongo.api;

import com.vominh.example.spring.mongo.data.model.OfficeModel;
import com.vominh.example.spring.mongo.service.OfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/office")
public class OfficeApi extends GenericApi<OfficeModel>{

    private final OfficeService service;

    public OfficeApi(OfficeService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OfficeModel> get(@PathVariable("id") String officeId) {
        return ResponseEntity.ok(service.findByOfficeId(officeId));
    }

//    @PostMapping("")
//    public ResponseEntity<OfficeModel> create(@RequestBody OfficeModel model) {
//        return ResponseEntity.ok(service.save(model));
//    }

//    @PutMapping("")
//    public ResponseEntity<OfficeModel> update(@RequestBody OfficeModel model) {
//        return ResponseEntity.ok(service.update(model));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable("id") String officeId) {
//        service.delete(officeId);
//        return ResponseEntity.ok("deleted");
//    }
}

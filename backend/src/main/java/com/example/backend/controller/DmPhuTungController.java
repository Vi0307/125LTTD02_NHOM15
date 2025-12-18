package com.example.backend.controller;

import com.example.backend.entity.DmPhuTung;
import com.example.backend.service.DmPhuTungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc-phutung")
@CrossOrigin(origins = "*")
public class DmPhuTungController {

    @Autowired
    private DmPhuTungService dmPhuTungService;

    @GetMapping
    public List<DmPhuTung> getAllDanhMuc() {
        return dmPhuTungService.getAllDanhMuc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DmPhuTung> getDanhMucById(@PathVariable String id) {
        return dmPhuTungService.getDanhMucById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DmPhuTung createDanhMuc(@RequestBody DmPhuTung danhMuc) {
        return dmPhuTungService.saveDanhMuc(danhMuc);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DmPhuTung> updateDanhMuc(@PathVariable String id, @RequestBody DmPhuTung danhMucDetails) {
        return dmPhuTungService.getDanhMucById(id)
                .map(danhMuc -> {
                    danhMuc.setTenDanhMuc(danhMucDetails.getTenDanhMuc());
                    return ResponseEntity.ok(dmPhuTungService.saveDanhMuc(danhMuc));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable String id) {
        if (dmPhuTungService.getDanhMucById(id).isPresent()) {
            dmPhuTungService.deleteDanhMuc(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

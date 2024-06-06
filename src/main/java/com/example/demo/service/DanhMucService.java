package com.example.demo.service;

import com.example.demo.entity.DanhMuc;
import com.example.demo.repos.DanhMucRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService {
    private final DanhMucRepository danhMucRepository;

    public DanhMucService(DanhMucRepository danhMucRepository) {
        this.danhMucRepository = danhMucRepository;
    }

    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    public DanhMuc saveDanhMuc(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    public void deleteDanhMucById(int id) {
        danhMucRepository.deleteById(id);
    }

    public DanhMuc getDanhMucById(int id) {
        return danhMucRepository.findById(id).orElse(null);
    }
}

package com.example.demo.service;

import com.example.demo.entity.Size;
import com.example.demo.repos.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    public void deleteSizeById(int id) {
        sizeRepository.deleteById(id);
    }

    public Size getSizeById(int id) {
        return sizeRepository.findById(id).orElse(null);
    }
}

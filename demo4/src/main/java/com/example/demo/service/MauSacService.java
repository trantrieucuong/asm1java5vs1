package com.example.demo.service;

import com.example.demo.entity.MauSac;
import com.example.demo.repos.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacService {
    private final MauSacRepository mauSacRepository;

    @Autowired
    public MauSacService(MauSacRepository mauSacRepository) {
        this.mauSacRepository = mauSacRepository;
    }

    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }

    public MauSac saveMauSac(MauSac mauSac) {
        return mauSacRepository.save(mauSac);
    }

    public void deleteMauSacById(int id) {
        mauSacRepository.deleteById(id);
    }

    public MauSac getMauSacById(int id) {
        return mauSacRepository.findById(id).orElse(null);
    }
}

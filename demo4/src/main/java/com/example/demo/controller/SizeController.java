package com.example.demo.controller;

import com.example.demo.entity.Size;
import com.example.demo.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("/size/list")
    public String sizeList(Model model) {
        List<Size> sizes = sizeService.getAllSizes();
        model.addAttribute("sizes", sizes);
        return "size-list";
    }

    @GetMapping("/size/add")
    public String showAddForm(Model model) {
        model.addAttribute("size", new Size());
        return "size-add";
    }

    @PostMapping("/size/add")
    public String addSize(Size size) {
        sizeService.saveSize(size);
        return "redirect:/size/list";
    }

    @GetMapping("/size/delete/{id}")
    public String deleteSize(@PathVariable("id") int id) {
        sizeService.deleteSizeById(id);
        return "redirect:/size/list";
    }

    @GetMapping("/size/detail/{id}")
    public String showSizeDetail(@PathVariable("id") int id, Model model) {
        Size size = sizeService.getSizeById(id);
        model.addAttribute("size", size);
        return "size_detail";
    }

    @GetMapping("/size/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Size size = sizeService.getSizeById(id);
        model.addAttribute("size", size);
        return "size-update";
    }

    @PostMapping("/size/update/{id}")
    public String updateSize(@PathVariable("id") int id, Size updatedSize) {
        Size existingSize = sizeService.getSizeById(id);
        existingSize.setMaSize(updatedSize.getMaSize());
        existingSize.setTenSize(updatedSize.getTenSize());
        existingSize.setTrangThai(updatedSize.getTrangThai());
        sizeService.saveSize(existingSize);
        return "redirect:/size/list";
    }
}

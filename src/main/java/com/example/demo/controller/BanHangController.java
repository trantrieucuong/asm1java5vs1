package com.example.demo.controller;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BanHangController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/chon")
    public String chon(Model model) {
        return "/banhang/chon";
    }

    @GetMapping("/index/khachhang/add")
    public String showAddFormKhachHang(Model model) {
        model.addAttribute("KhachHang", new KhachHang());
        return "/banhang/khachhangaddindex";
    }

    @PostMapping("/index/khachhang/add")
    public String addAdmin(KhachHang khachHang) {
        khachHangService.saveKhachHang(khachHang);
        return "redirect:/index/add";
    }

    @PostMapping("/khachhang/search")
    public String searchKhachHangBySdt(@RequestParam("sdt") String sdt, Model model) {
        KhachHang khachHang = khachHangService.findKhachHangBySdt(sdt);

        if (khachHang != null) {
            model.addAttribute("khachHangs", khachHang);
            return "/banhang/taohoadon";
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy khách hàng với số điện thoại này.");
            return "/banhang/taohoadon";
        }
    }

    @GetMapping("/index")
    public String sanPhamList(@RequestParam("hoaDonId") int hoaDonId, Model model) {
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAllChiTietSanPham();
        model.addAttribute("chiTietSanPhams", chiTietSanPhams);
        model.addAttribute("hoaDonId", hoaDonId); // Truyền ID hóa đơn vừa tạo sang view
        return "/banhang/index";
    }

    @GetMapping("/index/add")
    public String showAddForm(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        List<KhachHang> khachHangs = khachHangService.getAllKhachHang();
        model.addAttribute("khachHangs", khachHangs);
        return "/banhang/taohoadon";
    }

    @PostMapping("/index/add")
    public String addHoaDon(HoaDon hoaDon, Model model) {
        hoaDonService.saveHoaDon(hoaDon);
        HoaDon lastSavedHoaDon = hoaDonService.getLastSavedHoaDon();
        return "redirect:/index?hoaDonId=" + lastSavedHoaDon.getId(); // Chuyển hướng sang danh sách sản phẩm kèm theo ID hóa đơn vừa tạo
    }
    @GetMapping("/banhang/hoadon/view")
    public String viewHoaDon(@RequestParam("hoaDonId") int hoaDonId, Model model) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietService.getHoaDonChiTietsByHoaDonId(hoaDonId);
        model.addAttribute("hoaDonChiTiets", hoaDonChiTiets);
        model.addAttribute("hoaDonId", hoaDonId); // Ensure hoaDonId is added to the model for reuse in Thymeleaf templates
        return "/banhang/hoadon";
    }
    @GetMapping("/banhang/hoadon/thanh-toan")
    public String thanhToanHoaDon(@RequestParam("hoaDonId") int hoaDonId, Model model) {
        HoaDon hoaDon = hoaDonService.getHoaDonById(hoaDonId);
        System.out.println("Hoa Don ID: " + hoaDonId);
        System.out.println("Hoa Don: " + hoaDon);
        if (hoaDon != null) {
            hoaDon.setTrangThai("Đã thanh toán");
            hoaDonService.saveHoaDon(hoaDon);
            System.out.println("Trang Thai: " + hoaDon.getTrangThai());
        }
        model.addAttribute("message", "Thanh toán thành công!");
        return "/banhang/thanhcong";  // Chuyển hướng tới trang thanhcong.html
    }
}

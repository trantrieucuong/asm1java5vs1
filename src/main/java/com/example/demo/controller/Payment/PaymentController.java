package com.example.demo.controller.Payment;

import com.example.demo.Config.Config;
import com.example.demo.service.HoaDonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final HoaDonService hoaDonService;

    public PaymentController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @GetMapping("/create_payment")
    public ResponseEntity<String> createPayment(@RequestParam long amount, @RequestParam String id) throws Exception {
        String vnp_Version = Config.vnp_Version;
        String vnp_Command = Config.vnp_Command;
        String vnp_OrderInfo = Config.getRandomNumber(8);
        String orderType = Config.vnp_OrderType;
        String vnp_TxnRef = "thanh toan don hang: " + id;
        String vnp_IpAddr = "14.248.82.236";
        String vnp_TmnCode = Config.vnp_TmnCode;
        long amount_a = amount * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount_a));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        String htmlResponse = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Redirecting...</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Redirecting to payment gateway...</p>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        window.location.href = \"" + paymentUrl + "\";\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlResponse);
    }

    @GetMapping("/vnpay_return")
    public ResponseEntity<Void> vnpayReturn(HttpServletRequest request) {
        Map<String, String> vnp_Params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            vnp_Params.put(entry.getKey(), entry.getValue()[0]);
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (vnp_SecureHash != null) {
            vnp_Params.remove("vnp_SecureHash");
        }

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=').append(fieldValue);
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                }
            }
        }

        String secureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        HttpHeaders headers = new HttpHeaders();
        if (secureHash.equals(vnp_SecureHash)) {
            // Payment success
            String txnRef = request.getParameter("vnp_TxnRef");
            String orderId = txnRef.split(":")[1].trim();

            // Update order status to "paid"
            hoaDonService.updateOrderStatus(Integer.parseInt(orderId), "paid");
            System.out.println("chạy vào if");
            // Redirect to success page
            headers.setLocation(URI.create("/banhang/hoadon/thanh-toan"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            System.out.println("chạy vào else");
            // Payment failure
            headers.setLocation(URI.create("/banhang/hoadon/thanh-toan"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }
}

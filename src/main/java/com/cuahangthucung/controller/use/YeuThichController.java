package com.cuahangthucung.controller.use;

import com.cuahangthucung.dto.use.yeuthich.YeuThichDTO;
import com.cuahangthucung.dto.use.yeuthich.YeuThichRequest;
import com.cuahangthucung.dto.use.yeuthich.YeuThichSearchRequest;
import com.cuahangthucung.dto.user.User.UserDTO;
import com.cuahangthucung.service.use.service.YeuThichService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/yeu-thich")
public class YeuThichController {

    private final YeuThichService yeuThichService;

    public YeuThichController(YeuThichService yeuThichService) {
        this.yeuThichService = yeuThichService;
    }

    /**
     * 1. Tìm kiếm danh sách yêu thích
     */
    @GetMapping("/search")
    public ResponseEntity<List<YeuThichDTO>> search(YeuThichSearchRequest request) {
        List<YeuThichDTO> list = yeuThichService.search(request);
        return ResponseEntity.ok(list);
    }

    /**
     * 2. Thêm sản phẩm vào danh sách yêu thích
     */
    @PostMapping
    public ResponseEntity<YeuThichDTO> add(@Valid @RequestBody YeuThichRequest request) {
        YeuThichDTO result = yeuThichService.saveRequest(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 3. Lấy danh sách yêu thích của một User
     */
    @GetMapping("/user/{maUser}")
    public ResponseEntity<List<YeuThichDTO>> getByMaUser(@PathVariable String maUser) {
        List<YeuThichDTO> list = yeuThichService.findByMaUser(maUser);
        return ResponseEntity.ok(list);
    }

    /**
     * 4. Xóa một sản phẩm khỏi danh sách yêu thích
     */
    @DeleteMapping("/detail")
    public ResponseEntity<String> deleteItem(
            @RequestParam("maUser") String maUser,
            @RequestParam("maSP") String maSP) {
        yeuThichService.deleteByUserAndSanPham(maUser, maSP);
        return ResponseEntity.ok("Đã xóa sản phẩm khỏi danh sách yêu thích");
    }

    /**
     * 5. Kiểm tra sản phẩm đã được yêu thích chưa
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(
            @RequestParam("maUser") String maUser,
            @RequestParam("maSP") String maSP) {
        boolean exists = yeuThichService.existsByUserAndSanPham(maUser, maSP);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count/{maSP}")
    public ResponseEntity<Long> countBySanPham(@PathVariable String maSP) {
        return ResponseEntity.ok(yeuThichService.countByMaSP(maSP));
    }


    @GetMapping("/product/{maSP}/users")
    public ResponseEntity<List<UserDTO>> getLikedUsers(@PathVariable String maSP) {
        List<UserDTO> users = yeuThichService.findUsersBySanPham(maSP);
        return ResponseEntity.ok(users);
    }

}
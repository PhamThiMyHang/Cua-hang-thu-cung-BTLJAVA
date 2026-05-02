package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
// Kế thừa frame Jpa để tự tạo các lệnh CRUD
@Repository
public interface  PetRepository extends JpaRepository<Pet, String> {


    //1. Tìm thú cưng theo một mã pet cụ thể
    // 2. Tìm thú cưng theo một mã pet cụ thể theo cú pháp gần đúng
    //3. Tìm thú cưng theo một ten pet cụ thể kon phân biệt hoa thường
    List<Pet> findByTenPetContainingIgnoreCase(String tenPet);
    // 4. Tìm thú cưng theo một ten pet cụ thể theo cú pháp gần đúng không phân biệt hoa thường
    //5. Tìm thú cưng theo một giống cụ thể
    List<Pet> findByGiong(String giong);
    //6. Tìm thú cưng theo một tuoi cụ thể
    //7. Tìm thú cưng có tuoi nằm trong khoảng a, b đươc truyền vào
    //8. Tim thú cưng nhỏ hơn một gia cụ thể
    //9. Tìm thú cưng lớn hơn một giá cụ thể
    //10. Tìm thú cưng có giá nằm trong khoảng a, b đươc truyền vào
    //12. Tìm thú cưng có cân nặng nằm trong khoảng a, b đươc truyền vào
    //13. Tìm thú cưngcosos tình trạng bình thương
    //14. Tìm thú cứng có tình trạng có bệnh
    // 15. Tìm thú cưng tại mã chuồng nhập vào
    // Lưu ý: "chuong" là tên biến đối tượng Chuong trong Entity Pet, "maChuong" là biến trong Entity Chuong
    List<Pet> findByChuong_MaChuong(String maChuong);
    // 16. Tìm thú cưng theo một mã chuồng cụ thể theo cú pháp gần đúng
    // 17. Tìm thú cưng tại mã KH nhập vào
    List<Pet> findByMaKH(String maKH);
    // 18. Tìm thú cưng theo một mã KH cụ thể theo cú pháp gần đúng
    // 19. Tìm thú cưng tại mã NV nhập vào
    List<Pet> findByMaNV(String maNV);
    // 20. Tìm thú cưng theo một mã NV cụ thể theo cú pháp gần đúng
    // 21. Tìm thus cưng theo một ngày gửi cụ thể đầy
    // 22. Tìm thú cưng gửi tới cửa hàng trong khoảng thời gian (a, b)
    // 21. Tìm thus cưng theo một ngày trả cụ thể đầy
    // 22. Tìm thú cưng cửa hàng trả trong khoảng thời gian (a, b)
    // 23. Tìm thú cưng đã được trả
    // 24. Tìm thú cưng chưa được trả
    List<Pet> findByNgayTraIsNull();


   // Chức năng tính toán:
    //1. Tính số lượng thú cưng có độ tuổi truyền vào
    //2. Tính số lượng thú cưng có độ tuổi nằm trong khoảng từ a đến b truyền vào
    //3. Tính số lượng thú cưng có giá từ a đến b truyền vào
    //4. Tính số lượng thú cưng có giá cân nặng trong khoảng từ a đến b
    //5. Tính số lượng thú cưng có tình trạng bình thường
    //6. Tính số lượng thú cưng có tình trạng có bệnh
    //7. Tính số lượng thú cưng của khách hàng có mã nhập vào
    //8. Tính số lượng thú cưng được gửi trong tháng a năm b được truyền vào
    //9. Tính số lượng thú cưng được gửi trong ngày
    // 10. Tính số lượng thú cưng được gửi trong khoảng thời gian a, b
    // 11. Tính số lượng thú cưng do nhân viên có mã nhập vào phụ trách
    //12. Tính số lượng thú cưng nằm trong mootj loại chuồng
   // 6. Đếm số lượng thú cưng đang ở trong một loại chuồng cụ thể (ví dụ: VIP)
   long countByChuong_LoaiChuong_MaLoaiChuong(String maLoaiChuong);


    //Một số chức năng khác
    //1. Lấy 10 thú cưng nặng nhất
    //2. Lấy 10 thú cưng đắt nhất
    //. Lấy 10 thú cưng gửi gần đây nhất nhất


    //prefix là 1 biến để truyền vào năm, tháng
    // 7. Truy vấn nâng cao: Tìm thú cưng cuối cùng được thêm vào trong tháng/năm cụ thể
    // Hàm này cực kỳ quan trọng để bạn thực hiện logic tự động sinh MaPet (P260401)
    @Query("SELECT p FROM Pet p WHERE p.maPet LIKE :prefix% ORDER BY p.maPet DESC LIMIT 1")
    Optional<Pet> findLastPetByPrefix(@Param("prefix") String prefix);


}
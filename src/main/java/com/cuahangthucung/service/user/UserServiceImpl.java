package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.user.UserRepository;
import com.cuahangthucung.repository.user.UserSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public List<UserDTO> search(UserSearchRequest request) {
        return repository.findAll(UserSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO saveRequest(UserRequest request) {
        User user = (request.getUserID() != null)
                ? repository.findById(request.getUserID()).orElse(new User())
                : new User();

        BeanUtils.copyProperties(request, user);

        // Xử lý roles (nếu có logic RoleRepository thì inject thêm)
        if (request.getRoleNames() != null) {
            // Logic gán Role sẽ được xử lý chi tiết hơn ở tầng cao hơn hoặc mapper
            user.getRoles().clear();
            // TODO: Map roleName sang Role entity nếu cần
        }

        return convertToDTO(repository.save(user));
    }

    @Override
    public UserDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User mã: " + id));
    }

    @Override
    public List<UserDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserSummaryDTO getSummary() {
        return new UserSummaryDTO(
                repository.countTotalUsers(),
                repository.countByStatus(com.cuahangthucung.entity.user.enums.UserStatus.ACTIVE),
                repository.countByStatus(com.cuahangthucung.entity.user.enums.UserStatus.INACTIVE),
                repository.countUsersHaveNhanVien(),
                repository.countUsersHaveKhachHang()
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    private UserDTO convertToDTO(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name());
        }

        // Lấy tên role
        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toSet()));
        }

        return dto;
    }
}
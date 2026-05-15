package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.user.UserRepository;
import com.cuahangthucung.repository.user.UserSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<UserDTO> search(UserSearchRequest request, Pageable pageable) {
        return repository.findAll(UserSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public UserDTO saveRequest(UserRequest request) {
        User user = (request.getUserID() != null)
                ? repository.findById(request.getUserID()).orElse(new User())
                : new User();

        // Copy properties nhưng bỏ qua roles để xử lý riêng
        BeanUtils.copyProperties(request, user, "roles");

        // TODO: Xử lý gán Role sau (cần inject RoleService)
        if (request.getRoleNames() != null && !request.getRoleNames().isEmpty()) {
            user.getRoles().clear();
            // Logic map roleName → Role entity sẽ được bổ sung sau
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

        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toSet()));
        }

        return dto;
    }
}
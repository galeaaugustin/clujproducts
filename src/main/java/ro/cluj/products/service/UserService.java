package ro.cluj.products.service;

import ro.cluj.products.dto.UserDetailDto;

import java.util.List;

public interface UserService {
    List<UserDetailDto> getAllUsers();
}
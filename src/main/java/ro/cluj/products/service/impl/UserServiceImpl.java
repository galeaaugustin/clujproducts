package ro.cluj.products.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.cluj.products.dto.UserDetailDto;
import ro.cluj.products.entity.User;
import ro.cluj.products.repository.UserRepository;
import ro.cluj.products.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public List<UserDetailDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((u) -> modelMapper.map(u, UserDetailDto.class))
                .collect(Collectors.toList());
    }
}
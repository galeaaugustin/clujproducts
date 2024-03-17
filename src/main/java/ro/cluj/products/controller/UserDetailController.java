package ro.cluj.products.controller;


import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cluj.products.dto.UserDetailDto;
import ro.cluj.products.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserDetailController {

    private UserService userService;

    public UserDetailController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<UserDetailDto>> getAllUsers(){
        List<UserDetailDto> usersDto = userService.getAllUsers();
        return ResponseEntity.ok(usersDto);
    }
}
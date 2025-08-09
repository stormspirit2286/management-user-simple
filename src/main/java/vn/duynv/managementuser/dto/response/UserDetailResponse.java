package vn.duynv.managementuser.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
}

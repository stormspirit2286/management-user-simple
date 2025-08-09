package vn.duynv.managementuser.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.duynv.managementuser.dto.request.UserCreationRequest;
import vn.duynv.managementuser.dto.response.UserDetailResponse;
import vn.duynv.managementuser.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    User toUser(UserCreationRequest request);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    UserDetailResponse toUserResponse(User user);
}

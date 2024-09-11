package by.vanzoneway.securityserviceapi.mapper;

import by.vanzoneway.securityserviceapi.dto.request.SignUpDto;
import by.vanzoneway.securityserviceapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(SignUpDto signUpDto);
}

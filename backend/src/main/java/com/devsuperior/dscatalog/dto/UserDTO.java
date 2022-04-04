package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.devsuperior.dscatalog.entities.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Setter(value = AccessLevel.NONE)
    private Set<RoleDTO> roles = new HashSet<>();
    
    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        roles = entity.getRoles().stream().map(RoleDTO::new).collect(Collectors.toSet());
    }
}

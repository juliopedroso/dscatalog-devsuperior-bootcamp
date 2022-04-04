package com.devsuperior.dscatalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserInsertDTO extends UserDTO {
    
    private String password;

}

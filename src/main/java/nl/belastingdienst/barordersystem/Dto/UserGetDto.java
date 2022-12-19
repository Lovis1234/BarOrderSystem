package nl.belastingdienst.barordersystem.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGetDto {

    public String username;
    @JsonSerialize
    public Set<AuthorityRequestDto> authorities;
}
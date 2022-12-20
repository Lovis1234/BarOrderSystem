package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRequestDto implements GrantedAuthority {
    private String authority;

    public AuthorityRequestDto(String username, String authority) {
        this.authority = authority;
    }
}
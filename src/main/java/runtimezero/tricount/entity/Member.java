package runtimezero.tricount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String loginId;
    private String name;
    @JsonIgnore
    private String password;
}

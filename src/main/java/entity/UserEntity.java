package entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    private String userID;
    private String name;
    private String email;
    private String password;
    private String contactDetails;
}

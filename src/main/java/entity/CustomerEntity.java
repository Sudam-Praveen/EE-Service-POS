package entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEntity {
    private String CustId;
    private String name;
    private String email;
    private String contactDetails;
}

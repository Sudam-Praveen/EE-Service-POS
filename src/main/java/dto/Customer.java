package dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    private String CustId;
    private String name;
    private String email;
    private String contactDetails;
}

package dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    private String orderID;
    private String userID;
    private String custId;
    private String date;
    private String productName;
    private String processing_zone;
}

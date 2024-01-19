package entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    private String orderID;
    private String userID;
    private String custId;
    private String date;
    private String productName;
    private String processing_zone;
}
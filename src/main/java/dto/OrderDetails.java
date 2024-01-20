package dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetails {
    private String orderID;
    private String userID;
    private String itemCode;
    private String itemName;
    private String customerName;
    private String customerEmail;
    private String finishedDate;
    private String additionalItems;
    private double additionalCost;
    private double total;
}

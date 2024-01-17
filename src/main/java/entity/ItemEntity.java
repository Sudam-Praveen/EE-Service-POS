package entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemEntity {
    private String userId;
    private String itemCode;
    private String productName;
    private String category;
    private double initialPrice;
}

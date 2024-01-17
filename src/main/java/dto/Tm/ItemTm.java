package dto.Tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemTm extends RecursiveTreeObject<ItemTm> {
    private String userId;
    private String itemCode;
    private String productName;
    private String category;
    private double initialPrice;
    private JFXButton btn;
}

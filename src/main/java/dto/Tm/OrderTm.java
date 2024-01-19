package dto.Tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Label;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String orderID;
    private String userId;
    private String productName;
    private String date;
    private Label processZone;
    private JFXButton btnDelete;
    private JFXButton btnConfirm;

}

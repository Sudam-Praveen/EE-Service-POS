package dto.Tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerTm extends RecursiveTreeObject<CustomerTm> {
    private String CustId;
    private String name;
    private String email;
    private String contactDetails;
    private JFXButton btn;
}

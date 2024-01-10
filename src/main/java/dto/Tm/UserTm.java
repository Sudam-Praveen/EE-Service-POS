package dto.Tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTm extends RecursiveTreeObject<UserTm> {
    private String userID;
    private String name;
    private String email;
    private String password;
    private String contactDetails;
    private JFXButton btn;
}

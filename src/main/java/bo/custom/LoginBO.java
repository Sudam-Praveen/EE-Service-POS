package bo.custom;

import bo.SuperBO;
import dto.User;

import java.util.List;

public interface LoginBO extends SuperBO {
    public boolean isPasswordCorrect(String userID, String userEnteredPW);
    public List<User> getUsers();
}

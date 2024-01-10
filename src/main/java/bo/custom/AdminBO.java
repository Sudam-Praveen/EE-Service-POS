package bo.custom;

import bo.SuperBO;
import dto.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminBO extends SuperBO {
    boolean saveUser(User dto) throws SQLException, ClassNotFoundException;
    List<User> getAllUsers() throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
    boolean updateUser(User dto) throws SQLException, ClassNotFoundException;
}

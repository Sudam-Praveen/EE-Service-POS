package DAO.Custom.Impl;

import DAO.SuperDAO;
import DB.DBConnection;
import bo.SuperBO;
import dto.User;
import entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements SuperDAO {

    public List<UserEntity> getAllUsers() {
        List<UserEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                UserEntity user = new UserEntity(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                );
                list.add(user);
            }
            return list;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

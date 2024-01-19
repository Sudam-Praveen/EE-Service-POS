package DAO.Custom.Impl;

import DAO.Custom.AdminDAO;
import DB.DBConnection;
import entity.OrderEntity;
import entity.UserEntity;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean save(UserEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO User VALUES(?,?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,entity.getUserID());
            pstm.setString(2,entity.getName());
            pstm.setString(3,entity.getEmail());
            pstm.setString(4,entity.getPassword());
            pstm.setString(5,entity.getContactDetails());

            int rst = pstm.executeUpdate();
            if(rst>0){
                return true;
            }


        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public boolean update(UserEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE User SET name=? , email=? , password=? , ContactDetails=? WHERE User_ID=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,entity.getName());
            pstm.setString(2, entity.getEmail());
            pstm.setString(3, entity.getPassword());
            pstm.setString(4, entity.getContactDetails());
            pstm.setString(5, entity.getUserID());
            int result = pstm.executeUpdate();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM User WHERE User_ID=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,id);
            int result = pstm.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<UserEntity> getAll() throws SQLException, ClassNotFoundException {
        List<UserEntity> list =new ArrayList<>();
        String sql = "SELECT * FROM User";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next()){
            UserEntity c = new UserEntity(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)

            );
            list.add(c);
        }
        return list;
    }
}

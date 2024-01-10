package DAO.Custom.Impl;

import DAO.Custom.CustomerDAO;
import DB.DBConnection;
import entity.CustomerEntity;
import entity.UserEntity;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(CustomerEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,entity.getCustId());
            pstm.setString(2,entity.getName());
            pstm.setString(3,entity.getEmail());
            pstm.setString(4,entity.getContactDetails());

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
    public boolean update(CustomerEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET name=? , email=? , ContactDetails=? WHERE CustomerID=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,entity.getName());
            pstm.setString(2, entity.getEmail());
            pstm.setString(3, entity.getContactDetails());
            pstm.setString(4, entity.getCustId());
            int result = pstm.executeUpdate();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE CustomerID=?";

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
    public List<CustomerEntity> getAll() throws SQLException, ClassNotFoundException {
        List<CustomerEntity> list =new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next()){
            CustomerEntity c = new CustomerEntity(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)

            );
            list.add(c);
        }
        return list;
    }
}

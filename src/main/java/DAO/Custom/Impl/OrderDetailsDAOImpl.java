package DAO.Custom.Impl;

import DAO.Custom.OrderDetailsDAO;
import DB.DBConnection;
import entity.ItemEntity;
import entity.OrderDetailsEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderDetailsEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderDetails VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, entity.getOrderID());
        pstm.setString(2, entity.getUserID());
        pstm.setString(3, entity.getItemCode());
        pstm.setString(4, entity.getItemName());
        pstm.setString(5, entity.getCustomerName());
        pstm.setString(6, entity.getCustomerEmail());
        pstm.setString(7, entity.getFinishedDate());
        pstm.setString(8, entity.getAdditionalItems());
        pstm.setString(9, String.valueOf(entity.getAdditionalCost()));
        pstm.setString(10, String.valueOf(entity.getTotal()));
        int result = pstm.executeUpdate();
        if (result > 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean update(OrderDetailsEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetailsEntity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orderdetails";
        List<OrderDetailsEntity> details = new ArrayList<>();
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            details.add(new OrderDetailsEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9),
                    resultSet.getDouble(10)
            ));

        }
        return details;
    }
}


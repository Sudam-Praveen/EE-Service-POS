package DAO.Custom.Impl;

import DAO.Custom.OrderDAO;
import DB.DBConnection;
import dto.Order;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(OrderEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, entity.getOrderID());
        pstm.setString(2, entity.getUserID());
        pstm.setString(3, entity.getCustId());
        pstm.setString(4, entity.getDate());
        pstm.setString(5, entity.getProductName());
        pstm.setString(6, entity.getProcessing_zone());
        int result = pstm.executeUpdate();
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(OrderEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Orders SET Proccessing_Zone=? WHERE Order_ID=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,entity.getProcessing_zone());

            pstm.setString(2, entity.getOrderID());
            int result = pstm.executeUpdate();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Orders WHERE Order_ID=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, value);
            int result = pstm.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderEntity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orders";
        List<OrderEntity> orders = new ArrayList<>();
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            orders.add(new OrderEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            ));

        }
        return orders;
    }

    @Override
    public Order getLastOrder() {
        String sql = "SELECT * FROM Orders ORDER BY Order_ID DESC LIMIT 1";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                return new Order(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6)

                );
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

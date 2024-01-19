package DAO.Custom.Impl;

import DAO.Custom.ItemDAO;
import DB.DBConnection;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(ItemEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item VALUES(?,?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, entity.getItemCode());
        pstm.setString(2, entity.getUserId());
        pstm.setString(3, entity.getProductName());
        pstm.setString(4, entity.getCategory());
        pstm.setString(5, String.valueOf(entity.getInitialPrice()));
        int result = pstm.executeUpdate();
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(ItemEntity entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET Product_Name=? , Category=? , Initial_Price=? WHERE Item_Code=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,entity.getProductName());
            pstm.setString(2, entity.getCategory());
            pstm.setString(3, String.valueOf(entity.getInitialPrice()));
            pstm.setString(4, entity.getItemCode());
            int result = pstm.executeUpdate();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Item WHERE Item_Code=?";

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
    public List<ItemEntity> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item";
        List<ItemEntity> items = new ArrayList<>();
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            items.add(new ItemEntity(
                    resultSet.getString(2),
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));

        }
        return items;
    }
}

package bo.custom;

import bo.SuperBO;
import dto.Order;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    public String generateID();
    public boolean saveOrder(Order dto) throws SQLException, ClassNotFoundException;
    public List<Order> getAllOrders() throws SQLException, ClassNotFoundException;
    public boolean deleteOrderTable(String orderID) throws SQLException, ClassNotFoundException;
    public boolean updateOrder(Order dto) throws SQLException, ClassNotFoundException;
}

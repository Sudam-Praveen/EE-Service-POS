package bo.custom;

import bo.SuperBO;
import dto.Order;
import dto.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBO extends SuperBO {

    public boolean saveOrderDetails(OrderDetails dto) throws SQLException, ClassNotFoundException;
    public List<OrderDetails> getAllOrderDetails() throws SQLException, ClassNotFoundException;
    public boolean deleteOrderTable(String id) throws SQLException, ClassNotFoundException;
    public boolean updateOrderDetail(OrderDetails dto) throws SQLException, ClassNotFoundException;
}

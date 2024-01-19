package DAO.Custom;

import DAO.CrudDAO;
import dto.Order;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<OrderEntity> {
    Order getLastOrder();
    boolean save(OrderEntity entity) throws SQLException, ClassNotFoundException;
     boolean  update(OrderEntity entity) throws SQLException, ClassNotFoundException;
    boolean  delete(String value) throws SQLException, ClassNotFoundException;
    List<OrderEntity> getAll() throws SQLException, ClassNotFoundException;
}

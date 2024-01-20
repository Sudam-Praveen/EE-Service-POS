package DAO.Custom;

import DAO.CrudDAO;
import entity.OrderDetailsEntity;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO extends CrudDAO<OrderDetailsEntity> {
    boolean save(OrderDetailsEntity entity) throws SQLException, ClassNotFoundException;
    boolean  update(OrderDetailsEntity entity) throws SQLException, ClassNotFoundException;
    boolean  delete(String value) throws SQLException, ClassNotFoundException;
    List<OrderDetailsEntity> getAll() throws SQLException, ClassNotFoundException;
}

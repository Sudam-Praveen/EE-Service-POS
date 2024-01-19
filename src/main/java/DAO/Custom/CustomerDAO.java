package DAO.Custom;

import DAO.CrudDAO;
import entity.CustomerEntity;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerEntity> {
    boolean save(CustomerEntity entity) throws SQLException, ClassNotFoundException;

    boolean update(CustomerEntity entity) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<CustomerEntity> getAll() throws SQLException, ClassNotFoundException;
}

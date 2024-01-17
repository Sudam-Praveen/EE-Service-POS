package DAO.Custom;

import DAO.CrudDAO;
import entity.CustomerEntity;
import entity.ItemEntity;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<ItemEntity> {
    boolean save(ItemEntity entity) throws SQLException, ClassNotFoundException;
    boolean  update(ItemEntity entity) throws SQLException, ClassNotFoundException;
    boolean  delete(String value) throws SQLException, ClassNotFoundException;
    List<ItemEntity> getAll() throws SQLException, ClassNotFoundException;
}

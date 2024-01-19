package DAO.Custom;

import DAO.CrudDAO;
import entity.CustomerEntity;
import entity.OrderEntity;
import entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO extends CrudDAO<UserEntity> {
    boolean save(UserEntity entity) throws SQLException, ClassNotFoundException;

    boolean update(UserEntity entity) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<UserEntity> getAll() throws SQLException, ClassNotFoundException;


}

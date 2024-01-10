package bo.custom.impl;

import DAO.Custom.AdminDAO;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.AdminBO;
import dto.User;
import entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminBOImpl implements AdminBO {
    private final AdminDAO adminDAO= DAOFactory.getInstance().getDaoFactory(DAOType.USER);
    @Override
    public boolean saveUser(User dto) throws SQLException, ClassNotFoundException {
       return adminDAO.save(new UserEntity(
                dto.getUserID(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getContactDetails()
        ));

    }

    @Override
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> list =new ArrayList<>();
        List<UserEntity> allUsers = adminDAO.getAll();
        for (UserEntity entity:allUsers) {
             list.add(new User(
                    entity.getUserID(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getContactDetails()
            ));
        }
        return list;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
       return adminDAO.delete(id);
    }

    @Override
    public boolean updateUser(User dto) throws SQLException, ClassNotFoundException {
        return adminDAO.update(new UserEntity(
                dto.getUserID(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getContactDetails()
        ));

    }
}

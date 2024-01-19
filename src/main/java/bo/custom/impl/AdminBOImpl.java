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
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class AdminBOImpl implements AdminBO {
    private final AdminDAO adminDAO = DAOFactory.getInstance().getDaoFactory(DAOType.USER);



    public String setEncryptedpassword(User dto){
        String password = dto.getPassword();
        String encryptedpassword = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Display the unencrypted and encrypted passwords. */
        System.out.println("Plain-text password: "+password);
        System.out.println("Encrypted password using MD5: "+encryptedpassword);
        return encryptedpassword;
    }







    @Override
    public boolean saveUser(User dto) throws SQLException, ClassNotFoundException {
        String encryptPW = setEncryptedpassword(dto);
        return adminDAO.save(new UserEntity(
                dto.getUserID(),
                dto.getName(),
                dto.getEmail(),
                encryptPW,
                dto.getContactDetails()
        ));

    }

    @Override
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> list = new ArrayList<>();
        List<UserEntity> allUsers = adminDAO.getAll();
        for (UserEntity entity : allUsers) {
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

package bo.custom.impl;

import DAO.Custom.Impl.LoginDAOImpl;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.LoginBO;
import dto.User;
import entity.UserEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginBOImpl implements LoginBO {

    private final LoginDAOImpl loginDAO = DAOFactory.getInstance().getDaoFactory(DAOType.LOGIN);

    public boolean isPasswordCorrect(String userID, String userEnteredPW) {
        List<UserEntity> users = loginDAO.getAllUsers();
        for (UserEntity user : users) {

            if (user.getUserID().equals(userID)) {

                String storedHash = user.getPassword(); // User's stored hashed password
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(userEnteredPW.getBytes());
                    byte[] enteredBytes = md.digest();

                    // Convert the entered hash to hexadecimal format
                    StringBuilder enteredHash = new StringBuilder();
                    for (int i = 0; i < enteredBytes.length; i++) {
                        enteredHash.append(Integer.toString((enteredBytes[i] & 0xff) + 0x100, 16).substring(1));
                    }

                    // Compare the entered hash with the stored hash
                    boolean passwordCorrect = enteredHash.toString().equals(storedHash);

                    // Print the result
                    System.out.println("Entered password: " + userEnteredPW);
                    System.out.println("Entered hash: " + enteredHash.toString());
                    System.out.println("Stored hash: " + storedHash);
                    System.out.println("Password is " + (passwordCorrect ? "correct" : "incorrect"));

                    if (passwordCorrect) {
                        return true; // Exit the loop if the password is correct
                    }

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }

        return false; // If no user with a matching password is found
    }

    public List<User> getUsers(){
        List<UserEntity> users = loginDAO.getAllUsers();
        List<User> allusers = new ArrayList<>();
        for (UserEntity user:users) {
            allusers.add(new User(
                    user.getUserID(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getContactDetails()
            ));
        }
        return  allusers;
    }

}

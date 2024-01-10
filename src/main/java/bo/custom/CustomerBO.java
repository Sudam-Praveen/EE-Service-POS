package bo.custom;

import dto.Customer;
import dto.User;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO {
    boolean saveCustomer(Customer dto) throws SQLException, ClassNotFoundException;
    List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(Customer dto) throws SQLException, ClassNotFoundException;
}

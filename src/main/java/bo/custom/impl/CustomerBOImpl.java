package bo.custom.impl;

import bo.custom.CustomerBO;
import dto.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    @Override
    public boolean saveCustomer(Customer dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}

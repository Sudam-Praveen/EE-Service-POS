package bo.custom.impl;

import DAO.Custom.CustomerDAO;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.CustomerBO;
import dto.Customer;
import dto.User;
import entity.CustomerEntity;
import entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = DAOFactory.getInstance().getDaoFactory(DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(Customer dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new CustomerEntity(
                dto.getCustId(),
                dto.getName(),
                dto.getEmail(),
                dto.getContactDetails()
        ));
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<Customer> list =new ArrayList<>();
        List<CustomerEntity> allCustomers = customerDAO.getAll();
        for (CustomerEntity entity:allCustomers) {
            list.add(new Customer(
                    entity.getCustId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getContactDetails()
            ));
        }
        return list;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);

    }

    @Override
    public boolean updateCustomer(Customer dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new CustomerEntity(
                dto.getCustId(),
                dto.getName(),
                dto.getEmail(),
                dto.getContactDetails()
        ));
    }
}

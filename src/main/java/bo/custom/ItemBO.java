package bo.custom;

import bo.SuperBO;
import dto.Customer;
import dto.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(Item dto) throws SQLException, ClassNotFoundException;
    List<Item> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    boolean updateItem(Item dto) throws SQLException, ClassNotFoundException;
}

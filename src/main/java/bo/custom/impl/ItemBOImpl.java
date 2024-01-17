package bo.custom.impl;

import DAO.Custom.ItemDAO;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.ItemBO;
import dto.Item;
import entity.ItemEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = DAOFactory.getInstance().getDaoFactory(DAOType.ITEM);
    @Override
    public boolean saveItem(Item dto) throws SQLException, ClassNotFoundException {
       return itemDAO.save(new ItemEntity(
               dto.getUserId(),
               dto.getItemCode(),
               dto.getProductName(),
               dto.getCategory(),
               dto.getInitialPrice())

        );
    }

    @Override
    public List<Item> getAllItems() throws SQLException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        List<ItemEntity> allItems = itemDAO.getAll();
        for (ItemEntity entity:allItems) {
            items.add(new Item(
                    entity.getUserId(),
                    entity.getItemCode(),
                    entity.getCategory(),
                    entity.getProductName(),
                    entity.getInitialPrice()
            ));
        }
        return items;
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(Item dto) throws SQLException, ClassNotFoundException {
       return itemDAO.update(new ItemEntity(
                dto.getUserId(),
                dto.getItemCode(),
                dto.getProductName(),
                dto.getCategory(),
                dto.getInitialPrice()
        ));
    }
}

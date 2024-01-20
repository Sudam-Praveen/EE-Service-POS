package bo.custom.impl;

import DAO.Custom.OrderDetailsDAO;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.OrderDetailsBO;
import dto.Item;
import dto.OrderDetails;
import entity.ItemEntity;
import entity.OrderDetailsEntity;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsImpl implements OrderDetailsBO {
    private final OrderDetailsDAO orderDetailsDAO = DAOFactory.getInstance().getDaoFactory(DAOType.ORDER_DETAIL);




    @Override
    public boolean saveOrderDetails(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.save(new OrderDetailsEntity(
                dto.getOrderID(),
                dto.getUserID(),
                dto.getItemCode(),
                dto.getItemName(),
                dto.getCustomerName(),
                dto.getCustomerEmail(),
                dto.getFinishedDate(),
                dto.getAdditionalItems(),
                dto.getAdditionalCost(),
                dto.getTotal()
        ));
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        List<OrderDetails> details = new ArrayList<>();
        List<OrderDetailsEntity> allDetails = orderDetailsDAO.getAll();
        for (OrderDetailsEntity entity:allDetails) {
            details.add(new OrderDetails(
                    entity.getOrderID(),
                    entity.getUserID(),
                    entity.getItemCode(),
                    entity.getItemName(),
                    entity.getCustomerName(),
                    entity.getCustomerEmail(),
                    entity.getFinishedDate(),
                    entity.getAdditionalItems(),
                    entity.getAdditionalCost(),
                    entity.getTotal()

            ));
        }
        return details;
    }

    @Override
    public boolean deleteOrderTable(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOrderDetail(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }


}

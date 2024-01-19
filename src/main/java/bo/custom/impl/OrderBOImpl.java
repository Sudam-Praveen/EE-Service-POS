package bo.custom.impl;

import DAO.Custom.OrderDAO;
import DAO.DAOFactory;
import DAO.Utill.DAOType;
import bo.custom.OrderBO;
import dto.Item;
import dto.Order;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = DAOFactory.getInstance().getDaoFactory(DAOType.ORDER);

    public String generateID() {
        Order dto = orderDAO.getLastOrder();
        if (dto != null) {
            String id = dto.getOrderID();
            try {
                int num = Integer.parseInt(id.split("D")[1]);
                num++;
                return (String.format("D%03d", num));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

                return ("D001");
            }
        } else {
            return ("D001");
        }
    }

    @Override
    public boolean saveOrder(Order dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new OrderEntity(
                dto.getOrderID(),
                dto.getUserID(),
                dto.getCustId(),
                dto.getDate(),
                dto.getProductName(),
                dto.getProcessing_zone()

        ));
    }

    @Override
    public List<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderEntity> allOrders = orderDAO.getAll();
        List<Order> orders = new ArrayList<>();
        for (OrderEntity order : allOrders) {
            orders.add(new Order(
                    order.getOrderID(),
                    order.getUserID(),
                    order.getCustId(),
                    order.getDate(),
                    order.getProductName(),
                    order.getProcessing_zone()
            ));
        }
        return orders;
    }

    @Override
    public boolean deleteOrderTable(String orderID) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(orderID);
    }
    public boolean updateOrder(Order dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new OrderEntity(
                dto.getOrderID(),
                dto.getUserID(),
                dto.getCustId(),
                dto.getDate(),
                dto.getProductName(),
                dto.getProcessing_zone()
        ));
    }
}


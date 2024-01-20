package DAO;

import DAO.Custom.Impl.*;
import DAO.Utill.DAOType;
import bo.custom.impl.AdminBOImpl;

public class DAOFactory {
    public static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getInstance(){
        return daoFactory != null ? daoFactory : (daoFactory=new DAOFactory());
    }
    public <T extends SuperDAO>T getDaoFactory(DAOType type){
        switch (type){
            case USER:return (T) new AdminDAOImpl();
            case LOGIN:return (T) new LoginDAOImpl();
            case CUSTOMER:return (T) new CustomerDAOImpl();
            case ITEM:return (T) new ItemDAOImpl();
            case ORDER:return (T) new OrderDAOImpl();
            case ORDER_DETAIL:return (T)new OrderDetailsDAOImpl();


        }
        return null;
    }
}

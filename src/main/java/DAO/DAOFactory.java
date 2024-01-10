package DAO;

import DAO.Custom.Impl.AdminDAOImpl;
import DAO.Custom.Impl.CustomerDAOImpl;
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
            case CUSTOMER:return (T) new CustomerDAOImpl();


        }
        return null;
    }
}

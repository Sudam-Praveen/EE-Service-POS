package bo;

import DAO.Utill.BOType;
import bo.custom.impl.AdminBOImpl;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;

import static DAO.Utill.BOType.USER;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getInstance(){
        return boFactory!=null ? boFactory : (boFactory=new BOFactory());
    }

    public <T extends SuperBO>T getBoFactory(BOType type) {
        switch(type){
            case USER:return (T) new AdminBOImpl();
            case CUSTOMER:return (T)new CustomerBOImpl();
            case ITEM:return  (T)new ItemBOImpl();

        }
        return null;
    }
}

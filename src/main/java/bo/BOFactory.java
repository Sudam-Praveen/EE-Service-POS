package bo;

import DAO.Utill.BOType;
import bo.custom.impl.*;

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
            case LOGIN:return (T) new LoginBOImpl();
            case CUSTOMER:return (T)new CustomerBOImpl();
            case ITEM:return  (T)new ItemBOImpl();
            case ORDER:return (T)new OrderBOImpl();
            case ORDER_DETAIL:return (T)new OrderDetailsImpl();

        }
        return null;
    }
}

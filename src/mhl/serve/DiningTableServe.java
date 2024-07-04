package mhl.serve;

import mhl.dao.DiningTableDAO;
import mhl.daomain.DiningTable;

import java.util.List;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class DiningTableServe {
    //定义一个DiningTableDAO 属性
    private DiningTableDAO diningTableDAO = new DiningTableDAO();
    //返回所有餐桌信息
    public List<DiningTable> list(){
        return diningTableDAO.queryMultiply("select id,state from diningTable",DiningTable.class);

    }

    //提供一个 更新 餐桌状态的方法
    public boolean updateDiningTableState(String state ,Integer id ){
        int update = diningTableDAO.update("update diningTable set state = ? where id = ?", state, id);
        return update>0;
    }

    //根据id返回 DiningTable 对象
    //如果返回的是null，就表明id编号对应的餐桌不存在
    public DiningTable getDiningTableById(int id ){
        return diningTableDAO.querySingle("select * from diningTable where id = ?",DiningTable.class,id);
    }
    //如果餐桌可以预订，调用方法，对其状态进行更新（包括预订人的名字和电话）
    public boolean update(int id ,String orderName,String orderTel){
        int update= diningTableDAO.update("update diningTable set state='已经预定', orderName=?,orderTel=? where id = ?",orderName,orderTel,id);
        return update>0;
    }

    //提供方法，将指定餐桌设置成空闲状态
    public boolean updateDiningTableToFree(int id, String state){
        int update = diningTableDAO.update("update diningTable set state = ? , orderName='',orderTel='' where id =?"
                ,state,id);
        return update>0;
    }


}

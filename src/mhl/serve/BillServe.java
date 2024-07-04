package mhl.serve;

import mhl.dao.BillDAO;
import mhl.dao.MultiTabDAO;
import mhl.daomain.Bill;
import mhl.daomain.MultiTabBean;

import java.util.List;
import java.util.UUID;

/**
 * @author 王俊彪
 * @version 1.0
 * 该类用于完成对bill表的各种操作 （通过BillDAO）
 */
public class BillServe {
    //定义BillDAO属性
    private BillDAO billDAO = new BillDAO();
    //协同作战，完成一个业务，定义一个菜单业务属性
    private MenuServe menuServe = new MenuServe();
    //协同作战 diningTable 表
    private DiningTableServe diningTableServe = new DiningTableServe();

    private MultiTabDAO multiTabDao = new MultiTabDAO();

    //编写点餐的方式
    //1、生成订单
    //2、需要更新对应餐桌的状态
    public boolean orderMenu(int menuId,int nums, int diningTableId){
        //生成一个订单号
        String billId = UUID.randomUUID().toString();
        //将帐单生成到bill表，要求直接计算订单金额
        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未结帐')"
                ,billId,menuId,nums,menuServe.getMenuById(menuId).getPrice()*nums,diningTableId);
        if(update <=0){
            return false;
        }
        //需要更新对应表餐桌的状态
        return diningTableServe.updateDiningTableState("就餐中",diningTableId);
    }

    //返回所有账单，提供给view 调用
    public List<Bill> list(){
        return billDAO.queryMultiply("select * from bill",Bill.class);
    }

    public List<MultiTabBean> list2(){
        return multiTabDao.queryMultiply("select bill.*,name from bill,menu where menu.id = bill.menuId",MultiTabBean.class);
    }



    //查看某个餐桌是否有未结账的账单
    public boolean hasPayBillByDiningTableId(int id){
        Bill bill = billDAO.querySingle("select * from bill where diningTableId = ? and state = '未结账' limit 0,1", Bill.class, id);
        return  bill !=null;
    }

    //完成结账【如果餐桌存在，并且该餐桌有未结账的账单】
    public boolean payBill(int diningTableId,String payMode){
        //1、修改bill 表
        int update = billDAO.update("update bill set state = ? where diningTableId = ? and state = '未结账'"
                , payMode, diningTableId);
        if(update <= 0){//如果更新没有成功，表示失败
            return false;
        }

        //2、修改diningTable 表
        //注意不要直接在这里操作，而应该调用DiningTableServe 方法
        if(!(diningTableServe.updateDiningTableToFree(diningTableId,"空"))){
            return false;
        }
        return true;

    }
}

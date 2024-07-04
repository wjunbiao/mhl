package mhl.view;

import mhl.daomain.*;
import mhl.serve.BillServe;
import mhl.serve.DiningTableServe;
import mhl.serve.EmployeeServe;
import mhl.serve.MenuServe;
import mhl.utils.Utility;

import java.util.List;

/**
 * @author 王俊彪
 * @version 1.0
 * 这是主界面
 */
public class MHLview {
    public static void main(String[] args) {
        new MHLview().mainMenu();
    }

    //控制是否退出菜单
    private boolean loop = true;
    //用于接受用户的输入
    private String key;
    //定义EmployeeServe 属性
    private EmployeeServe employeeServe = new EmployeeServe();
    //定义DiningTableServe 属性
    private DiningTableServe diningTableServe = new DiningTableServe();
    //定义MenuServe 属性
    private MenuServe menuServe = new MenuServe();
    //定义BillServe 属性
    private BillServe billServe = new BillServe();

    //完成结账
    public void payBill(){
        System.out.println("==============结账服务==============");
        System.out.print("请选择要结账的餐桌编号（-1退出）：");
        int diningTableId = Utility.readInt();
        if(diningTableId == -1){
            System.out.println("==============取消结账==============");
            return;
        }
        //验证餐桌是否存在
        DiningTable diningTable = diningTableServe.getDiningTableById(diningTableId);
        if (diningTable == null){
            System.out.println("==============结账餐桌不存在==============");
            return;
        }
        //验证餐桌是否有需要结账的账单
        if(!billServe.hasPayBillByDiningTableId(diningTableId)){
            System.out.println("==============该餐位没有未结账的账单==============");
            return;
        }

        System.out.print("结账方式（现金/支付宝/微信）回车表示退出：");
        String payMode = Utility.readString(50,"");
        if("".equals(payMode)){
            System.out.println("==============取消结账==============");
            return;
        }

        System.out.print("是否结账（y/n）:");
        char key = Utility.readConfirmSelection();
        if(key != 'Y'){
            System.out.println("==============取消结账==============");
            return;
        }
        if( billServe.payBill(diningTableId, payMode)){
            System.out.println("==============结账完毕==============");
        }else{
            System.out.println("==============结账失败==============");
        }

    }



    //显示账单信息
//    public void listBill(){
//        System.out.println("==============账单信息==============");
//        System.out.println("编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
//        List<Bill> list = billServe.list();
//        for (Bill bill : list) {
//            System.out.println(bill);
//        }
//        System.out.println("==============显示完毕！==============");
//    }

    public void listBill(){
        System.out.println("==============账单信息==============");
        System.out.println("编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t\t菜名");
        List<MultiTabBean> list = billServe.list2();
        for (MultiTabBean bill : list) {
            System.out.println(bill);
        }
        System.out.println("==============显示完毕！==============");
    }

    //完成点餐
    public void orderMenu(){
        System.out.println("==============点餐服务==============");
        System.out.print("请输入点餐的餐桌（-1退出）：");
        int orderDiningTableId = Utility.readInt();
        if(orderDiningTableId == -1){
            System.out.println("==============取消点餐==============");
            return;
        }
        System.out.print("请输入点餐的菜品号（-1退出）：");
        int orderMenuId = Utility.readInt();
        if(orderMenuId == -1){
            System.out.println("==============取消点餐==============");
            return;
        }
        System.out.print("请输入点餐的菜品量（-1退出）：");
        int orderNums = Utility.readInt();
        if(orderNums == -1){
            System.out.println("==============取消点餐==============");
            return;
        }
        //验证餐桌号是否存在
        DiningTable diningTable = diningTableServe.getDiningTableById(orderDiningTableId);
        if (diningTable == null){
            System.out.println("==============餐桌号不存在==============");
            return;
        }
        //验证菜品编号
        Menu menu = menuServe.getMenuById(orderMenuId);
        if (menu == null){
            System.out.println("==============菜品号不存在==============");
            return;
        }

        //点餐
       if( billServe.orderMenu(orderMenuId,orderNums,orderDiningTableId)){
           System.out.println("==============点餐成功==============");
       }else{
           System.out.println("==============点餐失败==============");
       }
    }

    //完成显示菜品
    public void showMenu(){
        System.out.println("菜品编号\t菜品名\t\t\t类别\t\t价格");
        List<Menu> menus = menuServe.getMenus();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
        System.out.println("==============显示完毕！==============");
    }

    //完成订座
    public void orderDiningTable(){
        System.out.println("==============预订餐桌==============");
        System.out.print("请输入您要预订的座位(-1退出)：");
        int orderId = Utility.readInt();
        if(orderId==-1){
            System.out.println("==============取消预订==============");
            return;
        }
        //该方法得到的是Y 和 N
        System.out.print("确认是否预订(Y/N)：");
        char key = Utility.readConfirmSelection();
        if(key=='Y'){
            //根据orderId 返回对应的DiningTable对应，如果为null 说明对象不存在
            DiningTable diningTable = diningTableServe.getDiningTableById(orderId);
            if(diningTable==null){
                System.out.println("==============预订餐桌不存在==============");
                return;
            }
            //判断该餐桌的状态是否为空
            if(!("空".equals(diningTable.getState()))){
                System.out.println("==============预订餐桌已经预订或者处于就餐中==============");
                return;
            }
            //这时表示 真的要预订 更新餐桌状态
            System.out.print("请输入预订人的姓名：");
            String orderName = Utility.readString(50);
            System.out.print("请输入预订人的电话：");
            String orderTel = Utility.readString(50);
           if( diningTableServe.update(orderId,orderName,orderTel)){
               System.out.println("==============预订成功==============");
           }else{
               System.out.println("==============预订失败==============");
           }

        }else{
            System.out.println("==============取消预订==============");
        }


    }


    //显示所有餐桌状态
    public void listDiningTable(){
        List<DiningTable> list = diningTableServe.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
        System.out.println("==============显示完毕==============");
    }

    //显示主菜单
    public void mainMenu() {
        while (loop) {


            System.out.println("==============满汉楼==============");
            System.out.println("\t\t 1、登录满汉楼");
            System.out.println("\t\t 2、退出满汉楼");
            System.out.print("请输入您的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入用户名：");
                    String empId = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String pwd = Utility.readString(50);
                    //到数据库去判断
                    Employee employee = employeeServe.getEmployeeByIdAndPwd(empId, pwd);
                    if(employee!=null){
                        System.out.println("==============登录成功("+employee.getName()+")==============\n");
                        //显示二级菜单
                        while (loop) {
                            System.out.println("==============满汉楼(二级菜单)==============");
                            System.out.println("\t\t 1、 显示餐桌状态");
                            System.out.println("\t\t 2、 预定餐桌");
                            System.out.println("\t\t 3、 显示所有菜品");
                            System.out.println("\t\t 4、 点餐服务");
                            System.out.println("\t\t 5、 查看菜单");
                            System.out.println("\t\t 6、 结账");
                            System.out.println("\t\t 9、 退出满汉楼");
                            System.out.print("请输入您的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
                                    orderDiningTable();
                                    break;
                                case "3":
                                    showMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    listBill();
                                     break;
                                case "6":
                                    payBill();
                                     break;
                                case "9":
                                    loop = false;
                                    System.out.println("退出满汉楼");
                                     break;
                                default:
                                    System.out.println("您输入有误，请重新输入");
                            }
                        }
                    } else {
                        System.out.println("==============登录失败==============");
                    }
                    break;
                case "2":
                    System.out.println("退出满汉楼");
                    loop = false;
                    break;
                default:
                    System.out.println("您输入的有误！！！ 请重新输入");
            }
        }
        System.out.println("您退出了满汉楼项目");

    }
}

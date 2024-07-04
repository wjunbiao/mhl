package mhl.serve;

import mhl.dao.MenuDAO;
import mhl.daomain.Menu;

import java.util.List;

/**
 * @author 王俊彪
 * @version 1.0
 * 该类用于完成对 menu 表的各种操作，通过（MenuDAO）
 */
public class MenuServe {
    //定义一个MenuDAO属性
    private MenuDAO menuDAO = new MenuDAO();
    //返回当前所有菜品
    public List<Menu> getMenus(){
       return menuDAO.queryMultiply("select * from menu",Menu.class);
    }

    //通过id 返回查找的Menu
    public Menu getMenuById(int id){
        return menuDAO.querySingle("select * from menu where id = ?",Menu.class,id);
    }
}

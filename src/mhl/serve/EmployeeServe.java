package mhl.serve;

import mhl.dao.EmployeeDAO;
import mhl.daomain.Employee;

/**
 * @author 王俊彪
 * @version 1.0
 * 该类完成对employee 表的各种操作（通过调用EmployDAO对象来完成）
 */
public class EmployeeServe {

    //定义一个EmployeeDAO属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    //方法：根据empId和pwd 返回一个对象
    //如果查询不到就返回一个null
    public Employee getEmployeeByIdAndPwd(String empId,String pwd){
        return  employeeDAO.querySingle("select * from employee where empId = ? and pwd = MD5(?)"
                ,Employee.class,empId,pwd);

    }
}

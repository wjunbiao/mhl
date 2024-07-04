package mhl.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        //测试 Utility 工具类
        System.out.print("请输入一个整数：");
        int i = Utility.readInt();
        System.out.println(i);

        //测试JDBCUilsByDruid 工具类
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection);
    }
}

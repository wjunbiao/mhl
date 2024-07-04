package mhl.dao;

import mhl.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 王俊彪
 * @version 1.0
 * 开发 basicDao,是其他类的父类
 */
public class BasicDAO<T> {
    private QueryRunner qr = new QueryRunner();

    //开发通用的dml方法，针对任意表
    public int update(String sql,Object ...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection,sql, parameters);
            return  update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
    //返回多个对象（即查询的结果是多行），针对任意表
    public List<T> queryMultiply(String sql, Class<T> clazz, Object ...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            //new BeanListHanndler :处理器。（底层用的处理器模式）
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
    //查询单行结果的通用方法
    public T querySingle(String sql , Class<T>clazz,Object...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            //new BeanListHanndler :处理器。（底层用的处理器模式）
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //查询单行单列的方法，返回单值的方法
    public Object queryScalar(String sql , Object...parameters){
        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            //new BeanListHanndler :处理器。（底层用的处理器模式）
            return qr.query(connection, sql, new ScalarHandler<>(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
}

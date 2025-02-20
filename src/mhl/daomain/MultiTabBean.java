package mhl.daomain;

import java.sql.Timestamp;

/**
 * @author 王俊彪
 * @version 1.0
 * 这是一个javabean 与多表进行对应
 */
public class MultiTabBean {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private Timestamp billDate;
    private String state;
    //添加一个name列
    private String name;

    public MultiTabBean() {
    }

    public MultiTabBean(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId, Timestamp billDate, String state, String name) {
        this.id = id;
        this.billId = billId;
        this.menuId = menuId;
        this.nums = nums;
        this.money = money;
        this.diningTableId = diningTableId;
        this.billDate = billDate;
        this.state = state;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public Timestamp getBillDate() {
        return billDate;
    }

    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id + "\t\t"+
                menuId + "\t\t\t"+
                nums + "\t\t\t"+
                money + "\t"+
                diningTableId + "\t\t"+
                billDate + "\t\t"+
                state+ "\t\t"+
                name;
    }
}

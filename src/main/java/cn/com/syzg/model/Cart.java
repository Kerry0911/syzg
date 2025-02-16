package cn.com.syzg.model;

import java.io.Serializable;

public class Cart implements Serializable{
    private static final long serialVersionUID = -3352184760508469630L;

    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.emplyNo
     *
     * @mbg.generated
     */
    private String emplyno;


    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.id
     *
     * @param id the value for cart.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.emplyNo
     *
     * @return the value of cart.emplyNo
     *
     * @mbg.generated
     */
    public String getEmplyno() {
        return emplyno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.emplyNo
     *
     * @param emplyno the value for cart.emplyNo
     *
     * @mbg.generated
     */
    public void setEmplyno(String emplyno) {
        this.emplyno = emplyno == null ? null : emplyno.trim();
    }

    private Integer count;

    private Integer goodsId;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Cart(Integer id, String emplyno, Integer count, Integer goodsId) {
        this.id = id;
        this.emplyno = emplyno;
        this.count = count;
        this.goodsId = goodsId;
    }

    public Cart(String emplyno, Integer count, Integer goodsId) {
        this.emplyno = emplyno;
        this.count = count;
        this.goodsId = goodsId;
    }

    public Cart(Integer id, Integer count) {
        this.id = id;
        this.count = count;
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", emplyno='" + emplyno + '\'' +
                ", count=" + count +
                ", goodsId=" + goodsId +
                '}';
    }
}
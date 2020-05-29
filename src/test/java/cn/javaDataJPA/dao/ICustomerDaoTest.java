package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ICustomerDaoTest {
    @Autowired
    private ICustomerDao iCustomerDao;

    /**
     * 保存客户：调用save(obj)方法
     */
    @Test
    public void testSave() {
        Customer c = new Customer();
        c.setCustName("datajpa");
        iCustomerDao.save(c);
    }

    /**
     * 修改客户：调用save(obj)方法
     *      对于save方法的解释：如果执行此方法是对象中存在id属性，即为更新操作会先根据id查询，再更新
     *                      如果执行此方法中对象中不存在id属性，即为保存操作
     *
     */
    @Test
    public void testUpdate() {
        //根据id查询id为1的客户
        Customer customer = iCustomerDao.findOne(1l);
        //修改客户名称
        customer.setCustName("传智播客顺义校区");
        //更新
        iCustomerDao.save(customer);
    }

    /**
     * 根据id删除：调用delete(id)方法
     */
    @Test
    public void testDelete() {
        iCustomerDao.delete(1l);
    }

    /**
     * 根据id查询：调用findOne(id)方法
     */
    @Test
    public void testFindById() {
        Customer customer = iCustomerDao.findOne(3l);
        System.out.println(customer);
    }

    //自定方法
    @Test
    public void test() {
        List<Customer> list = iCustomerDao.findSql();
        for (Customer customer:list) {
            System.out.println(customer);
        }

    }
}

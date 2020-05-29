package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.Customer;
import cn.javaDataJPA.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Autowired
    private ILinkManDao iLinkManDao;


    /**
     * 保存操作
     * 需求:
     * 	保存一个客户和一个联系人
     * 要求：
     * 	创建一个客户对象和一个联系人对象
     *  建立客户和联系人之间关联关系（双向一对多的关联关系）
     *  先保存客户，再保存联系人
     * 问题：
     *		当我们建立了双向的关联关系之后，先保存主表，再保存从表时：
     *		会产生2条insert和1条update.
     * 		而实际开发中我们只需要2条insert。
     *
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testAdd() {
        Customer c = new Customer();
        c.setCustName("TBD云集中心");
        c.setCustLevel("VIP客户");
        c.setCustSource("网络");
        c.setCustIndustry("商业办公");
        c.setCustAddress("昌平区北七家镇");
        c.setCustPhone("010-84389340");

        LinkMan l = new LinkMan();
        l.setLkmName("TBD联系人");
        l.setLkmGender("male");
        l.setLkmMobile("13811111111");
        l.setLkmPhone("010-34785348");
        l.setLkmEmail("98354834@qq.com");
        l.setLkmPosition("老师");
        l.setLkmMemo("还行吧");

        c.getLinkMans().add(l);
        l.setCustomer(c);
        iCustomerDao.save(c);
        iLinkManDao.save(l);
    }

    @Test
    @Transactional
    @Rollback(false)//设置为不回滚
    public void testDelete() {
        iCustomerDao.delete(9l);
    }

    @Test
    //由于是在java代码中测试，为了解决no session问题，将操作配置到同一个事务中
    @Transactional
    public void testFindcus() {
        Customer customer = iCustomerDao.findOne(8l);
        Set<LinkMan> linkMans = customer.getLinkMans();//对象导航查询
        for(LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    @Test
    public void testFindlink() {
        LinkMan linkMan = iLinkManDao.findOne(1l);
        Customer customer = linkMan.getCustomer(); //对象导航查询
        System.out.println(customer);
    }



}

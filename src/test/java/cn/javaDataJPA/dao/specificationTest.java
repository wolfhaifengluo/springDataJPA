package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.Customer;
import cn.javaDataJPA.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class specificationTest {
    //依赖注入ICustomer
    @Autowired
    private ICustomerDao iCustomerDao;

    //依赖注入ICustomer
    @Autowired
    private ILinkManDao iLinkManDao;

    @Test
    public void testSpecifications() {
        //使用匿名内部类的方式，创建一个Specification的实现类，并实现toPredicate方法
        Specification <Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //cb:构建查询，添加查询方式   like：模糊匹配
                //root：从实体Customer对象中按照custName属性进行查询
                return cb.like(root.get("custName").as(String.class), "传智播客%");
            }
        };
        Customer customer = iCustomerDao.findOne(spec);
        System.out.println(customer);
    }

    //分页查询
    @Test
    public void testPage() {
        //构造查询条件
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("custName").as(String.class), "传智%");
            }
        };

        /**
         * 构造分页参数
         * 		Pageable : 接口
         * 			PageRequest实现了Pageable接口，调用构造方法的形式构造
         * 				第一个参数：页码（从0开始）
         * 				第二个参数：每页查询条数
         */
        Pageable pageable = new PageRequest(0, 5);

        /**
         * 分页查询，封装为Spring Data Jpa 内部的page bean
         * 		此重载的findAll方法为分页方法需要两个参数
         * 			第一个参数：查询条件Specification
         * 			第二个参数：分页参数
         */
        Page<Customer> page = iCustomerDao.findAll(spec,pageable);
        System.out.println(page.getContent());//得到数据集合列表
        System.out.println(page.getTotalElements());//得到的总条数
        System.out.println(page.getTotalPages());//得到的总页数

    }

    //specification 多表查询
    @Test
    public void testFind() {
        Specification<LinkMan> spec = new Specification<LinkMan>() {
            public Predicate toPredicate(Root<LinkMan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //Join代表链接查询，通过root对象获取
                //创建的过程中，第一个参数为关联对象的属性名称，第二个参数为连接查询的方式（left，inner，right）
                //JoinType.LEFT : 左外连接,JoinType.INNER：内连接,JoinType.RIGHT：右外连接
                Join<LinkMan, Customer> join = root.join("customer",JoinType.INNER);
                return cb.like(join.get("custName").as(String.class),"传智播客%");
            }
        };
        List<LinkMan> list = iLinkManDao.findAll(spec);
        for (LinkMan linkMan : list) {
            System.out.println(linkMan);
        }
    }






}

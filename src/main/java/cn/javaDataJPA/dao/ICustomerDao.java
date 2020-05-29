package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    //@Query 使用jpql的方式查询。
    @Query(value="from Customer")
    public List<Customer> findAllCustomer();

    //@Query 使用jpql的方式查询。?1代表参数的占位符，其中1对应方法中的参数索引
    @Query(value="from Customer where custName = ?1")
    public Customer findCustomer(String custName);

    @Query(value="update Customer set custName = ?1 where custId = ?2")
    @Modifying
    public void updateCustomer(String custName,Long custId);

    /**
     * nativeQuery : 使用本地sql的方式查询
     */
    @Query(value="select * from cst_customer",nativeQuery=true)
    public List<Customer> findSql();

    //方法命名方式查询（根据客户名称查询客户）
    public Customer findByCustName(String custName);



}

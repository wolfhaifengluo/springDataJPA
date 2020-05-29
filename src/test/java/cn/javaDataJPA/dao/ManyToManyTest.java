package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.SysRole;
import cn.javaDataJPA.domain.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private ISysRoleDao iSysRoleDao;

    @Autowired
    private ISysUserDao iSysUserDao;


    /**
     * 需求：
     * 	保存用户和角色
     * 要求：
     * 	创建2个用户和3个角色
     * 	让1号用户具有1号和2号角色(双向的)
     * 	让2号用户具有2号和3号角色(双向的)
     *  保存用户和角色
     * 问题：
     *  在保存时，会出现主键重复的错误，因为都是要往中间表中保存数据造成的。
     * 解决办法：
     * 	让任意一方放弃维护关联关系的权利
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void test1() {
        //创建对象
        SysUser u1 = new SysUser();
        u1.setUserName("用户1");
        SysRole r1 = new SysRole();
        r1.setRoleName("角色1");
        //建立关联关系
        u1.getRoles().add(r1);
        r1.getUsers().add(u1);
        //保存
        iSysUserDao.save(u1);
        iSysRoleDao.save(r1);
    }


    /**
            * 删除操作
	 * 	在多对多的删除时，双向级联删除根本不能配置
	 * 禁用
	 *	如果配了的话，如果数据之间有相互引用关系，可能会清空所有数据
	 */
    @Test
    @Transactional
    @Rollback(false)//设置为不回滚
    public void testDelete() {
        iSysUserDao.delete(1l);
    }

}

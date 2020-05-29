package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
}

package cn.javaDataJPA.dao;

import cn.javaDataJPA.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ISysRoleDao extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {
}

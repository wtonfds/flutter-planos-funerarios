package br.com.monitoratec.farol.sql.repository.permission;

import br.com.monitoratec.farol.sql.model.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findAll();
}

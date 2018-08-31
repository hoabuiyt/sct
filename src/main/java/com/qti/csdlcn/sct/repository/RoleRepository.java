package com.qti.csdlcn.sct.repository;

import com.qti.csdlcn.sct.model.Role;
import com.qti.csdlcn.sct.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by buithong.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

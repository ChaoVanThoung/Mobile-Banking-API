package kh.edu.cstad.mbbanking.repository;

import kh.edu.cstad.mbbanking.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
extends JpaRepository<Role,Integer> {
}

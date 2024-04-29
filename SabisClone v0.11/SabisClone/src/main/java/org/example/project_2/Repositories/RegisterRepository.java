package org.example.project_2.Repositories;

import org.example.project_2.Models.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {
}

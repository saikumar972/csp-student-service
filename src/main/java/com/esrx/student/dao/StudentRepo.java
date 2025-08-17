package com.esrx.student.dao;

import com.esrx.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<StudentEntity, Long> {
    @Query("from StudentEntity s where s.name=:name and s.id=:id")
    StudentEntity findStudentByNameAndId(Long id, String name);

    Optional<StudentEntity> findByName(String name);
}

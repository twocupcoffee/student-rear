package com.nwang.repository;

import com.nwang.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Long> {
    Clazz findByGradeAndClazz(String grade, String clazz);

    @Query(value = "SELECT DISTINCT(grade) FROM tb_clazz", nativeQuery=true)
    List<String> selectGrades();

    @Query(value = "SELECT DISTINCT(clazz) FROM tb_clazz where grade=?1", nativeQuery=true)
    List<String> selectClazzs(String grade);

}
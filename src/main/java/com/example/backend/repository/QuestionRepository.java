package com.example.backend.repository;

import com.example.backend.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAll(Pageable pageable);
    Page<Question> findByCategories_Id(Long categoryId, Pageable pageable);
    
    @Query("SELECT DISTINCT q FROM Question q JOIN q.categories c WHERE c.id IN :categoryIds")
    Page<Question> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);
} 
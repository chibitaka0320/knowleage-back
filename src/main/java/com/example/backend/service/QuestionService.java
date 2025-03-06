package com.example.backend.service;

import com.example.backend.entity.Question;
import com.example.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    // 質問を取得（ページング対応、カテゴリフィルタリング）
    public Page<Question> getQuestions(Pageable pageable, List<Long> categoryIds) {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            if (categoryIds.size() == 1) {
                return questionRepository.findByCategories_Id(categoryIds.get(0), pageable);
            }
            return questionRepository.findByCategoryIds(categoryIds, pageable);
        }
        return questionRepository.findAll(pageable);
    }

    // 全ての質問を取得
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // IDで質問を取得
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    // 新しい質問を作成
    public Question createQuestion(Question question) {
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        return questionRepository.save(question);
    }

    // 質問を更新
    public Optional<Question> updateQuestion(Long id, Question question) {
        return questionRepository.findById(id)
            .map(existingQuestion -> {
                question.setId(id);
                question.setCreatedAt(existingQuestion.getCreatedAt());
                question.setUpdatedAt(LocalDateTime.now());
                return questionRepository.save(question);
            });
    }

    // 質問を削除
    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 
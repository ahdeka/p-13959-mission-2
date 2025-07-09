package com.back.domain.answer.service;

import com.back.domain.answer.entity.Answer;
import com.back.domain.answer.repository.AnswerRepository;
import com.back.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(content);
        answerRepository.save(answer);
    }


}

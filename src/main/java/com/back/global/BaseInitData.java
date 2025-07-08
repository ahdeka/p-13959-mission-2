package com.back.global;

import com.back.domain.answer.entity.Answer;
import com.back.domain.answer.repository.AnswerRepository;
import com.back.domain.question.entity.Question;
import com.back.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            work1();
            work2();

        };
    }

    private void work1() {
        if (questionRepository.count() > 0) return;  // 이미 질문이 있다면 작업 중지

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    private void work2() {
        if (answerRepository.count() > 0) return;  // 이미 질문이 있다면 작업 중지
        Optional<Question> oq = questionRepository.findById(1);
        Question q = oq.get();
        Answer a1 = new Answer();
        a1.setContent("네, 자동으로 생성됩니다.");
        a1.setQuestion(q);
        answerRepository.save(a1);
    }
}

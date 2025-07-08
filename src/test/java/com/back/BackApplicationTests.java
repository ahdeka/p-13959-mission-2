package com.back;

import com.back.domain.answer.entity.Answer;
import com.back.domain.answer.repository.AnswerRepository;
import com.back.domain.question.entity.Question;
import com.back.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BackApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("질문 목록 조회")
    void t1() {
        List<Question> list = questionRepository.findAll();
        assertThat(list.size()).isEqualTo(2);

        Question q = list.get(0);
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @DisplayName("findById로 질문 조회")
    void t2() {
        Optional<Question> oq = questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
        }
    }

    @Test
    @DisplayName("findBySubject로 질문 조회")
    void t3() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent로 질문 조회")
    void t4() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectLike 질문 조회")
    void t5() {
        List<Question> questionList = questionRepository.findBySubjectLike("sbb%");
        Question q = questionList.get(0);
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @DisplayName("수정")
    void t6() {
        Optional<Question> oq = questionRepository.findById(1);
        assertThat(oq.isPresent()).isTrue();
        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
    }
    
    @Test
    @DisplayName("삭제")
    void t7() {
        assertThat(questionRepository.count()).isEqualTo(2);
        Optional<Question> oq = questionRepository.findById(1);
        assertThat(oq.isPresent()).isTrue();
        Question q = oq.get();
        questionRepository.delete(q);
        assertThat(questionRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 목록 조회")
    void t8() {
        Optional<Answer> oa = answerRepository.findById(1);
        assertThat(oa.isPresent()).isTrue();
        Answer a = oa.get();
        assertThat(a.getQuestion().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Transactional 테스트")
    @Transactional
    void t9() {
        Optional<Question> oq = questionRepository.findById(1);
        assertThat(oq.isPresent()).isTrue();
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertThat(answerList.size()).isEqualTo(1);
        assertThat(answerList.get(0).getContent()).isEqualTo("네, 자동으로 생성됩니다.");
    }



}

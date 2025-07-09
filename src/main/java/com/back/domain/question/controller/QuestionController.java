package com.back.domain.question.controller;

import com.back.domain.answer.entity.AnswerForm;
import com.back.domain.question.entity.Question;
import com.back.domain.question.entity.QuestionForm;
import com.back.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.findAll();
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "question_form";
        questionService.create(questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/list";
    }

}

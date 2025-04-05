package com.hotel.HotelService.ai;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.hotel.HotelService.ai.Answer;
import com.hotel.HotelService.ai.OpenAIService;
import com.hotel.HotelService.ai.Question;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @GetMapping("/ask-param")
    public Answer askQuestion(@RequestParam String question) {
        return openAIService.getAnswer(new Question(question));
    }
}
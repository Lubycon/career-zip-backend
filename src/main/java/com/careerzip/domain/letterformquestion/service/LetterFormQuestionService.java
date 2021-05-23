package com.careerzip.domain.letterformquestion.service;

import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.letterformquestion.repository.LetterFormQuestionRepository;
import com.careerzip.domain.letter.entity.Letter;
import com.careerzip.domain.letterform.entity.LetterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LetterFormQuestionService {

    private final LetterFormQuestionRepository letterFormQuestionRepository;

    public void findBy(Archiving archiving) {
        Letter letter = archiving.getLetter();
        LetterForm letterForm = letter.getLetterForm();
        List<LetterFormQuestion> letterFormQuestions = letterFormQuestionRepository.findAllBy(letterForm);

    }
}

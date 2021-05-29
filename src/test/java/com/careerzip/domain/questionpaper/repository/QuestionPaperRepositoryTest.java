package com.careerzip.domain.questionpaper.repository;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createJpaQuestionPaperOf;
import static com.careerzip.testobject.questionpaperform.QuestionPaperFormFactory.createJpaTestQuestionPaperForm;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionPaperRepositoryTest extends BaseRepositoryTest {

    @Autowired
    QuestionPaperRepository questionPaperRepository;

    @Autowired
    QuestionPaperFormRepository questionPaperFormRepository;

    @Test
    @DisplayName("가장 최근의 QuestionPaper를 찾는 메서드 테스트")
    void findLatestTest() {
        // given
        QuestionPaperForm questionPaperForm = questionPaperFormRepository.save(createJpaTestQuestionPaperForm());
        List<QuestionPaper> papers = Arrays.asList(createJpaQuestionPaperOf(questionPaperForm), createJpaQuestionPaperOf(questionPaperForm));
        List<QuestionPaper> questionPapers = questionPaperRepository.saveAll(papers);
        QuestionPaper testPaper = questionPapers.get(questionPapers.size() - 1);

        // when
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);

        // then
        assertThat(questionPaper.getId()).isEqualTo(testPaper.getId());
    }
}

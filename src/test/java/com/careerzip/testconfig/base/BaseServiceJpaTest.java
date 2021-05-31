package com.careerzip.testconfig.base;

import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.repository.ProjectRepository;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionitem.repository.QuestionItemRepository;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.domain.questiontype.repository.QuestionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class BaseServiceJpaTest {

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected AnswerRepository answerRepository;

    @Autowired
    protected ArchiveRepository archiveRepository;

    @Autowired
    protected QuestionRepository questionRepository;

    @Autowired
    protected QuestionPaperFormRepository questionPaperFormRepository;

    @Autowired
    protected QuestionPaperRepository questionPaperRepository;

    @Autowired
    protected QuestionItemRepository questionItemRepository;

    @Autowired
    protected QuestionTypeRepository questionTypeRepository;

    @Autowired
    protected ProjectRepository projectRepository;
}

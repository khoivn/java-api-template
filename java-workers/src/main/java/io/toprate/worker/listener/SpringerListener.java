package io.toprate.worker.listener;

import io.toprate.worker.message.KeywordMessage;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.service.SpringerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpringerListener {

    private static final Logger log = LoggerFactory.getLogger(SpringerListener.class);

    @Autowired
    private KeywordRepo keywordRepo;

    @Autowired
    private SpringerService springerService;

    @RabbitListener(queues = "springer_sync")
    public void receiveMessageSpringer(KeywordMessage message) {
        log.info(message.getKeyword());
        Keyword keyword = this.keywordRepo.save(new Keyword(message.getKeyword()));
        Pageable pageable = PageRequest.of(0, 50);
        int totalPage = this.springerService.getData(message.getKeyword(), pageable, keyword).getTotalPages();
        for (int i = 1; i < totalPage; i++) {
            Pageable pageable2 = PageRequest.of(i, 50);
            this.springerService.getData(message.getKeyword(), pageable2, keyword);
        }
    }

}

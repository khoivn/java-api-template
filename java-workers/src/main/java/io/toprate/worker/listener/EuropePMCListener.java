package io.toprate.worker.listener;

import io.toprate.worker.message.KeywordMessage;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.service.EuropePMCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EuropePMCListener {
    @Autowired
    private EuropePMCService europePMCService;

    @Autowired
    private KeywordRepo keywordRepo;

    private static final Logger log = LoggerFactory.getLogger(EuropePMCListener.class);


    @RabbitListener(queues = "europepmc_sync")
    public void receiveEuropePMCMessage(KeywordMessage message) {
        log.info(message.toString());
        Keyword savedKeyWord = keywordRepo.save(new Keyword(message.getKeyword()));
        Pageable pageable = PageRequest.of(0,1000);
        europePMCService.getEuropePMCData(message.getKeyword(), pageable, savedKeyWord).getTotalPages();

    }
}

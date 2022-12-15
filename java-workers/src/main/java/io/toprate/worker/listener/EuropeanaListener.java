package io.toprate.worker.listener;

import io.toprate.worker.message.KeywordMessage;
import io.toprate.worker.models.Keyword;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.service.EuropeanaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EuropeanaListener {

    @Autowired
    private EuropeanaService europeanaService;

    @Autowired
    private  KeywordRepo keywordRepo;

    private static final Logger log = LoggerFactory.getLogger(EuropeanaListener.class);


    @RabbitListener(queues = "europeana_sync")
    public void receiveMessage(KeywordMessage message) {
        log.info(message.toString());
        Keyword savedKeyWord = keywordRepo.save(new Keyword(message.getKeyword()));
        Pageable pageable = PageRequest.of(0,50);
        int totalPage = europeanaService.getData(message.getKeyword(), pageable, savedKeyWord).getTotalPages();
        if(totalPage > 20){
            totalPage = 20;
        }
        for(int i = 1; i<totalPage; i++){
            Pageable pageable2 = PageRequest.of(i,50);
            europeanaService.getData(message.getKeyword(), pageable2, savedKeyWord);
        }
    }

}

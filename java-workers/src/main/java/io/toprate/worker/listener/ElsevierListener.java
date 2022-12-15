package io.toprate.worker.listener;

import io.toprate.worker.models.Keyword;
import io.toprate.worker.repository.KeywordRepo;
import io.toprate.worker.service.ElsevierService;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ElsevierListener {
    @Autowired
    private KeywordRepo keywordRepo;
    @Autowired
    private ElsevierService elsevierService;


    @RabbitListener(queues = "elsevier_sync")
    public void recieveMessageFromElsevier(Keyword keyword) {
        String test = keyword.getKeyword();
        keywordRepo.save(keyword);
        ObjectId id = keyword.getId();
        Pageable pageable = PageRequest.of(0, 25);
        int totalPages = elsevierService.getAllInfo(keyword,pageable).getTotalPages();
        if (totalPages > 200) {
            totalPages = 200;
        }
        for (int i = 1; i < totalPages; i++) {
            pageable = PageRequest.of(i, 25);
            elsevierService.getAllInfo(keyword,pageable);
        }
    }
}

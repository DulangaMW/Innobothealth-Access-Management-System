package com.innobothealth.accessmanagementsystem.service.impl;
import com.innobothealth.accessmanagementsystem.document.Sequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SequenceService {

    private final MongoOperations mongoOperations;
    public Long getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("value", 1);
        Sequence sequence = mongoOperations.findAndModify(query, update,
                Sequence.class);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.setId(sequenceName);
            sequence.setValue(1L);
            mongoOperations.save(sequence);
        }
        return sequence.getValue();
    }


}


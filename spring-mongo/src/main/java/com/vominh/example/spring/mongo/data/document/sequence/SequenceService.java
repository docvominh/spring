package com.vominh.example.spring.mongo.data.document.sequence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceService {
    private final MongoOperations mongo;

    public SequenceService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public int getNextSequence(String seqName) {
        GlobalSequence counter = mongo.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                GlobalSequence.class);
        return counter.getSeq();
    }
}


@Document(collection = "globalSequences")
class GlobalSequence {
    @Id
    private String id;
    private int seq;

    public int getSeq() {
        return seq;
    }
}

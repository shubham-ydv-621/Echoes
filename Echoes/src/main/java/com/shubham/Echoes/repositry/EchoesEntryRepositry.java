package com.shubham.Echoes.repositry;

import com.shubham.Echoes.entity.EchoesEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EchoesEntryRepositry extends MongoRepository<EchoesEntry, ObjectId>{


}

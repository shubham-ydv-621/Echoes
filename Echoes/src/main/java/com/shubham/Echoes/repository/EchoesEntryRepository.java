package com.shubham.Echoes.repository;

import com.shubham.Echoes.entity.EchoesEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EchoesEntryRepository extends MongoRepository<EchoesEntry, ObjectId>{


}

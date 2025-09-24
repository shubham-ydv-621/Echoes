package com.shubham.Echoes.service;

import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.repositry.EchoesEntryRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EchoesEntryService {

    @Autowired
    private EchoesEntryRepositry echoesEntryRepositry;


    public void saveEntry(EchoesEntry echoesEntry){
        echoesEntryRepositry.save(echoesEntry);
    }
    public List<EchoesEntry> getAll(){
       return echoesEntryRepositry.findAll();
    }

    public Optional<EchoesEntry> findById(ObjectId id){
  return echoesEntryRepositry.findById(id);
    }

    public  void deleteById(ObjectId id){
        echoesEntryRepositry.deleteById(id);
    }
}

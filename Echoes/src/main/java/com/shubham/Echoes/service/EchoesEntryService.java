package com.shubham.Echoes.service;

import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.repository.EchoesEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EchoesEntryService {

    @Autowired
    private EchoesEntryRepository echoesEntryRepository;

    @Autowired
    private UserService userService;

  @Transactional
    public void saveEntry(EchoesEntry echoesEntry,String userName){
      try {


          User user = userService.findByUserName(userName);
          echoesEntry.setDate(LocalDateTime.now());
          EchoesEntry saved = echoesEntryRepository.save(echoesEntry);
          user.getEchoesEntries().add(saved);
          userService.saveEntry(user);
      }catch (Exception e) {
        throw new RuntimeException("An error occurred while saving the entry.", e);
    }
    }
    public void saveEntry(EchoesEntry echoesEntry){

        echoesEntryRepository.save(echoesEntry);
    }
    public List<EchoesEntry> getAll(){
       return echoesEntryRepository.findAll();
    }

    public Optional<EchoesEntry> findById(ObjectId id){
  return echoesEntryRepository.findById(id);
    }

    public  void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getEchoesEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        echoesEntryRepository.deleteById(id);
    }
}

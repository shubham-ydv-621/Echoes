package com.shubham.Echoes.service;

import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.repository.EchoesEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
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
          userService.saveUser(user);
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


    @Transactional
    public boolean deleteById(ObjectId id, String userName){
      boolean removed=false;
      try {
        User user = userService.findByUserName(userName);
          removed = user.getEchoesEntries().removeIf(x -> x.getId().toHexString().equals(id.toHexString()));

          if(removed){
           userService.saveUser(user);
           echoesEntryRepository.deleteById(id);
       }

      }catch (Exception e) {
          log.error("Error ",e);
          throw new RuntimeException("An error occurred while deleting the entry.", e);
      }
        return removed;

    }
}

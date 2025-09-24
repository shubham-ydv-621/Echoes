package com.shubham.Echoes.controller;

import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.service.EchoesEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/echoes")
public class EchoesEntryControllerv2 {

    @Autowired
    private EchoesEntryService echoesEntryService;
    @GetMapping
    public ResponseEntity<?> getall(){
        List<EchoesEntry> all = echoesEntryService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity createEntry(@RequestBody EchoesEntry myEntry){
       try{
           myEntry.setDate(LocalDateTime.now());
           echoesEntryService.saveEntry(myEntry);
           return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity getEchoesEntryById(@PathVariable ObjectId myId){
        Optional<EchoesEntry> echoesEntry = echoesEntryService.findById(myId);
        if (echoesEntry.isPresent()) {
            return new ResponseEntity<>(echoesEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEchoesEntryById(@PathVariable ObjectId myId){
       echoesEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public  ResponseEntity<?> updateEchoesEntryById(@PathVariable ObjectId id, @RequestBody EchoesEntry newEntry){
      EchoesEntry old=echoesEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setTittle(
                    newEntry.getTittle() != null && !newEntry.getTittle().equals("")
                            ? newEntry.getTittle()
                            : old.getTittle()
            );

            old.setContent(
                    newEntry.getContent() != null && !newEntry.getContent().equals("")
                            ? newEntry.getContent()
                            : old.getContent()
            );
            echoesEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

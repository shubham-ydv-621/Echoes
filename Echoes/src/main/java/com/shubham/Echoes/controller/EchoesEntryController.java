package com.shubham.Echoes.controller;

import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.service.EchoesEntryService;
import com.shubham.Echoes.service.UserService;
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
public class EchoesEntryController {

    @Autowired
    private EchoesEntryService echoesEntryService;
    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllEchoesEntriesOfUser(@PathVariable String userName){
        User user=userService.findByUserName(userName);
        List<EchoesEntry> all = user.getEchoesEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity createEntry(@RequestBody EchoesEntry myEntry ,@PathVariable String userName){
       try{
           myEntry.setDate(LocalDateTime.now());
           echoesEntryService.saveEntry(myEntry,userName);
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



    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteEchoesEntryById(@PathVariable ObjectId myId,@PathVariable String userName){

       echoesEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateEchoesEntryById(
            @PathVariable("myId") ObjectId myId,
            @PathVariable("userName") String userName,
            @RequestBody EchoesEntry newEntry) {

        EchoesEntry old = echoesEntryService.findById(myId).orElse(null);
        if (old != null) {
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

            echoesEntryService.saveEntry(old, userName);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

package com.shubham.Echoes.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document (collection = "echoes_entries")
@Data
public class EchoesEntry {

    @Id
    private ObjectId id;
    private String tittle;
    private String content;
  private LocalDateTime date;

}

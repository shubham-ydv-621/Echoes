package com.shubham.Echoes.entity;

import com.shubham.Echoes.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document (collection = "echoes_entries")
@Data
@NoArgsConstructor
public class EchoesEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String tittle;
    private String content;
  private LocalDateTime date;
    private Sentiment sentiment;


}

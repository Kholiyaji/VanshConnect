package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
   @Id
   private String id;
   private String name;
   private String email;
   private String password;
   private String  originVillage;
   private String district;
   private String  state;
   private List<String> ancestorNames;
   @CreatedDate
   private LocalDateTime createdAt;
}

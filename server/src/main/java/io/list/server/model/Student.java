package io.list.server.model;

import io.list.server.enumeration.Levels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String name;
  private Boolean isPreply;
  private Levels level;
  private String progressInfo;
  private String objectivesInfo;
  private String nextClassInfo;
  private String hobbiesInfo;
  private Integer numPaidClasses;
}




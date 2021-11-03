package io.list.server.model;

import io.list.server.enumeration.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
  @Id
  @GeneratedValue(strategy = AUTO)

  private Long id;
  @Column(unique = true)
  @NotEmpty(message = "IP Address cannot be empty or null")
  private String ipAddress;
  private String name;
  private String memory;
  private String type;
  private String imageUrl;
  private Status status;
  public Object getName() {
    return null;
  }
public void setImageUrl(Object setServerImageUrl) {
}
public void setStatus(Object object) {
}
}




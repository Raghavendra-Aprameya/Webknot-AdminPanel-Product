package com.example.AdminXpert.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbConnectDTO {
     private String connectionUrl;
     private  String username;

     public String getConnectionUrl() {
          return connectionUrl;
     }

     public void setConnectionUrl(String connectionUrl) {
          this.connectionUrl = connectionUrl;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     private String password;
}

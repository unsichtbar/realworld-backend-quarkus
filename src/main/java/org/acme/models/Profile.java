package org.acme.models;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Profile {
  private String username;
  private String bio;
  private String image;
  private Boolean following;
}
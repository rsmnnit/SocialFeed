package com.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
    @Setter
    private String userId;
    @NonNull
    private String userName;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
}

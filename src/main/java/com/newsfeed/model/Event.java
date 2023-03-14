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
public class Event {
    @Setter
    private String eventId;
    @NonNull
    private String eventOwnerUserName;
    private Long creationTimeMillis;
    private Double locationLatitude;
    private Double locationLongitude;
    @NonNull
    private String eventName;
    @NonNull
    private String eventDescription;
    @NonNull
    private Date eventDate;
    @NonNull
    private String venue;
    @NonNull
    private Double venueLatitude;
    @NonNull
    private Double venueLongitude;
    private Integer likes;
}

package com.daniel.codacy.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZonedDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommitDto {
    @JsonProperty("sha")
    String sha;
    @JsonProperty("message")
    String message;
    @JsonProperty("date")
    //@JsonFormat(pattern = "EE MMM dd HH:mm:ss yyyy XX")
    Instant date;
    @JsonProperty("author")
    String author;

}

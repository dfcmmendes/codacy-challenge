package com.daniel.codacy.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class CommitPageDto {
    @JsonProperty("items")
    List<CommitDto> items;
    @JsonProperty("page")
    int page;
    @JsonProperty("per_page")
    int perPage;
    @JsonProperty("count")
    int count;
    @JsonProperty("total")
    int total;
}

package com.daniel.codacy.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GitApiCommitDto {
    String sha;
    Commit commit;

    @Data
    public static class Commit {
        Author author;
        String message;
    }
    @Data
    public static class Author{
        String name;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Date date;
    }
}

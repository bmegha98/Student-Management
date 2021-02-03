package com.springboot.restProject.Student.Management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResourceException
{
    private Date timestamp;
    private String message;
}

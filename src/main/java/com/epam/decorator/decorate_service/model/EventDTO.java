package com.epam.decorator.decorate_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private long id;
    private UserDto owner;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime eventTime;
    private String imageUrl;
    private String telegramChannelRef;
}

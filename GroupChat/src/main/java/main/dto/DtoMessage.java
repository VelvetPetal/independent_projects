package main.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoMessage {
    private String text;
    private String datetime;
    private String username;
}

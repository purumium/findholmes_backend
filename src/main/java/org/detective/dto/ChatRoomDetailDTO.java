package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.detective.entity.Estimate;
import org.detective.entity.Speciality;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDetailDTO {
    private String chatRoomId;
    private List<ParticipantDTO> participants;
    private String estimate_title;
    private int estimate_price;
    private Speciality requestSpeciality;
}

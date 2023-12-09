package ru.netology.ddima.controller.dto;
import lombok.*;

import java.util.List;
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class GetOperationResponse {
    private List<OperationDTO> operations;
}
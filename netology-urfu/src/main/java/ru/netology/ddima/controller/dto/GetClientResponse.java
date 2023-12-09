package ru.netology.ddima.controller.dto;

import lombok.Data;

import java.util.List;
@Data
public class GetClientResponse {
    private final List<CustomerDTO> clients;
}

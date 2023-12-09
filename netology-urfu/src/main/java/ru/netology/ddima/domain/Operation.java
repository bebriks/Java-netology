package ru.netology.ddima.domain;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Operation {
    private int id;
    private int sum;
    private String currency;
    private String merchant;
}


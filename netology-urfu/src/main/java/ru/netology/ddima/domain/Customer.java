package ru.netology.ddima.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Customer {
    private int id;
    private String name;
}
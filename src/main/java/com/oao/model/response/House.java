package com.oao.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class House {
    private int houseId;
    private String houseNumber;
    private String owner;
    private String streetName;
    private int postalCode;
}

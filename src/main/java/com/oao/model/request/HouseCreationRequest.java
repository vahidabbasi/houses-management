package com.oao.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseCreationRequest {
	
	@NotNull
	@ApiModelProperty(required = true)
	@Size(min = 1, max = 6)
	private String houseNumber;

	@NotNull
	@ApiModelProperty(required = true)
	@Size(min = 1)
	private String streetName;

	@NotNull
	@ApiModelProperty(required = true)
	@Min(value = 1, message = "Postal code must be greater than zero")
	private Integer postalCode;

	@NotNull
	@ApiModelProperty(required = true)
	@Size(min = 1)
	private String owner;
}

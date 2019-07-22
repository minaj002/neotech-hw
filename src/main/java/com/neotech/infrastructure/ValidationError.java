package com.neotech.infrastructure;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {

    private String error;

}

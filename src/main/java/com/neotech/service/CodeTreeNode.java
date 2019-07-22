package com.neotech.service;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Builder
@Data
public class CodeTreeNode {

    private CountryCode country;
    private HashMap<Integer, CodeTreeNode> children;

}

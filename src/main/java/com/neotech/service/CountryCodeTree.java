package com.neotech.service;

import com.neotech.infrastructure.ValidationException;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class CountryCodeTree {

    private HashMap<Integer, CodeTreeNode> root = new HashMap<>();

    public boolean isEmpty() {
        return root.isEmpty();
    }

    public CountryCode get(String phone) {
        if (phone == null) {
            throw new ValidationException("Invalid code");
        }
        phone = phone.replace("+", "").replace(" ", "");
        if (StringUtils.isEmpty(phone)) {
            throw new ValidationException("Invalid code");
        }
        if (!phone.matches("[0-9]+")) {
            throw new ValidationException("Invalid code");
        }
        return get(phone, this.root, null);
    }

    private CountryCode get(String code, HashMap<Integer, CodeTreeNode> root, CountryCode latest) {
        if (code.length() == 1) {
            CodeTreeNode node = root.get(Integer.parseInt(code));
            if (node == null && latest == null) {
                throw new ValidationException("Invalid code");
            }
            if (node != null && node.getCountry() == null && latest == null) {
                throw new ValidationException("Invalid code");
            }
            if(node == null) {
                return latest;
            }
            if (node.getCountry() == null) {
                return latest;
            }
            return node.getCountry();
        } else {
            Integer first = Integer.parseInt(code.substring(0, 1));
            String leftOvers = code.substring(1);
            CodeTreeNode node = root.get(first);
            if (node == null && latest == null) {
                throw new ValidationException("Invalid code");
            }
            if (node == null) {
                return latest;
            }
            if (node.getCountry() == null && node.getChildren() == null && latest == null) {
                throw new ValidationException("Invalid code");
            }
            if (node.getChildren() == null) {
                return node.getCountry() == null ? latest : node.getCountry();
            }
            return get(leftOvers, node.getChildren(), node.getCountry() == null ? latest : node.getCountry());
        }
    }

    public void add(String country, String codes) {


        String[] codeArray = codes.split(",");
        for (String code : codeArray) {
            CountryCode countryCode = CountryCode.builder().country(country).code(codes).build();
            code = code.replace("+", "").replace(" ", "");
            addCode(countryCode, code, root);
        }
    }

    private void addCode(CountryCode country, String code, HashMap<Integer, CodeTreeNode> root) {
        if (code.length() == 1) {
            Integer codeAsInt = Integer.parseInt(code);
            CodeTreeNode currentNode = root.get(codeAsInt);
            if (currentNode == null) {
                currentNode = CodeTreeNode.builder().country(country).build();
                root.put(codeAsInt, currentNode);
            } else {
                if (currentNode.getCountry() == null) {
                    currentNode.setCountry(country);
                } else {
                    currentNode.getCountry().setCountry(currentNode.getCountry().getCountry() + ", " + country.getCountry());
                }
            }

        } else {
            Integer first = Integer.parseInt(code.substring(0, 1));
            String leftOvers = code.substring(1);
            CodeTreeNode currentNode = root.get(first);
            if (currentNode == null) {
                currentNode = CodeTreeNode.builder().children(new HashMap<>()).build();
                root.put(first, currentNode);
                addCode(country, leftOvers, currentNode.getChildren());
            } else {
                if (currentNode.getChildren() == null) {
                    currentNode.setChildren(new HashMap<>());
                }
                addCode(country, leftOvers, currentNode.getChildren());
            }
        }
    }
}

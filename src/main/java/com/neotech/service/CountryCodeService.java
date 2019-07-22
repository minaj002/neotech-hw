package com.neotech.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class CountryCodeService {

    private CountryCodeTree countryCodeTree = new CountryCodeTree();

    public CountryCodeService () {
        try {
            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_country_calling_codes").get();

            Element body = doc.body();
            // Assume that page format does not change, so phone code table is always second
            Element table = body.select("table").get(1);

            Elements rows = table.select("tr");

            rows.forEach(r -> insertCountry(r));

            if(countryCodeTree.isEmpty()) {
                // Fail fast in the case if no phone data were received
                throw new BeanCreationException("Failed getting info from Wikipedia");
            }
        } catch (IOException e) {
            throw new BeanCreationException("Failed getting info from Wikipedia");
        }
    }

    public CountryCode get(String phone) {

        return countryCodeTree.get(phone);
    }


    private void insertCountry(Element row) {
        Elements columns = row.select("td");
        if(!columns.isEmpty()) {
            countryCodeTree.add(columns.get(0).ownText(), columns.get(1).select("a").get(0).ownText());
        }
    }
}

package parser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

class SAXHandler extends DefaultHandler {

    private final int capacity = 1000;
    private Company company = null;
    private String content = null;
    Set<Company> companies = new HashSet<>(capacity);        // Try to avoid duplicates in current Set. May be useful if XML-file contains a lot of duplicates
//    List<Company> companies = new ArrayList<>(capacity);   // If order in XML-file is important, but no duplicate check in current List. All duplicate check lies on DB

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("company".equals(qName)) {
            company = new Company();
            company.setId(attributes.getValue("id"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch(qName){
            case "company":
                companies.add(company);
                if (companies.size()==capacity) {
                    DataBase.saveCompanies(companies);
                    companies.clear();
                }
                break;
            case "name":
                company.setName(content);
                break;
            case "location":
                company.setLocation(content);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        content = String.copyValueOf(ch, start, length).trim();
    }
}

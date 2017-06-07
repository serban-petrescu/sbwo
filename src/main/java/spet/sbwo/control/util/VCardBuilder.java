package spet.sbwo.control.util;

import java.util.LinkedList;
import java.util.List;

public class VCardBuilder {
    private String name;
    private String address;
    private String primaryPhone;
    private String primaryEmail;
    private List<String> phones;
    private List<String> emails;

    public VCardBuilder() {
        phones = new LinkedList<>();
        emails = new LinkedList<>();
    }

    public VCardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public VCardBuilder address(String address) {
        this.address = address;
        return this;
    }

    public VCardBuilder phone(String phone, boolean primary) {
        if (primary && primaryPhone == null) {
            primaryPhone = phone;
        } else {
            phones.add(phone);
        }
        return this;
    }

    public VCardBuilder email(String email, boolean primary) {
        if (primary && primaryEmail == null) {
            primaryEmail = email;
        } else {
            emails.add(email);
        }
        return this;
    }

    public String build() {
        StringVCard card = new StringVCard();
        card.begin();
        card.addField("FN", name);
        card.addField("TEL;TYPE=WORK,PREF", primaryPhone);
        card.addFieldList("TEL;TYPE=WORK", phones);
        card.addField("ADR;TYPE=WORK,PREF", address);
        card.addField("EMAIL;TYPE=INTERNET,PREF", primaryEmail);
        card.addFieldList("EMAIL;TYPE=INTERNET", emails);
        card.end();
        return card.toString();
    }

    private static class StringVCard {
        StringBuffer result = new StringBuffer(128);

        public void begin() {
            result.append("BEGIN:VCARD\nVERSION:2.1\n");
        }

        public void addFieldList(String field, List<String> values) {
            for (String value : values) {
                addField(field, value);
            }
        }

        public void addField(String field, String value) {
            if (value != null && !value.isEmpty()) {
                result.append(field);
                result.append(':');
                result.append(value);
                result.append('\n');
            }
        }

        public void end() {
            result.append("END:VCARD\n");
        }

        @Override
        public String toString() {
            return result.toString();
        }
    }
}

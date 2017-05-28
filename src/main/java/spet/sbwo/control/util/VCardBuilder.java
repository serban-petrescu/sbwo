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
        StringBuilder result = new StringBuilder(64);
        result.append("BEGIN:VCARD\nVERSION:2.1\n");

        if (name != null && !name.isEmpty()) {
            result.append("FN:").append(name).append('\n');
        }

        if (primaryPhone != null && !primaryPhone.isEmpty()) {
            result.append("TEL;TYPE=WORK,PREF:").append(primaryPhone).append('\n');
        }
        for (String phone : phones) {
            result.append("TEL;TYPE=WORK:").append(phone).append('\n');
        }

        if (address != null && !address.isEmpty()) {
            result.append("ADR;TYPE=WORK,PREF:").append(address).append('\n');
        }

        if (primaryEmail != null && !primaryEmail.isEmpty()) {
            result.append("EMAIL;TYPE=INTERNET,PREF:").append(primaryEmail).append('\n');
        }
        for (String email : emails) {
            result.append("EMAIL;TYPE=INTERNET:").append(email).append('\n');
        }

        result.append("END:VCARD\n");
        return result.toString();
    }
}

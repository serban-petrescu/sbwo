package spet.sbwo.control.importer.expertise;

import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.BaseMapImporter;
import spet.sbwo.control.importer.misc.LocationImporter;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.embed.Tariff;
import spet.sbwo.data.table.Court;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.data.table.User;

import java.util.*;

public class ExpertiseImporter extends BaseMapImporter<Expertise> {
    private final LocationImporter locationImporter;
    private final IDatabaseExecutor executor;

    public ExpertiseImporter(IDatabaseExecutor executor, LocationImporter locationImporter) {
        this.locationImporter = locationImporter;
        this.executor = executor;
    }

    public static List<String> fields() {
        List<String> fields = new LinkedList<>();
        Collections.addAll(fields, "number", "expertise_court_code", "expertise_responsible", "expertise_note",
            "expertise_number", "expertise_status", "expertise_year", "expertise_title", "expertise_tariff_price",
            "expertise_tariff_advance");
        fields.addAll(LocationImporter.fields());
        return fields;
    }

    @Override
    protected Expertise buildFromEntry(Map<String, String> entry) {
        Expertise result = new Expertise();
        findCourt(entry.get("expertise_court_code")).ifPresent(result::setCourt);
        findUser(entry.get("expertise_responsible")).ifPresent(result::setResponsible);
        result.setLocation(locationImporter.process(entry));
        result.setNote(entry.get("expertise_note"));
        result.setNumber(entry.get("expertise_number"));
        result.setStatus(Utils.toEnum(ExpertiseStatus.class, entry.get("expertise_status")));
        result.setYear(Utils.toInteger(entry.get("expertise_year")));
        result.setTitle(entry.get("expertise_title"));
        result.setTariff(buildTariff(entry));
        result.setDeleted(false);
        result.setFines(new ArrayList<>());
        return result;
    }

    private Tariff buildTariff(Map<String, String> entry) {
        Tariff tariff = new Tariff();
        tariff.setPrice(Utils.toDecimal(entry.get("expertise_tariff_price")));
        tariff.setAdvance(Utils.toDecimal(entry.get("expertise_tariff_advance")));
        return tariff;
    }

    private Optional<Court> findCourt(String code) {
        return executor.querySingle("Court.readByCode", Court.class, code);
    }

    private Optional<User> findUser(String username) {
        return executor.querySingle("User.getByUsername", User.class, username);
    }
}

package spet.sbwo.control.importer.expertise;

import spet.sbwo.control.importer.Utils;
import spet.sbwo.control.importer.base.BaseListImporter;
import spet.sbwo.control.importer.base.IEntityProvider;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.data.table.ExpertiseFine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExpertiseFineImporter extends BaseListImporter<ExpertiseFine> {
    private final IEntityProvider<Expertise> expertiseProvider;

    public ExpertiseFineImporter(IEntityProvider<Expertise> expertiseProvider) {
        this.expertiseProvider = expertiseProvider;
    }

    public static List<String> fields() {
        return Arrays.asList("expertise_number", "fine_sum", "fine_date");
    }

    @Override
    protected ExpertiseFine build(Map<String, String> entry) {
        ExpertiseFine result = new ExpertiseFine();
        result.setSum(Utils.toDecimal(entry.get("fine_sum")));
        result.setDate(Utils.toLocalDate(entry.get("fine_date")));
        expertiseProvider.getEntity(entry.get("expertise_number")).getFines().add(result);
        return result;
    }
}

package spet.sbwo.integration.web.rojustportal;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.integration.api.court.ICourtSystemApi;
import spet.sbwo.integration.api.court.model.Case;
import spet.sbwo.integration.api.court.model.Document;
import spet.sbwo.integration.api.court.model.Hearing;
import spet.sbwo.integration.api.court.model.Side;

public class RoJustPortalFacade implements ICourtSystemApi {
    private static final Logger LOG = LoggerFactory.getLogger(RoJustPortalFacade.class);
    private final QuerySoap soap;

    public RoJustPortalFacade() {
        soap = new Query().getQuerySoap();
    }

    @Override
    public Case read(String number) {
        try {
            Dosar dosar = select(soap.cautareDosare(number, null, null, null, null, null));
            if (dosar != null) {
                return map(dosar);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error while calling just.ro.", e);
            return null;
        }
    }

    protected Dosar select(ArrayOfDosar array) {
        if (array == null || array.getDosar() == null) {
            return null;
        } else {
            Dosar maxDosar = null;
            long maxTs = -1;
            for (Dosar dosar : array.getDosar()) {
                long ts = select(dosar.getSedinte());
                if (ts > maxTs) {
                    maxTs = ts;
                    maxDosar = dosar;
                }
            }
            return maxDosar;
        }
    }

    protected long select(ArrayOfDosarSedinta array) {
        if (array == null || array.getDosarSedinta() == null) {
            return -1;
        } else {
            long maxTs = -1;
            for (DosarSedinta sedinta : array.getDosarSedinta()) {
                maxTs = Math.max(maxTs, timestamp(sedinta.getData()));
            }
            return maxTs;
        }
    }

    protected Case map(Dosar dosar) {
        Case cas = new Case();
        cas.setCategory(dosar.getCategorieCazNume());
        cas.setDate(map(dosar.getData()));
        cas.setMatter(dosar.getObiect());
        cas.setNumber(dosar.getNumar());
        cas.setStatus(dosar.getStadiuProcesualNume());

        if (dosar.getSedinte() != null && dosar.getSedinte().getDosarSedinta() != null) {
            List<DosarSedinta> sedinte = dosar.getSedinte().getDosarSedinta();
            cas.setHearings(sedinte.stream().map(this::map).collect(Collectors.toList()));
        } else {
            cas.setHearings(Collections.emptyList());
        }

        if (dosar.getParti() != null && dosar.getParti().getDosarParte() != null) {
            List<DosarParte> parti = dosar.getParti().getDosarParte();
            cas.setSides(parti.stream().map(this::map).collect(Collectors.toList()));
        } else {
            cas.setSides(Collections.emptyList());
        }

        return cas;
    }

    protected Side map(DosarParte parte) {
        Side side = new Side();
        side.setName(parte.getNume());
        side.setType(parte.getCalitateParte());
        return side;
    }

    protected Hearing map(DosarSedinta sedinta) {
        Document document = new Document();
        document.setType(sedinta.getDocumentSedinta());
        document.setNumber(sedinta.getNumarDocument());
        document.setDate(map(sedinta.getData()));

        Hearing hearing = new Hearing();
        hearing.setDate(map(sedinta.getData()));
        hearing.setDelivery(map(sedinta.getDataPronuntare()));
        hearing.setDocument(document);
        hearing.setResult(sedinta.getSolutie());
        hearing.setSummary(sedinta.getSolutieSumar());
        hearing.setTime(sedinta.getOra());

        return hearing;
    }

    protected long timestamp(XMLGregorianCalendar calendar) {
        if (calendar != null) {
            return calendar.toGregorianCalendar().getTimeInMillis();
        } else {
            return -1;
        }
    }

    protected LocalDate map(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        } else {
            return calendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
        }
    }

}

package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StadiuProcesual.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StadiuProcesual"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Fond"/&gt;
 *     &lt;enumeration value="Apel"/&gt;
 *     &lt;enumeration value="Recurs"/&gt;
 *     &lt;enumeration value="Recursinanulare"/&gt;
 *     &lt;enumeration value="Recursininteresullegii"/&gt;
 *     &lt;enumeration value="Sesizareprealabila"/&gt;
 *     &lt;enumeration value="Contesta\u0163ieNCPP"/&gt;
 *     &lt;enumeration value="Recursincasatie"/&gt;
 *     &lt;enumeration value="ContestatieICCJ"/&gt;
 *     &lt;enumeration value="ContestatieInAnulareNCPP"/&gt;
 *     &lt;enumeration value="RevizuireContestatieNCPP"/&gt;
 *     &lt;enumeration value="Contestatie"/&gt;
 *     &lt;enumeration value="Revizuire"/&gt;
 *     &lt;enumeration value="Contestatieinanulare"/&gt;
 *     &lt;enumeration value="Stabilireacompetentei"/&gt;
 *     &lt;enumeration value="Recursimpotrivaincheierii"/&gt;
 *     &lt;enumeration value="Recuzare"/&gt;
 *     &lt;enumeration value="Stramutare"/&gt;
 *     &lt;enumeration value="Indreptareeroaremateriala"/&gt;
 *     &lt;enumeration value="ContestatieinanulareFond"/&gt;
 *     &lt;enumeration value="ContestatieinanulareApel"/&gt;
 *     &lt;enumeration value="ContestatieinanulareRecurs"/&gt;
 *     &lt;enumeration value="RevizuireFond"/&gt;
 *     &lt;enumeration value="RevizuireApel"/&gt;
 *     &lt;enumeration value="RevizuireRecurs"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "StadiuProcesual")
@XmlEnum
public enum StadiuProcesual {

    @XmlEnumValue("Fond")
    FOND("Fond"),
    @XmlEnumValue("Apel")
    APEL("Apel"),
    @XmlEnumValue("Recurs")
    RECURS("Recurs"),
    @XmlEnumValue("Recursinanulare")
    RECURSINANULARE("Recursinanulare"),
    @XmlEnumValue("Recursininteresullegii")
    RECURSININTERESULLEGII("Recursininteresullegii"),
    @XmlEnumValue("Sesizareprealabila")
    SESIZAREPREALABILA("Sesizareprealabila"),
    @XmlEnumValue("Contesta\u0163ieNCPP")
    CONTESTATIE_NCPP("Contesta\u0163ieNCPP"),

    @XmlEnumValue("Recursincasatie")
    RECURSINCASATIE("Recursincasatie"),

    @XmlEnumValue("ContestatieICCJ")
    CONTESTATIE_ICCJ("ContestatieICCJ"),

    @XmlEnumValue("ContestatieInAnulareNCPP")
    CONTESTATIE_IN_ANULARE_NCPP("ContestatieInAnulareNCPP"),

    @XmlEnumValue("RevizuireContestatieNCPP")
    REVIZUIRE_CONTESTATIE_NCPP("RevizuireContestatieNCPP"),

    @XmlEnumValue("Contestatie")
    CONTESTATIE("Contestatie"),

    @XmlEnumValue("Revizuire")
    REVIZUIRE("Revizuire"),

    @XmlEnumValue("Contestatieinanulare")
    CONTESTATIEINANULARE("Contestatieinanulare"),

    @XmlEnumValue("Stabilireacompetentei")
    STABILIREACOMPETENTEI("Stabilireacompetentei"),

    @XmlEnumValue("Recursimpotrivaincheierii")
    RECURSIMPOTRIVAINCHEIERII("Recursimpotrivaincheierii"),

    @XmlEnumValue("Recuzare")
    RECUZARE("Recuzare"),

    @XmlEnumValue("Stramutare")
    STRAMUTARE("Stramutare"),

    @XmlEnumValue("Indreptareeroaremateriala")
    INDREPTAREEROAREMATERIALA("Indreptareeroaremateriala"),

    @XmlEnumValue("ContestatieinanulareFond")
    CONTESTATIEINANULARE_FOND("ContestatieinanulareFond"),

    @XmlEnumValue("ContestatieinanulareApel")
    CONTESTATIEINANULARE_APEL("ContestatieinanulareApel"),

    @XmlEnumValue("ContestatieinanulareRecurs")
    CONTESTATIEINANULARE_RECURS("ContestatieinanulareRecurs"),

    @XmlEnumValue("RevizuireFond")
    REVIZUIRE_FOND("RevizuireFond"),

    @XmlEnumValue("RevizuireApel")
    REVIZUIRE_APEL("RevizuireApel"),

    @XmlEnumValue("RevizuireRecurs")
    REVIZUIRE_RECURS("RevizuireRecurs");

    private final String value;

    StadiuProcesual(String v) {
        value = v;
    }

    public static StadiuProcesual fromValue(String v) {
        for (StadiuProcesual c : StadiuProcesual.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }

}

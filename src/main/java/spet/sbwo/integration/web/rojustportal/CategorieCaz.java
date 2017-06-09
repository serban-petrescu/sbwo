package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategorieCaz.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CategorieCaz"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Penal"/&gt;
 *     &lt;enumeration value="Civil"/&gt;
 *     &lt;enumeration value="Litigiicuprofesionistii"/&gt;
 *     &lt;enumeration value="Contenciosadministrativsifiscal"/&gt;
 *     &lt;enumeration value="Minorisifamilie"/&gt;
 *     &lt;enumeration value="Litigiidemunca"/&gt;
 *     &lt;enumeration value="Altematerii"/&gt;
 *     &lt;enumeration value="Asigurarisociale"/&gt;
 *     &lt;enumeration value="Faliment"/&gt;
 *     &lt;enumeration value="ProprietateIntelectuala"/&gt;
 *     &lt;enumeration value="Dreptmaritimsifluvial"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "CategorieCaz")
@XmlEnum
public enum CategorieCaz {

    @XmlEnumValue("Penal")
    PENAL("Penal"),
    @XmlEnumValue("Civil")
    CIVIL("Civil"),
    @XmlEnumValue("Litigiicuprofesionistii")
    LITIGIICUPROFESIONISTII("Litigiicuprofesionistii"),
    @XmlEnumValue("Contenciosadministrativsifiscal")
    CONTENCIOSADMINISTRATIVSIFISCAL("Contenciosadministrativsifiscal"),
    @XmlEnumValue("Minorisifamilie")
    MINORISIFAMILIE("Minorisifamilie"),
    @XmlEnumValue("Litigiidemunca")
    LITIGIIDEMUNCA("Litigiidemunca"),
    @XmlEnumValue("Altematerii")
    ALTEMATERII("Altematerii"),
    @XmlEnumValue("Asigurarisociale")
    ASIGURARISOCIALE("Asigurarisociale"),
    @XmlEnumValue("Faliment")
    FALIMENT("Faliment"),
    @XmlEnumValue("ProprietateIntelectuala")
    PROPRIETATE_INTELECTUALA("ProprietateIntelectuala"),
    @XmlEnumValue("Dreptmaritimsifluvial")
    DREPTMARITIMSIFLUVIAL("Dreptmaritimsifluvial");
    private final String value;

    CategorieCaz(String v) {
        value = v;
    }

    public static CategorieCaz fromValue(String v) {
        for (CategorieCaz c : CategorieCaz.values()) {
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

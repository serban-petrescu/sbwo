
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DosarCaleAtac complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="DosarCaleAtac"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataDeclarare" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="parteDeclaratoare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipCaleAtac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DosarCaleAtac", propOrder = {
    "dataDeclarare",
    "parteDeclaratoare",
    "tipCaleAtac"
})
public class DosarCaleAtac {

    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDeclarare;
    protected String parteDeclaratoare;
    protected String tipCaleAtac;

    /**
     * Gets the value of the dataDeclarare property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataDeclarare() {
        return dataDeclarare;
    }

    /**
     * Sets the value of the dataDeclarare property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataDeclarare(XMLGregorianCalendar value) {
        this.dataDeclarare = value;
    }

    /**
     * Gets the value of the parteDeclaratoare property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getParteDeclaratoare() {
        return parteDeclaratoare;
    }

    /**
     * Sets the value of the parteDeclaratoare property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setParteDeclaratoare(String value) {
        this.parteDeclaratoare = value;
    }

    /**
     * Gets the value of the tipCaleAtac property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTipCaleAtac() {
        return tipCaleAtac;
    }

    /**
     * Sets the value of the tipCaleAtac property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTipCaleAtac(String value) {
        this.tipCaleAtac = value;
    }

}


package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numarDosar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="obiectDosar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeParte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="institutie" type="{portalquery.just.ro}Institutie"/&gt;
 *         &lt;element name="dataStart" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataStop" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "numarDosar",
    "obiectDosar",
    "numeParte",
    "institutie",
    "dataStart",
    "dataStop"
})
@XmlRootElement(name = "CautareDosare")
public class CautareDosare {

    protected String numarDosar;
    protected String obiectDosar;
    protected String numeParte;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Institutie institutie;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStart;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataStop;

    /**
     * Gets the value of the numarDosar property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNumarDosar() {
        return numarDosar;
    }

    /**
     * Sets the value of the numarDosar property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumarDosar(String value) {
        this.numarDosar = value;
    }

    /**
     * Gets the value of the obiectDosar property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getObiectDosar() {
        return obiectDosar;
    }

    /**
     * Sets the value of the obiectDosar property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setObiectDosar(String value) {
        this.obiectDosar = value;
    }

    /**
     * Gets the value of the numeParte property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNumeParte() {
        return numeParte;
    }

    /**
     * Sets the value of the numeParte property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumeParte(String value) {
        this.numeParte = value;
    }

    /**
     * Gets the value of the institutie property.
     *
     * @return possible object is
     * {@link Institutie }
     */
    public Institutie getInstitutie() {
        return institutie;
    }

    /**
     * Sets the value of the institutie property.
     *
     * @param value allowed object is
     *              {@link Institutie }
     */
    public void setInstitutie(Institutie value) {
        this.institutie = value;
    }

    /**
     * Gets the value of the dataStart property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataStart() {
        return dataStart;
    }

    /**
     * Sets the value of the dataStart property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataStart(XMLGregorianCalendar value) {
        this.dataStart = value;
    }

    /**
     * Gets the value of the dataStop property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataStop() {
        return dataStop;
    }

    /**
     * Sets the value of the dataStop property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataStop(XMLGregorianCalendar value) {
        this.dataStop = value;
    }

}

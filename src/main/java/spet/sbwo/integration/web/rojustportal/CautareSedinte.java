
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
 *         &lt;element name="dataSedinta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="institutie" type="{portalquery.just.ro}Institutie"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataSedinta",
    "institutie"
})
@XmlRootElement(name = "CautareSedinte")
public class CautareSedinte {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataSedinta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Institutie institutie;

    /**
     * Gets the value of the dataSedinta property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataSedinta() {
        return dataSedinta;
    }

    /**
     * Sets the value of the dataSedinta property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataSedinta(XMLGregorianCalendar value) {
        this.dataSedinta = value;
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

}


package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Sedinta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Sedinta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="departament" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="complet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dosare" type="{portalquery.just.ro}ArrayOfSedintaDosar" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sedinta", propOrder = {
    "departament",
    "complet",
    "data",
    "ora",
    "dosare"
})
public class Sedinta {

    protected String departament;
    protected String complet;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected String ora;
    protected ArrayOfSedintaDosar dosare;

    /**
     * Gets the value of the departament property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartament() {
        return departament;
    }

    /**
     * Sets the value of the departament property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartament(String value) {
        this.departament = value;
    }

    /**
     * Gets the value of the complet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplet() {
        return complet;
    }

    /**
     * Sets the value of the complet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplet(String value) {
        this.complet = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Gets the value of the ora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOra() {
        return ora;
    }

    /**
     * Sets the value of the ora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOra(String value) {
        this.ora = value;
    }

    /**
     * Gets the value of the dosare property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSedintaDosar }
     *     
     */
    public ArrayOfSedintaDosar getDosare() {
        return dosare;
    }

    /**
     * Sets the value of the dosare property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSedintaDosar }
     *     
     */
    public void setDosare(ArrayOfSedintaDosar value) {
        this.dosare = value;
    }

}

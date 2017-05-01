
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SedintaDosar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SedintaDosar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numar_vechi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="categorieCaz" type="{portalquery.just.ro}CategorieCaz"/&gt;
 *         &lt;element name="stadiuProcesual" type="{portalquery.just.ro}StadiuProcesual"/&gt;
 *         &lt;element name="categorieCazNume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stadiuProcesualNume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SedintaDosar", propOrder = {
    "numar",
    "numarVechi",
    "data",
    "ora",
    "categorieCaz",
    "stadiuProcesual",
    "categorieCazNume",
    "stadiuProcesualNume"
})
public class SedintaDosar {

    protected String numar;
    @XmlElement(name = "numar_vechi")
    protected String numarVechi;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected String ora;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CategorieCaz categorieCaz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected StadiuProcesual stadiuProcesual;
    protected String categorieCazNume;
    protected String stadiuProcesualNume;

    /**
     * Gets the value of the numar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumar() {
        return numar;
    }

    /**
     * Sets the value of the numar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumar(String value) {
        this.numar = value;
    }

    /**
     * Gets the value of the numarVechi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumarVechi() {
        return numarVechi;
    }

    /**
     * Sets the value of the numarVechi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumarVechi(String value) {
        this.numarVechi = value;
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
     * Gets the value of the categorieCaz property.
     * 
     * @return
     *     possible object is
     *     {@link CategorieCaz }
     *     
     */
    public CategorieCaz getCategorieCaz() {
        return categorieCaz;
    }

    /**
     * Sets the value of the categorieCaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategorieCaz }
     *     
     */
    public void setCategorieCaz(CategorieCaz value) {
        this.categorieCaz = value;
    }

    /**
     * Gets the value of the stadiuProcesual property.
     * 
     * @return
     *     possible object is
     *     {@link StadiuProcesual }
     *     
     */
    public StadiuProcesual getStadiuProcesual() {
        return stadiuProcesual;
    }

    /**
     * Sets the value of the stadiuProcesual property.
     * 
     * @param value
     *     allowed object is
     *     {@link StadiuProcesual }
     *     
     */
    public void setStadiuProcesual(StadiuProcesual value) {
        this.stadiuProcesual = value;
    }

    /**
     * Gets the value of the categorieCazNume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategorieCazNume() {
        return categorieCazNume;
    }

    /**
     * Sets the value of the categorieCazNume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategorieCazNume(String value) {
        this.categorieCazNume = value;
    }

    /**
     * Gets the value of the stadiuProcesualNume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStadiuProcesualNume() {
        return stadiuProcesualNume;
    }

    /**
     * Sets the value of the stadiuProcesualNume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStadiuProcesualNume(String value) {
        this.stadiuProcesualNume = value;
    }

}

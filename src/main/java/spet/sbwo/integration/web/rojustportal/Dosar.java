
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Dosar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dosar"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parti" type="{portalquery.just.ro}ArrayOfDosarParte" minOccurs="0"/&gt;
 *         &lt;element name="sedinte" type="{portalquery.just.ro}ArrayOfDosarSedinta" minOccurs="0"/&gt;
 *         &lt;element name="caiAtac" type="{portalquery.just.ro}ArrayOfDosarCaleAtac" minOccurs="0"/&gt;
 *         &lt;element name="numar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numarVechi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="institutie" type="{portalquery.just.ro}Institutie"/&gt;
 *         &lt;element name="departament" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="categorieCaz" type="{portalquery.just.ro}CategorieCaz"/&gt;
 *         &lt;element name="stadiuProcesual" type="{portalquery.just.ro}StadiuProcesual"/&gt;
 *         &lt;element name="obiect" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataModificare" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
@XmlType(name = "Dosar", propOrder = {
    "parti",
    "sedinte",
    "caiAtac",
    "numar",
    "numarVechi",
    "data",
    "institutie",
    "departament",
    "categorieCaz",
    "stadiuProcesual",
    "obiect",
    "dataModificare",
    "categorieCazNume",
    "stadiuProcesualNume"
})
public class Dosar {

    protected ArrayOfDosarParte parti;
    protected ArrayOfDosarSedinta sedinte;
    protected ArrayOfDosarCaleAtac caiAtac;
    protected String numar;
    protected String numarVechi;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Institutie institutie;
    protected String departament;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CategorieCaz categorieCaz;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected StadiuProcesual stadiuProcesual;
    protected String obiect;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataModificare;
    protected String categorieCazNume;
    protected String stadiuProcesualNume;

    /**
     * Gets the value of the parti property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDosarParte }
     *     
     */
    public ArrayOfDosarParte getParti() {
        return parti;
    }

    /**
     * Sets the value of the parti property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDosarParte }
     *     
     */
    public void setParti(ArrayOfDosarParte value) {
        this.parti = value;
    }

    /**
     * Gets the value of the sedinte property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDosarSedinta }
     *     
     */
    public ArrayOfDosarSedinta getSedinte() {
        return sedinte;
    }

    /**
     * Sets the value of the sedinte property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDosarSedinta }
     *     
     */
    public void setSedinte(ArrayOfDosarSedinta value) {
        this.sedinte = value;
    }

    /**
     * Gets the value of the caiAtac property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDosarCaleAtac }
     *     
     */
    public ArrayOfDosarCaleAtac getCaiAtac() {
        return caiAtac;
    }

    /**
     * Sets the value of the caiAtac property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDosarCaleAtac }
     *     
     */
    public void setCaiAtac(ArrayOfDosarCaleAtac value) {
        this.caiAtac = value;
    }

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
     * Gets the value of the institutie property.
     * 
     * @return
     *     possible object is
     *     {@link Institutie }
     *     
     */
    public Institutie getInstitutie() {
        return institutie;
    }

    /**
     * Sets the value of the institutie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Institutie }
     *     
     */
    public void setInstitutie(Institutie value) {
        this.institutie = value;
    }

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
     * Gets the value of the obiect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObiect() {
        return obiect;
    }

    /**
     * Sets the value of the obiect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObiect(String value) {
        this.obiect = value;
    }

    /**
     * Gets the value of the dataModificare property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataModificare() {
        return dataModificare;
    }

    /**
     * Sets the value of the dataModificare property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataModificare(XMLGregorianCalendar value) {
        this.dataModificare = value;
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

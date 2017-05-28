
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DosarSedinta complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="DosarSedinta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="complet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="solutie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="solutieSumar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataPronuntare" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="documentSedinta" type="{portalquery.just.ro}DocumentSedinta"/&gt;
 *         &lt;element name="numarDocument" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataDocument" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DosarSedinta", propOrder = {
    "complet",
    "data",
    "ora",
    "solutie",
    "solutieSumar",
    "dataPronuntare",
    "documentSedinta",
    "numarDocument",
    "dataDocument"
})
public class DosarSedinta {

    protected String complet;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected String ora;
    protected String solutie;
    protected String solutieSumar;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPronuntare;
    @XmlElement(required = true, nillable = true)
    protected String documentSedinta;
    protected String numarDocument;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDocument;

    /**
     * Gets the value of the complet property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getComplet() {
        return complet;
    }

    /**
     * Sets the value of the complet property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setComplet(String value) {
        this.complet = value;
    }

    /**
     * Gets the value of the data property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Gets the value of the ora property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOra() {
        return ora;
    }

    /**
     * Sets the value of the ora property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOra(String value) {
        this.ora = value;
    }

    /**
     * Gets the value of the solutie property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSolutie() {
        return solutie;
    }

    /**
     * Sets the value of the solutie property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSolutie(String value) {
        this.solutie = value;
    }

    /**
     * Gets the value of the solutieSumar property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSolutieSumar() {
        return solutieSumar;
    }

    /**
     * Sets the value of the solutieSumar property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSolutieSumar(String value) {
        this.solutieSumar = value;
    }

    /**
     * Gets the value of the dataPronuntare property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataPronuntare() {
        return dataPronuntare;
    }

    /**
     * Sets the value of the dataPronuntare property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataPronuntare(XMLGregorianCalendar value) {
        this.dataPronuntare = value;
    }

    /**
     * Gets the value of the documentSedinta property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDocumentSedinta() {
        return documentSedinta;
    }

    /**
     * Sets the value of the documentSedinta property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDocumentSedinta(String value) {
        this.documentSedinta = value;
    }

    /**
     * Gets the value of the numarDocument property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNumarDocument() {
        return numarDocument;
    }

    /**
     * Sets the value of the numarDocument property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumarDocument(String value) {
        this.numarDocument = value;
    }

    /**
     * Gets the value of the dataDocument property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDataDocument() {
        return dataDocument;
    }

    /**
     * Sets the value of the dataDocument property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDataDocument(XMLGregorianCalendar value) {
        this.dataDocument = value;
    }

}

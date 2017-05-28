
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DosarParte complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="DosarParte"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="calitateParte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DosarParte", propOrder = {
    "nume",
    "calitateParte"
})
public class DosarParte {

    protected String nume;
    protected String calitateParte;

    /**
    * Gets the value of the nume property.
    *
    * @return possible object is
    * {@link String }
    */
    public String getNume() {
        return nume;
    }

    /**
    * Sets the value of the nume property.
    *
    * @param value allowed object is
    *              {@link String }
    */
    public void setNume(String value) {
        this.nume = value;
    }

    /**
    * Gets the value of the calitateParte property.
    *
    * @return possible object is
    * {@link String }
    */
    public String getCalitateParte() {
        return calitateParte;
    }

    /**
    * Sets the value of the calitateParte property.
    *
    * @param value allowed object is
    *              {@link String }
    */
    public void setCalitateParte(String value) {
        this.calitateParte = value;
    }

}

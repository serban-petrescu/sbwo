
package spet.sbwo.integration.web.rojustportal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDosarCaleAtac complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="ArrayOfDosarCaleAtac"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DosarCaleAtac" type="{portalquery.just.ro}DosarCaleAtac" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDosarCaleAtac", propOrder = {
    "dosarCaleAtac"
})
public class ArrayOfDosarCaleAtac {

    @XmlElement(name = "DosarCaleAtac", nillable = true)
    protected List<DosarCaleAtac> dosarCaleAtac;

    /**
    * Gets the value of the dosarCaleAtac property.
    * <p>
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the dosarCaleAtac property.
    * <p>
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getDosarCaleAtac().add(newItem);
    * </pre>
    * <p>
    * <p>
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link DosarCaleAtac }
    */
    public List<DosarCaleAtac> getDosarCaleAtac() {
        if (dosarCaleAtac == null) {
            dosarCaleAtac = new ArrayList<>();
        }
        return this.dosarCaleAtac;
    }

}

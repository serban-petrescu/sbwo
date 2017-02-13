
package spet.sbwo.integration.web.rojustportal;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSedinta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSedinta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Sedinta" type="{portalquery.just.ro}Sedinta" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSedinta", propOrder = {
    "sedinta"
})
public class ArrayOfSedinta {

    @XmlElement(name = "Sedinta", nillable = true)
    protected List<Sedinta> sedinta;

    /**
     * Gets the value of the sedinta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sedinta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSedinta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sedinta }
     * 
     * 
     */
    public List<Sedinta> getSedinta() {
        if (sedinta == null) {
            sedinta = new ArrayList<Sedinta>();
        }
        return this.sedinta;
    }

}

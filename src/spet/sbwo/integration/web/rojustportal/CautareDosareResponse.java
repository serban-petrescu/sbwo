
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CautareDosareResult" type="{portalquery.just.ro}ArrayOfDosar" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cautareDosareResult"
})
@XmlRootElement(name = "CautareDosareResponse")
public class CautareDosareResponse {

    @XmlElement(name = "CautareDosareResult")
    protected ArrayOfDosar cautareDosareResult;

    /**
     * Gets the value of the cautareDosareResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDosar }
     *     
     */
    public ArrayOfDosar getCautareDosareResult() {
        return cautareDosareResult;
    }

    /**
     * Sets the value of the cautareDosareResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDosar }
     *     
     */
    public void setCautareDosareResult(ArrayOfDosar value) {
        this.cautareDosareResult = value;
    }

}

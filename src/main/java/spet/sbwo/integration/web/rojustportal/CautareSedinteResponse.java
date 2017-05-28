
package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="CautareSedinteResult" type="{portalquery.just.ro}ArrayOfSedinta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cautareSedinteResult"
})
@XmlRootElement(name = "CautareSedinteResponse")
public class CautareSedinteResponse {

    @XmlElement(name = "CautareSedinteResult")
    protected ArrayOfSedinta cautareSedinteResult;

    /**
     * Gets the value of the cautareSedinteResult property.
     *
     * @return possible object is
     * {@link ArrayOfSedinta }
     */
    public ArrayOfSedinta getCautareSedinteResult() {
        return cautareSedinteResult;
    }

    /**
     * Sets the value of the cautareSedinteResult property.
     *
     * @param value allowed object is
     *              {@link ArrayOfSedinta }
     */
    public void setCautareSedinteResult(ArrayOfSedinta value) {
        this.cautareSedinteResult = value;
    }

}

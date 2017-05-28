
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
 *         &lt;element name="CautareDosare2Result" type="{portalquery.just.ro}ArrayOfDosar" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cautareDosare2Result"
})
@XmlRootElement(name = "CautareDosare2Response")
public class CautareDosare2Response {

    @XmlElement(name = "CautareDosare2Result")
    protected ArrayOfDosar cautareDosare2Result;

    /**
    * Gets the value of the cautareDosare2Result property.
    *
    * @return possible object is
    * {@link ArrayOfDosar }
    */
    public ArrayOfDosar getCautareDosare2Result() {
        return cautareDosare2Result;
    }

    /**
    * Sets the value of the cautareDosare2Result property.
    *
    * @param value allowed object is
    *              {@link ArrayOfDosar }
    */
    public void setCautareDosare2Result(ArrayOfDosar value) {
        this.cautareDosare2Result = value;
    }

}

package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="HelloWorldResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "helloWorldResult"
})
@XmlRootElement(name = "HelloWorldResponse")
public class HelloWorldResponse {

    @XmlElement(name = "HelloWorldResult")
    protected String helloWorldResult;

    /**
     * Gets the value of the helloWorldResult property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getHelloWorldResult() {
        return helloWorldResult;
    }

    /**
     * Sets the value of the helloWorldResult property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHelloWorldResult(String value) {
        this.helloWorldResult = value;
    }

}

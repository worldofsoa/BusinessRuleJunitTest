//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.09.22 a las 02:38:58 PM CEST 
//


package com.worldofsoa.br.junit.stub;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}properties" minOccurs="0"/>
 *         &lt;element ref="{}testcase" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}system-out" minOccurs="0"/>
 *         &lt;element ref="{}system-err" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tests" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="failures" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="errors" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="disabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="skipped" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="hostname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="package" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "properties",
    "testcase",
    "systemOut",
    "systemErr"
})
@XmlRootElement(name = "testsuite")
public class Testsuite {

    protected Properties properties;
    protected List<Testcase> testcase;
    @XmlElement(name = "system-out")
    protected String systemOut;
    @XmlElement(name = "system-err")
    protected String systemErr;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "tests", required = true)
    protected String tests;
    @XmlAttribute(name = "failures")
    protected String failures;
    @XmlAttribute(name = "errors")
    protected String errors;
    @XmlAttribute(name = "time")
    protected String time;
    @XmlAttribute(name = "disabled")
    protected String disabled;
    @XmlAttribute(name = "skipped")
    protected String skipped;
    @XmlAttribute(name = "timestamp")
    protected String timestamp;
    @XmlAttribute(name = "hostname")
    protected String hostname;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "package")
    protected String _package;

    /**
     * Obtiene el valor de la propiedad properties.
     * 
     * @return
     *     possible object is
     *     {@link Properties }
     *     
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Define el valor de la propiedad properties.
     * 
     * @param value
     *     allowed object is
     *     {@link Properties }
     *     
     */
    public void setProperties(Properties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the testcase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testcase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestcase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Testcase }
     * 
     * 
     */
    public List<Testcase> getTestcase() {
        if (testcase == null) {
            testcase = new ArrayList<Testcase>();
        }
        return this.testcase;
    }

    /**
     * Obtiene el valor de la propiedad systemOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemOut() {
        return systemOut;
    }

    /**
     * Define el valor de la propiedad systemOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemOut(String value) {
        this.systemOut = value;
    }

    /**
     * Obtiene el valor de la propiedad systemErr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemErr() {
        return systemErr;
    }

    /**
     * Define el valor de la propiedad systemErr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemErr(String value) {
        this.systemErr = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad tests.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTests() {
        return tests;
    }

    /**
     * Define el valor de la propiedad tests.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTests(String value) {
        this.tests = value;
    }

    /**
     * Obtiene el valor de la propiedad failures.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailures() {
        return failures;
    }

    /**
     * Define el valor de la propiedad failures.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailures(String value) {
        this.failures = value;
    }

    /**
     * Obtiene el valor de la propiedad errors.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Define el valor de la propiedad errors.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrors(String value) {
        this.errors = value;
    }

    /**
     * Obtiene el valor de la propiedad time.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTime() {
        return time;
    }

    /**
     * Define el valor de la propiedad time.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Obtiene el valor de la propiedad disabled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisabled() {
        return disabled;
    }

    /**
     * Define el valor de la propiedad disabled.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisabled(String value) {
        this.disabled = value;
    }

    /**
     * Obtiene el valor de la propiedad skipped.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkipped() {
        return skipped;
    }

    /**
     * Define el valor de la propiedad skipped.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkipped(String value) {
        this.skipped = value;
    }

    /**
     * Obtiene el valor de la propiedad timestamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Define el valor de la propiedad timestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * Obtiene el valor de la propiedad hostname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Define el valor de la propiedad hostname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad package.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Define el valor de la propiedad package.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
    }

}

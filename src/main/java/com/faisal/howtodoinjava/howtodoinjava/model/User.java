package com.faisal.howtodoinjava.howtodoinjava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
@Data
@AllArgsConstructor
public class User implements Serializable {
    @XmlAttribute(name = "id")
    private int id;
    @XmlAttribute(name = "uri")
    private String uri;
    @XmlAttribute(name = "firstName")
    private String firstName;
    @XmlAttribute(name = "lastName")
    private String lastName;
}

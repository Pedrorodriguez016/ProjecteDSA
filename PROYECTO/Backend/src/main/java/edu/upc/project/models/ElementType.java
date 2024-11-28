package edu.upc.project.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ElementType {
    @XmlEnumValue("knife") KNIFE,
    @XmlEnumValue("shield") SHIELD,
    @XmlEnumValue("sword") SWORD,
    @XmlEnumValue("potion") POTION,
    @XmlEnumValue("armor") ARMOR
}
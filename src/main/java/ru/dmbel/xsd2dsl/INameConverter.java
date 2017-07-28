package ru.dmbel.xsd2dsl;

/**
 * Created by bdn on 28.07.2017.
 */
public interface INameConverter {
    String xsdName2dslName(String xsdName, XSDType type);
}

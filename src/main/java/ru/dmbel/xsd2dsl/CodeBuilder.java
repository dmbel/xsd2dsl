package ru.dmbel.xsd2dsl;

/**
 * Created by dm on 10.07.17.
 */
public class CodeBuilder {
    private String packageName;
    private CamelNameConverter nameConverter;

    public CodeBuilder(String packageName, CamelNameConverter nameConverter) {
        this.packageName = packageName;
        this.nameConverter = nameConverter;
    }

    public void addElement(String elemName) {
    }

    public void pushNode(XSDType type, String name) {
    }
}

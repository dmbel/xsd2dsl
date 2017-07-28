package ru.dmbel.xsd2dsl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.squareup.javapoet.ClassName;
import ru.dmbel.jcomander.FileExistsValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by dm on 04.07.17.
 */
public class Main {

    @Parameter(names = {"-o", "--output"}, description = "Output directory for java files", required = true)
    File javaFilesOutputDir;

    @Parameter(names = {"-x", "--xsd"}, description = "XSD input file", required = true, validateValueWith = FileExistsValidator.class)
    File xsdFile;

    @Parameter(names = {"-p", "--package"}, description = "Package name for generated java files", required = true)
    String packageName;

    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander jc = new JCommander();
        jc.addObject(main);
        boolean parsed = true;
        try {
            jc.parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            parsed = false;
        }
        if (parsed) {
            if (main.help) {
                jc.usage();
            } else {
                main.run();
            }
        }
    }

    private void run() {
        try {
            // XSD items name to java class/methods/parameters name converter
            NameConverter nameConverter = new NameConverter();
            // Javapoet based code builder
            CodeBuilder codeBuilder = new CodeBuilder(packageName, nameConverter);
            // XSD file parser
            XSDProcessor proc = new XSDProcessor(new FileInputStream(xsdFile), codeBuilder);
            proc.process();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

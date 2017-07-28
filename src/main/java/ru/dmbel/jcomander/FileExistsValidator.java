package ru.dmbel.jcomander;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * Created by bdn on 27.07.2017.
 */
public class FileExistsValidator implements IValueValidator<File> {
    @Override
    public void validate(String name, File file) throws ParameterException {
        if(!file.exists()) throw new ParameterException("File " + file.getAbsolutePath() + " does not exists");
    }
}

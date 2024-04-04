package org.vut.ija_project.Reader;

import org.vut.ija_project.Environment.Environment;

import java.io.File;
import java.io.IOException;

public interface EnvironmentReader
{
    public Environment createEnvironmentFromSource(File source) throws RuntimeException;
}

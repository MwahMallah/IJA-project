package org.vut.ija_project.DataLayer.Reader;

import org.vut.ija_project.DataLayer.Environment.Environment;

import java.io.File;

public interface EnvironmentReader
{
    public Environment createEnvironmentFromSource(File source) throws RuntimeException;
}

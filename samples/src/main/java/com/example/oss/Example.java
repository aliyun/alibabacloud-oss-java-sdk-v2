package com.example.oss;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public interface Example {

    Options getOptions();

    void runCmd(CommandLine cmd) throws ParseException;
}

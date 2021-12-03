package edu.estu;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;


public class Myoptions {
    @Option(name="-start",usage="Sets a name")
    public String start;

    @Option(name="-topN",usage="Sets a name")
    public int topN;

    @Option(name="-task",aliases = {"--task"},usage = "print the task")
    public String task;

    @Argument(required = true, usage = "name of the file that you want toaaaaaaaaaaaa sort")
    public String[] fileName;

    @Option(name = "-u", aliases = {"--unique", "--uniq"}, usage = "print the unique values (e.g. deduplicate the input)")
    boolean unique;

    @Option(name = "-r", aliases = {"--reverse"}, usage = "print the unique values (e.g. deduplicate the input)")
    boolean reverse;



}

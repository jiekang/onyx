# onyx
Java Parser To Byteman Rules

## What it does

onyx generates byteman rules for a given source directory. Rules are created for all methods in all java files that trigger on method entry and exit, printing call information to stderr. This can be redirected into a file and used as a run-time call trace for analysis. See aventurine for GUI displays of these output files. onyx only parses Java 7 code at this point in time.

## Requirements

Maven
Java 8

## How to use onyx

In onyx directory:
```
$ make install
$ ./bin/onyx <patch to directory with java files> <path to output directory for rules>
```

A sample usage is included in the ./src/test/system folder

## How to use generated rules

The rules are a byteman rules file. See byteman for more info on how to use these rules. The aventurine projects take the output from programs with these rules injected and displays them in GUI form.

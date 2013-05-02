StatsD proxy
=================

A simple server for forwarding HTTP requests to StatsD.


## Building

    mvn package

## Running

	java -jar blah.jar server path/to/configuration.yml

## Using

Send requests to `servername/stats` with the following query parameters:

`b`: The bucket name to use.

`m`: The type of metric, either `counter`, `gauge` or `time`. Optional, defaults to `counter`.

`v`: The value. Defaults to 1 for `counter`.
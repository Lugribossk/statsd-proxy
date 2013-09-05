StatsD proxy
=================

A simple server for forwarding HTTP requests to StatsD.
This is useful for sending data directly from a Javascript application.


## Building

    mvn package

## Running

	java -jar blah.jar server path/to/configuration.yml

## Using

Send GET or POST request to `servername/stats` with the following query or form parameters:

`p`: The path/bucket/aspect/metric name to use.

`t`: The type of metric, either `counter`, `gauge` or `time`. Optional, defaults to `counter`.

`v`: The value. Defaults to 1 for `counter`.

`servername/stats/s.gif` also works so that requests can be sent without Javascript.
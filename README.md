# README #

### What is this repository for? ###

This is the MAPE loop for the GLAPP


### How do I get set up? ###

1. Start a Docker Swarm and a Prometheus server.
2. Run some apps on your Swarm.
3. ...


### Config file for MAPE ###

File name: config.txt

Location: MAPE root directory

Sample content:
```
#!text
sailsServerIP=<IP address of the Sails server>
prometheusServerIP=<IP address of the Prometheus server>
prometheusServerPort=19090
ForceMDP=false
violoatedMetric=process_cpu_seconds_total
violatedCellId=57725130644b311b20c4d8a2
violatedOrganId=57724fef644b311b20c4d898
violatedAppId=57724fee644b311b20c4d896
healthinessValue=0

```

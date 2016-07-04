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

```
#!text
prometheusServerIP=146.185.171.54
prometheusServerPort=19090
violoatedMetric=process_cpu_seconds_total
violatedCellId=57725130644b311b20c4d8a2
violatedOrganId=57724fef644b311b20c4d898
violatedAppId=57724fee644b311b20c4d896
healthinessValue=0

```
# PEGI on steam games library

## purpose

Parse a game list (e.g. from steam) as input and prints its [PEGI info](https://pegi.info) in a HTML output. 

 
## On Ubuntu/Linux

JDK and sbt are required (see "install dev environment" section).

### launch app

    sbt "run my-games.txt"


### technical details

    
#### <a name="install"></a> install dev environment 

    ./__install.sh

#### unit test 

    sbt test
    
#### uninstall dev environment 

    ./__uninstall.sh

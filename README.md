This project provides simple KV (key-value) store web service with a subscription feature. 
As a user, one can perform set(key, val) and get(key) operations over HTTP and also subscribe to
changes happening to any of the keys. This repository provides Server-side implementation of the web 
service and https://github.com/Nancy-Chauhan/keystore-client repository provides a CLI client which consumes the web service
supporting following commands :
 
1) get <key>: displays the value of an existing key
2) put <key> <value>: sets the value of the given key
3) watch: when executed this displays any new changes happening on the KV store in realtime

## Libaries used

1) <a href="http://sparkjava.com/">Spark Framework</a>
2) gson

## API 

##### GET /keyvalue
This displays all the keys. 
##### GET /keyvalue/:key 
This displays value of the key.
##### POST /keyvalue
This sets Key and Value.
##### DELETE /keyvalue/:key
This deletes the key.
##### WebSocket /watch
Watch : It send a change notification to every socket client which is connected. 


## Usage

To deploy Keystore code clone the repository and BUILD and RUN docker, to enable the API at the port 4567 of the host by following commands:
```
docker build -t keystore .
docker run -p 4567:4567 keystore 
```
After setting up the environment of server side , move to https://github.com/Nancy-Chauhan/keystore-client repository for
the usage of CLI.

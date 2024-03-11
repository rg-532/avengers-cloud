# Device Manager Service

**Submittors:** Rom Gat, Dori Rozen, Noy Hanan, Ido Ronen


## What does this Service do?

This service manages the devices in the smart-home. As such, this service **does not communicate** with any other services, and many services may need to communicate with it.

## Setup

### Docker

You need to set up Docker on your local machine for this service to run. To do so:

- Install **Docker Desktop** using [this link](https://www.docker.com/products/docker-desktop/) and click "Download For [Your OS family]".

- If you wish to run this service without relying on other services being live, you may:

  - Go to the `compose.yaml` file at the root of our project, and remove the comment prefixes (the `#` at the start of a line) from all lines.
  - Go to the `build.gradle` file at the root of our project, and remove the comment prefixes (the `/` at the start of a line) from the line:
    
    `// developmentOnly 'org.springframework.boot:spring-boot-docker-compose'`
  
- Alternatively, you may configure a Docker Container to listen to port `27018` on your machine, or use another service's configuration which does the same, as was done in the demo in class.


### Kafka

You need to set up Kafka on your local machine as well. To do so:

- If you wish to configure Kafka on your own:
  - Download the set of scripts prepared for you [here](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.7.0/kafka_2.13-3.7.0.tgz), you may use the HTTP download option to start the download.
  - Copy the contents of the tar file into your drive, to avoid a long path-name error.
  - Change the name of the root folder you copied (root folder inside the tar file) to something shorter, such as "kafka". Again, this is done in order to avoid a long path-name error.
  - Open the file `.\config\server.properties` and find a commented-out line (Starts with `#` and no space) that looks like:
    
    `#listeners=PLAINTEXT://:9092`
    
    Remove the `#` from the beginning, before the last `:`, add the term `localhost` and change the port value from `9092` to `29092`.

  - Open three terminals and on each one run one of the following commands, in the given order, depending on your operating system, to start up a Kafka server and bind the topic `topic1` to port `29092`. On Linux, run:
  
    `$ bin/zookeeper-server-start.sh config/zookeeper.properties`
      
    `$ bin/kafka-server-start.sh config/server.properties`
    
    `$ bin/kafka-topics.sh --create --topic topic --bootstrap-server localhost:29092`

    and on Windows, run:

    `PS> ./bin/windows/zookeeper-server-start.bat config/zookeeper.properties`

    `PS> ./bin/windows/kafka-server-start.bat config/server.properties`
    
    `PS> ./bin/windows/kafka-topics.bat --create --topic topic1 --bootstrap-server localhost:29092`

- Alternatively, as with the docker setup, you may configure a Kafka server to run on port `29092` with topic `topic1`, or have another service do it for you, as was done in the demo in class.

- Optionally, you may use the third terminal (Or any terminal that is not running anything) to run a command which creates a consumer (listener)  for the configured topic. On Linux:
  
  `$ bin/kafka-console-consumer.sh --topic topic1 --bootstrap-server localhost:29092`
  
  And on Windows:
  
  `PS> ./bin/windows/kafka-console-consumer.bat --topic topic1 --bootstrap-server localhost:29092`


## Working with our Service

Our service utilizes Open-API as a client to send requests from, to port `6080`. It also uses an additional HTTP controller which connects to RSocket for ease of testing. You may also directly connect to RSocket using port `7071`. Both of these exist on `localhost`, of course.

To begin, you may wish to register a few new devices into the smart home. You may do so using:
- HTTP `POST` method with the path `/devices`, or:
- RSocket with route `registerDevice-req-resp`

With the request body containing something like:
```commandline
{
  "messageType": "deviceNotification",
  "summary": "Client created device.",
  "externalReferences": [
    {
      "service": "client",
      "externalServiceId": "clientId2"
    }
  ],
  "messageDetails": {
    "device": {
      "type": "Refrigerator",
      "subType": "Cold",
      "location": "Kitchen",
      "manufacturerPowerInWatts": 70,
      "additionalAttributes": {
        "colorRGB": [0, 0, 20]
      },
      "status": {
        "temperatureInCelsius": 18
      }
    }
  }
}
```

You may then fetch all registered devices using:
- HTTP `GET` method with the path `/devices`, or:
- RSocket with route `getAllDevices-req-stream`

If you wish to update the device, you may use:
- HTTP `PUT` method with the path `/devices/{id}`, or:
- RSocket with route `updateDevice-{id}-fnf`

With the request body containing something like:
```commandline
{
  "messageType": "deviceNotification",
  "summary": "Client updated device with (id=67f8e788-faae-48f6-a08c-f433e0528e0e).",
  "externalReferences": [
    {
      "service": "client",
      "externalServiceId": "clientId3"
    }
  ],
  "messageDetails": {
    "device": {
      "type": "Refrigerator",
      "subType": "Cold",
      "location": "Kitchen",
      "manufacturerPowerInWatts": 70,
      "additionalAttributes": {
        "colorRGB": [255, 255, 255]
      },
      "status": {
        "temperatureInCelsius": 18
      }
    }
  }
}
```

There is also a shortcut-method for updating a device's status:
- HTTP `PUT` method with the path `/devices/{id}/status`, or:
- RSocket with route `updateDeviceStatus-{id}-fnf`

With the request body containing the status alone, like:
```commandline
{
  "temperatureInCelsius": 14
}
```

Once you are done, you can clean up using:
- HTTP `DELETE` method with the path `/devices`, or:
- RSocket with route `deleteAllDevices-fnf`

The rest of our API can be found in the moodle, of course, and it contains a few more interesting utilities.

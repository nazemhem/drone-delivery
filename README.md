# drone-delivery

### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

### Requirements

- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;



#Solution


# **Drone App API**

# **By Nazem Elmadani**

This is a RESTful API service for a drone app that is used for medications delivery by drones.


**GET** api/v1/drone

This is used to get all drones.

Query parameter:

- State: used to filter returned drones based on their state.

http://localhost:8080/api/v1/drone?state=idle

Response:

[
    {
        "model": "Lightweight",
        "state": "IDLE",
        "meds": [
            {
                "medication": {
                    "code": "M2",
                    "name": "Adderall",
                    "weight": 26.0,
                    "imageURL": "image-url.com/adderall"
                },
                "count": 2
            },
            {
                "medication": {
                    "code": "M1",
                    "name": "Acetaminophen",
                    "weight": 50.0,
                    "imageURL": "image-url.com/acetaminophen"
                },
                "count": 1
            }
        ],
        "serial_number": "D1",
        "battery_capacity": 100
    },
    {
        "model": "Heavyweight",
        "state": "IDLE",
        "meds": [
            {
                "medication": {
                    "code": "M1",
                    "name": "Acetaminophen",
                    "weight": 50.0,
                    "imageURL": "image-url.com/acetaminophen"
                },
                "count": 4
            }
        ],
        "serial_number": "D2",
        "battery_capacity": 100
    },
    {
        "model": "Heavyweight",
        "state": "IDLE",
        "meds": [],
        "serial_number": "D4",
        "battery_capacity": 100
    }
]

**POST** api/v1/drone

This is used to register a new drone. Returns the drone created.

http://localhost:8080/api/v1/drone

Request:

    {
    "serial_number":"D22",
    "model":"Heavyweight"
}

Response:

{
    "model": "Heavyweight",
    "state": "IDLE",
    "meds": null,
    "serial_number": "D22",
    "battery_capacity": 50
}

**GET** api/v1/drone/{serial_number}

This is used to return a specific drone data using the drone serial number.

Query parameter:

- Field: used to specify parameters to only return them (battery/meds)

http://localhost:8080/api/v1/drone/D2

Response:

{
    "model": "Heavyweight",
    "state": "IDLE",
    "meds": [
        {
            "medication": {
                "code": "M1",
                "name": "Acetaminophen",
                "weight": 50.0,
                "imageURL": "image-url.com/acetaminophen"
            },
            "count": 4
        }
    ],
    "serial_number": "D2",
    "battery_capacity": 100
}

**PUT** api/v1/drone/{serial_number}

This is used to update drone data using the serial number.


http://localhost:8080/api/v1/drone/D2

Request: 
{
    "serial_number":"D2",
    "state":"IDLE"
}

Response:
{
    "model": "Heavyweight",
    "state": "IDLE",
    "meds": [
        {
            "medication": {
                "code": "M1",
                "name": "Acetaminophen",
                "weight": 50.0,
                "imageURL": "image-url.com/acetaminophen"
            },
            "count": 4
        }
    ],
    "serial_number": "D2",
    "battery_capacity": 100
}

**POST** api/v1/drone/load

This is used to unload the drone and load the new medications (medication_code, count) and returns the drone data. Cannot add meds more than drone maximum weight (500 gm)

http://localhost:8080/api/v1/drone/D2/load

Request:
   {
        "M1": "8",
        "M2":1
    }

Response:

{
    "model": "Heavyweight",
    "state": "LOADED",
    "meds": [
        {
            "medication": {
                "code": "M2",
                "name": "Adderall",
                "weight": 26.0,
                "imageURL": "image-url.com/adderall"
            },
            "count": 1
        },
        {
            "medication": {
                "code": "M1",
                "name": "Acetaminophen",
                "weight": 50.0,
                "imageURL": "image-url.com/acetaminophen"
            },
            "count": 8
        }
    ],
    "serial_number": "D2",
    "battery_capacity": 100
}

**POST** api/v1/medication

This is used to create a new medication. Returns the medication created.

http://localhost:8080/api/v1/medication

Request:

{
        "code": "M88",
        "name":"Panadol",
        "weight":8
    }
    
Response:

{
    "code": "M88",
    "name": "Panadol",
    "weight": 8.0,
    "imageURL": null
}



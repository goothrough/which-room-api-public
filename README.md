# Which Room API

The **Which Room API** provides the back-end functionality for the Which Room application, enabling users to check and manage Zoom room availability. It is designed to work seamlessly with the [Which Room Client](https://github.com/goothrough/which-room-client-public).

## Features

- RESTful API for managing room availability
- Integration with Zoom API for real-time updates
- Secure endpoints with authentication and authorization

## Prerequisites

Before setting up the API, ensure creating Zoom app following the link below.
https://developers.zoom.us/docs/build-flow/

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/goothrough/which-room-api-public.git
   ```

2. Navigate to the project directory:

   ```bash
   cd which-room-api-public
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

## Configuration

1. Open `application.yml` file in the `src/main/resources` directory.
2. Edit the following configuration:

   ```yml
        settings:
        # Zoom Owner Account ID
        accountid: YOUR_ZOOM_ACCOUNT_ID
        # Zoom App Client ID
        clientid: ZOOM_APP_CLIENT_ID
        # Zoom App Client Secret
        clientsecret: ZOOM_APP_CLIENT_SECRET

        # A Hash Variable to convert the user's PMI(Personal Meeting ID) to a display value.
        # Key: PMI, Value: Meeting room name to be displayed
        # The number of key-value pairs matches the number of rooms you manage. 
        pmitodisplayroomname: 
            PMI1: Meeting Room 1
            PMI2: Meeting Room 2
            PMI3: Meeting Room 3
   ```

   Replace `YOUR_ZOOM_ACCOUNT_ID`, `ZOOM_APP_CLIENT_ID`, `ZOOM_APP_CLIENT_SECRET`, which are Zoom API credentials, and `PMI1`, `PMI2`,`PMI3`, which are the Personal Meeting IDs you manage. You can add more rooms if needed.

## Running the API

To start the API server, run:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080` by default.

## Endpoints

### Room Management

- `GET /getZoomStatus`
  - Retrieve a status list of all rooms.

    Here is a sample response.
    ```
    {
        "zoomStatusList": [
            {
                "userName": "Eric Clapton",
                "roomName": "Eric Clapton's Personal Meeting Room",
                "roomDisplayName": "Meeting Room 1",
                "roomStatus": "waiting",
                "joinUrl": "https://us04web.zoom.us/j/xxxxxxxxx?pwd=xxxxxxxxx"
            },
            {
                "userName": "Paul McCartney",
                "roomName": "Paul McCartney's Personal Meeting Room",
                "roomDisplayName": "Meeting Room 2",
                "roomStatus": "waiting",
                "joinUrl": "https://us04web.zoom.us/j/xxxxxxxxx?pwd=xxxxxxxxx"
            },
            {
                "userName": "Ed Sheeran",
                "roomName": "Ed Sheeran's Personal Meeting Room",
                "roomDisplayName": "Meeting Room 3",
                "roomStatus": "waiting",
                "joinUrl": "https://us04web.zoom.us/j/xxxxxxxxx?pwd=xxxxxxxxx"
            }
        ],
        "requestTimeStamp": "2024/12/11 01:28:03"
    }
    ```

## Deployment

1. Package the application:

   ```bash
   mvn package
   ```

2. Run the JAR file:

   ```bash
   java -jar target/which-room-api-<VERSION>.jar
   ```

Replace `<VERSION>` with the actual version of the JAR file.


## Acknowledgments

The Which Room API was developed to provide a robust back-end solution for managing Zoom room availability in tandem with the Which Room Client.

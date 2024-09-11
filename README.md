## Build tools & versions used

Kotlin: 1.8.0
Android SDK: 34
Retrofit: 2.9.0
Jetpack Compose: 1.5.1
JUnit & Mockito: For unit testing
Gradle: 8.1.1
Coroutines: For asynchronous programming

## Steps to run the app
- Clone the repository:
  git clone https://github.com/seu-usuario/bitcoin-node-challenge.git
- Create a local.properties file in the root directory of the project with your API_KEY
- Run the project.
- 
## What areas of the app did you focus on?

- Main Screen: Displays a list of "Bitcoin Nodes".
- Detail Screen: Each card on the main screen opens a detail screen with detailed information about the node.
- API Integration: Used Retrofit to consume a Bitcoin node API.
- Pull to Refresh: Implemented pull-to-refresh functionality to update the list of nodes.
- Unit Testing: Focused on unit testing the ViewModel using coroutines and LiveData.

## What was the reason for your focus? What problems were you trying to solve?

 The main focus was on creating a easy and user-friendly interface to display and interact with Bitcoin nodes. 
 One of the primary challenges was make sure that the data from the API was integrated and displayed in a way that was both responsive and intuitive.
 I needed to ensure that the app could handle the dynamic data from the API without affecting performance. 
 This included implementing pull-to-refresh functionality to allow users to manually update the list of nodes and ensure that data was fetched and displayed correctly.
 Integrating with the API had its own set of challenges. I had to ensure that the API responses were working, including managing different states such as loading, success, and error.
 And i tested the ViewModel to ensure it handles various API responses correctly.

## How long did you spend on this project?

I spent approximately 3 days working on this project:
- 1 day to set up the app and configure the API.
- 2 days working on the pull-to-refresh functionality and the unit testing

## Did you make any trade-offs for this project? What would you have done differently with more time?

I would research ways to make the UI more user-friendly, and I would seek to implement stateFlow to deal with data updates in real time and avoid memory leaks.
Also would work on testing the Compose.

## What do you think is the weakest part of your project?

The weakest part would be the user interface that could be better worked on, and also the fact that it does not save data offline

## Is there any other information you’d like us to know?

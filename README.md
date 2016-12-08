# CSCapstone
There are 3 seperate project included:
WebApi: Is a ASP.Net web api that purpose is to store data from the drones, and send data to the phone application. 
Recommended IDE: Visual Studio (Community is fine)
Note: It can be very difficult to expose this api externally if run locally, so I recommend tethering your phone using Chrome://inspect and port forwarding to the local port used by your web api.

Visualizer: Is the Android app which displays the information which it gets from the Web Api
Recommended IDE: Android Studio

Data Seeder: is a small C# console application which seeds data into the web api, and can be used as an example of how the drones should communicate with the web api
Recommended IDE: Visual Studio (Community is fine)

If running on a new machine, you will have to insert the installion location of your python interpreter into the webapi class CoordinateController. 

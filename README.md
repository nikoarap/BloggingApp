# BloggingApp
Microblogging Application

Android application for purposes of a microblogging platform. This project was implemented with the use of MVVM architecural pattern for optimal data flow, component control, reusability and Unit Testing.

The following libraries were used:
------

-Android Lifecycle Extensions for the implementation of the ViewModels and LiveData

-Retrofit for RESTful API manipulation - Retrofit Gson converter for JSON Serialization

-Room persistance for SQLite integration and caching 

-RecyclerView for displaying ListItems

-Glide for image resources and image caching

-Hdodenhof Circle imageview for the displaying of circular images and avatars

-CardViews for the purposes of displaying ListItems more elegantly

-Android support Design for the use of custom Toolbars (collapsing toolbars)
 
 
 In the application File struture you can find:
 ------

-The POJO models which are the Entity objects 

-The SQLite Database (Room) with the corresponding DAO interface for the Database Inserts & Queries 

-The AsyncTask classes that were used for inserting data into the DB 

-The Repository responsible for handling the requests to Retrofit and their corresponding responses, inserting the data to the DB and then caching them, generally managing the data flow 

-The Viewmodels which are performing as the 'glue' between the Server/DB and the UI 

-The UI (View) in which the Activites of the application reside, with methods of observing the LiveData and performing changes to the UI accordingly 

-The Utility classes: Converter of JsonDataFormat to normal, VerticalSpaceDecorator for spacing between ListItems in the RecyclerView, TypeConverter for converting the type of an Entity to be recognized by the room persistance  DB (the Address array object of the Model Authors in my case 

-The adapters for the RecyclerViews used by the Activities to display ListItems with data such as images 










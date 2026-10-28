SMART PANTRY MANAGER.

1.Project Description

Smart Pantry Manager is an Android application developed using Java in Android Studio.
The purpose of this application is to help users manage ingredients in their pantry, keep track of
the expriry dates of these ingredients and find recipes based
on the ingredients that they already have available in their pantry.
The app aims to make pantry management easier for individuals and help them to reduce wasting of food. 

2.Features

The features of this app includes the following:

1. Adding new pantry ingredients. 
2. Editing existing ingredient details.
3. Deleting ingredients from the pantry.
4. Storing ingredient names, quantities, units and expiration dates.
5. Viewing suggested recipes based on the available ingredients in the pantry.
6. Viewing recipes ingredients and cooking instructions.
7. Enabling or disabling the expiration-alert preferences in Settings. 

3.Technology used

This application used a mix of different technologies which include:
Android Studio:  it was the main devlopment environment.
Java: This was the application programming language used.
SQLite Database: A local Database was used for storing all pantry ingredients and recipes.
XML/Android layouts: User interface resources, where applicable.

4.Database

The Pantry Manager application uses a local SQLite database names smart_pantry.db.
The database contains the following tables:

pantry table: stores ingredient names, quantities, units and the expiry dates.
recipes table: stores recipe names, and basic cooking instructions.
recipe_ingredients table: stores the ingredients required for each recipe.

5.How to Run the Application

1. Firstly open Android Studio. 
2. Open the Smart Pantry Manager project folder.
3. Allow and let Gradle to sync and finish loading completely.
4. Start an Android emulator or connect a compatible Android device.
5. Select the app run configuration.
6. Click run to build and launch the application.

Important to note:

The application stores its data locally on the device. Data may be lost if the application data or emulator is reset or if the app is uninstalled.


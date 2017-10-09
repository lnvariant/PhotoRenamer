PhotoRenamer
========================

An application that allows the user to rename image files with tags appended at the end.

## How To Use ##
Run the main method inside 'photo_renamer/PhotoRenamer.java'.

## How It Works ##

If you want a summery of how it all works, please refer to 'photo_renamer/PhotoRenamer.java'.

The backend of the program is all inside the 'photo_renamer' package. Any classes inside there
work without any input from the GUI. The frontend greatly relies the backend, so changing method
signatures can affect the frontend.

The frontend of the program was done using JavaFX. JavaFX is a newer version of Swing that is
suppose to serve as a replacement. JavaFX is used extensively throughout the application, so
the user must undersand how JavaFX works before proceeding with the code.

# JavaFX #

JavaFX splits up making the GUI into three components: Model, View, and Controller. The model is
the backend of the program, the View describes how the GUI looks, and the Controller is the median
between the Model and View, it binds methods components in the View with methods inside the Model.

The Models of the program are in the 'photo-renamer' package; all the classes that define the backend.
The Controllers and Views are in the 'GUI' package. Each package in the 'GUI' package contains a
Controller and a View. The 'GUI/Main' package contains the main view and controller of program, as well
as the start screen. Every other package inside 'GUI' describes a component in the Main GUI.

A View is represented by an FXML file. The FXML file is made up of tags which are put together to create
how the GUI will look to the end user. The FXML file is then connected to its associated controller, which
controls every component inside the FXML file. For example, a button is defined inside the FXML file which
the <Button/> tag, it is given an fx:id inside the tag, and then the the controller defines a variable
with type Button, that has the exact same name as the fx:id. The button is then manipulated however you
want inside the controller. What the button does after say clicking it, will be defined in the backend, but
any GUI related things would still be defined in the Controller

# JavaFX with Photorenamer #

In this program, we have a 'GUI/Main' package that has a photorenamer.fxml and PhotoRenamerController. The
FXML file defines how the entire GUI will look, and the controller then connects each component inside
the GUI with its individual controllers. We have 5 main components: FileExplorer, TagSelecter, TagBar,
LogManager, and MenuBar. Each component has a controller, and any GUI that the controller needs is given
to it through its construct method. Each Controller is also a singleton, since we will only need just one
instance of each controller. The PhotoRenamerController defines the FXML variables, then creates an instance
of the other controllers, passes the FXML variables to the controllers, and then the individual controllers
control what to do with the FXML variables.

The PhotoRenamer class in the backend extends Application, which is how the program knows it's the main class
and that we're working with JavaFX. Inside the class' start method, we load up the photorenamer.fxml, and then
we create a window and scene for which the FXML file will be displayed inside of. We also create a separare
window for a start screen that shows a small tutorial and has the user select the starting directory for the
program. After this, anything the user does in the main program is controlled by the individual controllers
in the 'GUI' package.
package com.example.project1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import static javafx.scene.control.Alert.AlertType.*;


public class Main extends Application {

    static LinkedList<Flight> flights = new LinkedList<>();

    private static double xOffset = 0;
    private static double yOffset = 0;

    static class WindowsButtons extends HBox {

        public WindowsButtons() {

            Button closeButton = new Button("\u2715");
            closeButton.setOnAction(e -> Platform.exit());
            closeButton.setStyle("-fx-background-color: #ff605c;-fx-border-color: #ff605c;");
            closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #ff0000;-fx-border-color: #ff0000;"));
            closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #ff605c;-fx-border-color: #ff605c;"));
            closeButton.setMinSize(20, 20);
            closeButton.setMaxSize(20, 20);
            closeButton.setPadding(new Insets(0, 0, 0, 0));

            Button minimizeButton = new Button("\u2212");
            minimizeButton.setOnAction(e -> ((Stage) getScene().getWindow()).setIconified(true));
            minimizeButton.setStyle("-fx-background-color: #ffbd44;-fx-border-color: #ffbd44;");
            minimizeButton.setOnMouseEntered(e -> minimizeButton.setStyle("-fx-background-color: #ff9439;-fx-border-color: #ff9439;"));
            minimizeButton.setOnMouseExited(e -> minimizeButton.setStyle("-fx-background-color: #ffbd44;-fx-border-color: #ffbd44;"));
            minimizeButton.setMinSize(20, 20);
            minimizeButton.setMaxSize(20, 20);
            minimizeButton.setPadding(new Insets(0, 0, 0, 0));


            Button infoButton = new Button("i");
            infoButton.setStyle("-fx-background-color: #44cdff;-fx-border-color: #44cdff;");
            infoButton.setOnMouseEntered(e -> infoButton.setStyle("-fx-background-color: #00a2c0;-fx-border-color: #00a2c0;"));
            infoButton.setOnMouseExited(e -> infoButton.setStyle("-fx-background-color: #44cdff;-fx-border-color: #44cdff;"));
            infoButton.setOnAction(e -> showAlert("About", "\t CREATED BY OMAR QALALWEH",0));
            infoButton.setMinSize(20, 20);
            infoButton.setMaxSize(20, 20);
            infoButton.setAlignment(Pos.CENTER);
            infoButton.setPadding(new Insets(0, 0, 0, 0));

            //make a spacer between the buttons
            Pane spacer = new Pane();


            getChildren().addAll(closeButton,minimizeButton, spacer, infoButton);


            this.setSpacing(7);

            setAlignment(Pos.CENTER);
            setPadding(new Insets(0, 0, 0, 0));
        }
    }



    @Override
    public void start(Stage stage) {


        //removing stage border
        stage.initStyle(StageStyle.UNDECORATED);
        //get screen size
        Screen screen = Screen.getPrimary();
        Rectangle2D screenSize = screen.getVisualBounds();

        //height of the toolbar
        int toolbarHeight = 30;


    //------------------------------  login page initialization --------------------------------
        BorderPane loginPagePane = new BorderPane();
        Scene loginPageScene = new Scene(loginPagePane, 800, 400);
        loginPagePane.getStylesheets().add("style1.css");


    //------------------------------  main page initialization ---------------------------------
        BorderPane mainPagePane = new BorderPane();
        Scene mainPageScene = new Scene(mainPagePane, screenSize.getWidth(), screenSize.getHeight() - 21);
        mainPageScene.getStylesheets().add("styleMainPage.css");


//------------------------------------------------ login page ----------------------------------------------------------


        //making the login page draggable
        loginPageScene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        loginPagePane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });


        //airline logo
        Image logo = new Image("logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(400);
        logoView.setFitWidth(450);


        ToolBar toolBar = new ToolBar();
        toolBar.setPrefHeight(toolbarHeight);
        toolBar.setMinHeight(toolbarHeight);
        toolBar.setMaxHeight(toolbarHeight);
        toolBar.getItems().add(new WindowsButtons());
        toolBar.setStyle("-fx-background-color: #000000;");


        loginPagePane.setTop(toolBar);

        Label userNameLabel = new Label("User Name");
        userNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-font-family: \"Roboto Light\"; -fx-border-color: #171717; -fx-border-width: 2px; -fx-border-radius: 15px; -fx-background-color:#F6F7F7; -fx-pref-width: 150px; -fx-pref-height: 30px;");
        userNameLabel.setAlignment(Pos.CENTER);
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter user name");
        userNameField.setAlignment(Pos.CENTER);
        userNameField.setStyle("-fx-background-radius: 15px;-fx-font-size: 12px;-fx-background-color: #171717;-fx-text-fill: #ffffff; -fx-pref-height: 29px;");
        HBox userNameBox = new HBox();
        userNameBox.setSpacing(5);
        userNameBox.getChildren().addAll(userNameLabel, userNameField);
        userNameBox.setAlignment(Pos.CENTER);


        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-font-family: \"Roboto Light\"; -fx-border-color: #171717; -fx-border-width: 2px; -fx-border-radius: 15px; -fx-background-color:#F6F7F7; -fx-pref-width: 150px; -fx-pref-height: 30px;");
        passwordLabel.setAlignment(Pos.CENTER);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setAlignment(Pos.CENTER);
        passwordField.setStyle("-fx-background-radius: 15px;-fx-font-size: 12px;-fx-background-color: #171717;-fx-text-fill: #ffffff; -fx-pref-height: 29px;");
        HBox passwordBox = new HBox();
        passwordBox.setSpacing(5);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);
        TextField loginError = new TextField();
        loginError.setAlignment(Pos.CENTER);
        loginError.setEditable(false);
        loginError.setStyle("-fx-text-fill: red;-fx-background-color: transparent;");
        loginError.setMaxWidth(200);
        loginError.setMaxHeight(20);


        Button loginButton = new Button("Login");
        loginButton.getStylesheets().add("style1.css");
        loginButton.setAlignment(Pos.CENTER);
        loginButton.setOnAction(e -> {
            if (userNameField.getText().equals("admin") && passwordField.getText().equals("1234")) {
                stage.setScene(mainPageScene);
                stage.setMaximized(true);
                userNameField.clear();
                passwordField.clear();
                loginError.clear();
            } else {
                loginError.setText("Invalid user name or password");
            }
        });


        VBox loginFields = new VBox();
        loginFields.setSpacing(20);
        loginFields.setAlignment(Pos.CENTER);
        loginFields.getChildren().addAll(userNameBox, passwordBox);

        VBox loginErrorBox = new VBox();
        loginErrorBox.setSpacing(10);
        loginErrorBox.setAlignment(Pos.CENTER);
        loginErrorBox.getChildren().addAll(loginButton, loginError);

        VBox loginBox = new VBox();
        loginBox.setSpacing(30);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.getChildren().addAll(loginFields, loginErrorBox);

        HBox mainElements = new HBox();
        mainElements.setSpacing(10);
        mainElements.getChildren().addAll(logoView, loginBox);
        HBox.setMargin(logoView, new Insets(0, 20, 0, 0));

        loginPagePane.setCenter(mainElements);
        BorderPane.setAlignment(mainElements, Pos.CENTER);

        BorderPane.setMargin(loginPagePane.getCenter(), new Insets(0, 60, 0, 0));

//---------------------------------------------------- mainPage -------------------------------------------------------------------------------

        ImageView mainPageLogo = new ImageView(new Image("WELCOME.png"));
        mainPageLogo.setFitWidth(400);
        mainPageLogo.setFitHeight(400);

        mainPagePane.setCenter(mainPageLogo);
        BorderPane.setAlignment(mainPageLogo, Pos.CENTER);


        ToolBar toolBar_MainPage = new ToolBar();
        toolBar_MainPage.setPrefHeight(toolbarHeight);
        toolBar_MainPage.setMinHeight(toolbarHeight);
        toolBar_MainPage.setMaxHeight(toolbarHeight);
        toolBar_MainPage.getItems().addAll(new WindowsButtons());
        toolBar_MainPage.setStyle("-fx-background-color: #000000;");
        mainPagePane.setTop(toolBar_MainPage);

        Label menuLabel = new Label(" Menu ");
        menuLabel.setMaxWidth(100);
        menuLabel.setStyle("-fx-font-size: 20px;-fx-text-fill: #000000;-fx-background-color: #ffffff;-fx-border-width: 0px;-fx-border-radius: 0px;-fx-background-radius: 15px;");
        menuLabel.setAlignment(Pos.CENTER);
        Image menuImage = new Image("list.png");
        ImageView menuImageView = new ImageView(menuImage);
        menuImageView.setFitHeight(20);
        menuImageView.setFitWidth(20);
        menuLabel.setGraphic(menuImageView);

        //side Buttons initialization
        Button displayFlightInfo = new Button("Display Flight Info");
        displayFlightInfo.setPrefWidth(170);
        displayFlightInfo.setMinWidth(170);
        displayFlightInfo.setMaxWidth(170);


        Button displayPassengersInfo = new Button("Display Passenger Info");
        displayPassengersInfo.setPrefWidth(170);
        displayPassengersInfo.setMinWidth(170);
        displayPassengersInfo.setMaxWidth(170);


        Button addEditFlight = new Button("Add/Edit Flight");
            addEditFlight.setPrefWidth(170);
        addEditFlight.setMinWidth(170);
        addEditFlight.setMaxWidth(170);


        Button reserveTicket = new Button("Reserve Ticket");
        reserveTicket.setPrefWidth(170);
        reserveTicket.setMinWidth(170);
        reserveTicket.setMaxWidth(170);


        Button cancelTicket = new Button("Cancel Ticket");
        cancelTicket.setPrefWidth(170);
        cancelTicket.setMinWidth(170);
        cancelTicket.setMaxWidth(170);


         Button checkIfTicketIsReserved = new Button("Check Reservation");
        checkIfTicketIsReserved.setPrefWidth(170);
        checkIfTicketIsReserved.setMinWidth(170);
        checkIfTicketIsReserved.setMaxWidth(170);

        Button searchForPassenger = new Button("Search for Passenger");
        searchForPassenger.setPrefWidth(170);
        searchForPassenger.setMinWidth(170);
        searchForPassenger.setMaxWidth(170);


        Button loadFromFiles = new Button("Load From Files");
        loadFromFiles.setPrefWidth(170);
        loadFromFiles.setMinWidth(170);
        loadFromFiles.setMaxWidth(170);



        Button logout = new Button("Logout");
        logout.styleProperty().bind(Bindings.when(logout.hoverProperty())
                .then("-fx-background-color: #ff0033;-fx-text-fill: #ffffff;")
                .otherwise("-fx-background-color: #171717;-fx-text-fill: #ffffff;-fx-border-color: #8A0000FF;"));

        logout.setPrefWidth(170);
        logout.setMinWidth(170);
        logout.setMaxWidth(170);

        Button exit = new Button("Exit");
        exit.styleProperty().bind(Bindings.when(exit.hoverProperty())
                .then("-fx-background-color: #ff0033;-fx-text-fill: #ffffff;")
                .otherwise("-fx-background-color: #8A0000FF;-fx-text-fill: #ffffff;-fx-border-color: #8A0000FF;"));

        exit.setPrefWidth(170);
        exit.setMinWidth(170);
        exit.setMaxWidth(170);




        VBox sideButtons = new VBox();
        sideButtons.setPrefWidth(180);
        sideButtons.setMinWidth(180);
        sideButtons.setMaxWidth(180);
        sideButtons.setStyle("-fx-background-color: #000000;");
        sideButtons.getChildren().addAll(menuLabel, new Pane(),displayFlightInfo, displayPassengersInfo, addEditFlight, reserveTicket, cancelTicket, checkIfTicketIsReserved,searchForPassenger,loadFromFiles,new Pane(),new Pane(),new Pane(),new Pane(), logout,exit);
        sideButtons.setSpacing(15);
        sideButtons.setAlignment(Pos.CENTER);
        sideButtons.setPadding(new Insets(5,7,5,5));
        mainPagePane.setLeft(sideButtons);


    //================================================ Display Flight Info =============================================
        BorderPane displayFlightInfoPane = new BorderPane();
        displayFlightInfoPane.setPadding(new Insets(10,10,10,10));

        BorderPane displayAllPassengersForAFlightPane = new BorderPane();
        displayAllPassengersForAFlightPane.setPadding(new Insets(10,10,10,10));


        //pane for displaying all passengers for a flight
        Label allPassengersForAFlightLabel = new Label(" All Passengers for the Flight ");
        allPassengersForAFlightLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        displayAllPassengersForAFlightPane.setTop(allPassengersForAFlightLabel);
        BorderPane.setAlignment(allPassengersForAFlightLabel, Pos.CENTER);
        BorderPane.setMargin(allPassengersForAFlightLabel, new Insets(10,10,10,10));

        ObservableList<Passenger> tempPassengerOfFlight = FXCollections.observableArrayList();

        TableView<Passenger> allPassengersForAFlightTable = new TableView<>();
        allPassengersForAFlightTable.setPrefWidth(1000);
        allPassengersForAFlightTable.setMaxWidth(1000);
        allPassengersForAFlightTable.setMinWidth(1000);
        allPassengersForAFlightTable.setEditable(false);



        TableColumn<Passenger, String> ticketNumberColumn = new TableColumn<>("Ticket Number");
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNum"));
        ticketNumberColumn.setPrefWidth(200);
        allPassengersForAFlightTable.getColumns().add(ticketNumberColumn);
        TableColumn<Passenger, String> firstNameCol = new TableColumn<>("Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameCol.setPrefWidth(200);
        allPassengersForAFlightTable.getColumns().add(firstNameCol);
        TableColumn<Passenger, String> passPortCol = new TableColumn<>("Passport Number");
        passPortCol.setCellValueFactory(new PropertyValueFactory<>("passport"));
        passPortCol.setPrefWidth(200);
        allPassengersForAFlightTable.getColumns().add(passPortCol);
        TableColumn<Passenger, String> nationalityCol = new TableColumn<>("Nationality");
        nationalityCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        nationalityCol.setPrefWidth(200);
        allPassengersForAFlightTable.getColumns().add(nationalityCol);
        TableColumn<Passenger, LocalDate> dateOfBirth  = new TableColumn<>("Date Of Birth");
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        dateOfBirth.setPrefWidth(200);
        allPassengersForAFlightTable.getColumns().add(dateOfBirth);

        displayAllPassengersForAFlightPane.setCenter(allPassengersForAFlightTable);
        BorderPane.setAlignment(allPassengersForAFlightTable, Pos.CENTER);
        BorderPane.setMargin(allPassengersForAFlightTable, new Insets(10,10,10,10));

        Button backButton = new Button(" Back to Flights List ");
        backButton.setPrefWidth(250);
        backButton.setMinWidth(250);
        backButton.setMaxWidth(250);
        backButton.setPrefHeight(50);
        backButton.setMinHeight(50);
        backButton.setMaxHeight(50);
        backButton.setOnAction(e -> mainPagePane.setCenter(displayFlightInfoPane));


        displayAllPassengersForAFlightPane.setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER);

        Label FlightInfoLabel = new Label("  Flights Info Table  ");
        FlightInfoLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        displayFlightInfoPane.setTop(FlightInfoLabel);
        BorderPane.setAlignment(FlightInfoLabel, Pos.CENTER);

        //create observable list for flight info
        ObservableList<Flight> flightInfo = FXCollections.observableArrayList();



        //create table view
        TableView<Flight> flightInfoTable = new TableView<>();
        flightInfoTable.setPrefWidth(1000);
        flightInfoTable.setMinWidth(1000);
        flightInfoTable.setMaxWidth(1000);
        flightInfoTable.setEditable(false);



        //create columns
        TableColumn<Flight, String> flightNumberCol = new TableColumn<>("Flight Number");
        flightNumberCol.setMinWidth(200);
        flightNumberCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        flightInfoTable.getColumns().add(flightNumberCol);
        TableColumn<Flight, String> nameCol = new TableColumn<>("Company Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        flightInfoTable.getColumns().add(nameCol);
        TableColumn<Flight, String> departureCityCol = new TableColumn<>("Departure City");
        departureCityCol.setMinWidth(200);
        departureCityCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        flightInfoTable.getColumns().add(departureCityCol);
        TableColumn<Flight, String> arrivalCityCol = new TableColumn<>("Arrival City");
        arrivalCityCol.setMinWidth(200);
        arrivalCityCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        flightInfoTable.getColumns().add(arrivalCityCol);
        TableColumn<Flight, String> CapacityCol = new TableColumn<>("Capacity");
        CapacityCol.setMinWidth(200);
        CapacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        flightInfoTable.getColumns().add(CapacityCol);


        //add data to table
        refreshFlightInfo(flightInfo, flightInfoTable);

        //create context menu for table
        ContextMenu contextMenu = new ContextMenu();
        MenuItem viewPassengers = new MenuItem(" View Passengers ");
        Image viewPassengersImage = new Image("viewPassengers.png");
        ImageView viewPassengersImageView = new ImageView(viewPassengersImage);
        viewPassengersImageView.setFitHeight(20);
        viewPassengersImageView.setFitWidth(20);
        viewPassengers.setGraphic(viewPassengersImageView);

        contextMenu.getItems().add(viewPassengers);
        flightInfoTable.setContextMenu(contextMenu);


        //add table to display flight info pane
        displayFlightInfoPane.setCenter(flightInfoTable);
        BorderPane.setMargin(displayFlightInfoPane.getCenter(), new Insets(10,10,10,10));

    //=========================================== display passenger info pane ==========================================
        BorderPane displayPassengerInfoPane = new BorderPane();
        displayPassengerInfoPane.setPadding(new Insets(10,10,10,10));

        //Label to enter flight number

        HBox flightNumberBox = new HBox();
        flightNumberBox.setAlignment(Pos.CENTER);
        flightNumberBox.setPadding(new Insets(10,10,10,10));
        flightNumberBox.setSpacing(10);
        Label flightNumberLabel = new Label("  Flight Number:  ");
        flightNumberLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        flightNumberBox.getChildren().add(flightNumberLabel);
        TextField flightNumberTextField_displayPass = new TextField();
        flightNumberTextField_displayPass.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        flightNumberBox.getChildren().add(flightNumberTextField_displayPass);
        displayPassengerInfoPane.setTop(flightNumberBox);
        BorderPane.setAlignment(flightNumberBox, Pos.CENTER);


        ObservableList<Passenger> tempPassengers_2 = FXCollections.observableArrayList();

        //table to display passenger info
        TableView<Passenger> passengerInfoTable = new TableView<>();
        passengerInfoTable.setPrefWidth(1000);
        passengerInfoTable.setMaxWidth(1000);
        passengerInfoTable.setMinWidth(1000);

        //table columns
        TableColumn<Passenger, String> ticketNumberColumn_2 = new TableColumn<>("Ticket Number");
        ticketNumberColumn_2.setCellValueFactory(new PropertyValueFactory<>("ticketNum"));
        ticketNumberColumn_2.setPrefWidth(200);
        passengerInfoTable.getColumns().add(ticketNumberColumn_2);
        TableColumn<Passenger, String> firstNameCol_2 = new TableColumn<>("Name");
        firstNameCol_2.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameCol_2.setPrefWidth(200);
        passengerInfoTable.getColumns().add(firstNameCol_2);
        TableColumn<Passenger, String> passPortCol_2 = new TableColumn<>("Passport Number");
        passPortCol_2.setCellValueFactory(new PropertyValueFactory<>("passport"));
        passPortCol_2.setPrefWidth(200);
        passengerInfoTable.getColumns().add(passPortCol_2);
        TableColumn<Passenger, String> nationalityCol_2 = new TableColumn<>("Nationality");
        nationalityCol_2.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        nationalityCol_2.setPrefWidth(200);
        passengerInfoTable.getColumns().add(nationalityCol_2);
        TableColumn<Passenger, LocalDate> dateOfBirth_2  = new TableColumn<>("Date Of Birth");
        dateOfBirth_2.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        dateOfBirth_2.setPrefWidth(200);
        passengerInfoTable.getColumns().add(dateOfBirth_2);


        displayPassengerInfoPane.setCenter(passengerInfoTable);
        BorderPane.setMargin(displayPassengerInfoPane.getCenter(), new Insets(10,10,10,10));




    //==================================================================================================================
    //------------------------------------------------ Add/Edit Flight -------------------------------------------------
    //==================================================================================================================


        BorderPane addEditFlightPane = new BorderPane();
        addEditFlightPane.setPadding(new Insets(10,10,10,10));


        Label addEditFlightLabel = new Label("  Add/Edit Flight  ");
        addEditFlightLabel.setPrefWidth(500);
        addEditFlightLabel.setMinWidth(500);
        addEditFlightLabel.setMaxWidth(500);

        addEditFlightLabel.setAlignment(Pos.CENTER);
        addEditFlightLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");


        addEditFlightPane.setTop(addEditFlightLabel);
        BorderPane.setAlignment(addEditFlightLabel, Pos.CENTER);


        GridPane addEditFlightGridPane = new GridPane();
        addEditFlightGridPane.setPadding(new Insets(10,10,10,10));
        addEditFlightGridPane.setHgap(10);
        addEditFlightGridPane.setVgap(10);


        Label flightNumberLabel_addPage = new Label("Flight Number");
        flightNumberLabel_addPage.setStyle("""
                -fx-background-color:#000000;
                    -fx-background-radius: 30;
                    -fx-font-weight: bold;
                    -fx-text-fill: white;
                    -fx-pref-width: 300;
                    -fx-pref-height: 35;
                    -fx-font-size: 18;
                    -fx-text-alignment: Center;""");
        flightNumberLabel_addPage.setAlignment(Pos.CENTER);


        TextField flightNumberTextField_addPage = new TextField();
        flightNumberTextField_addPage.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        flightNumberTextField_addPage.setPromptText("Enter Flight Number");


        HBox flightNumberHBox = new HBox();
        flightNumberHBox.setSpacing(10);
        flightNumberHBox.setAlignment(Pos.CENTER);
        flightNumberHBox.getChildren().addAll(flightNumberLabel_addPage,flightNumberTextField_addPage);


        Label flightNameLabel_addPage = new Label("Flight Name");
        flightNameLabel_addPage.setStyle("""
                        -fx-background-color:#000000;
                            -fx-background-radius: 30;
                            -fx-font-weight: bold;
                            -fx-text-fill: white;
                            -fx-pref-width: 300;
                            -fx-pref-height: 35;
                            -fx-font-size: 18;
                            -fx-text-alignment: Center;""");

        addEditFlightGridPane.add(flightNameLabel_addPage,0,1);
        flightNameLabel_addPage.setAlignment(Pos.CENTER);



        TextField flightNameTextField_addPage = new TextField();
        flightNameTextField_addPage.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        flightNameTextField_addPage.setEditable(false);
        addEditFlightGridPane.add(flightNameTextField_addPage,1,1);

        Label departureCountryLabel_addPage = new Label("Departure Country");
        departureCountryLabel_addPage.setStyle("""
                -fx-background-color:#000000;
                                        -fx-background-radius: 30;
                                        -fx-font-weight: bold;
                                        -fx-text-fill: white;
                                        -fx-pref-width: 300;
                                        -fx-pref-height: 35;
                                        -fx-font-size: 18;
                                        -fx-text-alignment: Center;""");
        departureCountryLabel_addPage.setAlignment(Pos.CENTER);

        addEditFlightGridPane.add(departureCountryLabel_addPage,0,2);


        TextField departureCountryTextField_addPage = new TextField();
        departureCountryTextField_addPage.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        departureCountryTextField_addPage.setEditable(false);
        addEditFlightGridPane.add(departureCountryTextField_addPage,1,2);

        Label arrivalCountryLabel_addPage = new Label("Arrival Country");
        arrivalCountryLabel_addPage.setStyle("""
                -fx-background-color:#000000;
                                        -fx-background-radius: 30;
                                        -fx-font-weight: bold;
                                        -fx-text-fill: white;
                                        -fx-pref-width: 300;
                                        -fx-pref-height: 35;
                                        -fx-font-size: 18;
                                        -fx-text-alignment: Center;""");
        arrivalCountryLabel_addPage.setAlignment(Pos.CENTER);
        addEditFlightGridPane.add(arrivalCountryLabel_addPage,0,3);

        TextField arrivalCountryTextField_addPage = new TextField();
        arrivalCountryTextField_addPage.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        arrivalCountryTextField_addPage.setEditable(false);
        addEditFlightGridPane.add(arrivalCountryTextField_addPage,1,3);

        Label capacityLabel_addPage = new Label("Capacity");
        capacityLabel_addPage.setStyle("""
                -fx-background-color:#000000;
                                        -fx-background-radius: 30;
                                        -fx-font-weight: bold;
                                        -fx-text-fill: white;
                                        -fx-pref-width: 300;
                                        -fx-pref-height: 35;
                                        -fx-font-size: 18;
                                        -fx-text-alignment: Center;""");
        capacityLabel_addPage.setAlignment(Pos.CENTER);
        addEditFlightGridPane.add(capacityLabel_addPage,0,4);


        TextField capacityTextField_addPage = new TextField();
        capacityTextField_addPage.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        capacityTextField_addPage.setEditable(false);
        addEditFlightGridPane.add(capacityTextField_addPage,1,4);

        addEditFlightGridPane.setAlignment(Pos.CENTER);


        VBox addEditFlightVBox = new VBox();
        addEditFlightVBox.setSpacing(30);
        addEditFlightVBox.setAlignment(Pos.CENTER);
        addEditFlightVBox.getChildren().addAll(flightNumberHBox,addEditFlightGridPane);

        Button addEditFlightButton = new Button("Add Flight");
        addEditFlightButton.setVisible(false);
        addEditFlightButton.setDisable(true);
        addEditFlightButton.setMinWidth(400);
        addEditFlightButton.setPrefWidth(400);
        addEditFlightButton.setMaxWidth(400);
        addEditFlightButton.setMaxHeight(50);
        addEditFlightButton.setPrefHeight(50);
        addEditFlightButton.setMinHeight(50);


        Button editFlightButton = new Button("Edit Flight");
        editFlightButton.setVisible(false);
        editFlightButton.setDisable(true);
        editFlightButton.setMinWidth(400);
        editFlightButton.setPrefWidth(400);
        editFlightButton.setMaxWidth(400);
        editFlightButton.setMaxHeight(50);
        editFlightButton.setPrefHeight(50);
        editFlightButton.setMinHeight(50);

        StackPane addEditFlightStackPane = new StackPane();
        addEditFlightStackPane.getChildren().addAll(addEditFlightButton,editFlightButton);
        addEditFlightStackPane.setAlignment(Pos.CENTER);
        addEditFlightStackPane.setMaxWidth(400);
        addEditFlightStackPane.setPrefWidth(400);
        addEditFlightStackPane.setMinWidth(400);
        addEditFlightStackPane.setMaxHeight(50);
        addEditFlightStackPane.setPrefHeight(50);
        addEditFlightStackPane.setMinHeight(50);
        addEditFlightVBox.getChildren().addAll(addEditFlightStackPane);


        addEditFlightPane.setCenter(addEditFlightVBox);


    //==================================================================================================================
    //--------------------------------------------- reservation page ---------------------------------------------------
    //==================================================================================================================

        BorderPane reservationPane = new BorderPane();
        reservationPane.setPadding(new Insets(10,10,10,10));

        Label reservationLabel = new Label("Reservation Page");
        reservationLabel.setPrefWidth(500);
        reservationLabel.setMinWidth(500);
        reservationLabel.setMaxWidth(500);
        reservationLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        reservationLabel.setAlignment(Pos.CENTER);

        reservationPane.setTop(reservationLabel);
        BorderPane.setAlignment(reservationLabel, Pos.CENTER);

        VBox reservationVBox = new VBox();
        reservationVBox.setSpacing(40);

        Label flightNumberLabel_reservation = new Label("Flight Number");
        flightNumberLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        flightNumberLabel_reservation.setAlignment(Pos.CENTER);

        TextField flightNumberTextField_reservation = new TextField();
        flightNumberTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        flightNumberTextField_reservation.setPromptText("Enter Flight Number");

        HBox flightNumberHBox_reservation = new HBox();
        flightNumberHBox_reservation.setSpacing(10);
        flightNumberHBox_reservation.setAlignment(Pos.CENTER);
        flightNumberHBox_reservation.getChildren().addAll(flightNumberLabel_reservation,flightNumberTextField_reservation);

        GridPane reservationGridPane = new GridPane();
        reservationGridPane.setHgap(10);
        reservationGridPane.setVgap(10);

        Label ticketNumberLabel_reservation = new Label("Ticket Number");
        ticketNumberLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        ticketNumberLabel_reservation.setAlignment(Pos.CENTER);
        reservationGridPane.add(ticketNumberLabel_reservation, 0, 0);

        TextField ticketNumberTextField_reservation = new TextField();
        ticketNumberTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        ticketNumberTextField_reservation.setEditable(false);
        reservationGridPane.add(ticketNumberTextField_reservation, 1, 0);

        Label passengerNameLabel_reservation = new Label("Passenger Name");
        passengerNameLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        passengerNameLabel_reservation.setAlignment(Pos.CENTER);
        reservationGridPane.add(passengerNameLabel_reservation, 0, 1);

        TextField passengerNameTextField_reservation = new TextField();
        passengerNameTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        reservationGridPane.add(passengerNameTextField_reservation, 1, 1);
        passengerNameTextField_reservation.setEditable(false);

        Label passportLabel_reservation = new Label("Passport Number");
        passportLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        passportLabel_reservation.setAlignment(Pos.CENTER);
        reservationGridPane.add(passportLabel_reservation, 0, 2);

        TextField passportTextField_reservation = new TextField();
        passportTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        reservationGridPane.add(passportTextField_reservation, 1, 2);
        passportTextField_reservation.setEditable(false);

        Label nationalityLabel_reservation = new Label("Nationality");
        nationalityLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        nationalityLabel_reservation.setAlignment(Pos.CENTER);
        reservationGridPane.add(nationalityLabel_reservation, 0, 3);

        TextField nationalityTextField_reservation = new TextField();
        nationalityTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        reservationGridPane.add(nationalityTextField_reservation, 1, 3);
        nationalityTextField_reservation.setEditable(false);

        Label birthDateLabel_reservation = new Label("Birth Date");
        birthDateLabel_reservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        birthDateLabel_reservation.setAlignment(Pos.CENTER);
        reservationGridPane.add(birthDateLabel_reservation, 0, 4);

        TextField birthDateTextField_reservation = new TextField();
        birthDateTextField_reservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        reservationGridPane.add(birthDateTextField_reservation, 1, 4);
        birthDateTextField_reservation.setEditable(false);

        reservationGridPane.setAlignment(Pos.CENTER);


        Button reservationButton = new Button("Reserve");
        reservationButton.setMinWidth(400);
        reservationButton.setPrefWidth(400);
        reservationButton.setMaxWidth(400);
        reservationButton.setMaxHeight(50);
        reservationButton.setPrefHeight(50);
        reservationButton.setMinHeight(50);
        reservationButton.setDisable(true);


        reservationVBox.getChildren().addAll(flightNumberHBox_reservation,reservationGridPane, reservationButton);
        reservationVBox.setAlignment(Pos.CENTER);

        reservationPane.setCenter(reservationVBox);




    //==================================================================================================================
    //----------------------------------------------- cancel ticket pane -----------------------------------------------
    //==================================================================================================================

        BorderPane cancelTicketPane = new BorderPane();
        cancelTicketPane.setPadding(new Insets(10,10,10,10));



        Label cancelTicketLabel = new Label("Cancel Reservation");
        cancelTicketLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        cancelTicketLabel.setAlignment(Pos.CENTER);
        cancelTicketLabel.setPrefWidth(500);
        cancelTicketLabel.setMinWidth(500);
        cancelTicketLabel.setMaxWidth(500);

        cancelTicketPane.setTop(cancelTicketLabel);
        BorderPane.setAlignment(cancelTicketLabel, Pos.CENTER);

        VBox cancelTicketVBox = new VBox();

        GridPane cancelTicketGridPane = new GridPane();
        cancelTicketGridPane.setHgap(10);
        cancelTicketGridPane.setVgap(10);

        cancelTicketGridPane.setPadding(new Insets(10,10,10,10));

        Label flightNumberLabel_cancelTicket = new Label("Flight Number");
        flightNumberLabel_cancelTicket.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        flightNumberLabel_cancelTicket.setAlignment(Pos.CENTER);
        cancelTicketGridPane.add(flightNumberLabel_cancelTicket, 0, 0);

        TextField flightNumberTextField_cancelTicket = new TextField();
        flightNumberTextField_cancelTicket.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        cancelTicketGridPane.add(flightNumberTextField_cancelTicket, 1, 0);
        flightNumberTextField_cancelTicket.setPromptText("Enter Flight Number");

        Label passportNumberLabel_cancelTicket = new Label("Passport Number");
        passportNumberLabel_cancelTicket.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        passportNumberLabel_cancelTicket.setAlignment(Pos.CENTER);
        cancelTicketGridPane.add(passportNumberLabel_cancelTicket, 0, 1);

        TextField passportNumberTextField_cancelTicket = new TextField();
        passportNumberTextField_cancelTicket.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        cancelTicketGridPane.add(passportNumberTextField_cancelTicket, 1, 1);
        passportNumberTextField_cancelTicket.setPromptText("Enter Passport Number");

        cancelTicketGridPane.setAlignment(Pos.CENTER);

        Button cancelTicketButton = new Button("Cancel Ticket");
        cancelTicketButton.setMinWidth(400);
        cancelTicketButton.setPrefWidth(400);
        cancelTicketButton.setMaxWidth(400);
        cancelTicketButton.setMaxHeight(50);
        cancelTicketButton.setPrefHeight(50);
        cancelTicketButton.setMinHeight(50);
        cancelTicketButton.styleProperty().bind(Bindings.when(cancelTicketButton.hoverProperty())
                .then("-fx-background-color: #ff0033;-fx-text-fill: #ffffff;")
                .otherwise("-fx-background-color: #000000;-fx-text-fill: #ffffff;-fx-border-color: #8A0000FF;"));

        cancelTicketVBox.getChildren().addAll(cancelTicketGridPane, cancelTicketButton);
        cancelTicketVBox.setAlignment(Pos.CENTER);
        cancelTicketPane.setCenter(cancelTicketVBox);
        cancelTicketVBox.setSpacing(40);


    //==================================================================================================================
    //----------------------------------------------- check reservation  -----------------------------------------------
    //==================================================================================================================

        BorderPane checkReservationPane = new BorderPane();
        checkReservationPane.setPadding(new Insets(10, 10, 10, 10));


        Label checkReservationLabel = new Label("Check Reservation");
        checkReservationLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        checkReservationLabel.setAlignment(Pos.CENTER);
        checkReservationLabel.setPrefWidth(500);
        checkReservationLabel.setMinWidth(500);
        checkReservationLabel.setMaxWidth(500);

        checkReservationPane.setTop(checkReservationLabel);
        BorderPane.setAlignment(checkReservationLabel, Pos.CENTER);


        VBox checkReservationVbox = new VBox();
        checkReservationVbox.setAlignment(Pos.CENTER);
        checkReservationVbox.setSpacing(40);

        GridPane checkReservationGridPane = new GridPane();
        checkReservationGridPane.setHgap(10);
        checkReservationGridPane.setVgap(10);
        checkReservationGridPane.setAlignment(Pos.CENTER);

        checkReservationGridPane.setPadding(new Insets(10,10,10,10));

        Label flightNumberLabel_checkReservation = new Label("Flight Number");
        flightNumberLabel_checkReservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        flightNumberLabel_checkReservation.setAlignment(Pos.CENTER);
        checkReservationGridPane.add(flightNumberLabel_checkReservation, 0, 0);

        TextField flightNumberTextField_checkReservation = new TextField();
        flightNumberTextField_checkReservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        checkReservationGridPane.add(flightNumberTextField_checkReservation, 1, 0);
        flightNumberTextField_checkReservation.setPromptText("Enter Flight Number");

        Label passportNumberLabel_checkReservation = new Label("Passport Number");
        passportNumberLabel_checkReservation.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        passportNumberLabel_checkReservation.setAlignment(Pos.CENTER);
        checkReservationGridPane.add(passportNumberLabel_checkReservation, 0, 1);

        TextField passportNumberTextField_checkReservation = new TextField();
        passportNumberTextField_checkReservation.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        checkReservationGridPane.add(passportNumberTextField_checkReservation, 1, 1);
        passportNumberTextField_checkReservation.setPromptText("Enter Passport Number");

        checkReservationGridPane.setAlignment(Pos.CENTER);


        TextField resultOfCheck = new TextField();
        resultOfCheck.setAlignment(Pos.CENTER);
        resultOfCheck.setEditable(false);
        resultOfCheck.setStyle("-fx-text-fill: red;-fx-background-color: transparent;");
        resultOfCheck.setMaxWidth(400);
        resultOfCheck.setMaxHeight(100);

        Button checkReservationButton = new Button("Check Reservation");
        checkReservationButton.setMinWidth(400);
        checkReservationButton.setPrefWidth(400);
        checkReservationButton.setMaxWidth(400);
        checkReservationButton.setMaxHeight(50);
        checkReservationButton.setPrefHeight(50);
        checkReservationButton.setMinHeight(50);
        checkReservationVbox.getChildren().addAll(checkReservationGridPane, resultOfCheck, checkReservationButton);
        checkReservationPane.setCenter(checkReservationVbox);



    //==================================================================================================================
    //----------------------------------------------- search passenger  ------------------------------------------------
    //==================================================================================================================


        BorderPane searchPassengerPane = new BorderPane();
        searchPassengerPane.setPadding(new Insets(10, 10, 10, 10));

        Label searchPassengerLabel = new Label("Search Passenger");
        searchPassengerLabel.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: #ffffff;-fx-background-color: #000000;-fx-background-radius: 15;-fx-border-radius: 15;");
        searchPassengerLabel.setAlignment(Pos.CENTER);
        searchPassengerPane.setTop(searchPassengerLabel);
        BorderPane.setAlignment(searchPassengerLabel, Pos.CENTER);


        VBox searchPassengerVbox = new VBox();
        searchPassengerVbox.setPadding(new Insets(10, 10, 10, 10));
        searchPassengerVbox.setSpacing(40);

        HBox searchPassengerHBox = new HBox();
        searchPassengerHBox.setPadding(new Insets(10, 10, 10, 10));
        searchPassengerHBox.setSpacing(10);
        searchPassengerHBox.setAlignment(Pos.CENTER);

        Label passPassport_searchPassenger = new Label("Passport Number");
        passPassport_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        passPassport_searchPassenger.setAlignment(Pos.CENTER);

        TextField passportNumberTextField_searchPassenger = new TextField();
        passportNumberTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        passportNumberTextField_searchPassenger.setPromptText("Enter Passport Number");

        searchPassengerHBox.getChildren().addAll(passPassport_searchPassenger, passportNumberTextField_searchPassenger);

        GridPane searchPassengerGridPane = new GridPane();
        searchPassengerGridPane.setPadding(new Insets(10, 10, 10, 10));
        searchPassengerGridPane.setVgap(10);
        searchPassengerGridPane.setHgap(10);

        Label Name_searchPassenger = new Label("Name");
        Name_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        Name_searchPassenger.setAlignment(Pos.CENTER);

        TextField nameTextField_searchPassenger = new TextField();
        nameTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        nameTextField_searchPassenger.setEditable(false);

        Label flightNumber_searchPassenger = new Label("Flight Number");
        flightNumber_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        flightNumber_searchPassenger.setAlignment(Pos.CENTER);

        TextField flightNumberTextField_searchPassenger = new TextField();
        flightNumberTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        flightNumberTextField_searchPassenger.setEditable(false);

        Label ticketNumber_searchPassenger = new Label("Ticket Number");
        ticketNumber_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        ticketNumber_searchPassenger.setAlignment(Pos.CENTER);

        TextField ticketNumberTextField_searchPassenger = new TextField();
        ticketNumberTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        ticketNumberTextField_searchPassenger.setEditable(false);

        Label country_searchPassenger = new Label("Nationality");
        country_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        country_searchPassenger.setAlignment(Pos.CENTER);

        TextField countryTextField_searchPassenger = new TextField();
        countryTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        countryTextField_searchPassenger.setEditable(false);

        Label birthDate_searchPassenger = new Label("Birth Date");
        birthDate_searchPassenger.setStyle(" -fx-background-color:#000000;-fx-background-radius: 30;-fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 300; -fx-pref-height: 35; -fx-font-size: 18; -fx-text-alignment: Center;");
        birthDate_searchPassenger.setAlignment(Pos.CENTER);

        TextField birthDateTextField_searchPassenger = new TextField();
        birthDateTextField_searchPassenger.setStyle("-fx-background-radius: 15px;-fx-font-size: 16px;-fx-background-color: #ffffff;-fx-text-fill: #000000; -fx-pref-height: 29px;-fx-border-color: #000000;-fx-border-width: 1px;-fx-border-radius: 15px;");
        birthDateTextField_searchPassenger.setEditable(false);


        searchPassengerGridPane.add(Name_searchPassenger, 0, 0);
        searchPassengerGridPane.add(nameTextField_searchPassenger, 1, 0);
        searchPassengerGridPane.add(flightNumber_searchPassenger, 0, 1);
        searchPassengerGridPane.add(flightNumberTextField_searchPassenger, 1, 1);
        searchPassengerGridPane.add(ticketNumber_searchPassenger, 0, 2);
        searchPassengerGridPane.add(ticketNumberTextField_searchPassenger, 1, 2);
        searchPassengerGridPane.add(country_searchPassenger, 0, 3);
        searchPassengerGridPane.add(countryTextField_searchPassenger, 1, 3);
        searchPassengerGridPane.add(birthDate_searchPassenger, 0, 4);
        searchPassengerGridPane.add(birthDateTextField_searchPassenger, 1, 4);

        searchPassengerGridPane.setAlignment(Pos.CENTER);

        Button searchPassengerButton = new Button("Search");
        searchPassengerButton.setMinWidth(400);
        searchPassengerButton.setPrefWidth(400);
        searchPassengerButton.setMaxWidth(400);
        searchPassengerButton.setMaxHeight(50);
        searchPassengerButton.setPrefHeight(50);
        searchPassengerButton.setMinHeight(50);

        searchPassengerVbox.getChildren().addAll(searchPassengerHBox,searchPassengerGridPane, searchPassengerButton);
        searchPassengerVbox.setAlignment(Pos.CENTER);
        searchPassengerVbox.setSpacing(20);

        searchPassengerPane.setCenter(searchPassengerVbox);





        //========================================== control buttons =======================================================
        //display flights
        displayFlightInfo.setOnAction(e -> {
            refreshFlightInfo(flightInfo,flightInfoTable);
            mainPagePane.setCenter(displayFlightInfoPane);
        });

        //display passengers
        displayPassengersInfo.setOnAction(e -> mainPagePane.setCenter(displayPassengerInfoPane));
        flightNumberTextField_displayPass.setOnAction(e->{
            tempPassengers_2.clear();
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_displayPass.getText().trim());
                Iterator<Flight> itr = flights.iterator();
                boolean found = false;
                while (itr.hasNext()) {
                    Flight tempFlight = itr.next();
                    if (tempFlight.getFlightNumber() == flightNum) {
                        found = true;
                        Iterator<Passenger> itr2 = tempFlight.getPassengers().iterator();
                        while (itr2.hasNext()) {
                            Passenger tempPassenger = itr2.next();
                            tempPassengers_2.add(tempPassenger);
                        }
                        break;
                    }
                }

                if (!found) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Flight Number Not Found");
                    alert.setContentText("Please Enter a Valid Flight Number");
                    alert.showAndWait();
                }else {
                    passengerInfoTable.setItems(tempPassengers_2);
                }
            }catch (NumberFormatException ex){
                showAlert("Invalid Flight Number", "Please enter a valid flight number",1);
            }
        });
        viewPassengers.setOnAction(e -> {
            Flight selectedFlight = flightInfoTable.getSelectionModel().getSelectedItem();
            if(selectedFlight != null){
                Iterator<Passenger> it = selectedFlight.getPassengers().iterator();
                tempPassengerOfFlight.clear();
                while(it.hasNext()){
                    Passenger p = it.next();
                    tempPassengerOfFlight.add(p);
                }
                allPassengersForAFlightTable.setItems(tempPassengerOfFlight);
                mainPagePane.setCenter(displayAllPassengersForAFlightPane);
            }
        });


        //add edit files
        addEditFlight.setOnAction(e -> mainPagePane.setCenter(addEditFlightPane));
        flightNumberTextField_addPage.setOnAction(e -> {
            editFlightButton.setDisable(true);
            editFlightButton.setVisible(false);
            addEditFlightButton.setDisable(true);
            addEditFlightButton.setVisible(false);

            flightNameTextField_addPage.clear();
            flightNameTextField_addPage.setEditable(false);
            arrivalCountryTextField_addPage.clear();
            arrivalCountryTextField_addPage.setEditable(false);
            departureCountryTextField_addPage.clear();
            departureCountryTextField_addPage.setEditable(false);
            capacityTextField_addPage.clear();
            capacityTextField_addPage.setEditable(false);

            try {
                int flightNum = Integer.parseInt(flightNumberTextField_addPage.getText().trim());
                Iterator<Flight> itr = flights.iterator();
                boolean flightExists = false;
                while (itr.hasNext()) {
                    Flight tempFlight = itr.next();
                    if (tempFlight.getFlightNumber() == flightNum) {
                        flightNameTextField_addPage.setText(tempFlight.getName().trim());
                        arrivalCountryTextField_addPage.setText(tempFlight.getTo().trim());
                        departureCountryTextField_addPage.setText(tempFlight.getFrom().trim());
                        capacityTextField_addPage.setText(String.valueOf(tempFlight.getCapacity()).trim());
                        flightExists = true;
                        break;
                    }
                }


                if(!flightExists) {
                    Alert alert = new Alert(CONFIRMATION);
                    alert.setTitle("Flight Not Found");
                    alert.setHeaderText("Flight does not exist");
                    alert.setContentText("Do you want to add a new flight?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(null) == ButtonType.OK) {
                        flightNameTextField_addPage.setDisable(false);
                        flightNameTextField_addPage.setEditable(true);
                        arrivalCountryTextField_addPage.setDisable(false);
                        arrivalCountryTextField_addPage.setEditable(true);
                        departureCountryTextField_addPage.setDisable(false);
                        departureCountryTextField_addPage.setEditable(true);
                        capacityTextField_addPage.setDisable(false);
                        capacityTextField_addPage.setEditable(true);
                        addEditFlightButton.setDisable(false);
                        addEditFlightButton.setVisible(true);
                        editFlightButton.setDisable(true);
                        editFlightButton.setVisible(false);

                    }else {
                        flightNumberTextField_addPage.clear();
                    }
                }else {
                    editFlightButton.setDisable(false);
                    editFlightButton.setVisible(true);
                    addEditFlightButton.setVisible(false);
                    addEditFlightButton.setDisable(true);
                    flightNameTextField_addPage.setEditable(true);
                    arrivalCountryTextField_addPage.setEditable(true);
                    departureCountryTextField_addPage.setEditable(true);
                    capacityTextField_addPage.setEditable(true);
                }


            }catch (NumberFormatException ex){
                showAlert("Invalid Flight Number", "Please enter a valid flight number",1);
            }

        });
        addEditFlightButton.setOnAction(event -> {
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_addPage.getText().trim());
                String flightName = flightNameTextField_addPage.getText().trim();
                String from = departureCountryTextField_addPage.getText().trim();
                String to = arrivalCountryTextField_addPage.getText().trim();
                if (flightName.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    showAlert("Empty Fields", "Please fill all the fields", 1);
                } else {
                    try {
                        int capacity = Integer.parseInt(capacityTextField_addPage.getText().trim());
                        if (capacity <= 0) {
                            showAlert("Invalid Capacity", "Please enter a valid capacity", 1);
                        }
                        else {
                            Flight flight = new Flight(flightNum, flightName, from, to, capacity);
                            flights.insertSort(flight);
                            showAlert("Flight Added", "Flight added successfully", 0);
                            editFlightButton.setDisable(true);
                            editFlightButton.setVisible(false);
                            addEditFlightButton.setDisable(true);
                            addEditFlightButton.setVisible(false);

                            flightNameTextField_addPage.clear();
                            flightNameTextField_addPage.setEditable(false);
                            arrivalCountryTextField_addPage.clear();
                            arrivalCountryTextField_addPage.setEditable(false);
                            departureCountryTextField_addPage.clear();
                            departureCountryTextField_addPage.setEditable(false);
                            capacityTextField_addPage.clear();
                            capacityTextField_addPage.setEditable(false);
                            flightNumberTextField_addPage.clear();

                        }

                    }
                    catch (NumberFormatException ex){
                        showAlert("Invalid Capacity", "Please enter a valid capacity",1);
                    }
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
            }
        });
        editFlightButton.setOnAction(event -> {
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_addPage.getText().trim());
                String flightName = flightNameTextField_addPage.getText().trim();
                String from = departureCountryTextField_addPage.getText().trim();
                String to = arrivalCountryTextField_addPage.getText().trim();
                if (flightName.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    showAlert("Empty Fields", "Please fill all the fields", 1);
                } else {
                    try {
                        int capacity = Integer.parseInt(capacityTextField_addPage.getText().trim());
                        if (capacity <= 0 ) {
                            showAlert("Invalid Capacity", "Please enter a valid capacity", 1);
                        }
                        else {
                            Iterator<Flight> it = flights.iterator();
                            while (it.hasNext()) {
                                Flight flight = it.next();
                                if (flight.getFlightNumber() == flightNum) {
                                    int numOfPassengers = flight.getPassengers().size();
                                    if (capacity < numOfPassengers) {
                                        showAlert("Invalid Capacity", "Please enter a valid capacity , the Flight has more passengers than the capacity", 1);
                                    }else {
                                        flight.setName(flightName);
                                        flight.setFrom(from);
                                        flight.setTo(to);
                                        flight.setCapacity(capacity);
                                        showAlert("Flight Edited", "Flight edited successfully", 0);
                                        addEditFlightButton.setDisable(true);
                                        addEditFlightButton.setVisible(false);
                                        editFlightButton.setDisable(true);
                                        editFlightButton.setVisible(false);
                                        flightNumberTextField_addPage.clear();
                                        flightNameTextField_addPage.clear();
                                        flightNameTextField_addPage.setEditable(false);
                                        departureCountryTextField_addPage.clear();
                                        departureCountryTextField_addPage.setEditable(false);
                                        arrivalCountryTextField_addPage.clear();
                                        arrivalCountryTextField_addPage.setEditable(false);
                                        capacityTextField_addPage.clear();
                                        capacityTextField_addPage.setEditable(false);
                                    }
                                }
                            }
                        }
                    }   catch (NumberFormatException ex){
                        showAlert("Invalid Capacity", "Please enter a valid capacity",1);
                    }
                }

            }   catch (NumberFormatException ex) {
                showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
            }
        });

        //reservation
        reserveTicket.setOnAction(e-> mainPagePane.setCenter(reservationPane));
        flightNumberTextField_reservation.setOnAction(e-> {
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_reservation.getText());
                Iterator<Flight> it = flights.iterator();
                boolean found = false;
                while (it.hasNext()) {
                    Flight flight = it.next();
                    if (flight.getFlightNumber() == flightNum) {
                        showAlert("Ready to go", "Flight found , you are ready to reserve ", 0);
                        passengerNameTextField_reservation.setEditable(true);
                        passengerNameTextField_reservation.clear();
                        passportTextField_reservation.setEditable(true);
                        passportTextField_reservation.clear();
                        nationalityTextField_reservation.setEditable(true);
                        nationalityTextField_reservation.clear();
                        birthDateTextField_reservation.setEditable(true);
                        birthDateTextField_reservation.clear();

                        ticketNumberTextField_reservation.setText((getMaxTicketsNumber(flight.getFlightNumber())+1)+"");
                        ticketNumberTextField_reservation.setEditable(false);

                        reservationButton.setDisable(false);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    showAlert("Flight not found", "Please enter an existing flight number ", 1);
                    flightNumberTextField_reservation.clear();

                    passengerNameTextField_reservation.clear();
                    passengerNameTextField_reservation.setEditable(false);
                    passportTextField_reservation.clear();
                    passportTextField_reservation.setEditable(false);
                    nationalityTextField_reservation.clear();
                    nationalityTextField_reservation.setEditable(false);
                    birthDateTextField_reservation.clear();
                    birthDateTextField_reservation.setEditable(false);
                    ticketNumberTextField_reservation.clear();
                    ticketNumberTextField_reservation.setEditable(false);
                    reservationButton.setDisable(true);
                }
            }   catch (NumberFormatException ex) {
                showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
            }

        });
        reservationButton.setOnAction(e-> {
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_reservation.getText().trim());
                String passengerName = passengerNameTextField_reservation.getText().trim();
                String passport = passportTextField_reservation.getText().trim();
                String country = nationalityTextField_reservation.getText().trim();
                String birthDate = birthDateTextField_reservation.getText().trim();
                if (passengerName.isEmpty() || passport.isEmpty() || country.isEmpty() || birthDate.isEmpty()) {
                    showAlert("Missing Information", "Please fill all the fields", 1);
                } else {
                    try {
                        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (date.isAfter(LocalDate.now())) {
                            showAlert("Invalid Birth Date", "Please enter a valid birth date", 1);
                        } else {
                            int ticketNum = Integer.parseInt(ticketNumberTextField_reservation.getText().trim());
                            Iterator<Flight> it = flights.iterator();
                            boolean flightFound = false;
                            while (it.hasNext()) {
                                Flight flight = it.next();
                                if (flight.getFlightNumber() == flightNum) {
                                    flightFound = true;
                                    Iterator<Passenger> it2 = flight.getPassengers().iterator();
                                    boolean exists = false;
                                    while (it2.hasNext()) {
                                        Passenger passenger = it2.next();
                                        if (passenger.getPassport().equals(passport)) {
                                            showAlert("Passport already exists", "Passenger with this passport already reserved", 1);
                                            exists = true;
                                            break;
                                        }
                                    }
                                    if (!exists) {
                                        if (flight.getPassengers().size() < flight.getCapacity()) {
                                            Passenger passenger = new Passenger(flightNum,ticketNum,passengerName,passport, country, date );
                                            flight.getPassengers().insertSort(passenger);
                                            showAlert("Reservation Successful", "Your reservation has been successful", 0);
                                            passengerNameTextField_reservation.setEditable(false);
                                            passportTextField_reservation.setEditable(false);
                                            nationalityTextField_reservation.setEditable(false);
                                            birthDateTextField_reservation.setEditable(false);
                                            ticketNumberTextField_reservation.setEditable(false);
                                            reservationButton.setDisable(true);
                                            flightNumberTextField_reservation.clear();
                                            passengerNameTextField_reservation.clear();
                                            passportTextField_reservation.clear();
                                            nationalityTextField_reservation.clear();
                                            birthDateTextField_reservation.clear();
                                            ticketNumberTextField_reservation.clear();
                                        }else {
                                            showAlert("Flight Full", "This flight is full", 1);
                                        }
                                    }else {
                                        showAlert("Reservation Failed", "Your reservation has failed", 1);
                                    }

                                    break;

                                }

                            }
                            if (!flightFound) {
                                showAlert("Flight Not Found", "Flight with this number does not exist", 1);
                            }


                        }

                    }catch (DateTimeParseException ex) {
                        showAlert("Invalid Birth Date", "Please enter a date in form ( dd/MM/yyyy )", 1);
                    }
                }

            } catch (NumberFormatException ex) {
            showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
        }});

        //Cancel
        cancelTicket.setOnAction(e -> mainPagePane.setCenter(cancelTicketPane));
        cancelTicketButton.setOnAction(e -> {
            try {
                int flightNum = Integer.parseInt(flightNumberTextField_cancelTicket.getText().trim());
                String passport = passportNumberTextField_cancelTicket.getText().trim();
                if (passport.isEmpty() || flightNumberTextField_cancelTicket.getText().trim().isEmpty()) {
                    showAlert("Invalid Input", "Please fill all fields", 1);
                }
                else {
                    Iterator<Flight> it = flights.iterator();
                    boolean flightFound = false;
                    while (it.hasNext()) {
                        Flight flight = it.next();
                        if (flight.getFlightNumber() == flightNum) {
                            flightFound = true;
                            Iterator<Passenger> it2 = flight.getPassengers().iterator();
                            boolean found = false;
                            if (flight.getPassengers().size() == 0) {
                                showAlert("No Passengers", "There are no passengers on this flight", 1);
                            }else {
                                while (it2.hasNext()) {
                                    Passenger passenger = it2.next();
                                    if (passenger.getPassport().equals(passport)) {
                                        flight.getPassengers().delete(passenger);
                                        showAlert("Cancellation Successful", "Your ticket has been cancelled", 0);
                                        found = true;
                                        flightNumberTextField_cancelTicket.clear();
                                        passportNumberTextField_cancelTicket.clear();
                                        break;

                                    }
                                }
                            }
                            if (!found) {
                                showAlert("Cancellation Failed", "passenger not in this flight", 1);
                            }
                            break;
                        }
                    }
                    if (!flightFound) {
                        showAlert("Cancellation Failed", "flight not found", 1);
                    }
                }
            }catch (NumberFormatException ex) {
                showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
            }
        });


        //check Reservation
        checkIfTicketIsReserved.setOnAction(e -> mainPagePane.setCenter(checkReservationPane));
        checkReservationButton.setOnAction(e -> {
                    try {
                        if (passportNumberTextField_checkReservation.getText().trim().isEmpty() || flightNumberTextField_checkReservation.getText().trim().isEmpty()) {
                            showAlert("Invalid Input", "Please fill all fields", 1);
                            resultOfCheck.clear();
                        } else {
                            int flightNum = Integer.parseInt(flightNumberTextField_checkReservation.getText().trim());
                            String passport = passportNumberTextField_checkReservation.getText().trim();
                            Iterator<Flight> it = flights.iterator();
                            boolean flightFound = false;
                            while (it.hasNext()) {
                                Flight flight = it.next();
                                if (flight.getFlightNumber() == flightNum) {
                                    flightFound = true;
                                    Iterator<Passenger> it2 = flight.getPassengers().iterator();
                                    boolean found = false;
                                    while (it2.hasNext()) {
                                        Passenger passenger = it2.next();
                                        if (passenger.getPassport().equals(passport)) {
                                            resultOfCheck.setStyle("-fx-text-fill: green;-fx-background-color: transparent;-fx-pref-width: 400;-fx-pref-height: 200;-fx-font-size: 30");
                                            resultOfCheck.setText("Passenger found");
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        resultOfCheck.setStyle("-fx-text-fill: red;-fx-background-color: transparent;-fx-pref-width: 400;-fx-pref-height: 200;-fx-font-size: 30");
                                        resultOfCheck.setText("Passenger not found");
                                    }
                                    if (flight.getPassengers().size() == 0) {
                                        showAlert("No Passengers", "There are no passengers on this flight", 1);
                                    }
                                    break;

                                }

                            }
                            if (!flightFound) {
                                showAlert("Flight not found", "Flight Not Found", 1);
                                resultOfCheck.clear();
                                flightNumberTextField_checkReservation.clear();
                                passportNumberTextField_checkReservation.clear();
                            }
                        }


                    }   catch (NumberFormatException ex) {
                        showAlert("Invalid Flight Number", "Please enter a valid flight number", 1);
                    }

        });

        //search for passenger
        searchForPassenger.setOnAction(e -> mainPagePane.setCenter(searchPassengerPane));
        searchPassengerButton.setOnAction(e -> {

            nameTextField_searchPassenger.clear();
            flightNumberTextField_searchPassenger.clear();
            ticketNumberTextField_searchPassenger.clear();
            countryTextField_searchPassenger.clear();
            birthDateTextField_searchPassenger.clear();

            try {
                if (passportNumberTextField_searchPassenger.getText().trim().isEmpty()) {
                    showAlert("Invalid Input", "Please Enter Passport Number", 1);

                }
                else {
                    Iterator<Flight> it = flights.iterator();
                    boolean passFound = false;
                    while (it.hasNext()) {
                        Flight flight = it.next();
                        Iterator<Passenger> it2 = flight.getPassengers().iterator();
                        while (it2.hasNext()) {
                            Passenger passenger = it2.next();
                            if (passenger.getPassport().equals(passportNumberTextField_searchPassenger.getText())) {
                                showAlert("Passenger Found", "Passenger Found", 0);
                                nameTextField_searchPassenger.setText(passenger.getName());
                                flightNumberTextField_searchPassenger.setText(String.valueOf(flight.getFlightNumber()));
                                ticketNumberTextField_searchPassenger.setText(String.valueOf(passenger.getTicketNum()));
                                countryTextField_searchPassenger.setText(passenger.getNationality());
                                birthDateTextField_searchPassenger.setText(passenger.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                passFound = true;
                                break;
                            }
                        }
                    }
                    if (!passFound) {
                        showAlert("Passenger Not Found", "Passenger Not Found", 1);
                        passportNumberTextField_searchPassenger.clear();
                        nameTextField_searchPassenger.clear();
                        flightNumberTextField_searchPassenger.clear();
                        ticketNumberTextField_searchPassenger.clear();
                        countryTextField_searchPassenger.clear();
                        birthDateTextField_searchPassenger.clear();
                    }
                }
            }
            catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please Enter Passport Number", 1);
            }
        });

        //load from file
        loadFromFiles.setOnAction(e -> {
            Alert alertFlight = new Alert(Alert.AlertType.CONFIRMATION);
            Image aeroplane = new Image("aeroplane.png");
            ImageView airplane = new ImageView(aeroplane);
            airplane.setFitHeight(50);
            airplane.setFitWidth(50);
            alertFlight.setGraphic(airplane);
            alertFlight.setTitle("LOAD FLIGHTS ?");
            alertFlight.setHeaderText("Do you want to load Flights from a file ?");
            Optional<ButtonType> result = alertFlight.showAndWait();
            if (result.orElse(null) == ButtonType.OK){
                FileChooser fileChooserFlight = new FileChooser();
                fileChooserFlight.setTitle("Open Flight File");
                fileChooserFlight.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File flightFile = fileChooserFlight.showOpenDialog(stage);

                loadFlights(flightFile);
                refreshFlightInfo(flightInfo,flightInfoTable);
            }else {
                alertFlight.close();
            }

            Alert alertPassenger = new Alert(Alert.AlertType.CONFIRMATION);
            alertPassenger.setTitle("LOAD PASSENGERS ?");
            Image avatar = new Image("avatar.png");
            ImageView avatarView = new ImageView(avatar);
            avatarView.setFitHeight(50);
            avatarView.setFitWidth(50);
            alertPassenger.setGraphic(avatarView);
            alertPassenger.setHeaderText("Do You Want To load Passengers from a file ?");

            Optional<ButtonType> resultPassenger = alertPassenger.showAndWait();

            if (resultPassenger.orElse(null) == ButtonType.OK){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Passenger File");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File passFile = fileChooser.showOpenDialog(stage);
                loadPassengers(passFile);

            }else {
                alertPassenger.close();
            }

        });

        //logout
        logout.setOnAction(e -> {
            stage.setMaximized(false);
            stage.setScene(loginPageScene);
        });

        //exit
        exit.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EXIT ?");
            ImageView imageView = new ImageView(new Image("sad.png")) ;
            imageView.setFitHeight(35);
            imageView.setFitWidth(35);
            alert.setGraphic(imageView);
            alert.setHeaderText("Do you want to exit ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.OK){
                Platform.exit();
            }
        });





    //==================================================================================================================
        stage.setTitle("PROJECT 1 / PALESTINIAN AIRLINES");
        stage.setResizable(false);
        stage.setScene(loginPageScene);
        stage.show();
    }



    private void refreshFlightInfo(ObservableList<Flight> flightInfo,TableView<Flight> flightInfoTable) {
        flightInfo.clear();
        Iterator<Flight> flightIterator = flights.iterator();
        while (flightIterator.hasNext()){
            Flight flight = flightIterator.next();
            flightInfo.add(flight);
        }
        flightInfoTable.setItems(flightInfo);
    }
    public static void loadFlights(File file) {
        try {

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    try {
                        int  flightNumber = Integer.parseInt(parts[0].trim());
                        String flightCompany = parts[1].trim();
                        String departureCountry = parts[2].trim().toUpperCase();
                        String arrivalCountry = parts[3].trim().toUpperCase();
                        String capacity = parts[4].trim();

                        boolean isValid = true;

                        Iterator<Flight> flightIterator = flights.iterator();
                        while (flightIterator.hasNext()) {
                            Flight flight = flightIterator.next();
                            if (flight.getFlightNumber() == flightNumber) {
                                showAlert("Flight Number already exists", "Flight Number " + flightNumber + " already exists", 1);
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid) {
                            Flight flight = new Flight(flightNumber, flightCompany, departureCountry, arrivalCountry, Integer.parseInt(capacity));
                            flights.insertSort(flight);
                        }
                    }catch (NumberFormatException e){
                        showAlert("Invalid Flight Number", "Flight Number " + parts[0] + " is not a valid number", 1);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            showAlert("Error : File not found", "File flights.txt not found",1);
        }catch (Exception e){
            showAlert("Error : Invalid File", "File flights.txt is invalid",1);
        }
    }
    public static void loadPassengers(File file){
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        int flightNum = Integer.parseInt(parts[0].trim());
                        try {
                            int ticketNum = Integer.parseInt(parts[1].trim());
                            String name = parts[2].trim();
                            String passport = parts[3].trim();
                            String from = parts[4].trim();

                            String dob = parts[5].trim();
                            String[] dobParts = dob.split("/");

                            if (dobParts.length == 3) {
                                try {
                                    int day = Integer.parseInt(dobParts[0].trim());
                                    int month = Integer.parseInt(dobParts[1].trim());
                                    int year = Integer.parseInt(dobParts[2].trim());
                                    LocalDate dOB = LocalDate.of(year, month, day);
                                    Passenger passenger = new Passenger(flightNum,ticketNum, name, passport, from, dOB);

                                    boolean isValid = true;
                                    Iterator<Flight> flightIterator = flights.iterator();
                                    while (flightIterator.hasNext()) {
                                        Flight flight = flightIterator.next();
                                        if (flight.getFlightNumber() == flightNum) {

                                            int available = flight.getAvailableSeats();
                                            if (available == 0) {
                                                showAlert("No seats available", "Flight " + flightNum + " is full , cant add passenger < "+ passport + " >", 1);
                                                break;
                                            }

                                            Iterator<Passenger> passengerIterator = flight.getPassengers().iterator();
                                            while (passengerIterator.hasNext()) {
                                                Passenger p = passengerIterator.next();
                                                if (p.getPassport().equals(passport)) {
                                                    showAlert(" Passport Number already exists", "Passenger with Passport Number " + passport + " already exists", 1);
                                                    isValid = false;
                                                }
                                            }

                                            if (isValid) {
                                                flight.getPassengers().insertSort(passenger);
                                            }
                                        }

                                    }

                                }catch (NumberFormatException e){
                                    showAlert("Invalid Date", "Date " + dob + " is not a valid date", 1);
                                }
                            }

                        }catch (NumberFormatException e){
                            showAlert("Invalid Ticket Number", "Ticket Number " + parts[1] + " is not a valid number", 1);
                        }

                    }catch (NumberFormatException e){
                        showAlert("Invalid Flight Number", "Flight Number " + parts[0] + " is not a valid number", 1);
                    }
                }
            }
        } catch (Exception e) {
            showAlert("Error : File not found", "File passengers.txt not found", 1);
        }
    }
    private static int getMaxTicketsNumber(int flightNumber){
        int max = 0;
        Iterator<Flight> flightIterator = flights.iterator();
        while (flightIterator.hasNext()) {
            Flight flight = flightIterator.next();
            if (flight.getFlightNumber() == flightNumber) {
                Iterator<Passenger> passengerIterator = flight.getPassengers().iterator();
                while (passengerIterator.hasNext()) {
                    Passenger p = passengerIterator.next();
                    if (p.getTicketNum() > max) {
                        max = p.getTicketNum();
                    }
                }
            }
        }
        return max;
    }
    public static void showAlert(String titleBar , String infoMessage, int x){    //  x = 0 -> no error \\  x = 1  -> error \\ x = 2 -> warning  \\ x = 3 -> warning with ok button

        Alert alert  = new Alert(ERROR);
        alert.getDialogPane().setStyle("-fx-text-alignment: Center ; -fx-font-size: 11; -fx-font-family: 'Arial Black' ");
        alert.setTitle(titleBar);
        alert.setContentText(infoMessage);

        if (x == 0){
            alert.setAlertType(CONFIRMATION);
            Image smileFace = new Image("smile.png");
            ImageView smileFaceView = new ImageView(smileFace);
            smileFaceView.setFitWidth(30);
            smileFaceView.setFitHeight(30);
            alert.setGraphic(smileFaceView);
        }else if(x == 1) {
            Image sadFace = new Image("sad.png");
            ImageView sadFaceImage = new ImageView(sadFace);
            sadFaceImage.setFitWidth(30);
            sadFaceImage.setFitHeight(30);
            alert.setGraphic(sadFaceImage);

        }else if(x == 2){
            alert.setAlertType(WARNING);
            Image sadFace = new Image("sad.png");
            ImageView sadFaceImage = new ImageView(sadFace);
            sadFaceImage.setFitWidth(30);
            sadFaceImage.setFitHeight(30);
            alert.setGraphic(sadFaceImage);
        } else if (x == 3){
            alert.setAlertType(WARNING);
        }
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch();
    }
}
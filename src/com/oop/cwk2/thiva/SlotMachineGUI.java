package com.oop.cwk2.thiva;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SlotMachineGUI extends Application {

    /*Elements of the window
    * or window Attributes
    */
    //Buttons for all activities
    private Button spinButton;
    private Button addCoin;
    private Button betOne;
    private Button betMax;
    private Button reset;
    private Button stats;
    private Button printStats; // For stats Window
    private Button payoutTable; // For stats Window

    //Slot Reels
    private ImageView reelOne;
    private ImageView reelTwo;
    private ImageView reelThree;

    //Labels to hold the imageViews
    private Label reelOneLabel = new Label();
    private Label reelTwoLabel = new Label();
    private Label reelThreeLabel = new Label();

    //Alert windows
    private Alert alert1;
    private Alert alert2;
    private Alert alert3;
    private Alert alert4;
    private Alert alert5;
    private Alert alert6;

    //Labels for views
    private Label betLabel = new Label("Bet \nCoins");
    private Label betArea = new Label("0");
    private Label creditLabel = new Label("Credits");
    private Label creditArea = new Label("10");
    private Label messageArea = new Label("(<<<Welcome>>>)");

    private Label winCountLbl = new Label("Wins");
    private Label winCountArea = new Label("0"); // For stats Window
    private Label lostCountLbl = new Label("Loses");
    private Label lostCountArea = new Label("0"); // For stats Window
    private Label netCreditLbl = new Label("Net \nCredits");
    private Label netCreditArea = new Label("0"); // For Stats Window
    private Label payoutPercentage = new Label("0");

    //Layout 1
    private GridPane layout = new GridPane();
    //private BorderPane layout = new BorderPane();
    private VBox btnSection1 = new VBox();
    private VBox btnSection2 = new VBox();
    private HBox reelHolder = new HBox();
    private HBox labelHolder1 = new HBox();
    private HBox labelHolder2 = new HBox();


    //Reel Objects
    private static Reel reel = new Reel();

    //An object for operation calling
    private static MainOperations operation = new MainOperations();

    /*
    * Thread objects for each
    * reel in the slot machine
    */
    private ReelThread thread1;
    private ReelThread thread2;
    private ReelThread thread3;

    /*boolean to check whether
      bet max is pressed more than once
     */
    private boolean betMaxPressed = false;

    //Payout variable
    private double payout;




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Slot Machine");

        //Alert Windows
        alert1 = new Alert(Alert.AlertType.WARNING);
        alert1.setTitle("Warning");
        alert1.setHeaderText("Warning!");
        alert1.setContentText("You can't Spin without betting any Credits!");

        alert2 = new Alert(Alert.AlertType.WARNING);
        alert2.setTitle("Warning");
        alert2.setHeaderText("Warning!");
        alert2.setContentText("You Have No credits for betting!");

        alert3 = new Alert(Alert.AlertType.INFORMATION);
        alert3.setTitle("Game Lost");
        alert3.setHeaderText("Lost!");
        alert3.setContentText("YOU HAVE LOST THE GAME!!");

        alert4 = new Alert(Alert.AlertType.WARNING);
        alert4.setTitle("GAME OVER!");
        alert4.setHeaderText("GAME OVER");
        alert4.setContentText("You are Out Of CREDITS!");

        alert5 = new Alert(Alert.AlertType.CONFIRMATION);
        alert5.setTitle("Successful");
        alert5.setContentText("Your File Successfully saved!");

        alert6 = new Alert(Alert.AlertType.WARNING);
        alert6.setTitle("Warning");
        alert6.setContentText("You can bet max only once");


        /*
        * Button Action
        * event Handlers
        */
        //spinButton
        spinButton = new Button("Spin");
        spinButton.setOnAction(e -> {
            messageArea.setText("...");
            if (operation.getCredits() != 0 || operation.getBetCredits() != 0) {

                if (operation.getBetCredits() != 0) {
                    thread1 = new ReelThread(reel.spin(), reelOne);
                    thread2 = new ReelThread(reel.spin(), reelTwo);
                    thread3 = new ReelThread(reel.spin(), reelThree);

                    thread1.start();
                    thread2.start();
                    thread3.start();
                } else {
                    alert1.showAndWait();
                }
            }else{
                messageArea.setText("!!! GAME OVER !!!");
                alert4.showAndWait();
            }
            if(operation.getBetCredits() != 0) {
            /*
            * Disabling all the buttons
            * while spinning
            */
                spinButton.setDisable(true);
                addCoin.setDisable(true);
                reset.setDisable(true);
                betOne.setDisable(true);
                betMax.setDisable(true);
                stats.setDisable(true);
            }

        });

        //reset button
        reset = new Button("Reset");
        reset.setOnAction(e -> {
            if (operation.getCredits() != 0) {
                operation.reset();
                creditArea.setText(Integer.toString(operation.getCredits()));
                betArea.setText(Integer.toString(operation.getBetCredits()));
            }
        });

        //statistics button
        stats = new Button("Statistics");
        stats.setOnAction(e -> {
            winCountArea.setText(Integer.toString(operation.getMatchWins()));
            lostCountArea.setText(Integer.toString(operation.getMatchLost()));
            netCreditArea.setText(Integer.toString(operation.getNettedCredits()));

            /*
            * Creating a New Window
            * for statistics
            */
            Stage stage2 = new Stage();

            //Layout 2 for Statistics Window
            BorderPane layout2 = new BorderPane();
            VBox statsLabels = new VBox(); // For stats Window
            HBox bottomBox = new HBox(); // For stats window

            //Print statistics button
            printStats = new Button("Save Statistics");
            printStats.setOnAction(event -> {
                saveStatistics();
            });
            payoutTable = new Button("Show Payout");
            payoutTable.setOnAction(event -> {
                /*
                * Creating a New Window
                * for statistics
                */
                Stage stage3 = new Stage();

                //Layout 2 for Statistics Window
                BorderPane layout3 = new BorderPane();
                HBox bottomBox2 = new HBox(); // For stats window
                ImageView payoutTable = new ImageView("payout.png");
                payoutPercentage.setText(Double.toString(payout) + "%");

                bottomBox2.getChildren().add(payoutPercentage);

                //Window Scene 2 layout
                layout3.setCenter(payoutTable);
                layout3.setBottom(bottomBox2);

                Scene scene3 = new Scene(layout3, 1100,900);
                stage3.setScene(scene3);
                scene3.getStylesheets().add("/StatsUI.css");
                stage3.show();
            });

            statsLabels.getChildren().addAll(winCountLbl, winCountArea, lostCountLbl,lostCountArea, netCreditLbl,netCreditArea);
            bottomBox.getChildren().addAll(printStats,payoutTable);

            /*
            *Pie Chart for Statistics
            */
            ObservableList<PieChart.Data> statChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Wins", operation.getMatchWins()),//Passing the total match win count
                    new PieChart.Data("Lost", operation.getMatchLost()));//Passing the total match lost count
            PieChart statChart = new PieChart(statChartData);
            statChart.setClockwise(true);
            statChart.setStartAngle(180);
            statChart.setLabelLineLength(80);
            statChart.setLabelsVisible(true);
            statChart.setAnimated(true);

            statsLabels.getStyleClass().add(".statLabels");
            statsLabels.setAlignment(Pos.CENTER);
            bottomBox.getStyleClass().add(".bottomLabels");
            bottomBox.setAlignment(Pos.CENTER);

            //Window Scene 2 layout
            layout2.setCenter(statChart);
            layout2.setLeft(statsLabels);
            layout2.setBottom(bottomBox);

            Scene scene2 = new Scene(layout2, 1100,600);
            stage2.setScene(scene2);
            scene2.getStylesheets().add("/StatsUI.css");
            stage2.show();

        });

        //addCoin button
        addCoin = new Button("Add Coin");
        addCoin.setOnAction(e -> {
            operation.addCoin();
            creditArea.setText(Integer.toString(operation.getCredits()));
            betArea.setText(Integer.toString(operation.getBetCredits()));

        });
        //betOne Button
        betOne = new Button("Bet One");
        betOne.setOnAction(e -> {
            if (operation.getCredits() != 0) {
                operation.betCoin();
                creditArea.setText(Integer.toString(operation.getCredits()));
                betArea.setText(Integer.toString(operation.getBetCredits()));
            }else{
                //Prompt an Alert window
                alert4.showAndWait();
            }
        });

        //betMax Button
        /*
        * It spends the coin maximum number of 3
        */
        betMax = new Button("Bet Max");
        betMax.setOnAction(e -> {

            if (betMaxPressed == false) {
                if (operation.getCredits() != 0) {
                    operation.betMax();
                    betMaxPressed = true;
                    creditArea.setText(Integer.toString(operation.getCredits()));
                    betArea.setText(Integer.toString(operation.getBetCredits()));
                } else {
                    //Prompt an Alert window
                    alert2.showAndWait();
                }
            }else{
                alert6.showAndWait();
            }

        });
        //stop the reel by clicking the HBox
        reelHolder.setOnMouseClicked(e -> {
            /*
             * stop the reel by
             * clicking the HBox which
             * contains the reel
             */
            try {
                stopReel(thread1,thread2,thread3);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        });

        /*Slot Reels'
        * Image Views and their
        * properties
        */
        reelOne = new ImageView("/Images/bell.png");
        reelTwo = new ImageView("/Images/cherry.png");
        reelThree = new ImageView("/Images/plum.png");

        reelOne.setFitWidth(100);
        reelOne.setPreserveRatio(true);
        reelOne.setCache(true);
        reelOneLabel.setGraphic(reelOne);
        reelOneLabel.getStyleClass().add("imageViews");


        reelTwo.setFitWidth(100);
        reelTwo.setPreserveRatio(true);
        reelTwo.setCache(true);
        reelTwoLabel.setGraphic(reelTwo);
        reelTwoLabel.getStyleClass().add("imageViews");

        reelThree.setFitWidth(100);
        reelThree.setPreserveRatio(true);
        reelThree.setCache(true);
        reelThreeLabel.setGraphic(reelThree);
        reelThreeLabel.getStyleClass().add("imageViews");

        /*
        * Label Customizations
        */

        labelHolder1.setAlignment(Pos.BOTTOM_CENTER);
        labelHolder1.getChildren().addAll(betLabel, betArea, creditArea, creditLabel);

        labelHolder2.setAlignment(Pos.CENTER);
        labelHolder2.getChildren().add(messageArea);

        //VBox for Buttons
        btnSection1.getChildren().addAll(spinButton, reset, stats);
        btnSection1.getStyleClass().add("vbox");

        btnSection2.getChildren().addAll(addCoin, betOne, betMax);
        btnSection2.getStyleClass().add("vbox");

        //HBox forImageViews
        reelHolder.getStyleClass().add("hbox");
        reelHolder.getChildren().addAll(reelOneLabel, reelTwoLabel, reelThreeLabel);


        //Row Constraints to make the UI responsive
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(60);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);

        //Column Constraints to make the UI responsive
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(120);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(50);

        layout.getRowConstraints().addAll(row1,row2,row3);
        layout.getColumnConstraints().addAll(col1,col2,col3);

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.add(labelHolder1, 1, 0);
        layout.add(btnSection1, 0, 1);
        layout.add(reelHolder, 1, 1);
        layout.add(btnSection2, 2, 1);
        layout.add(labelHolder2, 1, 2);

        Scene scene = new Scene(layout, 1000, 600);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("/StyleUi.css");

        primaryStage.show();

    }

    /*
    * Method to stop
    * The reel spinning
    */
    public void stopReel(Thread thread1, Thread thread2, Thread thread3) throws InterruptedException {
        //thread1.interrupt();
        //thread2.interrupt();
        //thread3.interrupt();

        this.thread1.terminate();
        thread1.suspend();
        this.thread2.terminate();
        thread2.suspend();
        this.thread3.terminate();
        thread3.suspend();


        /* Calling winOrLose method
         * to determine if the game
         * is won or lost
         * */
        winOrLose();

        /*
         * Enabling all the buttons
         * after spinning
         */
        spinButton.setDisable(false);
        addCoin.setDisable(false);
        reset.setDisable(false);
        betOne.setDisable(false);
        betMax.setDisable(false);
        stats.setDisable(false);
    }

    /*
    * To calculate the number of
    * win or lost & betting credits that
    * won
    */
    public void winOrLose(){
        /*
        * Boolean values to
        * compare the reel images
        * when reel stops
        */
        boolean reel1_eq_reel2 = (thread1.getCurrentSymbolObj().equals(thread2.getCurrentSymbolObj()));
        boolean reel2_eq_reel3 = (thread2.getCurrentSymbolObj().equals(thread3.getCurrentSymbolObj()));
        boolean reel1_eq_reel3 = (thread1.getCurrentSymbolObj().equals(thread3.getCurrentSymbolObj()));

        //Getting the current credit and betting values
        int symbolValue1 = thread1.getCurrentSymbolObj().getValue();
        int symbolValue2 = thread2.getCurrentSymbolObj().getValue();
        int symbolValue3 = thread3.getCurrentSymbolObj().getValue();
        int currentCredit = operation.getCredits();
        int currentBet = operation.getBetCredits();

        if (reel1_eq_reel2 || reel2_eq_reel3 || reel1_eq_reel3){
            //if 2 or 3 symbols are matching Game is won!

            if (reel1_eq_reel2 && reel2_eq_reel3 && reel1_eq_reel3){
                currentCredit += (symbolValue1 * currentBet);
                operation.setCredits(currentCredit);
                //Calculating payout
                payout += (((double)1/(double)216)*symbolValue1);
            }else if (reel2_eq_reel3){
                currentCredit += (symbolValue2 * currentBet);
                operation.setCredits(currentCredit);
                //set the betting credits to 0 after the game is won or lose
                operation.setBetCredits(0);
                //Calculating payout
                payout += (((double)1/(double)36)*symbolValue1);
            }else if (reel1_eq_reel3){
                currentCredit += (symbolValue3 * currentBet);
                operation.setCredits(currentCredit);
                //set the betting credits to 0 after the game is won or lose
                operation.setBetCredits(0);
                //Calculating payout
                payout += (((double)1/(double)36)*symbolValue1);
            }else if (reel1_eq_reel2){
                currentCredit += (symbolValue1 * currentBet);
                operation.setCredits(currentCredit);
                //set the betting credits to 0 after the game is won or lose
                operation.setBetCredits(0);
                //Calculating payout
                payout += (((double)1/(double)36)*symbolValue1);
            }
            //Increase the Win matches count by 1
            int matchWin = operation.getMatchWins();
            matchWin++;
            operation.setMatchWins(matchWin);

            System.out.println(symbolValue1);
            System.out.println(symbolValue2);
            System.out.println(symbolValue3);

            messageArea.setText("You Won!!");
            creditArea.setText(Integer.toString(operation.getCredits()));
            betArea.setText(Integer.toString(operation.getBetCredits()));
        }else{
            //Increase the Lost matches count by 1
            int matchLost = operation.getMatchLost();
            matchLost++;
            operation.setMatchLost(matchLost);

            System.out.println(symbolValue1);
            System.out.println(symbolValue2);
            System.out.println(symbolValue3);

            //setting betting credits to 0 if the user loses
            operation.setBetCredits(0);
            //updating the credits view in the UI
            creditArea.setText(Integer.toString(operation.getCredits()));
            betArea.setText(Integer.toString(operation.getBetCredits()));
            messageArea.setText("!!<GAME LOST>!!");

            alert3.showAndWait();
        }

    }

    public void saveStatistics(){
        /*
         *Getting the current Date and Time
         *to save the file with Time.
         */
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy 'at' HH.mm.ss");
        Date dateTime = new Date();
        String currentTime = dateTimeFormat.format(dateTime);

        double payout_amount = payout * 100;

        File stats = new File(currentTime + ".txt");

        try (PrintWriter pw = new PrintWriter(stats)) {
            pw.println("Statistics Data" + "(" + currentTime + ")");
            pw.println("\n \n");//Leaving 2 lines in the file
            pw.println("No of Wins:       " + operation.getMatchWins());
            pw.println("No of Lost Games: " + operation.getMatchLost());
            pw.println("Netted Credits:   " + operation.getNettedCredits());
            pw.println("Payout Percentage: " + Math.round(payout_amount) + "%");

            //Prompting a successful message
            alert5.showAndWait();


        } catch (Exception e) {
            alert5.setAlertType(Alert.AlertType.WARNING);
            alert5.setHeaderText("File Not Saved");
            alert5.setContentText("File Not saved!!!");
            alert5.showAndWait();
        }
    }


    /*
    * Inner Thread Class
    * for reels
    */
    class ReelThread extends Thread {
        private ArrayList<Symbol> reelSlot;
        private ImageView symbolView;

        /*This object is to check
        * the current symbol
        * when the reel stops
        * */
        private Symbol currentSymbolObj;
        private volatile boolean running = true;

        ReelThread(ArrayList<Symbol> reelSlot, ImageView symbolView) {
            this.reelSlot = reelSlot;
            this.symbolView = symbolView;
        }

        public void terminate() {
            running = false;
        }

        public Symbol getCurrentSymbolObj() {
            return this.currentSymbolObj;
        }

        @Override
        public void run() {

            while (running) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                            //To generate random numbers to scramble the Reel symbol array
                            Random ran = new Random();
                            int randomNum = ran.nextInt(6);
                            currentSymbolObj = reelSlot.get(randomNum);
                            symbolView.setImage(reelSlot.get(randomNum).getImage());

                    }
                });

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    System.out.println("Process Interrupted");
                }

            }

        }

    }
}

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Main extends Application{

	private static final String APP_TITLE = "Burke Truck Build Number Search";
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 200;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle(APP_TITLE);
		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(20,0,20,20));
		//Build Number code
		Label buildNumPrompt = new Label("Enter the build number:");
		TextField buildNumField = new TextField();
		
		//Code to choose Parent Directory
		DirectoryChooser dirChooser = new DirectoryChooser();
		TextArea textArea = new TextArea();
		textArea.setMaxHeight(25);
		Button dirButton = new Button("Click to select Parent Folder");
		
		dirButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				File selectedDir = dirChooser.showDialog(primaryStage);
				if (selectedDir != null) {
                    textArea.setText(selectedDir.getAbsolutePath());
                } else {
                    textArea.setText(null);
                }
			}
			
		});
		
		//Button to search selected parent directory for desired build number
		Button searchButton = new Button("Click to Search");
		TextArea resultArea = new TextArea();
		resultArea.setMaxHeight(25);
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@SuppressWarnings("unused")
			@Override
			public void handle(ActionEvent arg0) {
				File parentDir = new File(textArea.getText());
				String buildNumber = buildNumField.getText();
				String filePath = fileSearch(parentDir, buildNumber);
				System.out.println("fileSearch() ran with parameters " + parentDir.getAbsolutePath() + buildNumber);
				System.out.println("filePath is " + filePath);
				if(!filePath.equals("")) {
					System.out.println("filePath is not empty, is " + filePath);
					
					resultArea.setText(filePath);
				}
			}
			
		});
		
		
		final FlowPane choicePane = new FlowPane();
		choicePane.getChildren().addAll(buildNumPrompt, buildNumField, textArea, dirButton, searchButton, resultArea);
		
		mainPane.setTop(choicePane);
		Scene appScene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
		;
		
		
		//Add the Scene to the Stage
		primaryStage.setScene(appScene);
		primaryStage.show();
	}
	/**
	 * Method that takes in the parent directory and returns the absolute path of the matching build number file
	 * @return 
	 * @return 
	 */
	private static String fileSearch(File parentDirectory, String buildNumber) {
		ArrayList<File> fileList = new ArrayList<File>();
		String filePath = "";
		Scanner scnr;
		boolean found = false;
		try {
			Files.walk(Paths.get(parentDirectory.getAbsolutePath()))
			.filter(Files::isRegularFile)
			.forEach(p -> fileList.add(p.toFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(File f: fileList) {
			try {
				scnr = new Scanner(f);
				while(scnr.hasNextLine()) {
					if(scnr.nextLine().equals("Build Number: " + buildNumber)) {
						filePath = f.getAbsolutePath();
						found = true;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(found == false) {
			filePath = "NO SUCH BUILD NUMBER FOUND";
		}
		
		return filePath;
		
	}
	
	public static void main(String[] args){
		launch(args);
	}
}


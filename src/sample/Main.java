package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

public class Main extends Application {
    static String fontSize="14";
    static String fontColor="yellow";
    static String backgroundColor="black";

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            FileInputStream fin = new FileInputStream("myNotePadSetting");
            byte b[]=new byte[fin.available()];
            fin.read(b);
            StringTokenizer stringTokenizer=new StringTokenizer(new String(b));
            fontSize=stringTokenizer.nextToken();
            fontColor=stringTokenizer.nextToken();
            backgroundColor=stringTokenizer.nextToken();
        }
        catch(Exception e){}


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(Controller.Filename);
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Mycss.css").toExternalForm());
        primaryStage.setScene(scene);


        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try
                {
                    FileOutputStream fout=new FileOutputStream("myNotePadSetting");
                    String str=fontSize+" "+fontColor+" "+backgroundColor;
                    byte b[]=str.getBytes();
                    fout.write(b);
                }
                catch (Exception e){}
            }
        });
    }


    public static void main(String[] args) {

        launch(args);
    }
}

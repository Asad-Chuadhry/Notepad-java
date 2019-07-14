package sample;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.StringTokenizer;

public class Controller {

    public static String Filename="My NotePad ";
    private File f;
    boolean isFileOpen=false;
    Stage primaryStage;
    Scene scene;

    @FXML
    TextArea txtArea;
    @FXML
    Button btnClose;
    @FXML
    Button btnSaveAs;
    @FXML
    Button btnSave;
    @FXML
    Button btnAbout;





   public void mNew()
    {
        txtArea.setVisible(true);
        btnClose.setDisable(false);
        btnSaveAs.setDisable(false);
        isFileOpen=true;

        txtArea.setStyle("text-area-background:"+"\""+Main.backgroundColor+"\";"
                +"-fx-font-size: "+Main.fontSize+"px;"+
                "-fx-text-fill: "+"\""+Main.fontColor+"\";");
    }
    public void mOpen()
    {
        try {
            FileChooser chooser = new FileChooser();
            f= chooser.showOpenDialog(null);
            TempFile file = new TempFile();
            if (file.input(f)) {
                txtArea.setVisible(true);
                txtArea.setText(file.getFiletext());
                btnSave.setDisable(false);
                btnClose.setDisable(false);
                btnSaveAs.setDisable(false);
                isFileOpen=true;

                txtArea.setStyle("text-area-background:"+"\""+Main.backgroundColor+"\";"
                        +"-fx-font-size: "+Main.fontSize+"px;"+
                        "-fx-text-fill: "+"\""+Main.fontColor+"\";");
            } else {
                JOptionPane.showMessageDialog(null, "Cant Open File");
            }
        }
        catch(Exception e)
        {

        }


    }
    public void mSave()
    {
        try{
            TempFile file=new TempFile();
            file.setFiletext(txtArea.getText());
            file.output(f);
        }
        catch (Exception e)
        {

        }
    }
    public void mSaveAs()
    {
        try {
            FileChooser chooser = new FileChooser();
            File f =chooser.showSaveDialog(null);
            TempFile file = new TempFile();
            file.setFiletext(txtArea.getText());
            file.output(f);
        }
        catch(Exception e) {
        }

    }
    public void mClose()
    {
        onclose("close");
        btnSaveAs.setDisable(true);
        btnSave.setDisable(true);
        btnClose.setDisable(false);
    }
    public void mExit()
    {
        onclose("exit");
    }

    void onclose(String button) {
        if (isFileOpen) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Save");
            alert.setHeaderText("Save Change Before Exit");
            ButtonType btnTSave = new ButtonType("Yes");
            ButtonType btnTDontSave = new ButtonType("No");
            ButtonType btnTCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnTSave, btnTDontSave, btnTCancel);
            Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == btnTDontSave) {
                    if(button=="close")
                    {
                        txtArea.clear();
                        txtArea.setVisible(false);
                        isFileOpen=false;
                    }
                    else{
                        System.exit(0);
                    }

                }else if(result.get()==btnTSave)
                {
                    mSaveAs();
                    if(button=="close")
                    {
                        txtArea.clear();
                        txtArea.setVisible(false);
                        isFileOpen=false;
                    }
                    else{
                        System.exit(0);
                    }
                }

        }
        else System.exit(0);
    }

    public void changeBackgroundColor()
    {

        Dialog<ColorPicker> dialog=new Dialog<>();
        dialog.setTitle("Change Backgroubd");
        dialog.setHeaderText("Select Color");
        ColorPicker pickColor=new ColorPicker();
        dialog.getDialogPane().setContent(pickColor);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton->
        {if(dialogButton==ButtonType.OK)
        {
            return pickColor;
        } return null;
        });
        try {
            Optional<ColorPicker> result = dialog.showAndWait();
            Main.backgroundColor=result.get().getValue()+"";
            txtArea.setStyle("text-area-background:"+"\""+Main.backgroundColor+"\";"
                    +"-fx-font-size: "+Main.fontSize+"px;"+
                    "-fx-text-fill: "+"\""+Main.fontColor+"\";");

        }
        catch (Exception e){}


    }
    public void changeFontSize()
    {
        ArrayList<String> choices=new ArrayList<String>();
        for(int i=1;i<=72;i++)
        {
            choices.add(i+"");
        }
        System.out.println(txtArea.getStyle());
        ChoiceDialog<String> dialog=new ChoiceDialog<>(Main.fontSize,choices);
        dialog.setTitle("Font Size");
        dialog.setHeaderText("change font size");
        dialog.setContentText("select font size");

        Optional<String> result=dialog.showAndWait();
        if(result.isPresent())
        {
            Main.fontSize=result.get();
            txtArea.setStyle("text-area-background:"+"\""+Main.backgroundColor+"\";"
                    +"-fx-font-size: "+Main.fontSize+"px;"+
                    "-fx-text-fill: "+"\""+Main.fontColor+"\";");
        }
    }
    public void changeFontColor()
    {
        ColorPicker pickColor=new ColorPicker();
        Dialog<ColorPicker> dialog =new Dialog<>();
        dialog.setHeaderText("Select Color");
        dialog.setTitle("Change Font Color");
        dialog.getDialogPane().setContent(pickColor);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton->{
            if(dialogButton==ButtonType.OK)
                return pickColor;
            return null;
        });
        Optional<ColorPicker> result=dialog.showAndWait();
        if(result.isPresent())
        {

            Main.fontColor=result.get().getValue()+"";
            txtArea.setStyle("text-area-background:"+"\""+Main.backgroundColor+"\";"
                    +"-fx-font-size: "+Main.fontSize+"px;"+
                    "-fx-text-fill: "+"\""+Main.fontColor+"\";");
        }


    }
    public void mAbout()
    {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("developer.fxml"));
            primaryStage=new Stage();
            primaryStage.setTitle("Contact Developer");
            scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            btnAbout.setDisable(true);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {

                    primaryStage.close();
                    btnAbout.setDisable(false);
                }
            });

        }catch (IOException e){e.printStackTrace();}


    }

    

}

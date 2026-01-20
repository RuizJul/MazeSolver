/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Julian
 */
public class MenuController {

    @FXML
    public Button creatorButton;
    @FXML
    public Button playButton;

    private void changeView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + fxml));
            Parent root = loader.load();

            Stage stage = (Stage) creatorButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToCreator(ActionEvent event) {
        changeView("MazeEditor.fxml");
    }

    @FXML
    public void goToSelector(ActionEvent event) {
        changeView("Selector.fxml");
    }

}

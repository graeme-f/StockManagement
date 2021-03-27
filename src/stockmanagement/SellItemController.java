/*
 * The MIT License
 *
 * Copyright 2021 gfoster.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package stockmanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gfoster
 */
public class SellItemController implements Initializable {

    @FXML ChoiceBox<Item> cbItemNames;
    @FXML Label lblItemCode;
    @FXML TextField txtItemAmount;
    @FXML Button btnReject;
    @FXML Button btnAccept;
    
    private FXMLMainWindowController parentController = null;
    private Stage stage;
    private Item item;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtItemAmount.setText("1");
        cbItemNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>(){
            public void changed(ObservableValue ov, Item value, Item newValue){
                item = newValue;
                setItemCode(item.getCode());
                if (parentController != null){
                    parentController.updateItemSelection(newValue);
                }
            }
        });
    } // end of initialize()
    
    public void setItemNames(ObservableList<Item> list, Item item, FXMLMainWindowController pc){
        this.item = item;
        cbItemNames.setItems(list);
        cbItemNames.setValue(item);
        parentController = pc;
    }

    public void setItemCode(String code){
        lblItemCode.setText(code);
    }
    public String getItemCode(){
        return lblItemCode.getText();
    }

    public String getQuantity(){return txtItemAmount.getText();}
    public void  setQuantity(int amount){txtItemAmount.setText(Integer.toString(amount));}

    public Item getItem(){return item;}
    
    @FXML private void saveAndClose(){
        stage = (Stage) btnReject.getScene().getWindow();
        stage.close();        
    }
    
    @FXML private void closeWindow(ActionEvent event) {
        txtItemAmount.setText("");
        stage = (Stage) btnReject.getScene().getWindow();
        stage.close();
    } // end of method CloseWindow
}

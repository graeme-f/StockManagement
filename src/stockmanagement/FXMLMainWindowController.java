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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author gfoster
 */
public class FXMLMainWindowController implements Initializable {
    
    @FXML private ListView<Item> items;
    @FXML private ListView<SaleLine> display;
    @FXML private Label lblTotal;
       
    private String itemFileName;
    private ObservableList<Item> allItems;
    private Item selectedItem;
    private Sale sale;
    private ObservableList<SaleLine> allSaleLines;
    private SaleLine selectedSaleLine;
    
    @FXML private void addItemDialog() {
        // Code that will display the dialog
        selectedItem = items.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemSale.fxml"));
        Stage stage = getStage(loader);

        // Get the dialog controller so that a public method can be run to send date to the dialog
        SellItemController sic = loader.<SellItemController>getController();

        sic.setItemNames(allItems, selectedItem, this);
        sic.setItemCode(selectedItem.getCode());
        
        // Show the dialog (and wait for the user to close it)
        stage.showAndWait();
        // use the dialog controller to call a public method to get data from it
        if (!sic.getQuantity().equals("")){
            selectedItem = sic.getItem();
            int quantity = Integer.parseInt(sic.getQuantity());
            SaleLine sl = new SaleLine(selectedItem, quantity);
            allSaleLines.add(sl);
            lblTotal.setText(sale.getTotal());
        }
    } // end of method addItemDialog

    @FXML private void editItemDialog(MouseEvent event) {
        event.consume();

        // Code that will display the dialog
        selectedSaleLine = display.getSelectionModel().getSelectedItem();
        selectedItem = selectedSaleLine.getItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemSale.fxml"));
        Stage stage = getStage(loader);

        // Get the dialog controller so that a public method can be run to send date to the dialog
        SellItemController sic = loader.<SellItemController>getController();

        sic.setItemNames(allItems, selectedSaleLine.getItem(), this);
        sic.setItemCode(selectedSaleLine.getItem().getCode());
        sic.setQuantity(selectedSaleLine.getQuantity());
        
        // Show the dialog (and wait for the user to close it)
        stage.showAndWait();
        // use the dialog controller to call a public method to get data from it
        if (!sic.getQuantity().equals("")){
            selectedItem = sic.getItem();
            int quantity = Integer.parseInt(sic.getQuantity());
            selectedSaleLine.setItem(selectedItem);
            selectedSaleLine.setQuantity(quantity);
            display.refresh();
            lblTotal.setText(sale.getTotal());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create all the items
        allItems = FXCollections.observableArrayList();
        itemFileName = "items.txt";
        readItemData();
        items.setItems(allItems);
        Collections.sort(allItems);
        
        allSaleLines = FXCollections.observableArrayList();
        display.setItems(allSaleLines);
        display.setStyle("-fx-font-family: 'Courier New';");
        
        sale = new Sale(allSaleLines);
        lblTotal.setText(sale.getTotal());
        lblTotal.setStyle("-fx-font-family: 'Courier New';");
    }    
    
    public void readItemData(){
        Scanner sc;
        try {
            File file = new File(itemFileName);
            sc = new Scanner(file);
        } catch (FileNotFoundException e){
            Logger.getLogger (FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, e);
            return;
        }
        sc.useDelimiter(Item.DELIMITER);
        while (sc.hasNext()){
            String name = sc.next();
            String code = "";
            if (sc.hasNext()){
                code = sc.next();
            }
            String details = "";
            if (sc.hasNext()){
                details = sc.next();
            }
            int price = 0;
            if (sc.hasNext()){
                String strPrice = sc.next();
                price = Integer.parseInt(strPrice);
            }
            boolean taxExempt = false;
            if (sc.hasNext()){
                String strTaxExempt = sc.next();
                taxExempt = Boolean.parseBoolean(strTaxExempt);
            }
            Item item = new Item(name, code, details, price, taxExempt);
            allItems.add(item);
        } // loop through the file until all the records have been read
    } // end of method readItemData
   
    private Stage getStage(FXMLLoader loader){
        // Loads the scene from the fxml file
        Scene newScene;
        try {
            newScene = new Scene(loader.load());
        } catch (IOException e) {
            System.out.println("Fail");
            System.out.println(e);
            return null;
        }
        // Create the stage
        Stage inputStage = new Stage();
        // Sets the owner to being this window NOTE primaryStage is set up in StockManagement
        inputStage.initOwner(StockManagement.primaryStage);
        // Add the Scene to the stage
        inputStage.setScene(newScene);
        
        return inputStage;
    }

    public void updateItemSelection(Item newItem){
        items.getSelectionModel().select(newItem);
        selectedItem = newItem;
    }
    
} // end of class

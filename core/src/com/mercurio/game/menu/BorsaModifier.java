package com.mercurio.game.menu;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BorsaModifier {

    private JSONArray inventoryCure;
    private JSONArray inventoryBall;
    private JSONArray inventoryMT;
    private JSONArray inventoryKey;
    private String filename;

    // Costruttore che legge le informazioni dall'inventario JSON
    public BorsaModifier() {
        filename = "./assets/ashJson/borsa.json";
        loadInventoryFromJson(filename);
    }

    // Metodo per caricare le informazioni dall'inventario JSON
    private void loadInventoryFromJson(String filename) {
        try (FileReader fileReader = new FileReader(filename)) {
            // Utilizza JSONTokener per leggere il file JSON
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject inventoryData = new JSONObject(tokener);

            // Ottieni le matrici di inventario dal JSONObject
            inventoryCure = inventoryData.getJSONArray("inventoryCure");
            inventoryBall = inventoryData.getJSONArray("inventoryBall");
            inventoryMT = inventoryData.getJSONArray("inventoryMT");
            inventoryKey = inventoryData.getJSONArray("inventoryKey");

        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
        }
    }

    // Metodo per aggiungere un oggetto di cura all'inventario
    public void addInventoryCure(String itemName) {
        addInventoryItem(inventoryCure, itemName);
    }

    // Metodo per aggiungere una palla all'inventario
    public void addInventoryBall(String itemName) {
        addInventoryItem(inventoryBall, itemName);
    }

    // Metodo per aggiungere una MT all'inventario
    public void addInventoryMT(String itemName) {
        addInventoryItem(inventoryMT, itemName);
    }

    // Metodo per aggiungere una chiave all'inventario
    public void addInventoryKey(String itemName) {
        addInventoryItem(inventoryKey, itemName);
    }

    // Metodo di utilità per aggiungere un oggetto all'inventario specificato
    private void addInventoryItem(JSONArray inventory, String itemName) {
        try {
            
            boolean found = false;
            // Cerca se l'oggetto è già presente nell'inventario
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject item = inventory.getJSONObject(i);
                String existingItemName = item.getString("name");
                if (existingItemName.equals(itemName)) {
                    // Incrementa la quantità se l'oggetto è già presente
                    int quantity = item.getInt("quantity");
                    item.put("quantity", quantity + 1);
                    found = true;
                    break;
                }
            }
            // Se l'oggetto non è stato trovato, aggiungilo con quantità 1
            if (!found) {
                JSONObject newItem = new JSONObject();
                newItem.put("name", itemName);
                newItem.put("quantity", 1);
                inventory.put(newItem);
            }
            writeInventoryToJson();

        } catch (Exception e) {
            System.out.println("Errore addInvetoryItem, " + e);
        }
        
    }



    // Metodo per rimuovere un oggetto di cura all'inventario
    public void removeInventoryCure(String itemName) {
        removeInventoryItem(inventoryCure, itemName);
    }

    // Metodo per rimuovere una palla all'inventario
    public void removeInventoryBall(String itemName) {
        removeInventoryItem(inventoryBall, itemName);
    }

    // Metodo per rimuovere una MT all'inventario
    public void removeInventoryMT(String itemName) {
        removeInventoryItem(inventoryMT, itemName);
    }

    // Metodo per rimuovere una chiave all'inventario
    public void removeInventoryKey(String itemName) {
        removeInventoryItem(inventoryKey, itemName);
    }

    // Metodo di utilità per rimuovere un oggetto all'inventario specificato
    // Metodo per rimuovere un oggetto dall'inventario
    public void removeInventoryItem(JSONArray inventory, String itemName) {

        try {
            // Cerca se l'oggetto è già presente nell'inventario
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject item = inventory.getJSONObject(i);
                String existingItemName = item.getString("name");
                
                if (existingItemName.equals(itemName)) {
                    // Decrementa la quantità
                    int quantity = item.getInt("quantity");
                    item.put("quantity", quantity - 1);
                    
                    // Se la quantità diventa 0 o inferiore, rimuove l'oggetto
                    if (item.getInt("quantity") <= 0) {
                        inventory.remove(i);  // Rimuovi l'oggetto dall'array
                        i--;  // Decresci l'indice per compensare il cambiamento nell'array
                    }
        
                    break;
                }
            }
            
            // Scrivi le modifiche sull'inventario nel file JSON
            writeInventoryToJson();

        } catch (Exception e) {
            System.out.println("Errore RemoveInvetoryItem, " + e);
        }

        
    }
    



    // Metodo per scrivere le modifiche dell'inventario sul file JSON
    private void writeInventoryToJson() {
        JSONObject inventoryData = new JSONObject();
        inventoryData.put("inventoryCure", inventoryCure);
        inventoryData.put("inventoryBall", inventoryBall);
        inventoryData.put("inventoryMT", inventoryMT);
        inventoryData.put("inventoryKey", inventoryKey);

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(inventoryData.toString(4)); // Formattazione con rientri di 4 spazi
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura sul file JSON: " + e.getMessage());
        }
    }

    // Metodo per ottenere la matrice di oggetti di cura
    public JSONArray getInventoryCure() {
        return inventoryCure;
    }

    // Metodo per ottenere la matrice di palle
    public JSONArray getInventoryBall() {
        return inventoryBall;
    }

    // Metodo per ottenere la matrice di MT
    public JSONArray getInventoryMT() {
        return inventoryMT;
    }

    // Metodo per ottenere la matrice di chiavi
    public JSONArray getInventoryKey() {
        return inventoryKey;
    }

    // Metodo di esempio per stampare l'inventario di oggetti di cura
    public void printInventoryCure() {

        try {
            //System.out.println("Inventario di oggetti di cura:");
            for (int i = 0; i < inventoryCure.length(); i++) {
                //JSONObject item = inventoryCure.getJSONObject(i);
                //String itemName = item.getString("name");
                //int quantity = item.getInt("quantity");
                //System.out.println(itemName + ": " + quantity);
            }

        } catch (Exception e) {
            System.out.println("Errore printInvetoryCure, " + e);
        }
        
    }

    /* LEGGIMIIIIIIIIIIIIIIIIIIIII  READMEEEEEEEEEEEEEEEEEE
     nelle altre classi quando vorremo andare ad aggiungere o a rimuovere uno strumento,
     devi creare - private BorsaModifier borsaModifier;
     nel main o dove vuoi - borsaModifier = new BorsaModifier();
     e quando devi aggiungere o togliere - borsaModifier.chiamaQuelloCheTiPare("nome del oggetto bellissimo");
     */
}

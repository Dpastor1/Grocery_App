package com.firstapp.groceryapp;

import android.provider.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceiptReader {
    private final String GlobalFileTracker = "com/firstapp/groceryapp/GlobalReceiptTracker.txt";

    private Scanner globalscanner;


    public ReceiptReader(){
        try {
            this.globalscanner = new Scanner(new File(GlobalFileTracker));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private GroceryReceipt readSaveFile(String fileName){
        //reads the save file and returns a grocery receipt from the items. If the save if empty, returns null
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error: Save File could not be located");
        }
        if (!scanner.hasNext()){
            return null;
        }
        GroceryReceipt receipt = new GroceryReceipt(scanner.next());

        while (scanner.hasNext()){
            receipt.addItem(new GroceryItem(scanner.next(), scanner.next(), scanner.next()));
        }

        return receipt;

    }

    public ArrayList<GroceryReceipt> readAllSaves(){
        ArrayList<GroceryReceipt> receiptList = new ArrayList<GroceryReceipt>();
        ArrayList<String> fileList = getAllFileNames();
        for (String fileName : fileList){
            receiptList.add(readSaveFile(fileName));
        }

        return receiptList;
    }

    private ArrayList<String> getAllFileNames(){
        //gets the name of all save files on device
        ArrayList<String> list = new ArrayList<String>();
        while (globalscanner.hasNext()){
            list.add(globalscanner.next());
        }
    return list;
    }





}

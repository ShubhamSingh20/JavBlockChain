/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Shubham
 */
public class LexicalAnalysis {
    private String[] JBCKeywords = {"CREATE","SHOW","USER","TRANSACTION",
        "SET","POPULATE","BLOCK","MINER","POW","DIFFICULTY","ECONOMY","NAME"};
  
    HashMap<String, ArrayList<Integer>> keyWordsTable;
    HashMap<String, ArrayList<Integer>> otherWordsTable = new HashMap<String,ArrayList<Integer>>();
    
    
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain;

import com.sun.xml.internal.ws.util.StringUtils;
import java.security.*;
import java.util.ArrayList;
import javblockchain.BlockTransactions.*;

/**
 *
 * @author Shubham
 */

interface getOutputValues{
    public double outputTotal();
}
interface getInputValues{
     public double inputTotal();
}
public class Transactions {
    
    
    public String transactionID;
    public PublicKey senderKey;
    public PublicKey receiverKey;
    public double value;
    public byte[] signature;
    
    public ArrayList<TransactionInput> input_transactionsArrayList = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> output_transactionsaArrayList = new ArrayList<TransactionOutput>();
    
    private static int transaction_count = 0;

    public Transactions(PublicKey senderKey,PublicKey recevierKey,
            double value,ArrayList<TransactionInput> inputs) {
        
        this.senderKey = senderKey;
        this.receiverKey = recevierKey;
        this.value = value;
        this.input_transactionsArrayList = inputs;
        
    }
    
    
        getOutputValues gOutputValues = ()->{
            double total = 0;
            for (TransactionInput transactionInput : input_transactionsArrayList) {
                if(transactionInput.UTXOutput == null)continue;
                total += transactionInput.UTXOutput.value;
                
            }
            return total;
        };
        
        getInputValues gInputValues = ()->{
            double total = 0;
            for (TransactionOutput transactionOutput : output_transactionsaArrayList) {
                total += transactionOutput.value;
            }
            return total;
        };
        
    public void GenerateSignature(PrivateKey privateKey){
        String data = BlockStringFunctions.getStringFromKeyString(senderKey)+
                BlockStringFunctions.getStringFromKeyString(receiverKey)
                +Double.toString(value);
        
        signature = BlockStringFunctions.applySignature(privateKey, data);
    }
    public boolean VerifyTransaction(){
        String data = BlockStringFunctions.getStringFromKeyString(senderKey)+
                BlockStringFunctions.getStringFromKeyString(receiverKey)
                + Double.toString(value);
        
        return BlockStringFunctions.verifySignature(senderKey, data, signature);  
    }
    public String GenerateTransactionString(){
        transaction_count++;
        BlockStringFunctions gen_Hash = new BlockStringFunctions();
        
        return gen_Hash.Gen_SHA256_hash(
            BlockStringFunctions.getStringFromKeyString(senderKey)+
            BlockStringFunctions.getStringFromKeyString(receiverKey)+
            Float.toString(transaction_count)
        );
        
    }
    
    public boolean processTransaction(){
        if (VerifyTransaction() == false) {
            System.out.println("[-]Invalid Transaction ... ");
            return false;
        }
        for(TransactionInput transactionInput : input_transactionsArrayList) {
            transactionInput.UTXOutput = BlockChain.UTXOutput.get(transactionInput.transactionStringOutputId);          
        }
        
        if (gInputValues.inputTotal() <BlockChain.minimumTransaction ) {
            System.out.println("[-]Invalid Transaction Amount [--Amount should be greater then 0.1]");
            return false;
        }
        
        double leftOver = gInputValues.inputTotal()- value;
        // ArrayList Contains All the money possessed by the user..
        transactionID = GenerateTransactionString();
        output_transactionsaArrayList.add(new TransactionOutput(this.receiverKey, value, transactionID));
        output_transactionsaArrayList.add(new TransactionOutput(this.senderKey,leftOver, transactionID));
        
        for (TransactionInput transactionInput : input_transactionsArrayList) {
            if(transactionInput.UTXOutput == null)continue;
            BlockChain.UTXOutput.remove(transactionInput.transactionStringOutputId);
            
        }
          
        return true;
        
    }
    
    
}

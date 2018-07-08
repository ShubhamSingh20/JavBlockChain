/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javblockchain.BlockTransactions.TransactionInput;
import javblockchain.BlockTransactions.TransactionOutput;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Shubham
 */
public class Wallet {

    public PublicKey publicKey;
    public PrivateKey privateKey;
    
    public HashMap<String,TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public Wallet() {
        GenerateKeyPair();
    }
    
    public void GenerateKeyPair(){
        try {
            
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");            
            ECGenParameterSpec eCGenParameterSpec = new ECGenParameterSpec("prime192v1");
            
            keyPairGenerator.initialize(eCGenParameterSpec,secureRandom);
            KeyPair keyPair =  keyPairGenerator.genKeyPair();
            
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public double getBalance(){
        double total = 0;
        
        for (Map.Entry<String, TransactionOutput> entry : BlockChain.UTXOutput.entrySet()) {
            TransactionOutput UTransactionOutput = entry.getValue();
            if(UTransactionOutput.isOwner(publicKey)){
                UTXOs.put(UTransactionOutput.id, UTransactionOutput);
                total+= UTransactionOutput.value;
            }
        }
        return total;
    }
    
    public Transactions sendBlock(PublicKey recipent,double sendMoney){
        if(getBalance() < sendMoney){
            System.out.println("[-]Invalid Transaction amount");
            return null;
        }
        
        ArrayList<TransactionInput> transactionInputs = new ArrayList<TransactionInput>();
        double total = 0;
        
        for (Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()) {
            TransactionOutput transactionOutput = entry.getValue();
            total += transactionOutput.value;
            transactionInputs.add(new TransactionInput(transactionOutput.id));
            if(total>sendMoney)break;
        }
        Transactions transactions = new Transactions(publicKey, recipent,sendMoney, transactionInputs);
        transactions.GenerateSignature(privateKey);
        
        for (TransactionInput transactionInput : transactionInputs) {
            UTXOs.remove(transactionInput.transactionStringOutputId);
        }
        
        return transactions;
    }
    
}
   


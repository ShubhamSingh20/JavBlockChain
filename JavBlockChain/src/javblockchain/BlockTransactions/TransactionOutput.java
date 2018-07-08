/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain.BlockTransactions;

import java.security.PublicKey;
import javblockchain.BlockStringFunctions;

/**
 *
 * @author Shubham
 */
public class TransactionOutput {
    public String id;
    public PublicKey receiverKey;
    public double value;
    public String parentTransactionID;

    public TransactionOutput(PublicKey receiverKey,double value, String parentTransactionID) {
        this.receiverKey = receiverKey;
        this.value = value;
        this.parentTransactionID = parentTransactionID;
        this.id = BlockStringFunctions.Gen_SHA256_hash(BlockStringFunctions.getStringFromKeyString(receiverKey)
                +Double.toString(value) + parentTransactionID);
         
    }
    
    public boolean isOwner(PublicKey publicKey){
        return (publicKey == receiverKey);
    }
    
    
    
    
}

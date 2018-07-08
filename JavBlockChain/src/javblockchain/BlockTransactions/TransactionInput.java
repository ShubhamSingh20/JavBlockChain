/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain.BlockTransactions;

/**
 *
 * @author Shubham
 */
public class TransactionInput {
    public String transactionStringOutputId;
    public TransactionOutput UTXOutput; // Unspent Transaction Output
    
    public TransactionInput(String transactionStringOutputId){
        this.transactionStringOutputId = transactionStringOutputId;
    }
    
    
    
}

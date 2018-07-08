/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain;

import java.util.ArrayList;
import java.util.Date;
import sun.security.krb5.internal.crypto.Nonce;

/**
 *
 * @author Shubham
 */


public class Block {

    /**
     * @param args the command line arguments
     */
    public String Hash;
    public String PrevHash;
    public String merkleString;
    public long time_stamp;
    public int nonce; // It's value will determine the Leading zeroes in hash !
    public ArrayList<Transactions> transactions_ledgerArrayList = new ArrayList<Transactions>(); 
    
    Block(String PrevHash){
        this.PrevHash = PrevHash;
        this.time_stamp = new Date().getTime();
        this.Hash = Calculate_cur_hash();
    }

    public String Calculate_cur_hash() {
        
            return BlockStringFunctions.Gen_SHA256_hash(this.PrevHash 
                    +Long.toString(this.time_stamp)
                    +Integer.toString(this.nonce) + this.merkleString);
    }
    
    public void mineBlock(int difficulty){
        
        this.merkleString = BlockStringFunctions.getMerkleRoot(transactions_ledgerArrayList);
        String target = new String(new char[difficulty]).replace('\0','0');
        while (!this.Hash.substring(0,difficulty).equals(target)) {
            this.nonce++;
            this.Hash = Calculate_cur_hash();
        }
        return;
    }
    
    public boolean addTransactions(Transactions transactions){
        if(transactions == null) return false;
        if ((this.PrevHash != "0")) {
            if (transactions.processTransaction() != true) {
                //throw new TransactionFailed("Transaction Failed");
                System.out.println("Transaction failed Block");
                return false;
            }
        }
        transactions_ledgerArrayList.add(transactions);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}

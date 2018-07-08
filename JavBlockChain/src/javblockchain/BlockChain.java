/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain;


import com.google.gson.GsonBuilder;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import javblockchain.BlockTransactions.TransactionInput;
import javblockchain.BlockTransactions.TransactionOutput;

/**
 *
 * @author Shubham
 */
public class BlockChain {

    
    private static ArrayList<Block> block_chain = new ArrayList<Block>();
    // collection of unspent transactions ... UTXO  
    public static HashMap<String,TransactionOutput> UTXOutput= new HashMap<String, TransactionOutput>();
    
    public static double minimumTransaction = 0.1f;
    private static int ChainLenght = 0;
    private static int difficulty = 2;
    
    private static Wallet walletA;
    private static Wallet walletB;
    
    public static Transactions genesisTransaction; 
    
    public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
                System.out.println("[+]Block mined");
                ChainLenght++;
                System.out.println("[+]Block added");
		block_chain.add(newBlock);
    }
    
    public static boolean isChainValid(){
        Block currentBlock, prevBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXO = new HashMap<String,TransactionOutput>();
        tempUTXO.put(genesisTransaction.output_transactionsaArrayList.get(0).
                id,genesisTransaction.output_transactionsaArrayList.get(0));
        
        for (int i = 1; i < ChainLenght; i++) {
            currentBlock = block_chain.get(i);
            prevBlock = block_chain.get(i-1);
            
            if (!currentBlock.Hash.equals(currentBlock.Calculate_cur_hash())) {
                System.out.println("[-]Incorrect Hash Evaluation");
                return false;
            }
            
            if (!prevBlock.Hash.equals(currentBlock.PrevHash)) {
                System.out.println("[-]Inconsistency in Chain");
                return false;
            }
            
            if (!currentBlock.Hash.substring(0,difficulty).equals(hashTarget)) {
                System.out.println("[-]Block is not mined");
                return false;
            }
            
            TransactionOutput transactionOutput;
            for (int j = 0; j < currentBlock.transactions_ledgerArrayList.size(); j++) {
                Transactions currentTransactions = currentBlock.transactions_ledgerArrayList.get(j);
                
                if (!currentTransactions.VerifyTransaction()) {
                    System.out.println("[-]Transaction has not been signed");
                    return false;
                }
                if (currentTransactions.gInputValues.inputTotal() != currentTransactions.gOutputValues.outputTotal()) {
                    System.out.println("[-]Inputs are not equal to the output of the Transactions");
                    return false;
                }
                
                for (TransactionInput transactionInputIter: currentTransactions.input_transactionsArrayList) {
                    
                    transactionOutput = tempUTXO.get(transactionInputIter.transactionStringOutputId);
                    
                    if(transactionOutput == null){
                        System.out.println("[-]Referenced input on Transaction is missing");
                        return false;
                    }
                    if (transactionInputIter.UTXOutput.value != transactionOutput.value) {
                        System.out.println("[-]Referenced input on Transaction is Invalid");
                        return false;
                    }
                   
                   tempUTXO.remove(transactionInputIter.transactionStringOutputId);
                }
                
                for (TransactionOutput transactionOutputIter : currentTransactions.output_transactionsaArrayList) {
                    tempUTXO.put(transactionOutputIter.id, transactionOutputIter);
                }
                if (currentTransactions.output_transactionsaArrayList.get(0).receiverKey != currentTransactions.senderKey) {
                    System.out.println("#Transaction output reciepient is not who it should be");
                    return false;
                }
                
                if (currentTransactions.output_transactionsaArrayList.get(1).receiverKey != currentTransactions.senderKey) {
                    System.out.println("#Transaction output 'change' is not sender.");
                    return false;
                }
            }
            
            
        }
        return true;
    }
    
    public static void TestBlockChain(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        /*
        walletA = new Wallet();
        walletB = new Wallet();		
        Wallet coinbase = new Wallet();
        
        
	genesisTransaction = new Transactions(coinbase.publicKey, walletA.publicKey, 100f, null);
	genesisTransaction.GenerateSignature(coinbase.privateKey);	 //manually sign the genesis transaction	
	genesisTransaction.transactionID= "0"; //manually set the transaction id
	genesisTransaction.output_transactionsaArrayList.add(new TransactionOutput(genesisTransaction.receiverKey, genesisTransaction.value, genesisTransaction.transactionID)); //manually add the Transactions Output
	UTXOutput.put(genesisTransaction.output_transactionsaArrayList.get(0).id, genesisTransaction.output_transactionsaArrayList.get(0)); //its important to store our first transaction in the UTXOs list.

        
        System.out.println("Creating and Mining Genesis block... ");
	Block genesis = new Block("0");
	genesis.addTransactions(genesisTransaction);
	addBlock(genesis);
		
	//testing
	Block block1 = new Block(genesis.Hash);
	System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
	block1.addTransactions(walletA.sendBlock(walletB.publicKey, 40f));
	addBlock(block1);
	System.out.println("\nWalletA's balance is: " + walletA.getBalance());
	System.out.println("WalletB's balance is: " + walletB.getBalance());
	
	Block block2 = new Block(block1.Hash);
	System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
	block2.addTransactions(walletA.sendBlock(walletB.publicKey, 1000f));
	addBlock(block2);
	System.out.println("\nWalletA's balance is: " + walletA.getBalance());
	System.out.println("WalletB's balance is: " + walletB.getBalance());
		
	Block block3 = new Block(block2.Hash);
	System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
	block3.addTransactions(walletB.sendBlock(walletA.publicKey, 20));
	System.out.println("\nWalletA's balance is: " + walletA.getBalance());
        System.out.println("WalletB's balance is: " + walletB.getBalance());
        
        isChainValid();
        */
        
    }
    
    public static void main(String[] args) {

    }
    
}
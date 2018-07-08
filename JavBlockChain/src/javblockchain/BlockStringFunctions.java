package javblockchain;
//Test
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubham
 */
public class BlockStringFunctions {
    
    public static String Gen_SHA256_hash(String InputString){
        try {
            
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashString = messageDigest.digest(InputString.getBytes("UTF-8"));
            StringBuffer hashStringBuffer = new StringBuffer();
            
            for (int i = 0; i < hashString.length; i++) {
                String hex = Integer.toHexString(0xfb &hashString[i]);
                
                if (hex.length()==1) {
                    hashStringBuffer.append('0');
                }
                hashStringBuffer.append(hex);
            }
            return hashStringBuffer.toString();
            
        } catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
    
    public static String getMerkleRoot(ArrayList<Transactions> transactions){
        
        int count = transactions.size();
        ArrayList<String> prevTreeLayer = new ArrayList<String>();
        
        for (Transactions iterTransactions : transactions) {
            prevTreeLayer.add(iterTransactions.transactionID);
        }
        ArrayList<String> treeLayer = new ArrayList<String>();
        while(count>1){
            treeLayer = new ArrayList<String>();
            for (int i = 0; i<prevTreeLayer.size(); i++) {
                treeLayer.add(Gen_SHA256_hash(prevTreeLayer.get(i-1)
                        +prevTreeLayer.get(i)));
            }
            count = treeLayer.size();
            prevTreeLayer = treeLayer;
        }
        if (treeLayer.size()==1) {
           return treeLayer.get(0);
        }
        return "";
    }
    
    public static byte[] applySignature(PrivateKey privateKey, String inputString){
        
        Security.addProvider(new BouncyCastleProvider());
        Signature signature;
        byte[] output = new byte[0];
        
        try {
            signature = Signature.getInstance("ECDSA","BC");
            signature.initSign(privateKey);
            signature.update(inputString.getBytes());
            output = signature.sign();
            
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        
        return output;
    }
    
    
    public static boolean verifySignature(PublicKey publicKey,String dataString,byte [] signature){
        Security.addProvider(new BouncyCastleProvider());

        try {
            Signature verifySignature = Signature.getInstance("ECDSA","BC");
            verifySignature.initVerify(publicKey);
            verifySignature.update(dataString.getBytes());
            return verifySignature.verify(signature);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        
    }
    
    public static String getStringFromKeyString(Key key){
        //I don't know how  this line does.. what it does... but it works !!
        
        return Base64.getEncoder().encodeToString(key.getEncoded()); 
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javblockchain;

/**
 *
 * @author Shubham
 */

class InvalidBlockError extends Error{

    public InvalidBlockError(String defaultString) {
        super(defaultString);
    }
    
}
class InvalidTransactionAmount extends Error{
    public InvalidTransactionAmount(String defaultString) {
        super(defaultString);
    }
}

class IncompatableHashKey extends Error{
    public IncompatableHashKey(String defaultString){
        super(defaultString);
    }
}

class InconsistenteHashKey extends Error{
    public InconsistenteHashKey(String defaultString){
        super(defaultString);
    }
}

class TransactionFailed extends Error{

    public TransactionFailed(String defaultString) {
        super(defaultString);
    }
    
}
public class BlockChainErrorsExceptions {
    
}

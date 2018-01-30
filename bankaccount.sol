pragma solidity ^0.4.0;
import "github.com/oraclize/ethereum-api/oraclizeAPI.sol";

contract bankaccount is usingOraclize{
    address owner;
    uint id;
     mapping (uint => Transaction) transactions;
    uint totalLeisureTransacts;
    uint totalLeisureTransactsInvestedInETH;
    
       uint public ETHUSD;

    event newOraclizeQuery(string description);
    event newKrakenPriceTicker(string price);
    
      function KrakenPriceTicker() {
     
        update(0);
    }
    
    function __callback(bytes32 myid, string result, bytes proof) {
        if (msg.sender != oraclize_cbAddress()) throw;
        newKrakenPriceTicker(result);
        ETHUSD = parseInt(result, 2); // save it in storage as $ cents
        // do something with ETHUSD
       
    }

    function update(uint delay) payable {
        if (oraclize_getPrice("URL") > this.balance) {
            newOraclizeQuery("Oraclize query was NOT sent, please add some ETH to cover for the query fee");
        } else {
            newOraclizeQuery("Oraclize query was sent, standing by for the answer..");
            oraclize_query(delay, "URL", "json(https://api.kraken.com/0/public/Ticker?pair=ETHUSD).result.XETHZUSD.c.0");
        }
    }
    
    struct Transaction {
        string  desc;
        uint amount; 
        bool leisure;
        bytes32 date;
    }
    
  
    function addTransaction(string desc,uint amount,bool leisure,bytes32 date) public returns(uint tID){
        tID = id++;
        transactions[tID] = Transaction(desc,amount,leisure,date);
        
        if(leisure == true){
            newLeisure(amount);
        }
        
    }
    
    function newLeisure(uint amount){
        totalLeisureTransacts+=amount;
        uint amountinEth;
        KrakenPriceTicker();
        amountinEth = ETHUSD/amount;
        totalLeisureTransactsInvestedInETH+=amountinEth;
    }
    
   function  checkAmountInETH() public returns(uint total){
        KrakenPriceTicker();
       total = totalLeisureTransactsInvestedInETH * ETHUSD;
    }
    
    
    
}
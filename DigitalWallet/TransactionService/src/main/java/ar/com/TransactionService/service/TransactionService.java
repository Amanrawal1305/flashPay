package ar.com.TransactionService.service;

import ar.com.TransactionService.modal.Transaction;
import ar.com.TransactionService.modal.TransactionResponse;
import ar.com.TransactionService.repository.TransactionRepository;
import ar.com.enums.TxnStatus;
import ar.com.util.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    public String initiateTransaction(String sender,String receiver,String amount,String purpose){
        Transaction transaction=new Transaction();
        transaction.setTxnId(UUID.randomUUID().toString());
        transaction.setTxnAmount(Double.parseDouble(amount));
        transaction.setSenderId(sender);
        transaction.setReceiverId(receiver);
        transaction.setTxnPurpose(purpose);
        transaction.setTxnStatus(TxnStatus.INITIATED);
        transaction.setTxnMessage("transaction is initiated");
        transactionRepository.save(transaction);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(CommonConstants.SENDER_ID,transaction.getSenderId());
        jsonObject.put(CommonConstants.RECEIVER_ID,transaction.getReceiverId());
        jsonObject.put(CommonConstants.TXN_ID,transaction.getTxnId());
        jsonObject.put(CommonConstants.TXN_AMOUNT,transaction.getTxnAmount());
        kafkaTemplate.send(CommonConstants.TXN_INITIATE_QUEUE_TOPIC,jsonObject.toString());
        return transaction.getTxnId();

    }
    public List<TransactionResponse> getTransactionHistory(String username){

        List<Transaction> list = transactionRepository. findBySenderIdOrReceiverId(username, username);

        List<TransactionResponse> ans = new ArrayList<>();

        for (Transaction t: list) {
            TransactionResponse tr = new TransactionResponse();
            tr.setAmount(t.getTxnAmount());
            tr.setTxnTime(t.getCreatedOn());
            tr.setTxnId(t.getTxnId());
            if (t.getSenderId().equals(username)) {
                tr.setSendTo(t.getReceiverId());
                tr.setTxnType("DEBIT");
            } else {
                tr.setTxnType("CREDIT");
                tr.setReceiveFrom(t.getSenderId());
            }
            ans.add(tr);
        }
        return ans;
    }
}

package ar.com.TransactionService.consumer;

import ar.com.TransactionService.repository.TransactionRepository;
import ar.com.enums.TxnStatus;
import ar.com.util.CommonConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UpdatedTransactionConsumer {
    @Autowired
    TransactionRepository transactionRepository;

    @KafkaListener(topics = "TXN_UPDATE_QUEUE", groupId = "txn-update-group")
    public void listenUpdatedTransaction(String data){
        System.out.println(data);

        JSONObject jsonObject = new JSONObject(data);

        String txnId = jsonObject.getString(CommonConstants.TXN_ID);
        String txnMessage = jsonObject.getString(CommonConstants.TXN_MESSAGE);
        TxnStatus txnStatus = TxnStatus.valueOf(jsonObject.getString(CommonConstants.TXN_STATUS));

        transactionRepository.updateTransactionDetails(txnId, txnStatus, txnMessage);

        System.out.println("Final Transaction Updated");
    }

}

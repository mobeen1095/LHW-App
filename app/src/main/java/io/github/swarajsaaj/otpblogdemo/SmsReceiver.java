package io.github.swarajsaaj.otpblogdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by swarajpal on 19-04-2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle  = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[pdus.length];

        for(int i=0;i<pdus.length;i++){
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        String receivedMessage = smsMessage[0].getMessageBody().toString();
        String originatingAddress = smsMessage[0].getOriginatingAddress();
        mListener.messageReceived(receivedMessage);

        SmsManager smsManager = SmsManager.getDefault();
        if(MainActivity.separated[0].equals("#0#") && MainActivity.separated.length == 7)
        {
            try {
                smsManager.sendTextMessage(originatingAddress, null, "Response is Recorded.\nThank you for your cooperation.!", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                smsManager.sendTextMessage(originatingAddress, null, "Response not Recorded.\nCheck message format and Try Again.!\n" +
                        "Send your record with this Format :\n#0#,LHWXX,UC,Head-Name,Head-Number,Sex,No.of Un-Reg-Children", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }


}

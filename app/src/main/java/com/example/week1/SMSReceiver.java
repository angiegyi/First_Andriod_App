package com.example.week1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;


public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Use the Telephony class to extract the incoming messages from the intent
         * intent holds sms
         * */
        //extracts all the SMS messages into an array of SmsMessage class.
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        //iterate through them and extract the SMS text body and number:
        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String message = currentMessage.getDisplayMessageBody();

            //making the intent and sending the message to your application
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);
        }
    }
}
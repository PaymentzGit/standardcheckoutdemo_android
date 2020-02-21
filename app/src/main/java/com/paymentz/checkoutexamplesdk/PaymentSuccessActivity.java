package com.paymentz.checkoutexamplesdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 9/8/2018.
 */

public class PaymentSuccessActivity extends AppCompatActivity
{
    public TextView demoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        getSupportActionBar().setTitle("Payment Success");

        demoText = (TextView)findViewById(R.id.demoText);
        String result = getIntent().getExtras().getString("result");
        try
        {
            JSONObject paymentResultJson = new JSONObject(result);

             demoText.setText("Tracking Id: "+paymentResultJson.getString("trackingId")+ "\n"
                    +"Status:" +paymentResultJson.getString("status") + "\n"
                    +"FirstName:" +paymentResultJson.getString("firstName") + "\n"
                    +"LastName:" +paymentResultJson.getString("lastName") + "\n"
                    +"Checksum:" +paymentResultJson.getString("checksum") + "\n"
                    +"Description:" +paymentResultJson.getString("desc") + "\n"
                    +"Currency:" +paymentResultJson.getString("currency") + "\n"
                    +"Amount:" +paymentResultJson.getString("amount") + "\n"
                    +"TMPL_Currency:" +paymentResultJson.getString("tmpl_currency") + "\n"
                    +"TMPL_Amount:" +paymentResultJson.getString("tmpl_amount") + "\n"
                    +"TimeStamp:" +paymentResultJson.getString("timestamp") + "\n"
                    +"Result Code:" +paymentResultJson.getString("resultCode") + "\n"
                    +"Result Description:" +paymentResultJson.getString("resultDescription") + "\n"
                    +"Card Bin:" +paymentResultJson.getString("cardBin") + "\n"
                    +"Card Last 4 Digit:" +paymentResultJson.getString("cardLast4Digits") + "\n"
                    +"Email Id:" +paymentResultJson.getString("custEmail") + "\n"
                    +"Payment Mode:" +paymentResultJson.getString("paymentMode") + "\n"
                    +"Payment Brand:" +paymentResultJson.getString("paymentBrand"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}

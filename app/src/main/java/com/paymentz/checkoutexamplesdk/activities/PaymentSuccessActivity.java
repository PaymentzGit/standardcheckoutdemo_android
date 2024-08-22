package com.paymentz.checkoutexamplesdk.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paymentz.checkoutexamplesdk.R;

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

        demoText = (TextView) findViewById(R.id.demoText);
        String result = getIntent().getExtras().getString("result");
        try {
            JSONObject paymentResultJson = new JSONObject(result);
            if (paymentResultJson.has("resultCode")) {
                if (paymentResultJson.getString("resultCode").equals("00001")) {
                    getDialogMsg("Success", "Transaction Success", getResources().getDrawable(R.drawable.ic_done));
                } else {
                    getDialogMsg("Fail", "Transaction Failed", getResources().getDrawable(R.drawable.ic_fail24dp));
                }

//             demoText.setText("Tracking Id: "+paymentResultJson.getString("trackingId")+ "\n"
//                    +"Status:" +paymentResultJson.getString("status") + "\n"
//                    +"FirstName:" +paymentResultJson.getString("firstName") + "\n"
//                    +"LastName:" +paymentResultJson.getString("lastName") + "\n"
//                    +"Checksum:" +paymentResultJson.getString("checksum") + "\n"
//                    +"Description:" +paymentResultJson.getString("desc") + "\n"
//                    +"Currency:" +paymentResultJson.getString("currency") + "\n"
//                    +"Amount:" +paymentResultJson.getString("amount") + "\n"
//                    +"TMPL_Currency:" +paymentResultJson.getString("tmpl_currency") + "\n"
//                    +"TMPL_Amount:" +paymentResultJson.getString("tmpl_amount") + "\n"
//                    +"TimeStamp:" +paymentResultJson.getString("timestamp") + "\n"
//                    +"Result Code:" +paymentResultJson.getString("resultCode") + "\n"
//                    +"Result Description:" +paymentResultJson.getString("resultDescription") + "\n"
//                    +"Email Id:" +paymentResultJson.getString("custEmail") + "\n"
//                    +"Payment Mode:" +paymentResultJson.getString("paymentMode") + "\n"
//                    +"Payment Brand:" +paymentResultJson.getString("paymentBrand"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


        public void getDialogMsg(String txtMsg, String msg, Drawable image){
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_failed_dialog, null);
        final android.app.AlertDialog deleteDialog = new android.app.AlertDialog.Builder(PaymentSuccessActivity.this).create();
        deleteDialog.setView(deleteDialogView);
        TextView sndFail_Msg =(TextView) deleteDialogView.findViewById(R.id.sndFailMsg);
        TextView txt_msgSet= (TextView) deleteDialogView.findViewById(R.id.txt_msg);
        ImageView imageView=(ImageView) deleteDialogView.findViewById(R.id.image_msg);
        txt_msgSet.setText(txtMsg);
        sndFail_Msg.setText(msg);
        imageView.setImageDrawable(image);
        deleteDialogView.findViewById(R.id.sendOkFailBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                Intent iTime = new Intent(PaymentSuccessActivity.this, CheckoutActivity.class);
                startActivity(iTime);
            }
        });
        deleteDialog.show();
    }
}

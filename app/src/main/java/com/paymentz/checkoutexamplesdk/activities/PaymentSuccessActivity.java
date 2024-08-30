package com.paymentz.checkoutexamplesdk.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.paymentz.checkoutexamplesdk.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 9/8/2018.
 */

public class PaymentSuccessActivity extends AppCompatActivity {
    public TextView demoText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        if (getActionBar() != null) {
            getActionBar().setTitle("Payment Success");
        }

        demoText = findViewById(R.id.demoText);
        Bundle intentExtras = getIntent().getExtras();
        String result = intentExtras != null ? intentExtras.getString("result") : "";
        try {
            JSONObject paymentResultJson = new JSONObject(result);
            if (paymentResultJson.has("resultCode")) {
                if (paymentResultJson.getString("resultCode").equals("00001")) {
                    getDialogMsg("Success", "Transaction Success", getResources().getDrawable(R.drawable.ic_done));
                } else {
                    getDialogMsg("Fail", "Transaction Failed", getResources().getDrawable(R.drawable.ic_fail24dp));
                }

            }
        } catch (JSONException e) {
            Log.e(this.getLocalClassName(),e.toString());
        }
    }


    public void getDialogMsg(String txtMsg, String msg, Drawable image) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_failed_dialog, null);
        final android.app.AlertDialog deleteDialog = new android.app.AlertDialog.Builder(PaymentSuccessActivity.this).create();
        deleteDialog.setView(deleteDialogView);
        TextView sndFail_Msg = deleteDialogView.findViewById(R.id.sndFailMsg);
        TextView txt_msgSet = deleteDialogView.findViewById(R.id.txt_msg);
        ImageView imageView = deleteDialogView.findViewById(R.id.image_msg);
        txt_msgSet.setText(txtMsg);
        sndFail_Msg.setText(msg);
        imageView.setImageDrawable(image);
        deleteDialogView.findViewById(R.id.sendOkFailBtn).setOnClickListener(v -> {
                deleteDialog.dismiss();
                Intent iTime = new Intent(PaymentSuccessActivity.this, CheckoutActivity.class);
                startActivity(iTime);
        });
        deleteDialog.show();
    }
}

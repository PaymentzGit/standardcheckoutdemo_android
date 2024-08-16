package com.paymentz.checkoutexamplesdk;


import static com.paymentz.checkoutexamplesdk.R.id.*;
import static com.paymentz.checkoutexamplesdk.R.id.tietdescription;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.paymentz.pz_checkout_sdk.model.PZCheckout;
import com.paymentz.pz_checkout_sdk.model.PayBrand;
import com.paymentz.pz_checkout_sdk.model.PayMode;
import com.paymentz.pz_checkout_sdk.model.PayResult;
import com.paymentz.pz_checkout_sdk.model.RequestParameters;

import java.util.Locale;

/**
 * Created by Admin on 8/23/2018.
 */

public class CheckoutActivity extends AppCompatActivity {

    public TextInputLayout tildescription, tilamount;
    public TextInputEditText etamount, etorderdescription;
    public String mtransactionid;
    public Float amount;
    private Button pay;

    final RequestParameters requestParameters = new RequestParameters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutinfo);

        tildescription = (TextInputLayout) findViewById(R.id.tildescription);
        tilamount = (TextInputLayout) findViewById(R.id.tilamount);

        etorderdescription = (TextInputEditText) findViewById(tietdescription);
        etamount = (TextInputEditText) findViewById(tietamount);

        pay = (Button) findViewById(R.id.btn_payNow);
        etorderdescription.addTextChangedListener(new MyTextWatcher(etorderdescription));
        etamount.addTextChangedListener(new MyTextWatcher(etamount));

        requestParameters.setMemberId("10558");
        requestParameters.setToType("paymentz");
        requestParameters.setMemberKey("bzI93aEQeYDeE50Pa929NiDk3us8XTbU");
        requestParameters.setOrderDescription("Testing Transaction");
        requestParameters.setCountry("IN");
        requestParameters.setCity("Mumbai");
        requestParameters.setState("MH");
        requestParameters.setPostCode("400064");
        requestParameters.setStreet("Malad");
        requestParameters.setTelnocc("091");
        requestParameters.setPhone("9854785236");
        requestParameters.setEmail("udaybhan.rajbhar@paymentz.com");
        requestParameters.setPaymentBrand(String.valueOf(PayBrand.VISA));
        requestParameters.setPaymentMode(String.valueOf(PayMode.CC));
        requestParameters.setCurrency("USD");
        requestParameters.setTerminalId("7079");
        requestParameters.setHostUrl("https://sandbox.paymentplug.com/transaction/Checkout");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateMemberid()) {
                    return;
                } else {
                    mtransactionid = etorderdescription.getText().toString();
                    requestParameters.setMerchantTransactionId(mtransactionid);
                }

                if (!validateAmount()) {
                    return;
                } else {
                    amount = Float.parseFloat(etamount.getText().toString());
                    requestParameters.setAmount(String.format(Locale.US, "%.2f", amount));
                }
                submitForm();
            }
        });
    }

    public void submitForm() {

        if (!validateMemberid()) {
            return;
        }
        if (!validateAmount()) {
            return;
        }
        PZCheckout pzCheckout = new PZCheckout();
        pzCheckout.initPayment(CheckoutActivity.this, requestParameters);
    }

    private boolean validateMemberid() {
        if (etorderdescription.getText().toString().trim().isEmpty()) {
            tildescription.setError(getString(R.string.error_msg_mail));
            requestFocus(etorderdescription);
            return false;
        } else {
            tildescription.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateAmount() {
        if (etamount.getText().toString().trim().isEmpty()) {
            tilamount.setError(getString(R.string.error_msg_amount));
            requestFocus(etamount);
            return false;
        } else {
            tilamount.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @SuppressLint("NonConstantResourceId")
        public void afterTextChanged(Editable editable) {
            /*switch (view.getId()) {

                case R.id.tietdescription:
                    validateMemberid();
                    break;
                case R.id.tietamount:
                    validateAmount();
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // First you need to check the request code
        if (requestCode == PZCheckout.PAYMENT_REQUEST_CODE) {
            // After this you need to check the result code
            if (resultCode == RESULT_OK) {
                // If its ok, you can get the payment result as described below
                PayResult paymentResult = (PayResult) data.getExtras().get(PZCheckout.PAYMENT_RESULT);
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.putExtra("result", paymentResult.toJsonString());
                startActivity(intent);
            }
        }
    }
}

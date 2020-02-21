package com.paymentz.checkoutexamplesdk;

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

import com.paymentz.pz_checkout_sdk.model.PayBrand;
import com.paymentz.pz_checkout_sdk.model.PayEncrypt;
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

    final RequestParameters requestParameters= new RequestParameters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutinfo);

        tildescription = (TextInputLayout) findViewById(R.id.tildescription);
        tilamount  = (TextInputLayout) findViewById(R.id.tilamount);

        etorderdescription = (TextInputEditText) findViewById(R.id.tietdescription);
        etamount = (TextInputEditText) findViewById(R.id.tietamount);

        pay = (Button) findViewById(R.id.btn_payNow);
        etorderdescription.addTextChangedListener(new MyTextWatcher(etorderdescription));
        etamount.addTextChangedListener(new MyTextWatcher(etamount));

        requestParameters.setMemberId("11344");
        requestParameters.setToType("docspartner");
        requestParameters.setMemberKey("9P8vdzPP4oL9BeDgko3ti6HGnou59LEB");
        requestParameters.setOrderDescription("Testing Transaction");
        requestParameters.setMerchantRedirectUrl("www.paymentz.com");
        requestParameters.setCountry("IN");
        requestParameters.setCity("Mumbai");
        requestParameters.setState("MH");
        requestParameters.setPostCode("400064");
        requestParameters.setStreet("Malad");
        requestParameters.setTelnocc("091");
        requestParameters.setPhone("9854785236");
        requestParameters.setEmail("udaybhan.rajbhar@paymentz.com");
        requestParameters.setIpAddress("192.168.0.1");
        requestParameters.setPaymentBrand(PayBrand.VISA);
        requestParameters.setPaymentMode(PayMode.CC);
        requestParameters.setCurrency("USD");
        requestParameters.setTmplCurrency("USD");
        requestParameters.setTerminalId("1106");
        requestParameters.setHostUrl("https://preprod.paymentz.com/transaction/Checkout");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Float.parseFloat(etamount.getText().toString());
                mtransactionid=etorderdescription.getText().toString();

                requestParameters.setMerchantTransactionId(mtransactionid);
                requestParameters.setAmount(String.format(Locale.US,"%.2f",amount));
                requestParameters.setTmplAmount(String.format(Locale.US,"%.2f",amount));
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
        PayEncrypt.initPayment(CheckoutActivity.this, requestParameters);
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

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.tietdescription:
                        validateMemberid();
                    break;
                case R.id.tietamount:
                        validateAmount();
                    break;


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // First you need to check the request code
        if(requestCode == PayEncrypt.PAYMENT_REQUEST_CODE)
        {
            // After this you need to check the result code
            if(resultCode == RESULT_OK)
            {
                // If its ok, you can get the payment result as described below
                PayResult paymentResult = (PayResult) data.getExtras().get(PayEncrypt.PAYMENT_RESULT);
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.putExtra("result", paymentResult.toJsonString());
                startActivity(intent);
            }
        }
    }
}

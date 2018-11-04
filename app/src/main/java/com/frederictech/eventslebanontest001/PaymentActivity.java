package com.frederictech.eventslebanontest001;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;

import static com.frederictech.eventslebanontest001.R.id.fab;
import static java.security.AccessController.getContext;


public class PaymentActivity extends AppCompatActivity {

    private Button btnConfirmPayment;
    private CardInputWidget mCardInputWidget;
    private Card card;
    private Stripe stripe = new Stripe(this, "pk_test_6pRNASCoBOKtIshFeQd4XMUh");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        btnConfirmPayment = (Button) findViewById(R.id.btn_confirm_payment);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remember that the card object will be null if the user inputs invalid data.

                card = mCardInputWidget.getCard();
                if (card == null) {
                    // Do not continue token creation.
                }

                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                            }
                            public void onError(Exception error) {
                                // Show localized error message
                            }
                        }
                );


            }
        });



    }


}

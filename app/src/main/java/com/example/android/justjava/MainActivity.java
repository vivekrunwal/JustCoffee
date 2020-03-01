package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        EditText editname = (EditText) findViewById(R.id.name_view);
        String name = editname.getText().toString();

        CheckBox WhippedCream = (CheckBox) findViewById(R.id.whipped_checkbox);
        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = Chocolate.isChecked();
        boolean hasWhippedCream = WhippedCream.isChecked();
        //Log.v("MainActivity","Has Whipped Cream: "+hasWhippedCream);
        int price = calculatePrice();
        //displayQuantity(price);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        // displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffee",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private int calculatePrice() {
        int price = quantity * 5;
        return price;
    }

    private void displayQuantity(int anumber) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + anumber);
    }

    private String createOrderSummary(int price, boolean addcream, boolean addChocolate, String name) {

        String Message = getString(R.string.order_summary_name, name);
        Message += "\n Add Whipped Cream : " + addcream;
        Message += "\n Add Chocolate : " + addChocolate;
        if (addChocolate == true)
            price += quantity * 2;
        if (addcream == true)
            price += quantity * 1;

        Message += "\n Quantity=" + quantity + "\n Total = $" + price + "\n";
        Message += getString(R.string.thank_you);

        return Message;
    }

    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

}
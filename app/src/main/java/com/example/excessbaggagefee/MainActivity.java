package com.example.excessbaggagefee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etBaggageWeight;
    private RadioGroup rgTicketClass;
    private TextView tvExcessWeight, tvExcessCharge, tvTotalPayable;
    private Button btnCalculate, btnReset, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Apply Window Insets for top status bar & camera cutout space
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Header Action Buttons inside Hero Card
        ImageButton btnShareHeader = findViewById(R.id.btnShareHeader);

        if (btnShareHeader != null) {
            btnShareHeader.setOnClickListener(v -> shareApp());
        }

        // Initialize UI components
        etBaggageWeight = findViewById(R.id.etBaggageWeight);
        etBaggageWeight.setPrivateImeOptions("nm,com.google.android.inputmethod.latin.noSuggestions");
        rgTicketClass = findViewById(R.id.rgTicketClass);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        btnAbout = findViewById(R.id.btnAbout);

        btnCalculate.setOnClickListener(v -> calculateExcessBaggageFee());
        btnReset.setOnClickListener(v -> resetFields());
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void calculateExcessBaggageFee() {
        String weightStr = etBaggageWeight.getText() != null ? etBaggageWeight.getText().toString().trim() : "";

        if (weightStr.isEmpty()) {
            etBaggageWeight.setError("Please enter baggage weight in kg");
            etBaggageWeight.requestFocus();
            return;
        }

        double weight;
        try {
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            etBaggageWeight.setError("Invalid weight format");
            etBaggageWeight.requestFocus();
            return;
        }

        if (weight < 0) {
            etBaggageWeight.setError("Weight cannot be negative");
            etBaggageWeight.requestFocus();
            return;
        }

        int selectedId = rgTicketClass.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a ticket class (Economy or Business)", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isBusiness = (selectedId == R.id.rbBusiness);
        
        double allowance = isBusiness ? 40.0 : 20.0;
        double rate = isBusiness ? 12.0 : 15.0;

        // Calculations
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = 0.08 * excessCharge;
        double totalPayable = excessCharge + serviceTax;

        // Display results
        tvExcessWeight.setText(String.format(Locale.getDefault(), "%.2f kg", excessWeight));
        tvExcessCharge.setText(String.format(Locale.getDefault(), "RM %.2f", excessCharge));
        tvTotalPayable.setText(String.format(Locale.getDefault(), "RM %.2f", totalPayable));

        Toast.makeText(this, "Calculation completed successfully!", Toast.LENGTH_SHORT).show();
    }

    private void resetFields() {
        etBaggageWeight.setText("");
        rgTicketClass.clearCheck();
        tvExcessWeight.setText("0.00 kg");
        tvExcessCharge.setText("RM 0.00");
        tvTotalPayable.setText("RM 0.00");
        etBaggageWeight.setError(null);
        etBaggageWeight.clearFocus();
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message));
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}


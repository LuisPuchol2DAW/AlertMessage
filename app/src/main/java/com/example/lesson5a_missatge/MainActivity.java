package com.example.lesson5a_missatge;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    private Button buttonNormalAlert, buttonRadioGroupAlert, buttonCheckboxAlert;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    String resultTextBuilder = "Has pitjat ";
    String ok = "OK";
    String cancel = "CANCEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Activity has been created");
        super.onCreate(savedInstanceState);
        startStuff();
        startLayout();

        startListeners();
    }

    private void startListeners() {
        normalAlert();
        radioAlert();
        checkboxAlert();
    }

    private void checkboxAlert() {
        buttonCheckboxAlert.setOnClickListener(v -> {
            View dialogView = getView(R.layout.alert_checkbox);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Hello");
            builder.setCancelable(false);
            builder.setView(dialogView);

            CheckBox checkBox1 = dialogView.findViewById(R.id.checkBox1);
            CheckBox checkBox2 = dialogView.findViewById(R.id.checkBox2);

            builder.setPositiveButton(ok, (DialogInterface.OnClickListener) (dialog, which) -> {
                StringBuilder selectedOptions = new StringBuilder();
                if (checkBox1.isChecked()) {
                    selectedOptions.append(checkBox1.getText().toString()).append(" ");
                }
                if (checkBox2.isChecked()) {
                    selectedOptions.append(checkBox2.getText().toString()).append(" ");
                }
                if (!checkBox1.isChecked() && !checkBox2.isChecked()) {
                    Toast.makeText(MainActivity.this, "No has seleccionat res", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTextOnTextView(dialog, selectedOptions.toString());
            });

            builder.setNegativeButton(cancel, (DialogInterface.OnClickListener) (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
    }

    private void radioAlert() {
        buttonRadioGroupAlert.setOnClickListener(v -> {
            View dialogView = getView(R.layout.alert_radio_group);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Hello");
            builder.setCancelable(false);
            builder.setView(dialogView);
            RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);

            resultRadioGroup(builder, radioGroup, dialogView);

            builder.create().show();
        });
    }

    private View getView(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(layoutResID, null);
        return dialogView;
    }

    private void resultRadioGroup(AlertDialog.Builder builder, RadioGroup radioGroup, View dialogView) {
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(MainActivity.this, "No has seleccionat res", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedOption = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = dialogView.findViewById(selectedOption);
            String selectedText = selectedRadioButton.getText().toString();
            setTextOnTextView(dialog, selectedText);
        });

        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            setTextOnTextView(dialog, cancel);
        });
    }

    private void normalAlert() {
        buttonNormalAlert.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Hello");

            builder.setMessage("Missatje alerta");
            builder.setCancelable(false);

            positiveResult(builder);
            negativeResult(builder);

            builder.create().show();
        });
    }

    private void negativeResult(AlertDialog.Builder builder) {
        builder.setNegativeButton(cancel, (DialogInterface.OnClickListener) (dialog, which) -> {
            setTextOnTextView(dialog, cancel);
        });
    }

    private void setTextOnTextView(DialogInterface dialog, String value) {
        textView.setText(resultTextBuilder + value);
        dialog.cancel();
    }

    private void positiveResult(AlertDialog.Builder builder) {
        builder.setPositiveButton(ok, (DialogInterface.OnClickListener) (dialog, which) -> {
            setTextOnTextView(dialog, ok);
        });
    }

    public void startStuff() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void startLayout() {
        textView = findViewById(R.id.textView);
        buttonNormalAlert = findViewById(R.id.buttonDialeg);
        buttonRadioGroupAlert = findViewById(R.id.buttonRadioGroup);
        buttonCheckboxAlert = findViewById(R.id.buttonCheckbox);
    }


}
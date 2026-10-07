package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private double memoria = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.input);

        View.OnClickListener operacionView = this::operacion;
        View.OnClickListener numeroView = this::numero;

        findViewById(R.id.opMC).setOnClickListener(operacionView);
        findViewById(R.id.opMR).setOnClickListener(operacionView);
        findViewById(R.id.opMPlus).setOnClickListener(operacionView);
        findViewById(R.id.opMMinus).setOnClickListener(operacionView);
        findViewById(R.id.opMS).setOnClickListener(operacionView);

        findViewById(R.id.opAC).setOnClickListener(operacionView);
        findViewById(R.id.opDel1).setOnClickListener(operacionView);
        findViewById(R.id.opDiv).setOnClickListener(operacionView);
        findViewById(R.id.opEq).setOnClickListener(operacionView);
        findViewById(R.id.opMul).setOnClickListener(operacionView);
        findViewById(R.id.opRes).setOnClickListener(operacionView);
        findViewById(R.id.opSum).setOnClickListener(operacionView);

        findViewById(R.id.num0).setOnClickListener(numeroView);
        findViewById(R.id.num1).setOnClickListener(numeroView);
        findViewById(R.id.num2).setOnClickListener(numeroView);
        findViewById(R.id.num3).setOnClickListener(numeroView);
        findViewById(R.id.num4).setOnClickListener(numeroView);
        findViewById(R.id.num5).setOnClickListener(numeroView);
        findViewById(R.id.num6).setOnClickListener(numeroView);
        findViewById(R.id.num7).setOnClickListener(numeroView);
        findViewById(R.id.num8).setOnClickListener(numeroView);
        findViewById(R.id.num9).setOnClickListener(numeroView);
    }

    public void operacion(View view) {
        if (editText == null) editText = findViewById(R.id.input);
        int id = view.getId();
        String valorText = editText.getText().toString();

        if (id == R.id.opAC) editText.setText("0");
        else if (id == R.id.opMC) memoria = 0.0;
        else if (id == R.id.opMR) {
            String valMem = (memoria == (long) memoria) ? String.valueOf((long) memoria) : String.valueOf(memoria);
            editText.setText(valMem);
        } else if (id == R.id.opMS) {
            if (valorText.split(" ").length == 1) {
                String primerNumero = valorText.split(" ")[0];
                switch (primerNumero) {
                    case "*":
                    case "+":
                    case "-":
                    case "/":
                    case "":
                        break;
                    default:
                        memoria = Double.parseDouble(primerNumero);
                }
            }
        } else if (id == R.id.opMPlus) {
            calcular(editText);
            try {
                double val = Double.parseDouble(editText.getText().toString().trim());
                memoria += val;
            } catch (Exception ignored) {}
        } else if (id == R.id.opMMinus) {
            calcular(editText);
            try {
                double val = Double.parseDouble(editText.getText().toString().trim());
                memoria -= val;
            } catch (Exception ignored) {}
        } else if (id == R.id.opSum) editText.setText(valorText + " + ");
        else if (id == R.id.opRes) editText.setText(valorText + " - ");
        else if (id == R.id.opMul) editText.setText(valorText + " * ");
        else if (id == R.id.opDiv) editText.setText(valorText + " / ");
        else if (id == R.id.opEq) calcular(editText);
        else if (id == R.id.opDel1) {
            if (!valorText.isEmpty()) {
                valorText = valorText.substring(0, valorText.length() - 1).trim();
                editText.setText(valorText);
            }
        }
    }

    public void numero(View view) {
        if (editText == null) editText = findViewById(R.id.input);
        int id = view.getId();
        String currentText = editText.getText().toString();
        if (currentText.equals("Error")) currentText = "";

        if (currentText.equals("0")){
            currentText = "";
        }

        if (id == R.id.num0) editText.setText(currentText + "0");
        else if (id == R.id.num1) editText.setText(currentText + "1");
        else if (id == R.id.num2) editText.setText(currentText + "2");
        else if (id == R.id.num3) editText.setText(currentText + "3");
        else if (id == R.id.num4) editText.setText(currentText + "4");
        else if (id == R.id.num5) editText.setText(currentText + "5");
        else if (id == R.id.num6) editText.setText(currentText + "6");
        else if (id == R.id.num7) editText.setText(currentText + "7");
        else if (id == R.id.num8) editText.setText(currentText + "8");
        else if (id == R.id.num9) editText.setText(currentText + "9");
    }

    public void calcular(EditText editText) {
        if (editText == null) return;
        String texto = editText.getText().toString().trim();
        if (texto.isEmpty()) return;

        String[] partes = texto.split("\\s+");
        if (partes.length < 3) return;

        try {
            double num1 = Double.parseDouble(partes[0]);
            String op = partes[1];
            double num2 = Double.parseDouble(partes[2]);
            double res = 0;

            if (op.equals("+")) res = num1 + num2;
            else if (op.equals("-")) res = num1 - num2;
            else if (op.equals("*")) res = num1 * num2;
            else if (op.equals("/")) {
                if (num2 == 0) {
                    editText.setText("Error");
                    return;
                }
                res = num1 / num2;
            }

            if (res == (long) res) {
                editText.setText(String.valueOf((long) res));
            } else {
                editText.setText(String.valueOf(res));
            }
        } catch (Exception e) {
            editText.setText("Error");
        }
    }
}

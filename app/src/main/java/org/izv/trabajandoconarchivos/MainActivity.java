package org.izv.trabajandoconarchivos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "xyzyx";

    private EditText etText;
    private TextView tvText;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        Button btReadExtern, btReadIntern, btWriteExtern, btWriteIntern;
        btReadExtern = findViewById(R.id.btReadExtern);
        btReadIntern = findViewById(R.id.btReadIntern);
        btWriteExtern = findViewById(R.id.btWriteExtern);
        btWriteIntern = findViewById(R.id.btWriteIntern);

        etText = findViewById(R.id.etText);
        tvText = findViewById(R.id.tvText);
        fileName = getString(R.string.file_name);

        btReadExtern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readExternalFile();
            }
        });
        btReadIntern.setOnClickListener((View v) -> {
            readInternalFle();
        });
        btWriteExtern.setOnClickListener((View v) -> {
            writeExternalFile();
        });
        btWriteIntern.setOnClickListener((View v) -> {
            writeInternalFile();
        });
    }

    private boolean writeFile(File file, String fileName, String string) {
        //https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        File f = new File(file, fileName);
        FileWriter fw = null;
        boolean ok = true;
        try {
            fw = new FileWriter(f, true);
            fw.write(string);
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            ok = false;
            Log.v(TAG, e.toString());
        }
        return ok;
    }

    private void writeResult(boolean result) {
        String mensaje = getString(R.string.message_ok);
        if(!result) {
            mensaje = getString(R.string.message_no);
        }
        tvText.setText(mensaje);
    }

    private void writeInternalFile() {
        String text = etText.getText().toString();
        writeResult(writeFile(getFilesDir(), fileName, text));
    }

    private void writeExternalFile() {
        String text = etText.getText().toString();
        writeResult(writeFile(getExternalFilesDir(null), fileName, text));
    }

    private String readFile(File file, String fileName) {
        //https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        File f = new File(file, fileName);
        String texto = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            br.close();
        } catch (IOException e) {
            texto = null;
            Log.v(TAG, e.toString());
        }
        return texto;
    }

    private void writeReadResult(String result) {
        String string = result;
        if(result == null) {
            string = getString(R.string.read_no);
        } else if(result.isEmpty()) {
            string = getString(R.string.read_ok);
        }
        tvText.setText(string);
    }

    private void readInternalFle() {
        writeReadResult(readFile(getFilesDir(), fileName));
    }

    private void readExternalFile() {
        writeReadResult(readFile(getExternalFilesDir(null), fileName));
    }
}
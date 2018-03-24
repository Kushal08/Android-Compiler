package com.example.coed152.androcompile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.example.coed152.androcompile.FileOperations;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText fname,fcontent,fnameread,inputs;
    Button write,read,next;
    TextView filecon;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fname = (EditText)findViewById(R.id.fname);
        // Spinner element
       spinner = (Spinner) findViewById(R.id.spinner1);



        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Choose Language");
        categories.add("C");
        categories.add("C++");
        categories.add("Python");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0); //set default selection to 0

        inputs = (EditText)findViewById(R.id.inputs);

        fcontent = (EditText)findViewById(R.id.ftext);



       // fnameread = (EditText)findViewById(R.id.fnameread);
        write = (Button)findViewById(R.id.btnwrite);
     //   read = (Button)findViewById(R.id.btnread);
        next = (Button)findViewById(R.id.button2);
      //  filecon = (TextView)findViewById(R.id.filecon);
        write.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String filename = "test";
                String filecontent = fcontent.getText().toString();
                FileOperations fop = new FileOperations();
                fop.write(filename, filecontent);
                if(fop.write(filename, filecontent)){
                    Toast.makeText(getApplicationContext(), filename+".txt created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();

                }
            }
        });

      /*  read.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String readfilename = fnameread.getText().toString();

                FileOperations fop = new FileOperations();
                String text = fop.read(readfilename);
                if(text != null){
                    filecon.setText(text);
                }
                else {
                    Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
                    filecon.setText(null);
                }

            }
        });
        */

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getBaseContext(), SuccessActivity.class);
                intent.putExtra("code",fcontent.getText().toString());
                intent.putExtra("inputs",inputs.getText().toString());
                intent.putExtra("operation",String.valueOf(spinner.getSelectedItem()));


                startActivity(intent);

            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(String.valueOf(spinner.getSelectedItem()) == "C") {
            fcontent.setText("#include<stdio.h>\n"+
                    "int main(int argc, char * argv[]) {\n"+
                    "printf(\"Hello %s\",argv[1]);\n"+
                    "return 0;\n"+
                    "}");
        }
        else  if(String.valueOf(spinner.getSelectedItem()) == "C++") {
            fcontent.setText("#include<iostream>\n" +
                    "using namespace std;\n" +
                    "int main(int argc, char * argv[])\n" +
                    "{ cout << \"Hello \"<<argv[1];\n" +
                    "}");
        }
        else if(String.valueOf(spinner.getSelectedItem()) == "Python") {
            fcontent.setText("import sys\n" +
                    "arg = sys.argv;\n" +
                    "print int(arg[1])+int(arg[2])"
            );
        }
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}

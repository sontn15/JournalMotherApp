package com.sh.journalmotherapp.ui.memory;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sh.journalmotherapp.R;

public class AddMemoryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imv_memory;
    EditText edt_felling, edt_content;
    Spinner spn_feeling;
    Button btn_Cancel, btn_Save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memory);
        initViews();
    }

    private void initViews() {
        imv_memory = this.findViewById(R.id.imageContainer_memory);
        edt_felling = this.findViewById(R.id.fellingEditText);
        edt_content = this.findViewById(R.id.contentEditText);
        btn_Cancel = this.findViewById(R.id.btnCancel_memory);
        btn_Save = this.findViewById(R.id.btnSave_memory);
        spn_feeling = this.findViewById(R.id.spn_feeling);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.feelings));
        spn_feeling.setAdapter(spinnerArrayAdapter);
        btn_Save.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        imv_memory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageContainer_memory:

                break;
            case R.id.btnCancel_memory:

                break;
            case R.id.btnSave_memory:

                break;
        }
    }
}

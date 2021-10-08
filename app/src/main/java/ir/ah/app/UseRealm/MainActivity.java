package ir.ah.app.UseRealm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import ir.ah.app.UseRealm.model.User;
import ir.ah.app.UseRealm.model.UserDAO;


public class MainActivity extends AppCompatActivity {

    private EditText editTextTextPersonName;
    private TextView textView;
    private UserDAO userDAO =new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTextPersonName = (EditText)findViewById(R.id.editTextTextPersonName);
        textView = (TextView) findViewById(R.id.textView);

        editTextTextPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s =editTextTextPersonName.getText().toString();
                BigDecimal number = new BigDecimal(s);

                textView.setText(PersianNumberToWord.onWork(number, "ریال"));

            }
        });

        User user= new User();
        user.setName("asghar");
        user.setEmail("asghar@gmail.com");
        userDAO.saveUser(user);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
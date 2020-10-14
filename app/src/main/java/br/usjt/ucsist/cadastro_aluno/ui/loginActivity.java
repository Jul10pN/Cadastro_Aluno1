package br.usjt.ucsist.cadastro_aluno.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadastro_aluno.R;
import br.usjt.ucsist.cadastro_aluno.model.Usuario;
import br.usjt.ucsist.cadastro_aluno.model.UsuarioViewModel;

public class loginActivity extends AppCompatActivity {

    private TextView textViewNovoCadastro;
    private Button buttonLogin;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextEmail;
    private EditText editTextSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);

        textViewNovoCadastro = findViewById(R.id.textViewNovoCadastro);
        buttonLogin = findViewById(R.id.buttonLogin);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateUsuario(usuario);
            }
        });

    }

    private void updateUsuario(Usuario usuario) {
        this.usuarioCorrente = usuario;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(Hawk.contains("ten_cadastro")){
            if(Hawk.get("ten_cadastro")) {
                habilitarLogin();
            }else{
                desabilitarLogin();
            }
        }else{
            desabilitarLogin();
        }
    }

    private void habilitarLogin(){
        textViewNovoCadastro.setVisibility(View.GONE);
        buttonLogin.setEnabled(true);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void desabilitarLogin(){
        textViewNovoCadastro.setVisibility(View.VISIBLE);
        buttonLogin.setEnabled(false);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.cinza));
    }

    public void novoCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(usuarioCorrente != null) {
            String usuario = editTextEmail.getText().toString();
            String senha = editTextSenha.getText().toString();
            if (usuario.equalsIgnoreCase(usuarioCorrente.getEmail())
            && senha.equals(usuarioCorrente.getSenha())){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                editTextSenha.setText("");
            }else {
                Toast.makeText(this,"Usuário ou Senha Inválidos",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}
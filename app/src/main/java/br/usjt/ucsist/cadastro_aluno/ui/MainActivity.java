package br.usjt.ucsist.cadastro_aluno.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadastro_aluno.R;
import br.usjt.ucsist.cadastro_aluno.model.Usuario;
import br.usjt.ucsist.cadastro_aluno.model.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {


    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private TextView editTextNome;
    private TextView editTextEmail;
    private TextView editTextCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this).build();

        editTextNome = findViewById(R.id.textViewNomeL);
        editTextEmail = findViewById(R.id.textViewEmailL);
        editTextCurso = findViewById(R.id.textViewCurso);

        usuarioCorrente = new Usuario();

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });
    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            editTextNome.setText(usuario.getNome());
            editTextEmail.setText(usuario.getEmail());
            editTextCurso.setText(usuario.getCurso());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.editar:
                Intent intent = new Intent(this, CadastroActivity.class);
                startActivity(intent);
                return(true);
            case R.id.sair:
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }







}

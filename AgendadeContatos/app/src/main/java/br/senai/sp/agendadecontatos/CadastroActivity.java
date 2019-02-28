package br.senai.sp.agendadecontatos;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    private CadastroContatoHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(this);

        Intent intent = getIntent();

        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuCadastro = getMenuInflater();
        menuCadastro.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.salvar:
                Contato contato = helper.getContato();
                ContatoDAO dao = new ContatoDAO(this);

                if(helper.validar() == true) {
                    if (contato.getId() == 0) {

                        dao.salvar(contato);
                        dao.close();
                        finish();

                    } else {
                        dao.atualizar(contato);
                        dao.close();
                        finish();
                    }
                }


                break;

            case R.id.limpar:

                helper.limparCampos();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

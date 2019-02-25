package br.senai.sp.agendadecontatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContatos = findViewById(R.id.lista_contatos);
        btnCadastrar = findViewById(R.id.btn_cadastrar);



        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cadastrar = new Intent(MainActivity.this,CadastroActivity.class);

                startActivity(cadastrar);

            }
        });

        registerForContextMenu(listaContatos);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuContexto = getMenuInflater();
        menuContexto.inflate(R.menu.menu_context_lista_contatos, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ContatoDAO dao = new ContatoDAO(MainActivity.this);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);

        dao.excluir(contato);
        dao.close();
        carregarLista();

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        carregarLista();


        super.onResume();
    }


    public void carregarLista(){
        ContatoDAO dao = new ContatoDAO(this);

        List<Contato> contatos = dao.getContatos();
        dao.close();
        ArrayAdapter<Contato> listaContatosHelper= new ArrayAdapter<Contato>(this,android.R.layout.simple_list_item_1, contatos);

        listaContatos.setAdapter(listaContatosHelper);
    }

}

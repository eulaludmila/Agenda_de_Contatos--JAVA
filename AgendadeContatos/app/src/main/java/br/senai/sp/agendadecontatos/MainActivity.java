package br.senai.sp.agendadecontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private ImageButton btnCadastrar;
    private LinearLayout novoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContatos = findViewById(R.id.lista_contatos);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        novoContato = findViewById(R.id.novo_contato);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cadastrar = new Intent(MainActivity.this,CadastroActivity.class);
                novoContato.setVisibility(View.VISIBLE);
                startActivity(cadastrar);

            }
        });

        registerForContextMenu(listaContatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                Intent editar = new Intent(MainActivity.this, CadastroActivity.class);
                editar.putExtra("contato", contato);


                novoContato.setVisibility(View.INVISIBLE);
                startActivity(editar);


            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuContexto = getMenuInflater();
        menuContexto.inflate(R.menu.menu_context_lista_contatos, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ContatoDAO dao = new ContatoDAO(MainActivity.this);
        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Contato");
        builder.setMessage("Confirma a exclusão do contato " + contato.getNome() + " ?");
        builder.setIcon(R.drawable.ic_deletar);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.excluir(contato);
                        dao.close();
                        carregarLista();

                    }
        });

        builder.setNegativeButton("Não" , null);

        builder.create().show();





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

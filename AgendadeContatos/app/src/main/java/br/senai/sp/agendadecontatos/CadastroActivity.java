package br.senai.sp.agendadecontatos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroActivity extends AppCompatActivity {

    public static final int GALERIA_REQUEST = 1000;
    public static final int CAMERA_REQUEST = 2000;
    private CadastroContatoHelper helper;
    private LinearLayout novoContato;
    private LinearLayout atualizaContato;
    private Button btnCamera;
    private Button btnGaleria;
    private ImageView imgFoto;
    private String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCamera = findViewById(R.id.btn_camera);
        btnGaleria = findViewById(R.id.btn_galeria);
        imgFoto= findViewById(R.id.imgFoto);


        /*BOTAO GALERIA*/
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*INTEÇÃO DE ABRIR A GALERIA*/
                Intent abrirGaleria = new Intent(Intent.ACTION_GET_CONTENT);

                /*TIPOS DE IMAGENS QUE SERÃO PERMITIDAS*/
                abrirGaleria.setType("image/*");

                /*ABRIR A ACTIVITY PASSANDO UM PARÂMETRO*/
                startActivityForResult(abrirGaleria,GALERIA_REQUEST);


            }
        });


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*INTENÇÃO DE ABRIR A CAMERA*/
                Intent abrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                /*NOME DO ARQUIVO UTILIZANDO UM METODO PARA O NOME ALEATORIO*/
                String nomeArquivo = "/IMG_" + System.currentTimeMillis() + ".jpg";

                /*PASSANDO AO CAMINHO DA FOTO ONDE ESTARÁ ESSE ARQUIVO*/
                caminhoFoto = getExternalFilesDir(null) + nomeArquivo;

                /*OBJETO DO TIPO FILE QUE VAI GUARDAR O ARQUIVO*/
                File arquivoFoto = new File(caminhoFoto);

                /*USANDO O PROVIDER PARA TER A PERMISSÃO*/
                /*PASSANDO O CONTEXTO, PEGANDO O DONO DA APLICAÇÃO + PROVIDER E PEGANDO O ARQUIVO DA FOTO(O CAMINHO ONDE ELA ESTÁ)*/
                Uri fotoUri = FileProvider.getUriForFile(CadastroActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        arquivoFoto);

                /*PENDURAR A FOTO NO INTENT PARA TER A SAÍDA DA FOTO*/
                abrirCamera.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);/*FUNÇÃO DE ENVIAR A FOTO TIRADA PARA ONDE A INTENT FOR CHAMADA*/

                /*ABRIR A ACTIVITY CHAMANDO A INTENT E PASSANDO UM PARÂMETRO COMO IDENTIFICAÇÃO*/
                startActivityForResult(abrirCamera, CAMERA_REQUEST);


            }
        });







        helper = new CadastroContatoHelper(this);
        novoContato = findViewById(R.id.novo_contato);
        atualizaContato = findViewById(R.id.atualiza_contato);

        atualizaContato.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            novoContato.setVisibility(View.INVISIBLE);
            atualizaContato.setVisibility(View.VISIBLE);
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
                        Toast.makeText(this, contato.getNome() + " salvo com sucesso", Toast.LENGTH_LONG).show();
                        dao.close();
                        finish();

                    } else {
                        dao.atualizar(contato);
                        Toast.makeText(this, contato.getNome() + " atualizado com sucesso", Toast.LENGTH_LONG).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {

            if(resultCode == RESULT_OK) {

                if (requestCode == GALERIA_REQUEST) {

                    /*pegando a imagem da galeria decodificada*/
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());

                    Bitmap bitmapFactory = BitmapFactory.decodeStream(inputStream);

                    imgFoto.setImageBitmap(bitmapFactory);

                }

                if(requestCode == CAMERA_REQUEST){

                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300,300, true);

                    imgFoto.setImageBitmap(bitmapReduzido);

                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

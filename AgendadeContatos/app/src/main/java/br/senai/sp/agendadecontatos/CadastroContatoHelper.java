package br.senai.sp.agendadecontatos;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {
    //ESTA É A MINHA CLASSE
    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    private ImageView foto;

    private TextInputLayout layoutTxtNome;
    private TextInputLayout layoutTxtEndereco;
    private TextInputLayout layoutTxtTelefone;
    private TextInputLayout layoutTxtEmail;
    private TextInputLayout layoutTxtLinkedin;
    Contato contato;


    public CadastroContatoHelper(CadastroActivity activity){

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        txtEmail = activity.findViewById(R.id.txt_email);
        foto = activity.findViewById(R.id.imgFoto);
        layoutTxtNome = activity.findViewById(R.id.layout_nome);
        layoutTxtEndereco = activity.findViewById(R.id.layout_endereco);
        layoutTxtEmail = activity.findViewById(R.id.layout_email);
        layoutTxtTelefone = activity.findViewById(R.id.layout_telefone);
        layoutTxtLinkedin = activity.findViewById(R.id.layout_linkedin);
        layoutTxtLinkedin = activity.findViewById(R.id.layout_linkedin);

        contato = new Contato();
    }


    public Contato getContato (){

        contato.setNome(txtNome.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());


        Bitmap bm = ((BitmapDrawable)foto.getDrawable()).getBitmap();

        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bm,300,300,true);

        ByteArrayOutputStream saidaBytes = new ByteArrayOutputStream();

        bitmapReduzido.compress(Bitmap.CompressFormat.PNG,0,saidaBytes);

        byte[] fotoArray = saidaBytes.toByteArray();

        contato.setFoto(fotoArray);

        return contato;

    }


    public void preencherFormulario (Contato contato){

        txtNome.setText(contato.getNome());
        txtEmail.setText(contato.getEmail());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtLinkedin.setText(contato.getLinkedin());

        if(foto != null){

            /*Tranformando o array de bytes em imagem novamente*/
            foto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }

        this.contato = contato;
    }

    public boolean validar(){

        boolean validado = true;

        if(txtNome.getText().toString().isEmpty()){
            layoutTxtNome.setErrorEnabled(true);
            layoutTxtNome.setError("Por favor digite seu nome");
            validado = false;
        }else{
            layoutTxtNome.setErrorEnabled(false);
        }

        if(txtEndereco.getText().toString().isEmpty()){
            layoutTxtEndereco.setErrorEnabled(true);
            layoutTxtEndereco.setError("Por favor digite seu endereço");
            validado = false;
        }else{
            layoutTxtEndereco.setErrorEnabled(false);
        }

        if(txtTelefone.getText().toString().isEmpty()){
            layoutTxtTelefone.setErrorEnabled(true);
            layoutTxtTelefone.setError("Por favor digite seu telefone");
            validado = false;
        }else{
            layoutTxtTelefone.setErrorEnabled(false);
        }


        if(txtEmail.getText().toString().isEmpty()){
            layoutTxtEmail.setErrorEnabled(true);
            layoutTxtEmail.setError("Por favor digite seu telefone");
            validado = false;
        }else{
            layoutTxtTelefone.setErrorEnabled(false);
        }

        if(txtLinkedin.getText().toString().isEmpty()){
            layoutTxtLinkedin.setErrorEnabled(true);
            layoutTxtLinkedin.setError("Por favor digite o seu linkedin");
            validado = false;
        }else{

            layoutTxtLinkedin.setErrorEnabled(false);
        }
        return validado;
    }



    public void limparCampos(){

        txtNome.setText(null);
        txtEndereco.setText(null);
        txtTelefone.setText(null);
        txtEmail.setText(null);
        txtLinkedin.setText(null);
        foto.setImageDrawable(null);
        txtNome.requestFocus();
    }

}

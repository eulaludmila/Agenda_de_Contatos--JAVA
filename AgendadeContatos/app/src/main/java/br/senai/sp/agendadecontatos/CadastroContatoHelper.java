package br.senai.sp.agendadecontatos;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {
    //ESTA É A MINHA CLASSE
    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;

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

        return contato;

    }


    public void preencherFormulario (Contato contato){

        txtNome.setText(contato.getNome());
        txtEmail.setText(contato.getEmail());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtLinkedin.setText(contato.getLinkedin());
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
        txtNome.requestFocus();
    }

}

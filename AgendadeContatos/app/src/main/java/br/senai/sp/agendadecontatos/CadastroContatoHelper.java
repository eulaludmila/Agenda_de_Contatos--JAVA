package br.senai.sp.agendadecontatos;

import android.widget.EditText;

import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    Contato contato;


    public CadastroContatoHelper(CadastroActivity activity){

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        txtEmail = activity.findViewById(R.id.txt_email);
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


}

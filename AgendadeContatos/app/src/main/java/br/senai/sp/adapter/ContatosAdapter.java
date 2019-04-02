package br.senai.sp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.agendadecontatos.R;
import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Contato;

public class ContatosAdapter extends BaseAdapter {

    private Context contexto;
    private List<Contato> contatos;

    public  ContatosAdapter (Context contexto, List<Contato> contatos){

        this.contexto = contexto;
        this.contatos = contatos;

    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contato contato = contatos.get(position);

        LayoutInflater inflarLayout = LayoutInflater.from(contexto);

        View view = inflarLayout.inflate(R.layout.lista_contatos,null);

        TextView txtNomeContato = view.findViewById(R.id.txt_nome_contato);
        txtNomeContato.setText(contato.getNome());

        TextView txtTelefone = view.findViewById(R.id.txt_telefone_list);
        txtTelefone.setText(contato.getTelefone());


        /*Se algum contato que já estiver cadastrado não tem foto, o aplicativo vai abrir normalmente pois só entrará no if se tiver foto*/
        if(contato.getFoto() != null){

            ImageView imgFoto = view.findViewById(R.id.img_contato);
            imgFoto.setImageBitmap(Imagem.arrayToBitmap(contato.getFoto()));
        }




        return view;
    }
}

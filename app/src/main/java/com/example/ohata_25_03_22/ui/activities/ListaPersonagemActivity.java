package com.example.ohata_25_03_22.ui.activities;

import android.app.Activity;

import android.content.DiaLogInterface;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContentMenu;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ohata_25_03_22.R;
import com.example.ohata_25_03_22.dao.PersonagemDAO;
import com.example.ohata_25_03_22.model.Personagem;
import com.google.android.material.floatingactionbutton.floatingActionButton;

import static com.example.ohata_25_03_22.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity{
    public static final string TITULO_APPBAR = "Lista de Personagem";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }

    private void configuraFabNovoPersonagem(){
        floatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener()
        @Override
        public void onClick(View view) { abreFormulario();
        });
    }

    private void abreFormulario(){
        startActivity(new Intent(this, FormularioPersoagemActivity.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        atualizarPersonagem();
    }

    private void atualizarPersonagem(){
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover")
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.activity_main_lista_personagem_menu_remover){
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }
    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagem){
        listaDePersonagem.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido){
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersoagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    private void configuraAdapter(ListView listaDePersonagem){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagem.setAdapter(adapter);
    }
}

package com.example.ohata_25_03_22.ui.activities;

import static com.example.ohata_25_03_22.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaPersonagemActivity extends AppCompatActivity{
    public static final String TITULO_APPBAR = "Lista de Personagem";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    // Cria a activity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR); // Cria um titulo
        configuraFabNovoPersonagem(); // Cria novo personagem
        configuraLista();
    }

    // Prepara o metodo para abrir o formulario
   private void configuraFabNovoPersonagem(){
       FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
       botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {abreFormulario();}
       });
   }

   // abre um novo formulario
    private void abreFormulario() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    // Atualiza a pagina
    @Override
    protected void onResume(){
        super.onResume();
        atualizaPersonagem();
    }

    // Atualiza os personagens
    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    // Remove os personagens da lista
    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    // Cria um contexto novo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    // Case o contexto seja selecionado
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)    {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_main_lista_personagem_menu_remover){ // Caso seja o remover
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover") // Cria uma mensagem alertando o usuario
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() { // Se "sim", cria um novo dialog
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { // Deleta o personagem
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null) // Caso "nao", ele retorna
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    // Confirura lista
    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    // Confirura ao clicar
    private void configuraItemPorClique(ListView listaDePersonagens){
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    // Abre o formulario para editar o personagem
    private void abreFormularioEditar(Personagem personagemEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    // Atribui os valores ao array
    private void configuraAdapter(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}

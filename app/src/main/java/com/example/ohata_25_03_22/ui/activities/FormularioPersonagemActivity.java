package com.example.ohata_25_03_22.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ohata_25_03_22.R;
import com.example.ohata_25_03_22.dao.PersonagemDAO;
import com.example.ohata_25_03_22.model.Personagem;

import java.text.BreakIterator;

public class FormularioPersonagemActivity extends AppCompatActivity {
    // Declaracao de variaveis
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private static final String CHAVE_PERSONAGEM = "Chave Personagem";
    private EditText campoNome;
    private EditText campoNasmimento;
    private EditText campoAltura;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    // Cria o menu com as referencias do xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Cria um metodo que seleciona um item para finalizar o formulario
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    // Cria uma view do formulário
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        carregaPersonagem();
        checaPermissao();
    }

    private void checaPermissao() {
    }

    // Em caso de edicao ele carrega os dados de um personagem
    // Em caso de criacao de um personagem ele cria um novo
    private void carregaPersonagem(){
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_PERSONAGEM)){
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) ((Intent) dados).getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    // Vai abrir para que o usuario preencha os valores do personagem
    private void preencheCampos(){
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNasmimento.setText(personagem.getNascimento());
    }

    // Atribui as variaveis definidas pelo usuario
    private void preencherPersonagem(){
        String nome = campoNome.getText().toString();
        BreakIterator campoNascimento = null;
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
    }

    // se for um id valido ele pode editar ou salvar o personagem
    private void finalizarFormulario(){
        preencherPersonagem();
        if(personagem.IdValido()){
            dao.edita(personagem);
            finish();
        }else{
            dao.salva(personagem);
        }
    }

    // Pega os campos de texto e armazena seus dados dentro da variavel
    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.editText_nome);
        campoNasmimento = findViewById(R.id.editText_nascimento);
        campoAltura = findViewById(R.id.editText_altura);

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N.NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener((TextWatcher) mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        EditText campoNascimento = null;
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoAltura.addTextChangedListener((TextWatcher) mtwNascimento);
    }
}
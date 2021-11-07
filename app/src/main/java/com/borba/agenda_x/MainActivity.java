package com.borba.agenda_x;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nome, empresa, id, uf;
    String idaux = "";
    int ID_CONTATO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.edtNome);
        empresa = findViewById(R.id.edtEmpresa);
        uf = findViewById(R.id.edtEstado);
        id = findViewById(R.id.edtId);
    }

    public void pesquisarContato(View view) {
        findContact();
    }

    public int findContact() {
        DataBaseHelper dbh = new DataBaseHelper(this);

        idaux = id.getText().toString();

        if (idaux.equals("")) {
            Toast.makeText(this, "Coloque um código", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            ID_CONTATO = Integer.parseInt(idaux);
            Agenda a = dbh.getAgenda(ID_CONTATO);

            if (a.getNome().equals("")) {
                id.setText("");
                nome.setText("");
                empresa.setText("");
                uf.setText("");
                Toast.makeText(this, "Registro não localizado", Toast.LENGTH_SHORT).show();
                return 0;
            } else {
                try {
                    nome.setText(a.getNome());
                    empresa.setText(a.getEmpresa());
                    uf.setText(a.getUf());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erro ao buscar!", Toast.LENGTH_SHORT).show();

                }
                return ID_CONTATO;
            }
        }
    }

    public void salvarContato(View view) {
        idaux = id.getText().toString();

        if (idaux.equals("")) {
            Toast.makeText(this, "Coloque um código", Toast.LENGTH_LONG).show();

        } else {
            try {
                ID_CONTATO = Integer.parseInt(idaux);
                Agenda a = new Agenda();

                a.setNome(nome.getText().toString());
                a.setEmpresa((empresa.getText().toString()));
                a.setUf(uf.getText().toString());

                int achou = findContact();

                if (achou > 0) { //ACHOU CONTATO - ALTERA DADOS
                    DataBaseHelper helper = new DataBaseHelper(this);
                    helper.updateAgenda(a, ID_CONTATO);

                    Toast.makeText(this, a.getNome() + "foi ALTERADO copm sucesso", Toast.LENGTH_LONG).show();
                } else {// NOVO USUÁRIO INCLUI DADOS

                    DataBaseHelper helper = new DataBaseHelper(this);
                    helper.addAgenda(a);

                    Toast.makeText(this, a.getNome() + "foi adicionado com sucesso", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "erro ao adicionar!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void limparAgenda(View view) {
        DataBaseHelper helper = new DataBaseHelper(this);
        int i = helper.deleteAgenda();

        Toast.makeText(this, "deletou " + i + " contatos", Toast.LENGTH_SHORT).show();
    }
}
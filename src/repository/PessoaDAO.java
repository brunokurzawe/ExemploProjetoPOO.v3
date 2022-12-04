package repository;

import model.Pessoa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PessoaDAO implements IGenericDAO<Pessoa> {

    static List<Pessoa> pessoas = new ArrayList<>();


    @Override
    public void salvar(Pessoa pessoa) {
        PessoaRepository pessoaRepository = new PessoaRepository();
        try {
            if (pessoa.getId() != null) {
                pessoaRepository.update(pessoa);
            } else {
                pessoa.setId(pessoaRepository.proximoId().longValue());
                pessoaRepository.insere(pessoa);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        pessoas.add(pessoa);
    }

    @Override
    public void remover(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        PessoaRepository pessoaRepository = new PessoaRepository();
        pessoaRepository.delete(pessoa);
    }

    @Override
    public List<Pessoa> buscarTodos() {
        System.out.println(pessoas);

        PessoaRepository pessoaRepository = new PessoaRepository();
        try {
            pessoas = pessoaRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return pessoas;
    }

    @Override
    public List<Pessoa> buscarPorNome(String nome) {
        List<Pessoa> pessoasFiltradas = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getNome().contains(nome)) {
                pessoasFiltradas.add(pessoa);
            }
        }
        return pessoasFiltradas;
    }

    public Object[] findPessoasInArray() {
        List<Pessoa> pessoas = buscarTodos();
        List<String> pessoasNomes = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            pessoasNomes.add(pessoa.getNome());
        }

        return pessoasNomes.toArray();
    }

}

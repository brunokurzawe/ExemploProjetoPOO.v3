package repository;

import model.Seguro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SeguroDAO implements IGenericDAO<Seguro> {

    static List<Seguro> seguros = new ArrayList<>();

    @Override
    public void salvar(Seguro seguro) {
        SeguroRepository seguroRepository = new SeguroRepository();
        try {
            if (seguro.getId() != null) {
                seguroRepository.update(seguro);
            } else {
                seguro.setId(seguroRepository.proximoId().longValue());
                seguroRepository.insere(seguro);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        seguros.add(seguro);
    }

    @Override
    public void remover(Seguro seguro) throws SQLException, ClassNotFoundException {
        SeguroRepository seguroRepository = new SeguroRepository();
        seguroRepository.delete(seguro);
    }

    @Override
    public List<Seguro> buscarTodos() {
        SeguroRepository seguroRepository = new SeguroRepository();
        try {
            seguros = seguroRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return seguros;
    }

    @Override
    public List<Seguro> buscarPorNome(String nome) {
        List<Seguro> filtradas = new ArrayList<>();
        for (Seguro seguro : seguros) {
            if (seguro.getSegurado().getNome().contains(nome)) {
                filtradas.add(seguro);
            }
        }
        return filtradas;
    }

    public Object[] findSegurosInArray() {
        List<Seguro> seguros = buscarTodos();
        List<String> segurosNomes = new ArrayList<>();

        for (Seguro seguro : seguros) {
            segurosNomes.add(seguro.getSegurado().getNome());
        }

        return segurosNomes.toArray();
    }

}

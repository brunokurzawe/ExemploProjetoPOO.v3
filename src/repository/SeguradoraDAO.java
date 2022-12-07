package repository;

import model.Seguradora;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SeguradoraDAO implements IGenericDAO<Seguradora> {

    static List<Seguradora> seguradoras = new ArrayList<>();

    @Override
    public void salvar(Seguradora seguradora) {
        SeguradoraRepository seguradoraRepository = new SeguradoraRepository();
        try {
            if (seguradora.getId() != null) {
                seguradoraRepository.update(seguradora);
            } else {
                seguradora.setId(seguradoraRepository.proximoId().longValue());
                seguradoraRepository.insere(seguradora);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        seguradoras.add(seguradora);
    }

    @Override
    public void remover(Seguradora seguradora) throws SQLException, ClassNotFoundException {
        SeguradoraRepository seguradoraRepository = new SeguradoraRepository();
        seguradoraRepository.delete(seguradora);
    }

    @Override
    public List<Seguradora> buscarTodos() {
        SeguradoraRepository seguradoraRepository = new SeguradoraRepository();
        try {
            seguradoras = seguradoraRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return seguradoras;
    }

    @Override
    public List<Seguradora> buscarPorNome(String nome) {
        List<Seguradora> filtradas = new ArrayList<>();
        for (Seguradora seguradora : seguradoras) {
            if (seguradora.getNome().contains(nome)) {
                filtradas.add(seguradora);
            }
        }
        return filtradas;
    }

    public Object[] findSeguradoraInArray() {
        List<Seguradora> seguradoras = buscarTodos();
        List<String> seguradorasNomes = new ArrayList<>();

        for (Seguradora seguradora : seguradoras) {
            seguradorasNomes.add(seguradora.getNome());
        }

        return seguradorasNomes.toArray();
    }

}

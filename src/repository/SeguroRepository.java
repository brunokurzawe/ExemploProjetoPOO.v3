package repository;

import model.Seguradora;
import model.Seguro;
import model.TipoSeguro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroRepository {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/locadora";
        Connection connection = DriverManager.getConnection(url, "folha_owner", "");

        return connection;
    }


    public void insere(Seguro seguro) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into " +
                "seguros values(?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, seguro.getId().intValue());
        stmt.setInt(2, seguro.getSegurado().getId().intValue());
        stmt.setInt(3, seguro.getSeguradora().getId().intValue());
        stmt.setInt(4, seguro.getTipo().ordinal());
        stmt.setDouble(5, seguro.getValorApolice().doubleValue());
        stmt.setDouble(6, seguro.getValorPremio().doubleValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Seguro> buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Seguro> seguros = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from seguros WHERE id = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Seguro seguro = new Seguro();
            seguro.setId(resultSet.getLong(1));

            //FK
            PessoaRepository pessoaRepository = new PessoaRepository();
            seguro.setSegurado(pessoaRepository.buscaPorId(resultSet.getLong(2)).get(0));

            //FK
            SeguradoraRepository seguradoraRepository = new SeguradoraRepository();
            seguro.setSeguradora(seguradoraRepository.buscaPorId(resultSet.getLong(3)).get(0));

            seguro.setTipo(TipoSeguro.getTipoById(resultSet.getInt(4)));
            seguro.setValorApolice(resultSet.getBigDecimal(5));
            seguro.setValorPremio(resultSet.getBigDecimal(6));
            seguros.add(seguro);
        }
        connection.close();
        return seguros;
    }

    public List<Seguro> busca() throws SQLException, ClassNotFoundException {
        List<Seguro> seguros = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from seguros");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Seguro seguro = new Seguro();
            seguro.setId(resultSet.getLong(1));

            //FK
            PessoaRepository pessoaRepository = new PessoaRepository();
            seguro.setSegurado(pessoaRepository.buscaPorId(resultSet.getLong(2)).get(0));

            //FK
            SeguradoraRepository seguradoraRepository = new SeguradoraRepository();
            seguro.setSeguradora(seguradoraRepository.buscaPorId(resultSet.getLong(3)).get(0));

            seguro.setTipo(TipoSeguro.getTipoById(resultSet.getInt(4)));
            seguro.setValorApolice(resultSet.getBigDecimal(5));
            seguro.setValorPremio(resultSet.getBigDecimal(6));
            seguros.add(seguro);
        }
        connection.close();
        return seguros;
    }


    public Integer proximoId() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select max(id) from seguros");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        }
        return 1;
    }


    public void update(Seguro seguro) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("update seguros " +
                "SET segurado = ?, seguro = ?, tipo = ?, valor_apolice = ?, valor_premio = ? WHERE id = ?");
        stmt.setInt(1, seguro.getSegurado().getId().intValue());
        stmt.setInt(2, seguro.getSeguradora().getId().intValue());
        stmt.setInt(3, seguro.getTipo().ordinal());
        stmt.setBigDecimal(4, seguro.getValorApolice());
        stmt.setBigDecimal(5, seguro.getValorPremio());
        stmt.setInt(6, seguro.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas atualizadas");
        connection.close();
    }


    public void delete(Seguro seguro) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM seguros" +
                " WHERE id = ?");
        stmt.setInt(1, seguro.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

}

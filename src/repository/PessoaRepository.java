package repository;

import model.Pessoa;
import model.PessoaFisica;
import model.PessoaJuridica;
import model.TipoPessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepository {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/locadora";
        Connection connection = DriverManager.getConnection(url, "folha_owner", "");

        return connection;
    }


    public void insere(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into " +
                "pessoa values(?, ?, ?, ?)");
        stmt.setInt(1, pessoa.getId().intValue());
        stmt.setString(2, pessoa.getNome());
        stmt.setString(3, pessoa.getDocumento());
        stmt.setInt(4, pessoa.getTipo().ordinal());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Pessoa> buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Pessoa> pessoas = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from pessoa WHERE id = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(4) == 0) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSet.getLong(1));
                pessoaFisica.setNome(resultSet.getString(2));
                pessoaFisica.setCpf(resultSet.getString(3));
                pessoaFisica.setTipo(TipoPessoa.FISICA);
                pessoas.add(pessoaFisica);
            } else {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getLong(1));
                pessoaJuridica.setNome(resultSet.getString(2));
                pessoaJuridica.setCnpj(resultSet.getString(3));
                pessoaJuridica.setTipo(TipoPessoa.JURIDICA);
                pessoas.add(pessoaJuridica);
            }
        }
        connection.close();
        return pessoas;
    }

    public List<Pessoa> busca() throws SQLException, ClassNotFoundException {
        List<Pessoa> pessoas = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from pessoa");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getInt(4) == 0) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSet.getLong(1));
                pessoaFisica.setNome(resultSet.getString(2));
                pessoaFisica.setCpf(resultSet.getString(3));
                pessoaFisica.setTipo(TipoPessoa.FISICA);
                pessoas.add(pessoaFisica);
            } else {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getLong(1));
                pessoaJuridica.setNome(resultSet.getString(2));
                pessoaJuridica.setCnpj(resultSet.getString(3));
                pessoaJuridica.setTipo(TipoPessoa.JURIDICA);
                pessoas.add(pessoaJuridica);
            }
        }
        connection.close();
        return pessoas;
    }


    public Integer proximoId() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select max(id) from pessoa");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        }
        return 1;
    }


    public void update(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("update pessoa " +
                "SET nome = ?, documento = ? WHERE id = ?");
        stmt.setString(1, pessoa.getNome());
        stmt.setString(2, pessoa.getDocumento());
        stmt.setInt(3, pessoa.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas atualizadas");
        connection.close();
    }


    public void delete(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM pessoa" +
                " WHERE id = ?");
        stmt.setInt(1, pessoa.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

}

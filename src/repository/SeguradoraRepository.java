package repository;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguradoraRepository {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/locadora";
        Connection connection = DriverManager.getConnection(url, "folha_owner", "");

        return connection;
    }


    public void insere(Seguradora seguradora) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into " +
                "seguradoras values(?, ?, ?, ?)");
        stmt.setInt(1, seguradora.getId().intValue());
        stmt.setString(2, seguradora.getNome());
        stmt.setString(3, seguradora.getEndereco());
        stmt.setString(4, seguradora.getSite());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Seguradora> buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Seguradora> seguradoras = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from seguradoras WHERE id = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Seguradora seguradora = new Seguradora();
            seguradora.setId(resultSet.getLong(1));
            seguradora.setNome(resultSet.getString(2));
            seguradora.setEndereco(resultSet.getString(3));
            seguradora.setSite(resultSet.getString(4));
            seguradoras.add(seguradora);
        }
        connection.close();
        return seguradoras;
    }

    public List<Seguradora> busca() throws SQLException, ClassNotFoundException {
        List<Seguradora> seguradoras = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("select * from seguradoras");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Seguradora seguradora = new Seguradora();
            seguradora.setId(resultSet.getLong(1));
            seguradora.setNome(resultSet.getString(2));
            seguradora.setEndereco(resultSet.getString(3));
            seguradora.setSite(resultSet.getString(4));
            seguradoras.add(seguradora);
        }
        connection.close();
        return seguradoras;
    }


    public Integer proximoId() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select max(id) from seguradoras");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        }
        return 1;
    }


    public void update(Seguradora seguradora) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("update seguradoras " +
                "SET nome = ?, endereco = ?, site = ? WHERE id = ?");
        stmt.setString(1, seguradora.getNome());
        stmt.setString(2, seguradora.getEndereco());
        stmt.setString(2, seguradora.getSite());
        stmt.setInt(4, seguradora.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas atualizadas");
        connection.close();
    }


    public void delete(Seguradora seguradora) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM seguradoras" +
                " WHERE id = ?");
        stmt.setInt(1, seguradora.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

}

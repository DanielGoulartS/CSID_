package Cadastro;

import Model.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Daniel
 */
public class Porto {

    public String id, nome, telefone, email, rua, cidade, estado, pais;
    public int ddi, ddd, numero;

    public Porto(String id, String nome, int ddi, int ddd, String telefone, String email, String rua,
            int numero, String cidade, String estado, String pais) {
        this.id = id;
        this.nome = nome;
        this.ddi = ddi;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public boolean insert(Connection conexao, Porto porto) {
        boolean permit;
        permit = conexao.execute("INSERT INTO `portos`"
                + "(`nome`, `ddi`, `ddd`, `telefone`, `email`, `rua`, `numero`, `cidade`, `estado`, `pais`)"
                + " VALUES ("
                + "'" + porto.nome + "',"
                + "'" + porto.ddi + "',"
                + "'" + porto.ddd + "',"
                + "'" + porto.telefone + "',"
                + "'" + porto.email + "',"
                + "'" + porto.rua + "',"
                + "'" + porto.numero + "',"
                + "'" + porto.cidade + "',"
                + "'" + porto.estado + "',"
                + "'" + porto.pais + "');");

        return permit;
    }

    public boolean delete(Connection conexao, Porto porto) {

        boolean result = false;

        if (conexao.execute("DELETE FROM portos WHERE "
                + "`id` LIKE '%" + porto.id 
                + "%' AND `nome` LIKE '%" + porto.nome
                + "%' AND `ddi` LIKE '%" + porto.ddi
                + "%' AND `ddd` LIKE '%" + porto.ddd
                + "%' AND `telefone` LIKE '%" + porto.telefone
                + "%' AND `email` LIKE '%" + porto.email
                + "%' AND `rua` LIKE '%" + porto.rua
                + "%' AND `numero` LIKE '%" + porto.numero
                + "%' AND `cidade` LIKE '%" + porto.cidade
                + "%' AND `estado` LIKE '%" + porto.estado
                + "%' AND `pais` LIKE '%" + porto.pais + "%';")) {
            result = true;
        }


        return result;
    }

    public ResultSet select(Connection conexao, Porto porto){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM portos WHERE "
                + "`id` LIKE '%" + porto.id 
                + "%' AND `nome` LIKE '%" + porto.nome
                + "%' AND `ddi` LIKE '%" + ""
                + "%' AND `ddd` LIKE '%" + ""
                + "%' AND `telefone` LIKE '%" + porto.telefone
                + "%' AND `email` LIKE '%" + porto.email
                + "%' AND `rua` LIKE '%" + porto.rua
                + "%' AND `numero` LIKE '%" + ""
                + "%' AND `cidade` LIKE '%" + porto.cidade
                + "%' AND `estado` LIKE '%" + porto.estado
                + "%' AND `pais` LIKE '%" + porto.pais + "%' ORDER BY `id` ASC;");

        return rs;
    }

    public ResultSet selectPorId(Connection conexao, Porto porto){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM portos WHERE `id` = '" + porto.id + "';");

        return rs;
    }

    public ResultSet selectSolicitadoPorId(Connection conexao, Porto porto){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM solicitacao WHERE `porto` = '" + porto.id + "';");

        return rs;
    }

}
package Cadastro;

import Model.Connection;
import Solicitacoes.Solicitacao;
import java.sql.ResultSet;

/**
 *
 * @author Daniel
 */
public class Servico {

    public String id, nome, descricao;
    public int solicitacao;

    public Servico(String id, String nome, String descricao, int solicitacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.solicitacao = solicitacao;
    }

    //Cadastro
    public boolean insert(Connection conexao, Servico servico) {
        boolean permit;
        permit = conexao.execute("INSERT INTO `servicos`(`nome`, `descricao`)"
                + " VALUES ('" + servico.nome + "','" + servico.descricao + "');");


        return permit;
    }

    public boolean delete(Connection conexao, Servico servico) {

        boolean result = false;

        if (conexao.execute("DELETE FROM servicos WHERE "
                + "`id` LIKE '%" + servico.id
                + "%' AND `nome` LIKE '%" + servico.nome
                + "%' AND `descricao` LIKE '%" + servico.descricao + "%';")) {
            result = true;
        }


        return result;
    }

    public ResultSet select(Connection conexao, Servico servico) {
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM servicos WHERE "
                + "`id` LIKE '%" + servico.id
                + "%' AND `nome` LIKE '%" + servico.nome
                + "%' AND `descricao` LIKE '%" + servico.descricao + "%' ORDER BY `id` ASC;");

        return rs;
    }

    public ResultSet selectPorId(Connection conexao, Servico servico) {
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM servicos WHERE `id` = '" + servico.id + "' ORDER BY `id` ASC;");

        return rs;
    }


    //Solicitacao
    public boolean insertServicoSolicitado(Connection conexao, Servico servico, Solicitacao solicitacao) {
        boolean permit;
        permit = conexao.execute("INSERT INTO `servicos_solicitados` (`solicitacao`, `id`)"
                + " VALUES ('" + solicitacao.id + "','" + servico.id + "');");


        return permit;
    }

    public boolean deleteSolicitacao(Connection conexao, Servico servico, Solicitacao solicitacao) {

        boolean result = false;

        if (conexao.execute("DELETE FROM servicos_solicitados WHERE " + "`solicitacao` = '" + servico.id + "';")) {
            result = true;
        }

        return result;
    }

    public ResultSet selectPorSolicitacao(Connection conexao, Solicitacao solicitacao) {
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM servicos_solicitados WHERE `solicitacao` = '" + solicitacao.id + "' ;");

        return rs;
    }

    public ResultSet selectSolicitadoPorId(Connection conexao, Servico servico) {
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM servicos_solicitados WHERE `id` = '" + servico.id + "' ;");

        return rs;
    }

}

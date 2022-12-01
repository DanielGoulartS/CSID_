package Cadastro;

import Model.Connection;
import Solicitacoes.Solicitacao;
import java.sql.ResultSet;

/**
 *
 * @author Daniel
 */
public class Equipamento {

    public int  quantidade, solicitacao;
    public String id, nome;

    public Equipamento(String id, String nome, int quantidade, int solicitacao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.solicitacao = solicitacao;
    }

    //Cadastro
    public boolean insert(Connection conexao, Equipamento equipamento) {
        boolean permit;
        permit = conexao.execute("INSERT INTO `equipamentos`(`nome`, `quantidade`)"
                + " VALUES ('" + equipamento.nome + "','" + equipamento.quantidade + "');");

        return permit;
    }

    public boolean delete(Connection conexao, Equipamento equipamento) {
        boolean result = false;

        if (conexao.execute("DELETE FROM equipamentos WHERE `id` = '" + equipamento.id + "';")) {
            result = true;
        }

        return result;
    }

    public ResultSet select(Connection conexao, Equipamento equipamento) {

        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM equipamentos WHERE "
                + "`id` LIKE '%" + equipamento.id
                + "%' AND `nome` LIKE '%" + equipamento.nome + "%' ORDER BY `id` ASC;");

        return rs;
    }

    public ResultSet selectDisponiveis(Connection conexao, Equipamento equipamento) {

        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM equipamentos WHERE `id` LIKE '%" + equipamento.id
                + "%' AND `nome` LIKE '%" + equipamento.nome + "%' AND `quantidade` > 0 ORDER BY `id` ASC;");

        return rs;
    }
   
    public ResultSet selectPorId(Connection conexao, Equipamento equipamento) {

        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM equipamentos WHERE "
                + "`id` = '" + equipamento.id + "';");

        return rs;
    }

    public boolean updateQuantidadePorId(Connection conexao, Equipamento equipamento) {
        boolean result;

        result = conexao.execute("UPDATE `equipamentos` SET `quantidade` = '" + equipamento.quantidade + "' WHERE "
                + "`id` = '" + equipamento.id + "';");

        return result;
    }

    //Solicitacao
    public boolean insertEquipamentoSolicitado(Connection conexao, Equipamento equipamento, Solicitacao solicitacao) {
        boolean permit;
        permit = conexao.execute("INSERT INTO `equipamentos_solicitados`(`solicitacao`, `id`, `quantidade`)"
                + " VALUES ('"+ solicitacao.id + "','" + equipamento.id + "','" + equipamento.quantidade + "');");

        return permit;
    }
    
    public boolean deleteEquipamentoSolicitado(Connection conexao, Solicitacao solicitacao) {
        boolean result = false;

        if (conexao.execute("DELETE FROM equipamentos_solicitados WHERE "
                + " `solicitacao` = '" + solicitacao.id + "' ;")) {
            result = true;
        }

        return result;
    }
    
    public ResultSet selectPorSolicitacao(Connection conexao, Solicitacao solicitacao) {

        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM equipamentos_solicitados WHERE "
                + "`solicitacao` = '" + solicitacao.id + "' ORDER BY `id` ASC;");

        return rs;
    }
    
    public ResultSet selectSolicitadoPorId(Connection conexao, Equipamento equipamento) {

        ResultSet rs;

        rs = conexao.executeQuery("SELECT `solicitacao` FROM equipamentos_solicitados WHERE "
                + "`id` = '" + equipamento.id + "' ORDER BY `id` ASC;");

        return rs;
    }

}
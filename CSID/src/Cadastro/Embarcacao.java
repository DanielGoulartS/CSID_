package Cadastro;

import Model.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Daniel
 */
public class Embarcacao {
    
    public String id, nome, numero;
    
    public Embarcacao(String id, String nome, String numero){
        this.id = id;
        this.nome = nome;
        this.numero = numero;
    }
    
    public boolean insert(Connection conexao, Embarcacao embarcacao){
        boolean permit;
        permit = conexao.execute("INSERT INTO `navios`( `nome`, `numero`) VALUES"
                + " ( '" + embarcacao.nome + "','" + embarcacao.numero + "');");


        return permit;
    }
    
    public boolean delete(Connection conexao, Embarcacao embarcacao){
        boolean result = false;

        if (conexao.execute("DELETE FROM navios WHERE "
                + "`id` LIKE '%" + embarcacao.id 
                + "%' AND `nome` LIKE '%" + embarcacao.nome
                + "%' AND `numero` LIKE '%" + embarcacao.numero + "%';")) {
            result = true;
        }

        return result;
    }
    
    public ResultSet select(Connection conexao, Embarcacao embarcacao){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM navios WHERE "
                + "`id` LIKE '%" + embarcacao.id 
                + "%' AND `nome` LIKE '%" + embarcacao.nome
                + "%' AND `numero` LIKE '%" + embarcacao.numero + "%' ORDER BY `id` ASC;");

        return rs;
    }
    
    public ResultSet selectPorId(Connection conexao, Embarcacao embarcacao){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM navios WHERE `id` = '" + embarcacao.id + "';");

        return rs;
    }
    
    public ResultSet selectSolicitadaPorId(Connection conexao, Embarcacao embarcacao){
        ResultSet rs;

        rs = conexao.executeQuery("SELECT * FROM solicitacao WHERE `embarcacao` = '" + embarcacao.id + "';");

        return rs;
    }
    
}

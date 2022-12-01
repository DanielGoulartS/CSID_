package Solicitacoes;

import Cadastro.Usuario;
import Model.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Daniel
 */
public class Solicitacao {

    //Variaveis
    public Date inicio, fim;
    public int id, encarregado, solicitante;
    public String embarcacao, porto, obs;

    //Consruções do Objeto
    public Solicitacao(int id, int encarregado, String inicio, String fim, int solicitante, String embarcacao,
            String porto, String itemUrgente) {

        this.id = id;
        this.encarregado = encarregado;
        this.inicio = stringToDate(inicio);
        this.fim = stringToDate(fim);
        this.solicitante = solicitante;
        this.embarcacao = embarcacao;
        this.porto = porto;
        this.obs = itemUrgente;
    }

    //Impressão do objeto
    @Override
    public String toString() {
        return dateToString(this.inicio) + "\n"
                + dateToString(this.fim) + "\n"
                + this.embarcacao + "\n"
                + this.porto + "\n"
                + this.obs;
    }

    //Converte a String recebida em Date
    public final Date stringToDate(String data) {
        Date data1 = new Date();
        try {
            SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
            data1 = sdt.parse(data);
        } catch (ParseException ex) {
            System.err.println("Falha na conversão: CSID.Classes.Solicitacao.formatarData()" + ex);
        }
        return data1;
    }

    //Converte a Date recebida em String
    public String dateToString(Date data) {
        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        String data1 = sdt.format(data);
        return data1;
    }

    public boolean insert(Connection conexao, Solicitacao solicitacao) {
        boolean result;

        result = true;
        conexao.execute("INSERT INTO `solicitacao`( `inicio`, `fim`, `encarregado`, "
                + "`solicitante`, `embarcacao`, `porto`, `obs`)"
                + " VALUES ('" + dateToString(solicitacao.inicio) + "','" + dateToString(solicitacao.fim) + "','"
                + solicitacao.encarregado + "','" + solicitacao.solicitante + "','" + solicitacao.embarcacao + "','"
                + solicitacao.porto + "','" + solicitacao.obs + "');");

        return result;
    }

    public boolean delete(Connection conexao, Solicitacao solicitacao) {
        boolean result = true;
        conexao.execute("DELETE FROM solicitacao WHERE `id` = "
                + solicitacao.id + ";");
        return result;
    }

    public ResultSet selectAll(Connection conexao) {
        //Painel Meus Serviços
        ResultSet rs = conexao.executeQuery("SELECT * FROM solicitacao ORDER BY `id`;");
        return rs;
    }

    public ResultSet selectPorId(Connection conexao, Solicitacao solicitacao) {
        //Painel Meus Serviços
        ResultSet rs = conexao.executeQuery("SELECT * FROM solicitacao WHERE `id` = " + solicitacao.id + " ;");
        return rs;
    }

    public ResultSet selectPorSolicitante(Connection conexao, Usuario usuario) {
        //Painel Meus Serviços
        ResultSet rs = conexao.executeQuery("SELECT * FROM solicitacao WHERE solicitante = "
                + usuario.id + ";");
        return rs;
    }

    public ResultSet selectPorEncarregado(Connection conexao, Usuario usuario) {
        //Painel Meus Serviços
        ResultSet rs = conexao.executeQuery("SELECT * FROM solicitacao WHERE encarregado = "
                + usuario.id + ";");
        return rs;
    }
    
    public ResultSet selectMaxIdPorUsuario(Connection conexao, Usuario usuario){
        //Painel Meus Serviços
        ResultSet rs = conexao.executeQuery("SELECT MAX(id) FROM solicitacao WHERE `solicitante` = "
                + usuario.id + " ;");
        return rs;
    }
   
    public static boolean updateEquipamento(Connection conexao, String equipamentoId) {
        boolean result = true;
        conexao.execute("UPDATE `solicitacao` SET `quantidade`= 0 WHERE `id` = " + equipamentoId + ";");
        return result;
    }
   
    public boolean updateAtribuir(Connection conexao, Usuario usuario, Solicitacao solicitacao) {
        boolean result = true;
        conexao.execute("UPDATE solicitacao SET encarregado = " + usuario.id
                    + " WHERE id = " + solicitacao.id + " AND encarregado = 0;");
        return result;
    }
   
    public boolean updateDesatribuir(Connection conexao, Solicitacao solicitacao) {
        boolean result = true;
        conexao.execute("UPDATE solicitacao SET encarregado = 0 WHERE id = '" + solicitacao.id + "'"
                    + " AND encarregado = '" + solicitacao.encarregado + "';");
        return result;
    }

}

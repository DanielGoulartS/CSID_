package Cadastro;

import Solicitacoes.JanelaSolicitar;
import Solicitacoes.JanelaTodasAsSolicitacoes;
import Solicitacoes.Solicitacao;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Daniel
 */
public class Tecnico extends Usuario {


    public Tecnico(int id, String nome, String sobrenome, String email, String usuario, char[] senha, String cargo) {
        super(id, nome, sobrenome, email, usuario, senha, cargo);
    }

    @Override
    public void exibir(Janela janela) {

        janela.exibirInterfaceTecnico();

    }
    
    @Override
    public void verMeusServicos(JanelaTodasAsSolicitacoes jTAS) {
        jTAS.conexao.conectar();

        jTAS.painelSolicitacoes.removeAll();
        jTAS.painelMeusServicos.removeAll();
        jTAS.painelMinhasSolicitacoes.removeAll();
        //Busca as solicitacoes
        ResultSet rs = jTAS.solicitacao.selectPorEncarregado(jTAS.conexao, jTAS.usuario);
        try {
            //Se houver ao menos uma
            if (rs.next()) {
                //Para cada uma exibe-a num painel alocado na scroll principal
                do {
                    Solicitacao novaSolicitacao = new Solicitacao(rs.getInt("id"), rs.getInt("encarregado"),
                            rs.getString("inicio"), rs.getString("fim"), rs.getInt("solicitante"),
                            rs.getString("embarcacao"), rs.getString("porto"), rs.getString("obs"));

                    jTAS.painelMeusServicos.add(jTAS.novoPainel(novaSolicitacao));
                } while (rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger("Usuario.verMeusServicos()" + ex);
        }
        //exibe o scrollpane
        jTAS.scrollPane.setViewportView(jTAS.painelMeusServicos);
        jTAS.conexao.desconectar();
    }

    @Override
    public ActionListener aceitarSolicitacao(JLabel labelEncarregado, Solicitacao solicitacao, JButton este,
            JanelaTodasAsSolicitacoes jTAS) {
        return (ActionEvent e) -> {

            jTAS.conexao.conectar();

            try {

                if (solicitacao.encarregado == 0) { //Se a solicitacao n??o tiver Encarregado, atribua-se
                    atribuir(labelEncarregado, solicitacao, este, jTAS);
                } else { //Caso seja voc??, desatribua-se
                    desatribuir(labelEncarregado, solicitacao, este, jTAS);
                }

            } catch (SQLException ex) {
                System.err.println("Erro ao interagir com a solicita????o, View.Principal.aceitarSolicitacao().");
                System.err.println(ex);

                JOptionPane.showMessageDialog(jTAS.janela, "A Solicita????o n??o p??de "
                        + "ser apropriadamente atualizada, talvez ja tenha sido atribuida a outro usu??rio."
                        + "\nAtualize o feed, ou procure pela Solicita????o usando o ID da mesma.",
                        "Falha na Intera????o", JOptionPane.ERROR_MESSAGE);
            }

            jTAS.conexao.desconectar();
        };
    }

    @Override
    public void atribuir(JLabel labelEncarregado, Solicitacao solicitacao, JButton este,
            JanelaTodasAsSolicitacoes jTAS) throws SQLException {
        //0 = YES, 1 = NO
        int permit = JOptionPane.showConfirmDialog(jTAS.janela, "Uma vez atribuida a solicita????o,"
                + " o cliente (solicitante) "
                + "\nN??O RECEBER?? uma notifica????o desta atividade. "
                + "\nDeseja continuar?",
                "Atribuir Solicita????o", JOptionPane.YES_NO_OPTION);

        if (permit == 0) {

            //Atualiza o encarregado na base de dados usando o id do usuario logado e da solicitacao
            solicitacao.updateAtribuir(jTAS.conexao, jTAS.usuario, solicitacao);

            //Busca o usuario pelo id, e atribui seu nome na solicitacao e na interface gr??fica
            ResultSet rs = jTAS.conexao.executeQuery("SELECT usuario FROM usuarios WHERE id = " + jTAS.usuario.id + ";");
            rs.next();
            String nomeUsuario = rs.getString("usuario");
            solicitacao.encarregado = jTAS.usuario.id;
            labelEncarregado.setText("Encarregado: " + nomeUsuario);
            este.setText("Desatribuir");

            JOptionPane.showMessageDialog(jTAS.janela, "Solicita????o Atribuida. "
                    + "\nEsta solicita????o ainda pode ser vista por todos nesta listagem,"
                    + " mas agora voc?? ter?? acesso a ela atrav??s do menu Servi??os > Meus Servi??os.",
                    "Solicita????o Atribuida", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void desatribuir(JLabel labelEncarregado, Solicitacao solicitacao, JButton este,
            JanelaTodasAsSolicitacoes jTAS) throws SQLException {

        //0 = YES, 1 = NO
        int permit = JOptionPane.showConfirmDialog(jTAS.janela, "Uma vez desatribuida a solicita????o,"
                + " o cliente (solicitante) "
                + "\nN??O RECEBER?? uma notifica????o desta atividade. "
                + "\nDeseja continuar?",
                "Desatribuir Solicita????o", JOptionPane.YES_NO_OPTION);

        if (permit == 0) {
            //Atualiza o encarregado para 0 na base de dados.
            solicitacao.updateDesatribuir(jTAS.conexao, solicitacao);

            solicitacao.encarregado = 0;
            labelEncarregado.setText("Encarregado: ");
            este.setText("Atribuir");

            JOptionPane.showMessageDialog(jTAS.janela, "Solicita????o Desatribuida. "
                    + "\nVoc?? n??o ver?? mais esta solicita????o em seus Servi??os.",
                    "Solicita????o Desatribuida", JOptionPane.PLAIN_MESSAGE);
        }

    }
}
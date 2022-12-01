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

                if (solicitacao.encarregado == 0) { //Se a solicitacao não tiver Encarregado, atribua-se
                    atribuir(labelEncarregado, solicitacao, este, jTAS);
                } else { //Caso seja você, desatribua-se
                    desatribuir(labelEncarregado, solicitacao, este, jTAS);
                }

            } catch (SQLException ex) {
                System.err.println("Erro ao interagir com a solicitação, View.Principal.aceitarSolicitacao().");
                System.err.println(ex);

                JOptionPane.showMessageDialog(jTAS.janela, "A Solicitação não pôde "
                        + "ser apropriadamente atualizada, talvez ja tenha sido atribuida a outro usuário."
                        + "\nAtualize o feed, ou procure pela Solicitação usando o ID da mesma.",
                        "Falha na Interação", JOptionPane.ERROR_MESSAGE);
            }

            jTAS.conexao.desconectar();
        };
    }

    @Override
    public void atribuir(JLabel labelEncarregado, Solicitacao solicitacao, JButton este,
            JanelaTodasAsSolicitacoes jTAS) throws SQLException {
        //0 = YES, 1 = NO
        int permit = JOptionPane.showConfirmDialog(jTAS.janela, "Uma vez atribuida a solicitação,"
                + " o cliente (solicitante) "
                + "\nNÃO RECEBERÁ uma notificação desta atividade. "
                + "\nDeseja continuar?",
                "Atribuir Solicitação", JOptionPane.YES_NO_OPTION);

        if (permit == 0) {

            //Atualiza o encarregado na base de dados usando o id do usuario logado e da solicitacao
            solicitacao.updateAtribuir(jTAS.conexao, jTAS.usuario, solicitacao);

            //Busca o usuario pelo id, e atribui seu nome na solicitacao e na interface gráfica
            ResultSet rs = jTAS.conexao.executeQuery("SELECT usuario FROM usuarios WHERE id = " + jTAS.usuario.id + ";");
            rs.next();
            String nomeUsuario = rs.getString("usuario");
            solicitacao.encarregado = jTAS.usuario.id;
            labelEncarregado.setText("Encarregado: " + nomeUsuario);
            este.setText("Desatribuir");

            JOptionPane.showMessageDialog(jTAS.janela, "Solicitação Atribuida. "
                    + "\nEsta solicitação ainda pode ser vista por todos nesta listagem,"
                    + " mas agora você terá acesso a ela através do menu Serviços > Meus Serviços.",
                    "Solicitação Atribuida", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void desatribuir(JLabel labelEncarregado, Solicitacao solicitacao, JButton este,
            JanelaTodasAsSolicitacoes jTAS) throws SQLException {

        //0 = YES, 1 = NO
        int permit = JOptionPane.showConfirmDialog(jTAS.janela, "Uma vez desatribuida a solicitação,"
                + " o cliente (solicitante) "
                + "\nNÃO RECEBERÁ uma notificação desta atividade. "
                + "\nDeseja continuar?",
                "Desatribuir Solicitação", JOptionPane.YES_NO_OPTION);

        if (permit == 0) {
            //Atualiza o encarregado para 0 na base de dados.
            solicitacao.updateDesatribuir(jTAS.conexao, solicitacao);

            solicitacao.encarregado = 0;
            labelEncarregado.setText("Encarregado: ");
            este.setText("Atribuir");

            JOptionPane.showMessageDialog(jTAS.janela, "Solicitação Desatribuida. "
                    + "\nVocê não verá mais esta solicitação em seus Serviços.",
                    "Solicitação Desatribuida", JOptionPane.PLAIN_MESSAGE);
        }

    }
}
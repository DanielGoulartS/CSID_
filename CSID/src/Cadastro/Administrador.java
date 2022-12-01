package Cadastro;

import Solicitacoes.JanelaTodasAsSolicitacoes;
import Solicitacoes.Solicitacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class Administrador extends Usuario {

    public Administrador(int id, String nome, String sobrenome, String email, String usuario, char[] senha, String cargo)
    {
        super(id, nome, sobrenome, email, usuario, senha, cargo);
    }

    public static ActionListener excluirUsuario(JanelaCadastrarUsuarios jCU) {
        return (ActionEvent e) -> {

            jCU.conexao.conectar();

            //0 = YES, 1 = NO
            int permition = JOptionPane.showConfirmDialog(jCU.janela, "Confirme os dados, e principalmente o ID."
                    + "\nUma vez executada, esta ação não poderá ser desfeita."
                    + "\nDeseja continuar?",
                    "Excluir Usuário", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (permition == 0) {
                //Monta o usuário para Excluir
                jCU.usuario = new Comandante(Integer.parseInt(jCU.tfId.getText()),
                        jCU.tfNome.getText(), jCU.tfSobrenome.getText(), jCU.tfEmail.getText(),
                        jCU.tfUsuario.getText().replaceAll(" ", ""), jCU.pfSenha.getPassword(),
                        jCU.cbCargo.getSelectedItem().toString());

                                        
            //Verifica se já está em uso e oferece opção de cancelar
            ResultSet rsSolicitacoes = jCU.usuario.selectSolicitadoPorId(jCU.conexao, jCU.usuario);
            String solicitacoes = "";
            
            try {
                while(rsSolicitacoes.next()){
                    solicitacoes += rsSolicitacoes.getString("id") + ", "; //Id da Solicitacao
                }
            } catch (SQLException ex) {
                 System.err.println(Administrador.class.getName() + " " + ex);
            }
            if(!rsSolicitacoes.equals("")){
                //0 = YES, 1 = NO
                int permit = JOptionPane.showConfirmDialog(jCU.janela, "Este item está presente nas solicitações:\n"
                        + solicitacoes + "\n e ao executar esta ação será impossível identifica-lo nas mesmas.\n"
                        + "Deseja Prosseguir mesmo assim?\n"
                        + "(Se prosseguir, é recomendavel avisar os solicitantes e responsáveis das"
                        + "solicitações mencionadas acima.)\n",
                        "Deseja Prosseguir?", JOptionPane.YES_NO_OPTION);
                if(permit == 1){
                    return;
                }
            }
            
                
                //Busca o usuário pelo nome de usuário
                ResultSet rs = jCU.usuario.selectPorUsuario(jCU.conexao, jCU.usuario);

                //Se obtiver o resultado, obviamente unico por conta do cadastro
                try {

                    if (rs.next()) {
                        jCU.usuario.delete(jCU.conexao, jCU.janela, jCU.usuario);
                    }

                } catch (SQLException ex) {
                    System.err.println("\n\nExceção em Cadastro.Administrador.excluirUsuario();\n\n" + ex);
                }
            }

            jCU.conexao.desconectar();
        };
    }

    public static ActionListener cadastrarEmbarcacoes(JanelaCadastrarEmbarcacoes jCE) {
        return (ActionEvent e) -> {
            jCE.conexao.conectar();

            //Monta o objeto para cadastrar Embarcacao
            jCE.embarcacao = new Embarcacao(jCE.tfId.getText(), jCE.tfNome.getText(), 
                    jCE.tfNumero.getText());

            //Busca-o na base de dados
            ResultSet rs = jCE.embarcacao.select(jCE.conexao, jCE.embarcacao);
            try {

                if (rs.next()) {
                    //Se encontra-lo não cadastra, e avisa
                    JOptionPane.showMessageDialog(jCE.janela, "Já consta uma embarcação com mesmo nome."
                            + " \nConfira, ou tente outro nome.");
                } else {
                    //Se não encontra-lo cadastra-o
                    JOptionPane.showMessageDialog(jCE.janela, "O id será escolhido pelo Sistema.");
                    jCE.embarcacao.insert(jCE.conexao, jCE.embarcacao);
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarEmbarcacoes()\n\n.");
            }
            jCE.conexao.desconectar();
        };
    }

    public static ActionListener excluirEmbarcacoes(JanelaCadastrarEmbarcacoes jCE) {
        return (ActionEvent e) -> {
            jCE.conexao.conectar();

            //Monta o objeto para excluirUsuario
            jCE.embarcacao = new Embarcacao(jCE.tfId.getText(), jCE.tfNome.getText().replaceAll(" ", ""), 
                    jCE.tfNumero.getText());

                        
            //Verifica se já está em uso e oferece opção de cancelar
            ResultSet rsSolicitacoes = jCE.embarcacao.selectSolicitadaPorId(jCE.conexao, jCE.embarcacao);
            String solicitacoes = "";
            
            try {
                while(rsSolicitacoes.next()){
                    solicitacoes += rsSolicitacoes.getString("id") + ", "; //Id da Solicitacao
                }
            } catch (SQLException ex) {
                 System.err.println(Administrador.class.getName() + " " + ex);
            }
            if(!rsSolicitacoes.equals("")){
                //0 = YES, 1 = NO
                int permit = JOptionPane.showConfirmDialog(jCE.janela, "Este item está presente nas solicitações:\n"
                        + solicitacoes + "\n e ao executar esta ação será impossível identifica-lo nas mesmas.\n"
                        + "Deseja Prosseguir mesmo assim?\n"
                        + "(Se prosseguir, é recomendavel avisar os solicitantes e responsáveis das"
                        + "solicitações mencionadas acima.)\n",
                        "Deseja Prosseguir?", JOptionPane.YES_NO_OPTION);
                if(permit == 1){
                    return;
                }
            }
            
                        
            //Busca-o na base de dados
            ResultSet rs = jCE.embarcacao.select(jCE.conexao, jCE.embarcacao);
            try {

                if (rs.next()) {
                    //Se for primeiro e ultimo resultado (evita exclusão em massa)
                    if (rs.isLast()) {
                        //Ao encontra-lo excluirá
                        JOptionPane.showMessageDialog(jCE.janela, "Embarcação Excluída.");
                        jCE.embarcacao.delete(jCE.conexao, jCE.embarcacao);
                        jCE.limparFormulario();
                    } else {
                        JOptionPane.showMessageDialog(jCE.janela, "Confira se as informações inseridas estão idênticas.");
                    }
                } else {
                    //Se não encontra-lo parará e avisará o usuário
                    JOptionPane.showMessageDialog(jCE.janela, "Confira todas as informações da Embarcação.");
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarEmbarcacoes()\n\n.");
            }
            jCE.conexao.desconectar();
        };
    }

    public static ActionListener cadastrarPortos(JanelaCadastrarPortos jCP) {
        return (ActionEvent e) -> {
            
            //Se algum campo for null, cancela e pede preenchimento
            if( jCP.tfNome.getText().equals("") || jCP.tfDdi.getText().equals("") ||
                    jCP.tfDdd.getText().equals("") || jCP.tfTelefone.getText().equals("") ||
                    jCP.tfEmail.getText().equals("") || jCP.tfRua.getText().equals("") || 
                    jCP.tfNumero.getText().equals("") ||
                    jCP.tfCidade.getText().equals("") || jCP.tfEstado.getText().equals("") ||
                    jCP.tfPais.getText().equals("")){
                JOptionPane.showMessageDialog(jCP.janela, "Preencha precisamente todos os campos.");
                return;
            }
            
            jCP.conexao.conectar();
            
            //Monta o objeto para cadastrarPorto
            jCP.porto = new Porto("", jCP.tfNome.getText(), Integer.parseInt(jCP.tfDdi.getText()),
                    Integer.parseInt(jCP.tfDdd.getText()), jCP.tfTelefone.getText(),
                    jCP.tfEmail.getText(), jCP.tfRua.getText(), Integer.parseInt(jCP.tfNumero.getText()),
                    jCP.tfCidade.getText(), jCP.tfEstado.getText(),jCP.tfPais.getText());

            //Busca-o na base de dados
            ResultSet rs = jCP.porto.select(jCP.conexao, jCP.porto);
            try {

                if (rs.next()) {
                    //Se encontra-lo não cadastra, e avisa
                    JOptionPane.showMessageDialog(jCP.janela, "Já consta um porto com mesmo nome, confira-o, ou tente outro nome.");
                } else {
                    //Se não encontra-lo cadastra-o
                    JOptionPane.showMessageDialog(jCP.janela, "O id será escolhido pelo Sistema.");
                    jCP.porto.insert(jCP.conexao, jCP.porto);
                    jCP.limparFormulario();
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarPorto()\n\n.");
            }
            jCP.conexao.desconectar();
        };
    }

    public static ActionListener excluirPortos(JanelaCadastrarPortos jCP) {
        return (ActionEvent e) -> {
            //Se algum campo for null, cancela e pede preenchimento
            if( jCP.tfNome.getText().equals("") || jCP.tfDdi.getText().equals("") ||
                    jCP.tfDdd.getText().equals("") || jCP.tfTelefone.getText().equals("") ||
                    jCP.tfEmail.getText().equals("") || jCP.tfRua.getText().equals("") || 
                    jCP.tfNumero.getText().equals("") ||
                    jCP.tfCidade.getText().equals("") || jCP.tfEstado.getText().equals("") ||
                    jCP.tfPais.getText().equals("")){
                JOptionPane.showMessageDialog(jCP.janela, "Preencha precisamente todos os campos.");
                return;
            }
            
            jCP.conexao.conectar();

            //Monta o objeto para excluir Porto
            jCP.porto = new Porto(jCP.tfId.getText(), jCP.tfNome.getText(), Integer.parseInt(jCP.tfDdi.getText()),
                    Integer.parseInt(jCP.tfDdd.getText()), jCP.tfTelefone.getText(),
                    jCP.tfEmail.getText(), jCP.tfRua.getText(), Integer.parseInt(jCP.tfNumero.getText()),
                    jCP.tfCidade.getText(), jCP.tfEstado.getText(),jCP.tfPais.getText());
            
            //Verifica se já está em uso e oferece opção de cancelar
            ResultSet rsSolicitacoes = jCP.porto.selectSolicitadoPorId(jCP.conexao, jCP.porto);
            String solicitacoes = "";
            
            try {
                while(rsSolicitacoes.next()){
                    solicitacoes += rsSolicitacoes.getString("id") + ", "; //Id da Solicitacao
                }
            } catch (SQLException ex) {
                 System.err.println(Administrador.class.getName() + " " + ex);
            }
            if(!rsSolicitacoes.equals("")){
                //0 = YES, 1 = NO
                int permit = JOptionPane.showConfirmDialog(jCP.janela, "Este item está presente nas solicitações:\n"
                        + solicitacoes + "\n e ao executar esta ação será impossível identifica-lo nas mesmas.\n"
                        + "Deseja Prosseguir mesmo assim?\n"
                        + "(Se prosseguir, é recomendavel avisar os solicitantes e responsáveis das"
                        + "solicitações mencionadas acima.)\n",
                        "Deseja Prosseguir?", JOptionPane.YES_NO_OPTION);
                if(permit == 1){
                    return;
                }
            }
            

            //Busca-o na base de dados
            ResultSet rs = jCP.porto.select(jCP.conexao, jCP.porto);
            try {

                if (rs.next()) {
                    //Se for primeiro e ultimo resultado (evita exclusão em massa)
                    if (rs.isLast()) {
                        //Ao encontra-lo excluirá
                        JOptionPane.showMessageDialog(jCP.janela, "Porto Excluído.");
                        jCP.porto.delete(jCP.conexao, jCP.porto);
                        jCP.limparFormulario();
                    } else {
                        JOptionPane.showMessageDialog(jCP.janela, "Confira se as informações inseridas estão idênticas.");
                    }
                } else {
                    //Se não encontra-lo parará e avisará o usuário
                    JOptionPane.showMessageDialog(jCP.janela, "Confira todas as informações do Porto.");
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarPorto()\n\n.");
            }
            jCP.conexao.desconectar();
        };
    }

    public static ActionListener cadastrarServicos(JanelaCadastrarServicos jCS) {
        return (ActionEvent e) -> {
            jCS.conexao.conectar();

            //Monta o objeto para cadastrar Servico
            jCS.servico = new Servico("", jCS.tfNome.getText(), jCS.tfDescricao.getText(),0);

            //Busca-o na base de dados
            ResultSet rs = jCS.servico.select(jCS.conexao, jCS.servico);
            try {

                if (rs.next()) {
                    //Se encontra-lo não cadastra, e avisa
                    JOptionPane.showMessageDialog(jCS.janela, "Já consta um serviço com mesmo nome, confira-o,"
                            + " ou tente outro nome.");
                } else {
                    //Se não encontra-lo cadastra-o
                    JOptionPane.showMessageDialog(jCS.janela, "O id será escolhido pelo Sistema.");
                    jCS.servico.insert(jCS.conexao, jCS.servico);
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarServico()\n\n.");
            }
            jCS.conexao.desconectar();
        };
    }

    public static ActionListener excluirServicos(JanelaCadastrarServicos jCS) {
        return (ActionEvent e) -> {
            jCS.conexao.conectar();

            //Monta o objeto para excluir Servico
            jCS.servico = new Servico(jCS.tfId.getText(), jCS.tfNome.getText(), jCS.tfDescricao.getText(),0);
            
            //Verifica se já está em uso e oferece opção de cancelar
            ResultSet rsSolicitacoes = jCS.servico.selectSolicitadoPorId(jCS.conexao, jCS.servico);
            String solicitacoes = "";
            
            try {
                while(rsSolicitacoes.next()){
                    solicitacoes += rsSolicitacoes.getString("solicitacao") + ", ";
                }
            } catch (SQLException ex) {
                 System.err.println(Administrador.class.getName() + " " + ex);
            }
            if(!rsSolicitacoes.equals("")){
                //0 = YES, 1 = NO
                int permit = JOptionPane.showConfirmDialog(jCS.janela, "Este item está presente nas solicitações:\n"
                        + solicitacoes + "\n e ao executar esta ação será impossível identifica-lo nas mesmas.\n"
                        + "Deseja Prosseguir mesmo assim?\n"
                        + "(Se prosseguir, é recomendavel avisar os solicitantes e responsáveis das"
                        + "solicitações mencionadas acima.)\n",
                        "Deseja Prosseguir?", JOptionPane.YES_NO_OPTION);
                if(permit == 1){
                    return;
                }
            }
            
            //Busca-o na base de dados
            ResultSet rs = jCS.servico.select(jCS.conexao, jCS.servico);
            try {

                if (rs.next()) {
                    //Se for primeiro e ultimo resultado (evita exclusão em massa)
                    if (rs.isLast()) {
                        //Ao encontra-lo excluirá
                        JOptionPane.showMessageDialog(jCS.janela, "Serviço Excluído.");
                        jCS.servico.delete(jCS.conexao, jCS.servico);
                        jCS.limparFormulario();
                    } else {
                        JOptionPane.showMessageDialog(jCS.janela, "Confira se as informações inseridas estão idênticas.");
                    }
                } else {
                    //Se não encontra-lo parará e avisará o usuário
                    JOptionPane.showMessageDialog(jCS.janela, "Confira todas as informações do Serviço.");
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarServico()\n\n.");
            }
            jCS.conexao.desconectar();
        };
    }

    public static ActionListener cadastrarEquipamentos(JanelaCadastrarEquipamentos jCEq) {
        return (ActionEvent e) -> {
            //Pede todos os campos caso não estejam preenchidos
            if (jCEq.tfId.getText().equals("") || jCEq.tfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(jCEq.janela, "Preencha todos os campos devidamente");
                return;
            }

            jCEq.conexao.conectar();

            //Monta o objeto para cadastrar Equipamento
            jCEq.equipamento = new Equipamento("", jCEq.tfNome.getText(),
                    jCEq.cbQuantidade.getSelectedIndex(), 0 );

            //Busca-o na base de dados
            ResultSet rs = jCEq.equipamento.select(jCEq.conexao, jCEq.equipamento);
            try {

                if (rs.next()) {
                    //Se encontra-lo não cadastra, mas

                    if (rs.isLast()) {
                        //Se for unico resultado, soma a nova quantidade e avisa (Evita acrescimos paralelos)
                        
                        //Soma a quantidade para registrá-la
                        int quantiaDb = Integer.valueOf(rs.getString("quantidade"));
                        int quantiaLocal = jCEq.equipamento.quantidade;
                        jCEq.equipamento.quantidade = quantiaDb + quantiaLocal;

                        jCEq.equipamento.updateQuantidadePorId(jCEq.conexao, jCEq.equipamento);
                        JOptionPane.showMessageDialog(jCEq.janela, "Já consta um equipamento com mesmo nome, "
                                + "os itens foram somados.");
                        jCEq.limparFormulario();
                    } else {
                        //Se encontrar muitos itens com estes atributos, avisa e pede correção.
                        JOptionPane.showMessageDialog(jCEq.janela, "Constam muitos equipamentos com estes dados,"
                                + "seja mais específico.");
                    }

                } else {
                    //Se não encontra-lo cadastra-o
                    JOptionPane.showMessageDialog(jCEq.janela, "Novo equipamento cadastrado,"
                            + "\nO id será escolhido pelo Sistema.");
                    jCEq.equipamento.insert(jCEq.conexao, jCEq.equipamento);
                    jCEq.limparFormulario();
                }

            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.cadastrarEquipamentos()\n\n.");
            }
            jCEq.conexao.desconectar();
        };
    }

    public static ActionListener excluirEquipamentos(JanelaCadastrarEquipamentos jCEq) {
        return (ActionEvent e) -> {

            //Pede todos os campos caso não estejam preenchidos
            if (jCEq.tfId.getText().equals("") || jCEq.tfNome.getText().equals("")) {
                JOptionPane.showMessageDialog(jCEq.janela, "Preencha todos os campos devidamente");
                return;
            }

            jCEq.conexao.conectar();

            //Monta o objeto para excluir o Equipamento
            jCEq.equipamento = new Equipamento(jCEq.tfId.getText(), 
                    jCEq.tfNome.getText(), jCEq.cbQuantidade.getSelectedIndex(), 0);

            //Verifica se já está em uso e oferece opção de cancelar
            ResultSet rsSolicitacoes = jCEq.equipamento.selectSolicitadoPorId(jCEq.conexao, jCEq.equipamento);
            String solicitacoes = "";
            
            try {
                while(rsSolicitacoes.next()){
                    solicitacoes += rsSolicitacoes.getString("solicitacao") + ", ";
                }
            } catch (SQLException ex) {
                 System.err.println(Administrador.class.getName() + " " + ex);
            }
            if(!rsSolicitacoes.equals("")){
                //0 = YES, 1 = NO
                int permit = JOptionPane.showConfirmDialog(jCEq.janela, "Este item está presente nas solicitações:\n"
                        + solicitacoes + "\n e ao executar esta ação será impossível identifica-lo nas mesmas.\n"
                        + "Deseja Prosseguir mesmo assim?\n"
                        + "(Se prosseguir, é recomendavel avisar os solicitantes e responsáveis das"
                        + "solicitações mencionadas acima.)\n",
                        "Deseja Prosseguir?", JOptionPane.YES_NO_OPTION);
                if(permit == 1){
                    return;
                }
            }
            
            //Busca-o na base de dados
            ResultSet rs = jCEq.equipamento.select(jCEq.conexao, jCEq.equipamento);

            try {
                if (rs.next()) {
                    //Se encontra-lo

                    if (rs.isLast()) {
                        //E for unico resultado

                        //Subtrai a quantia necessária para atualizar a base de dados
                        int quantiaDb = Integer.parseInt(rs.getString("quantidade"));
                        int quantiaLocal = jCEq.equipamento.quantidade;
                        jCEq.equipamento.quantidade = quantiaDb - quantiaLocal;

                        if (jCEq.equipamento.quantidade < 0) {

                            //Se a quantia negativar, apaga-o e remove-o das solicitações que está
                            jCEq.equipamento.delete(jCEq.conexao, jCEq.equipamento);
                            Solicitacao.updateEquipamento(jCEq.conexao, jCEq.equipamento.id);
                            JOptionPane.showMessageDialog(jCEq.janela, "Equipamento Completamente Excluido.");
                        } else {
                            //Subtrai-o
                            jCEq.equipamento.updateQuantidadePorId(jCEq.conexao, jCEq.equipamento);
                            JOptionPane.showMessageDialog(jCEq.janela, "Equipamento Subtraído.");
                        }

                        jCEq.limparFormulario();
                    } else {
                        //Se encontrar muitos, avisa e cancela
                        JOptionPane.showMessageDialog(jCEq.janela, "Confira se as informações inseridas estão idênticas.");
                    }
                } else {
                    //Se não encontra-lo parará e avisará o usuário
                    JOptionPane.showMessageDialog(jCEq.janela, "Confira todas as informações do Equipamento.");
                }
            } catch (SQLException ex) {
                System.err.println("\n\n1-Exceção em Cadastro.Administrador.excluirEquipamentos()\n\n.");
            }
            jCEq.conexao.desconectar();
        };
    }

    @Override
    public void exibir(Janela janela) {
        janela.exibirInterfaceAdministrador();

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
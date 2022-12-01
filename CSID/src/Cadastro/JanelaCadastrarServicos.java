package Cadastro;

import Model.Connection;
import Solicitacoes.JanelaSolicitar;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Daniel
 */
public class JanelaCadastrarServicos implements Janela {

    public Usuario usuario;
    public Servico servico;
    public Connection conexao;

    public JFrame janela;
    public JPanel painel, painelEsq1, painelEsqForm, painelEsqBotoes, painelLista;
    public JScrollPane scrollPanel;
    public JLabel lbId, lbNome, lbDescricao;
    public JTextField tfId, tfNome, tfDescricao;
    public JButton btCadastrar, btExcluir;

    public JanelaCadastrarServicos(Usuario usuario) {
        //Constrrução do Usuário
        this.usuario = usuario;

        //Construção da Conexão
        conexao = new Connection();

        //Formatação da Interface
        janela = new JFrame();
        janela.setTitle("Cadastrar Serviços");
        janela.setBounds(new Rectangle(720, 500));
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setResizable(false);

        painel = new JPanel(new GridLayout(0,2));
        painelEsq1 = new JPanel(new BorderLayout());
        painelEsqForm = new JPanel(new GridLayout(0, 2));
        painelEsqBotoes = new JPanel();
        painelLista = new JPanel(new GridLayout(0, 1));
        scrollPanel = new JScrollPane();
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        lbId = new JLabel("Id: ");
        lbNome = new JLabel("Nome: ");
        lbDescricao = new JLabel("Descrição: ");

        tfId = new JTextField();
        tfNome = new JTextField();
        tfDescricao = new JTextField();

    }

    
    
    @Override
    public void limparFormulario() {
        tfId.setText("");
        tfNome.setText("");
        tfDescricao.setText("");
    }

    @Override
    public boolean exibirInterfaceComandante() {
        //Adiciona painel de Formulário
        painelEsqForm.add(lbId);
        painelEsqForm.add(tfId);
        painelEsqForm.add(lbNome);
        painelEsqForm.add(tfNome);
        painelEsqForm.add(lbDescricao);
        painelEsqForm.add(tfDescricao);

        tfId.addKeyListener(usuario.pesquisaDinamicaServicos(this));
        tfNome.addKeyListener(usuario.pesquisaDinamicaServicos(this));
        tfDescricao.addKeyListener(usuario.pesquisaDinamicaServicos(this));
        
        tfId.addKeyListener(JanelaSolicitar.dataListener(tfId));
        tfNome.addKeyListener(JanelaSolicitar.listener(tfNome, 40));
        tfDescricao.addKeyListener(JanelaSolicitar.listener(tfDescricao, 150));

        scrollPanel.setViewportView(painelLista);

        painelEsq1.add(painelEsqForm, BorderLayout.CENTER);
        painel.add(painelEsq1);
        painel.add(scrollPanel);

        janela.add(painel);

        janela.setVisible(true);
        return true;

    }

    @Override
    public boolean exibirInterfaceTecnico() {
        exibirInterfaceComandante();
        return true;
    }

    @Override
    public boolean exibirInterfaceAdministrador() {
        //Adiciona as opções de cadastro e exclusao
        painelEsq1.add(painelEsqBotoes, BorderLayout.SOUTH);

        btCadastrar = new JButton("Cadastrar");
        btExcluir = new JButton("Excluir");

        painelEsqBotoes.add(btCadastrar);
        painelEsqBotoes.add(btExcluir);

        btCadastrar.addActionListener(Administrador.cadastrarServicos(this));
        btExcluir.addActionListener(Administrador.excluirServicos(this));

        exibirInterfaceTecnico();
        return true;
    }
}

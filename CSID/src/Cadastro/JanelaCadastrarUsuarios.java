package Cadastro;

import Model.Connection;
import Solicitacoes.JanelaSolicitar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Daniel
 */
public final class JanelaCadastrarUsuarios implements Janela {

    //Instanciação de Váriáveis
    public Connection conexao;
    public Usuario usuario;
    public JFrame janela;
    public JPanel painel, painelEsquerdo, painelEsquerdo1, painelEsquerdo2, painelLista;
    public JScrollPane scrollPane;
    public JLabel lbId, lbNome, lbSobrenome, lbEmail, lbUsuario, lbSenha, lbConfirmarSenha, lbCargo;
    public JTextField tfId, tfNome, tfSobrenome, tfEmail, tfUsuario;
    public JPasswordField pfSenha, pfConfirmarSenha;
    public JComboBox cbCargo;
    public JButton btCadastrar, btExcluir;

    public JanelaCadastrarUsuarios(Usuario usuario) {
        //Constrrução do Usuário
        this.usuario = usuario;
        
        //Construção da Conexão
        conexao = new Connection();

        //Formatação da Interface
        janela = new JFrame();
        janela.setBounds(new Rectangle(720, 500));
        janela.setTitle("Cadastrar Usuários");
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setResizable(false);
        
        painel = new JPanel(new GridLayout(0, 2));

        //Painel Formulário de Usuário
        painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo1 = new JPanel(new GridLayout(0, 2));
        painelEsquerdo2 = new JPanel(new FlowLayout());
        
        lbId = new JLabel("ID: ");
        lbNome = new JLabel("Nome: ");
        lbSobrenome = new JLabel("Sobrenome: ");
        lbEmail = new JLabel("Email: ");
        lbUsuario = new JLabel("Usuário: ");
        lbSenha = new JLabel("Senha: ");
        lbConfirmarSenha = new JLabel("Confirmar Senha: ");
        lbCargo = new JLabel("Cargo: ");
        
        tfId = new JTextField();
        tfNome = new JTextField();
        tfSobrenome = new JTextField();
        tfEmail = new JTextField();
        tfUsuario = new JTextField();
        pfSenha = new JPasswordField();
        pfConfirmarSenha = new JPasswordField();
        

        tfId.addKeyListener(JanelaSolicitar.dataListener(tfId));
        tfId.addKeyListener(JanelaSolicitar.listener(tfId, 40));
        tfUsuario.addKeyListener(JanelaSolicitar.listener(tfUsuario, 40));
        tfNome.addKeyListener(JanelaSolicitar.listener(tfNome, 40));
        tfSobrenome.addKeyListener(JanelaSolicitar.listener(tfSobrenome, 40));
        tfEmail.addKeyListener(JanelaSolicitar.listener(tfEmail, 40));
        pfSenha.addKeyListener(JanelaSolicitar.listener(pfSenha, 40));
        pfConfirmarSenha.addKeyListener(JanelaSolicitar.listener(pfConfirmarSenha, 40));
        
        
        //Exibição
        janela.add(painel);
    }


    public JPanel novoPainel(String id, String usuario, String nome, String sobrenome, String cargo) {
        //Formata Elemento
        JPanel novo = new JPanel(new BorderLayout(2, 2));
        novo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));//)Border(5, 2, 5, 2));
        JPanel novo2 = new JPanel(new GridLayout(2, 3));
        JLabel lbIdPesquisa = new JLabel(id);
        JLabel lbUsuarioPesquisa = new JLabel(usuario);
        JLabel lbNomePesquisa = new JLabel(nome);
        JLabel lbSobrenomePesquisa = new JLabel(sobrenome);
        JLabel lbCargoPesquisa = new JLabel(cargo);

        novo2.add(lbIdPesquisa);
        novo2.add(lbUsuarioPesquisa);
        novo2.add(new JLabel());
        novo2.add(lbNomePesquisa);
        novo2.add(lbSobrenomePesquisa);
        novo2.add(lbCargoPesquisa);

        novo.add(novo2);

        novo.setPreferredSize(new Dimension(10, 10));

        return novo;
    }

    @Override
    public void limparFormulario() {
        tfId.setText("");
        tfNome.setText("");
        tfSobrenome.setText("");
        tfEmail.setText("");
        tfUsuario.setText("");
        pfSenha.setText("");
        pfConfirmarSenha.setText("");
    }

    public boolean exibirInterfaceExterna() {
        
        //Construção de itens ainda não criados
        
        cbCargo = new JComboBox();
        
        btCadastrar = new JButton("Cadastrar");

        
        //Completar formulário de usuário
        painelEsquerdo1.add(lbId);
        painelEsquerdo1.add(tfId);
        painelEsquerdo1.add(lbNome);
        painelEsquerdo1.add(tfNome);
        painelEsquerdo1.add(lbSobrenome);
        painelEsquerdo1.add(tfSobrenome);
        painelEsquerdo1.add(lbEmail);
        painelEsquerdo1.add(tfEmail);
        painelEsquerdo1.add(lbUsuario);
        painelEsquerdo1.add(tfUsuario);
        painelEsquerdo1.add(lbSenha);
        painelEsquerdo1.add(pfSenha);
        painelEsquerdo1.add(lbConfirmarSenha);
        painelEsquerdo1.add(pfConfirmarSenha);
        painelEsquerdo1.add(lbCargo);
        painelEsquerdo1.add(cbCargo);
        
        cbCargo.addItem("Administrador");
        cbCargo.addItem("Técnico");
        cbCargo.addItem("Comandante de Embarcação");
        

        //Botões de cadastro
        btCadastrar.addActionListener(usuario.cadastrarUsuario(this));
        painelEsquerdo2.add(btCadastrar);
        painelEsquerdo.add(painelEsquerdo2, BorderLayout.SOUTH);

        //Painel de Pesquisa
        painelEsquerdo.add(painelEsquerdo1, BorderLayout.CENTER);
        painelEsquerdo.add(painelEsquerdo2, BorderLayout.SOUTH);

        //Painel de Usuarios
        painel.add(painelEsquerdo);

        //Geral
        janela.setVisible(true);
        return true;
    }

    @Override
    public boolean exibirInterfaceComandante() {

        //Exibição da Interface Gráfica
        //Painel Usuários
        painelLista = new JPanel(new GridLayout(0, 1));
        scrollPane = new JScrollPane();
        
        scrollPane.setViewportView(painelLista);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Painel de Formulário
        painelEsquerdo1.add(lbId);
        painelEsquerdo1.add(tfId);
        painelEsquerdo1.add(lbNome);
        painelEsquerdo1.add(tfNome);
        painelEsquerdo1.add(lbSobrenome);
        painelEsquerdo1.add(tfSobrenome);
        painelEsquerdo1.add(lbEmail);
        painelEsquerdo1.add(tfEmail);
        painelEsquerdo1.add(lbUsuario);
        painelEsquerdo1.add(tfUsuario);
        
        
        //Funcionalidades
        tfId.addKeyListener(usuario.pesquisaDinamicaUsuarios(this));
        tfUsuario.addKeyListener(usuario.pesquisaDinamicaUsuarios(this));
        tfNome.addKeyListener(usuario.pesquisaDinamicaUsuarios(this));
        tfSobrenome.addKeyListener(usuario.pesquisaDinamicaUsuarios(this));
        tfEmail.addKeyListener(usuario.pesquisaDinamicaUsuarios(this));
        
        //Painel de pesquisa
        painelEsquerdo.add(painelEsquerdo1, BorderLayout.CENTER);
        painelEsquerdo.add(painelEsquerdo2, BorderLayout.SOUTH);
        
        //Painel de Usuarios
        painel.add(painelEsquerdo);
        painel.add(scrollPane);

        //Geral
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
        
        exibirInterfaceTecnico();
        
        //Construção de Itens
        
        cbCargo = new JComboBox();
        
        btCadastrar = new JButton("Cadastrar");
        btExcluir = new JButton("Excluir");
        
        
        //Completar formulário de usuário
        painelEsquerdo1.add(lbSenha);
        painelEsquerdo1.add(pfSenha);
        painelEsquerdo1.add(lbConfirmarSenha);
        painelEsquerdo1.add(pfConfirmarSenha);
        painelEsquerdo1.add(lbCargo);
        painelEsquerdo1.add(cbCargo);

        cbCargo.addItem("Administrador");
        cbCargo.addItem("Técnico");
        cbCargo.addItem("Comandante de Embarcação");
        
        
        //Botões de cadastro
        btCadastrar.addActionListener(usuario.cadastrarUsuario(this));
        btExcluir.addActionListener(Administrador.excluirUsuario(this));
        painelEsquerdo2.add(btCadastrar);
        painelEsquerdo2.add(btExcluir);

        //Geral
        return true;
    }

}
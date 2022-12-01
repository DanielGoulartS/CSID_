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
public class JanelaCadastrarPortos implements Janela {

    public Usuario usuario;
    public Porto porto;
    public Connection conexao;

    public JFrame janela;
    public JPanel painel, painelEsq1, painelEsqForm, painelEsqBotoes, painelLista;
    public JScrollPane scrollPanel;
    public JLabel lbId, lbNome, lbDdi, lbDdd, lbTelefone, lbEmail, lbRua, lbNumero, lbCidade, lbEstado, lbPais;
    public JTextField tfId, tfNome, tfDdi, tfDdd, tfTelefone, tfEmail, tfRua, tfNumero, tfCidade, tfEstado, tfPais;
    public JButton btCadastrar, btExcluir;

    public JanelaCadastrarPortos(Usuario usuario) {
        //Constrrução do Usuário
        this.usuario = usuario;

        //Construção da Conexão
        conexao = new Connection();

        //Formatação da Interface
        janela = new JFrame();
        janela.setTitle("Cadastrar Portos");
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

        lbId = new JLabel("*Id: ");
        lbNome = new JLabel("*Nome: ");
        lbDdi = new JLabel("Ddi: ");
        lbDdd = new JLabel("Ddd: ");
        lbTelefone = new JLabel("*Telefone: ");
        lbEmail = new JLabel("*E-mail: ");
        lbRua = new JLabel("*Rua: ");
        lbNumero = new JLabel("Número: ");
        lbCidade = new JLabel("*Cidade: ");
        lbEstado = new JLabel("*Estado: ");
        lbPais = new JLabel("*Pais: ");

        tfId = new JTextField();
        tfNome = new JTextField();
        tfDdi = new JTextField();
        tfDdd = new JTextField();
        tfTelefone = new JTextField();
        tfEmail = new JTextField();
        tfRua = new JTextField();
        tfNumero = new JTextField();
        tfCidade = new JTextField();
        tfEstado = new JTextField();
        tfPais = new JTextField();

    }

    public void limparFormulario() {
        tfId.setText("");
        tfNome.setText("");
        tfDdi.setText("");
        tfDdd.setText("");
        tfTelefone.setText("");
        tfEmail.setText("");
        tfRua.setText("");
        tfNumero.setText("");
        tfCidade.setText("");
        tfEstado.setText("");
        tfPais.setText("");
    }

    @Override
    public boolean exibirInterfaceComandante() {
        //Adiciona painel de Formulário
        painelEsqForm.add(lbId);
        painelEsqForm.add(tfId);
        painelEsqForm.add(lbNome);
        painelEsqForm.add(tfNome);
        painelEsqForm.add(lbDdi);
        painelEsqForm.add(tfDdi);
        painelEsqForm.add(lbDdd);
        painelEsqForm.add(tfDdd);
        painelEsqForm.add(lbTelefone);
        painelEsqForm.add(tfTelefone);
        painelEsqForm.add(lbNome);
        painelEsqForm.add(tfNome);
        painelEsqForm.add(lbEmail);
        painelEsqForm.add(tfEmail);
        painelEsqForm.add(lbRua);
        painelEsqForm.add(tfRua);
        painelEsqForm.add(lbNumero);
        painelEsqForm.add(tfNumero);
        painelEsqForm.add(lbCidade);
        painelEsqForm.add(tfCidade);
        painelEsqForm.add(lbEstado);
        painelEsqForm.add(tfEstado);
        painelEsqForm.add(lbPais);
        painelEsqForm.add(tfPais);

        tfId.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfNome.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfDdi.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfDdd.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfTelefone.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfEmail.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfRua.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfNumero.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfCidade.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfEstado.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        tfPais.addKeyListener(usuario.pesquisaDinamicaPortos(this));
        
        tfId.addKeyListener(JanelaSolicitar.dataListener(tfId));
        tfNome.addKeyListener(JanelaSolicitar.listener(tfNome, 40));
        tfDdi.addKeyListener(JanelaSolicitar.dataListener(tfDdi));
        tfDdd.addKeyListener(JanelaSolicitar.dataListener(tfDdd));
        tfTelefone.addKeyListener(JanelaSolicitar.listener(tfTelefone, 40));
        tfEmail.addKeyListener(JanelaSolicitar.listener(tfEmail, 100));
        tfRua.addKeyListener(JanelaSolicitar.listener(tfRua, 20));
        tfNumero.addKeyListener(JanelaSolicitar.dataListener(tfNumero));
        tfCidade.addKeyListener(JanelaSolicitar.listener(tfCidade, 20));
        tfEstado.addKeyListener(JanelaSolicitar.listener(tfEstado, 20));
        tfPais.addKeyListener(JanelaSolicitar.listener(tfPais, 20));
        

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
        exibirInterfaceTecnico();
        
        //Adiciona as opções de cadastro e exclusao
        painelEsq1.add(painelEsqBotoes, BorderLayout.SOUTH);

        btCadastrar = new JButton("Cadastrar");
        btExcluir = new JButton("Excluir");

        
        painelEsqBotoes.add(btCadastrar);
        painelEsqBotoes.add(btExcluir);

        btCadastrar.addActionListener(Administrador.cadastrarPortos(this));
        btExcluir.addActionListener(Administrador.excluirPortos(this));

        return true;
    }

}

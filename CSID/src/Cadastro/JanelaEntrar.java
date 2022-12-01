package Cadastro;

import Model.Connection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public final class JanelaEntrar implements Janela {

    //Declaração da Conexão
    public Connection conexao = new Connection();
    //Declaração de Usuário
    public Usuario usuario;

    //Declaração da Interface Gráfica
    public JFrame janela = new JFrame("CSID - Controle de Serviço de Infraestrutura de Docagem");
    public JPanel painel = new JPanel();
    public JPanel painel2 = new JPanel();
    public JLabel titulo = new JLabel("Controle de Serviço de Infraestrutura de Docagem");
    public JButton btAcessar = new JButton("Acessar");
    public JButton btCadastrar = new JButton("Sou novo aqui!");

    
    public JanelaEntrar() {
        //Construção do Usuario
        usuario = new Usuario(0, "", "", "", "", "".toCharArray(), "Com") {
            @Override
            public void exibir(Janela janela) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        //Construção da Conexão
        conexao = new Connection();

        //Formatação da Interface
        janela = new JFrame("CSID - Controle de Serviço de Infraestrutura de Docagem");

        painel = new JPanel();
        painel.setLayout(new BorderLayout());

        painel2 = new JPanel();
        painel2.setLayout(new GridLayout(0, 2));

        titulo = new JLabel("<HTML><h2>Controle de Serviço de Infraestrutura de Docagem</h2>");

        btAcessar = new JButton("Acessar!");

        btCadastrar = new JButton("Sou novo aqui!");
        exibirInterfaceComandante();
    }

    @Override
    public void limparFormulario(){
    }
    
    @Override
    public boolean exibirInterfaceComandante() {

        //Exibição da Interface Gráfica
        janela.setBounds(new Rectangle(720, 200));
        janela.setLocationRelativeTo(null);
        janela.setLayout(new FlowLayout());
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);

        painel2.add(btAcessar);
        painel2.add(btCadastrar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(painel2, BorderLayout.CENTER);

        btAcessar.addActionListener(usuario.logIn(this));
        btCadastrar.addActionListener((ActionEvent e) -> {
            new JanelaCadastrarUsuarios(usuario).exibirInterfaceExterna();
        });

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
        return true;
    }

}

package Solicitacoes;

import Cadastro.Embarcacao;
import Cadastro.Equipamento;
import Cadastro.Janela;
import Cadastro.Porto;
import Cadastro.Servico;
import Cadastro.Usuario;
import Model.Connection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Daniel
 */
public final class JanelaSolicitar implements Janela {

    //Declaração de Conexão
    public Connection conexao;
    //Declaração de Usuario
    public Usuario usuario;
    public Embarcacao embarcacao;
    public Porto porto;
    public Servico servico;
    public Equipamento equipamento;
    
    //Informações usadas
    public ArrayList<Embarcacao> embarcacoes;
    public ArrayList<Porto> portos;
    public ArrayList<Servico> servicos, meusServicos;
    public ArrayList<Equipamento> equipamentos, meusEquipamentos;

    //Declaração da Interface Gráfica
    public JFrame janela, janelaDeOpcoesServicos, janelaDeOpcoesEquipamentos;
    public JPanel painel, painel2, painel3, painelDeServicos, painelDeServicosDescricao, 
            painelDeEquipamentos, painelDeEquipamentosDescricao;
    public JLabel lbInicio, lbFim, lbEmbarcacao, lbPorto, lbServicos, lbObs, 
            lbEquipamentos;
    public JTextArea taListarEquipamentos, taListarServicos;
    public JFormattedTextField tfInicio, tfFim;
    public JComboBox comboEmbarcacao, comboPorto;
    public JTextField tfObs;
    public JButton btAddServico, enviar, btAddEquipamento;
    public JScrollPane scrollServicos, scrollEquipamentos;
    public MaskFormatter mf, mf2;

    public JanelaSolicitar(Usuario usuario) {

        //Construção de Conexão
        conexao = new Connection();
        conexao.conectar();

        //Construção de Usuario (Já criado na janela anterior)
        this.usuario = usuario;
        embarcacao = new Embarcacao("", "", "");
        porto = new Porto("", "", 0, 0, "" ,"" , "", 0, "", "", "");
        servico = new Servico("", "", "",0);
        equipamento = new Equipamento("", "", 0, 0);
        
        embarcacoes = new ArrayList<>();
        portos = new ArrayList<>();
        servicos = new ArrayList<>();
        equipamentos = new ArrayList<>();
        meusServicos = new ArrayList<>();
        meusEquipamentos = new ArrayList<>();


        //Inicia Janela de Serviços
        janelaDeOpcoesServicos = new JFrame("Adicionar Serviços");
        scrollServicos = new JScrollPane();
        painelDeServicos = new JPanel(new GridLayout(0,1));
        painelDeServicosDescricao = new JPanel( new GridLayout(0,1));
        scrollServicos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Inicia Janela de Equipamentos
        janelaDeOpcoesEquipamentos = new JFrame("Adicionar Equipamentos");
        scrollEquipamentos = new JScrollPane();
        painelDeEquipamentos = new JPanel(new GridLayout(0,1));
        painelDeEquipamentosDescricao = new JPanel( new GridLayout(0,1));
        scrollEquipamentos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Inicia Janela Solicitar
        janela = new JFrame("Solicitar Novo Serviço");

        painel = new JPanel();
        painel2 = new JPanel();
        painel3 = new JPanel();

        painel = new JPanel();

        painel2 = new JPanel(new GridLayout(0, 2));

        painel3 = new JPanel(new GridLayout(0, 1));

        lbInicio = new JLabel("Inicio de Docagem");
        tfInicio = new JFormattedTextField();
        tfInicio.addKeyListener(dataListener(tfInicio));

        lbFim = new JLabel("Fim de Docagem");
        tfFim = new JFormattedTextField();
        tfFim.addKeyListener(dataListener(tfFim));
        
        try {
            mf = new MaskFormatter("##/##/####");
            mf2 = new MaskFormatter("##/##/####");
            mf.install(tfInicio);
            mf2.install(tfFim);
        } catch (ParseException ex) {
            System.err.println("Exceção em JanelaSolicitar.JanelaSolicitar(). MaskFormatter de datas. " + ex);
        }

        
        lbEmbarcacao = new JLabel("Embarcação:");
        comboEmbarcacao = new JComboBox();
        buscarEmbarcacoes();

        lbPorto = new JLabel("Porto:");
        comboPorto = new JComboBox();
        buscarPortos();

        buscarServicos();
        criarJanelaServicos();
        lbServicos = new JLabel("Serviços:");
        btAddServico = new JButton("Adicionar Serviço");
        btAddServico.addActionListener((e) -> { janelaDeOpcoesServicos.setVisible(true); });
        taListarServicos = new JTextArea();
        taListarServicos.setLineWrap(true);
        taListarServicos.setEditable(false);

        buscarEquipamentos();
        criarJanelaEquipamentos();
        lbEquipamentos = new JLabel("Equipamentos:");
        btAddEquipamento = new JButton("Adicionar Equipamentos");
        btAddEquipamento.addActionListener((e) -> { janelaDeOpcoesEquipamentos.setVisible(true); });
        taListarEquipamentos = new JTextArea();
        taListarEquipamentos.setLineWrap(true);
        taListarEquipamentos.setEditable(false);

        
        lbObs = new JLabel("Observações:");
        tfObs = new JTextField();
        tfObs.addKeyListener(listener(tfObs, 200));
        tfObs.setBorder(new BasicBorders.FieldBorder(Color.gray, Color.white, Color.gray, Color.white));


        enviar = new JButton("Solicitar");
        enviar.addActionListener(this.usuario.solicitar(this));


        conexao.desconectar();
    }

    
    public void buscarEmbarcacoes() {
        //Busca Embarcações
        ResultSet rs = embarcacao.select(conexao, embarcacao);
        try {
            //Se houver ao menos um adiciona cada item ao ArrayList e ComboBox correspondente
            if (rs.next()) {
                do {
                    embarcacoes.add(new Embarcacao(rs.getString("id"), rs.getString("nome"), rs.getString("numero")) );
                    comboEmbarcacao.addItem("ID: " + rs.getString("id") + " | " + embarcacoes.get(rs.getRow()-1).nome);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.err.println("Exceção em JanelaSolicitar.buscarEmbarcacoes()" + ex);
        }
    }

    public void buscarPortos() {
        //Busca Portos
        ResultSet rs = porto.select(conexao, porto);
        try {
            //Se houver ao menos um adiciona cada item ao ArrayList e ComboBox correspondente
            if (rs.next()) {
                do {
                    portos.add(new Porto(rs.getString("id"), rs.getString("nome"), rs.getInt("ddi"), 
                            rs.getInt("ddd"), rs.getString("telefone"), rs.getString("email"),  rs.getString("rua"), 
                            rs.getInt("numero"),rs.getString("cidade"),rs.getString("estado"),
                    rs.getString("pais")) );
                    comboPorto.addItem("ID: " + rs.getString("id") + " | " + portos.get(rs.getRow()-1).nome );
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.err.println("Exceção em JanelaSolicitar.buscarPortos()" + ex);
        }
    }
    
    public void buscarServicos() {
        //Busca Serviços
        ResultSet rs = servico.select(conexao, servico);
        try {
            //Se houver ao menos um adiciona cada item ao ArrayList
            if (rs.next()) {
                do {
                    Servico servico = new Servico(rs.getString("id"), rs.getString("nome"), rs.getString("descricao"),0);
                    servicos.add(servico);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.err.println("Exceção em JanelaSolicitar.buscarPortos()" + ex);
        }
    }

    private void buscarEquipamentos() {
        //Busca Serviços
        ResultSet rs = equipamento.selectDisponiveis(conexao, equipamento);
        try {
            //Se houver ao menos um adiciona cada item ao ArrayList
            if (rs.next()) {
                do {
                    Equipamento equipamento = new Equipamento (rs.getString("id"),
                        rs.getString("nome"), rs.getInt("quantidade") ,0);
                    equipamentos.add(equipamento);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.err.println("Exceção em JanelaSolicitar.buscarEquipamentos()" + ex);
        }
    }
    
    public void criarJanelaServicos(){
        janelaDeOpcoesServicos.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        janelaDeOpcoesServicos.setLayout(new GridLayout(0,1));
        janelaDeOpcoesServicos.setSize(520, 500);
        janelaDeOpcoesServicos.setLocationRelativeTo(null);
        janelaDeOpcoesServicos.setResizable(false);
        
        for (Servico service : servicos) {
            JButton btServico = new JButton(service.nome);//nome
            JButton btAdicionarServico = new JButton("Adicionar");
            painelDeServicos.add(btServico);
            btServico.addActionListener((e) -> {
                painelDeServicosDescricao.setVisible(false);

                painelDeServicosDescricao.removeAll();
                painelDeServicosDescricao.add(new JLabel("id: " + service.id)); //id
                painelDeServicosDescricao.add(new JLabel(service.nome));//nome
                painelDeServicosDescricao.add(new JLabel(service.descricao));//descricao
                painelDeServicosDescricao.add(btAdicionarServico);
                
                painelDeServicosDescricao.setVisible(true);
            });
               btAdicionarServico.addActionListener((ev) -> {
                adicionarRemoverItem(service, meusServicos, btAdicionarServico);
                
                String s = "";
                for(Servico servico : meusServicos){
                    s += "id: "+servico.id + ". " + servico.nome + ".\n";
                }
                taListarServicos.setText( s );
            });

            scrollServicos.setViewportView(painelDeServicos);
            
            janelaDeOpcoesServicos.add(scrollServicos);
            janelaDeOpcoesServicos.add(painelDeServicosDescricao);
        }
    }
    
    public void criarJanelaEquipamentos(){
        janelaDeOpcoesEquipamentos.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        janelaDeOpcoesEquipamentos.setLayout(new GridLayout(0,1));
        janelaDeOpcoesEquipamentos.setSize(520, 500);
        janelaDeOpcoesEquipamentos.setLocationRelativeTo(null);
        janelaDeOpcoesEquipamentos.setResizable(false);
        
        for (Equipamento equipment : equipamentos) {
            JButton btEquipamento = new JButton(equipment.nome);//nome
            JButton btAdicionarEquipamento = new JButton("Adicionar");
            JComboBox cbQuantidade = new JComboBox();
            
            for (int i = 0; i <= equipment.quantidade; i++) {
                cbQuantidade.addItem(i);
            }
            
            painelDeEquipamentos.add(btEquipamento);
            btEquipamento.addActionListener((e) -> {
                painelDeEquipamentosDescricao.setVisible(false);

                painelDeEquipamentosDescricao.removeAll();
                painelDeEquipamentosDescricao.add(new JLabel("id: " + equipment.id));//id
                painelDeEquipamentosDescricao.add(new JLabel(equipment.nome));//nome
                painelDeEquipamentosDescricao.add(cbQuantidade);//quantidade
                painelDeEquipamentosDescricao.add(btAdicionarEquipamento);
                
                painelDeEquipamentosDescricao.setVisible(true);
            });
               btAdicionarEquipamento.addActionListener((ev) -> {
                   equipment.quantidade = cbQuantidade.getSelectedIndex();
                adicionarRemoverItem(equipment, meusEquipamentos, btAdicionarEquipamento);
                
                String s = "";
                for(Equipamento equipamento : meusEquipamentos){
                    s += "id: " + equipamento.id + ". " + equipamento.nome + ". " + equipamento.quantidade + " un.\n";
                }
                taListarEquipamentos.setText( s );
            });

            scrollEquipamentos.setViewportView(painelDeEquipamentos);
            
            janelaDeOpcoesEquipamentos.add(scrollEquipamentos);
            janelaDeOpcoesEquipamentos.add(painelDeEquipamentosDescricao);
        }
    }

    public void adicionarRemoverItem(Object item, ArrayList array, JButton botao) {
            
            if (array.contains(item)) {
                array.remove(item);
                botao.setText("Adicionar");
            } else {
                array.add(item);
                botao.setText("Remover");
            }

    }

    public static KeyListener listener(JTextField tf, int tamanho) {
        return new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.valueOf(e.getKeyChar()).toString().equals("'")
                        || Character.valueOf(e.getKeyChar()).toString().equals("\"")) {
                    e.consume();
                }
                if (tf.getText().length() >= tamanho) {
                    e.consume();
                    String texto = tf.getText().substring(0, tamanho);
                    tf.setText(texto);
                }
                
            }
        };
    }

    public static KeyAdapter dataListener(JTextField tf){
        return new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (String.valueOf(e.getKeyChar()).equals("1") || String.valueOf(e.getKeyChar()).equals("2") ||
                        String.valueOf(e.getKeyChar()).equals("3") ||String.valueOf(e.getKeyChar()).equals("4") ||
                        String.valueOf(e.getKeyChar()).equals("5") ||String.valueOf(e.getKeyChar()).equals("6") ||
                        String.valueOf(e.getKeyChar()).equals("7") ||String.valueOf(e.getKeyChar()).equals("8") ||
                        String.valueOf(e.getKeyChar()).equals("9") ||String.valueOf(e.getKeyChar()).equals("0")) {
                }else{
                    e.consume();
                }
                    
            }
        };
    }
    
    @Override
    public boolean exibirInterfaceComandante() {
        janela.setVisible(false);
        //Exibe todo mundo
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setSize(500, 700);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);

        painel.setLayout(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Exibição da Interface Gráfica
        painel.add(painel2, BorderLayout.NORTH);
        painel.add(painel3, BorderLayout.CENTER);
        

        painel2.add(lbInicio);
        painel2.add(tfInicio);

        painel2.add(lbFim);
        painel2.add(tfFim);
        

        painel2.add(lbEmbarcacao);
        painel2.add(comboEmbarcacao);

        painel2.add(lbPorto);
        painel2.add(comboPorto);

        painel3.add(lbServicos);
        painel3.add(btAddServico);
        painel3.add(taListarServicos);

        painel3.add(lbEquipamentos);
        painel3.add(btAddEquipamento);
        painel3.add(taListarEquipamentos);

        painel3.add(lbObs);
        painel3.add(tfObs);

        painel.add(enviar, BorderLayout.SOUTH);

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

    @Override
    public void limparFormulario() {
    }

}

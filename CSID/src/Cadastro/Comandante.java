package Cadastro;
/**
 *
 * @author Daniel
 */
public class Comandante extends Usuario {

    public Comandante(int id, String nome, String sobrenome, String email, String usuario, char[] senha, String cargo) {
        super(id, nome, sobrenome, email, usuario, senha, cargo);
    }

    @Override
    public void exibir(Janela janela) {
        janela.exibirInterfaceComandante();

    }
}

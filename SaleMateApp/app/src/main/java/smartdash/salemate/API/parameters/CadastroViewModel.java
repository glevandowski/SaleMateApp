package smartdash.salemate.API.parameters;

public class CadastroViewModel {
    public String Senha;
    public String Email;
    public String Nome;
    public String CPF;
    public String Telefone;
    public String DataNascimento;


    public CadastroViewModel(String senha,String nome,String email, String CPF, String telefone, String dataNascimento) {
        Senha = senha;
        Email = email;
        Nome = nome;
        this.CPF = CPF;
        Telefone = telefone;
        DataNascimento = dataNascimento;
    }
}

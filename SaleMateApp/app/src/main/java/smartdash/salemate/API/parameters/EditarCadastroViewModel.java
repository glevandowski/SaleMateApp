package smartdash.salemate.API.parameters;

public class EditarCadastroViewModel {

    public String Nome;
    public String Email;
    public String Telefone;
    public String DataNascimento;

    public EditarCadastroViewModel(String nome, String Email, String Telefone, String DataNascimento) {
        this.Nome = nome;
        this.Email = Email;
        this.Telefone = Telefone;
        this.DataNascimento = DataNascimento;
    }

}

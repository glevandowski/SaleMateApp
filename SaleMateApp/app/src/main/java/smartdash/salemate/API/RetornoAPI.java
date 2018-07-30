package smartdash.salemate.API;

import java.util.ArrayList;
import java.util.List;


public class RetornoAPI<T> {
    public T Dados;
    public String Mensagem;
    public boolean Sucesso;
    public List<ValidacaoViewModel> ErrosValidacao=new ArrayList<>();

    public String getErrosValidacao() {
        String messageRetorno = "";
        if(ErrosValidacao.size()==0){
            return Mensagem;
        }
        for (ValidacaoViewModel message : ErrosValidacao) {
            messageRetorno += " - " + message.Mensagem + System.getProperty("line.separator");
        }
        return messageRetorno;
    }
}

class ValidacaoViewModel {
    public String Campo;
    public String Mensagem;
}
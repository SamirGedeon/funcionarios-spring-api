package br.com.estudo.funcionariosapi;


public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(Long id) {
        super("Recurso " + id + " não encontrado");
    }
}

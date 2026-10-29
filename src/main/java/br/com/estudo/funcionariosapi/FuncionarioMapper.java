package br.com.estudo.funcionariosapi;

public final class FuncionarioMapper {
    private FuncionarioMapper() {}
    public static Funcionario toEntity(FuncionarioRequest r, Long id) { return new Funcionario(id, r.nome().strip(), r.cargo().strip(), r.salario()); }
    public static FuncionarioResponse toResponse(Funcionario item) { return new FuncionarioResponse(item.getId(), item.getNome(), item.getCargo(), item.getSalario()); }
}

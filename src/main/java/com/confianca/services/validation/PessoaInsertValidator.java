package com.confianca.services.validation;

import com.confianca.domain.Usuario;
import com.confianca.domain.enums.TipoPessoa;
import com.confianca.repositories.UsuarioRepository;
import com.confianca.resources.exeptions.FieldMessage;
import com.confianca.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PessoaInsertValidator implements ConstraintValidator<PessoaInsert, Usuario> {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public void initialize(PessoaInsert ann) {
    }

    @Override
    public boolean isValid(Usuario objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoPessoa.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getTipo())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
        }

        if(objDto.getTipo().equals(TipoPessoa.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getTipo())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
        }

        Usuario aux = repo.findByLogin(objDto.getLogin());
        if(aux != null){
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}

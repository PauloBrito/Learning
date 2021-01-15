package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private Titulos titulos;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		modelView.addObject(new Titulo());
		return modelView;
	}

	@RequestMapping
	public ModelAndView pesquisar() {
		ModelAndView modelView = new ModelAndView("PesquisaTitulo");
		List<Titulo> todosTitulos = titulos.findAll();
		modelView.addObject("todosTitulos", todosTitulos);
		return modelView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors) {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		if(errors.hasErrors()) {
			return modelView;
		}

		// TODO salvar no banco de dados
		titulos.save(titulo);
		modelView.addObject("mensagem", "Salvo com sucesso!");
		return modelView;
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> StatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

}

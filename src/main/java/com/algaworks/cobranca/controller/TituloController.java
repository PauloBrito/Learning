package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		return modelView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		// TODO salvar no banco de dados	
		titulos.save(titulo);
		modelView.addObject("mensagem", "Salvo com sucesso!");
		return modelView;
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> StatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	

}
